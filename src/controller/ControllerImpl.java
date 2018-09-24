package controller;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.google.common.base.Optional;

import model.ConcrateConsequences;
import model.GameInitializer;
import model.Model;
import model.ResourceManager;
import model.exceptions.NotEnoughMoneyException;
import model.player.PlayerInfo;
import model.tiles.Obtainable;
import model.tiles.Tile;
import utilities.Pair;
import utilities.enumerations.ClassicType;
import utilities.enumerations.ModeGame;
import view.View;
import view.gameDialog.AuctionDialog;
import view.gameDialog.CardDialog;
import view.gameDialog.MortgageDialog;
import view.gameDialog.SettingsDialog;
import view.gameDialog.TradeDialog;
import view.gameSetup.MainMenu;

public class ControllerImpl implements Controller {

	private static final ControllerImpl SINGLETON = new ControllerImpl();

	private Model model;
	private View view;
	private final SoundController sound;

	private ControllerImpl() {
		this.sound = new SoundController(ClassicType.Music.GeneralMusicMap.getMonopolyMainMusic());
		setBackgroundMusic();
	}

	/**
	 * Method that return the only one instance of Controller.
	 * 
	 * @return instance of Controller.
	 */
	public static ControllerImpl getController() {
		return SINGLETON;
	}

	private void setBackgroundMusic() {
		MainMenu.setBeckgroundMusic(sound);
	}

	@Override
	public void setView(final View view) {
		this.view = view;
	}

	@Override
	public void newGameInit(final String mode, final List<String> playersName, final List<String> playersIcon) {
		try {
			this.model = GameInitializer.getInstance().newGame(mode, IntStream.range(0, playersName.size()).boxed()
					.collect(Collectors.toMap(playersName::get, playersIcon::get)));
			this.setDialogContorller();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void loadGameFromFile(final File file) {
		Objects.requireNonNull(file, "NullPointerException, file required non-null.");

		this.model = GameInitializer.getInstance().loadGame(ResourceManager.getInstance().loadGameFromFile(file));
		
		this.setDialogContorller();
	}

	private void setDialogContorller() {
		DialogController.getDialogController().setDialogController(model, sound, view);
	}

	@Override
	public void saveGame() {
		this.model.saveGame();
	}

	@Override
	public void endTurnClick() {
		if (this.getCurrentPlayer().isInJail()) {
			model.exitFromJail(true);
		} else {
			this.model.endTurn();
		}

		this.updateView(true);
	}

	@Override
	public void showContract(Obtainable property) {
		/*
		 * If the current player is the same owner of the selected property and he/she
		 * have all the property of that color he will be able to build
		 */
		if (property.getOwner().isPresent() && property.getOwner().get().equals(this.getCurrentPlayer().getName())) {
			int numProperties = this.getCurrentPlayer().getPopertiesByColor().get(property.getColorOf()).size();
			CardDialog.getCardDialog().createCardDialog(property, property.getColorOf().getNumTiles() == numProperties);
			/*
			 * If the current player is in the same position of the property without any
			 * owner he/she will be able to buy that property
			 */
		} else if (!property.getOwner().isPresent()
				&& property.getPosition() == this.getCurrentPlayer().getPosition()) {
			CardDialog.getCardDialog().createCardDialog(property,
					property.getPrice() <= this.getCurrentPlayer().getMoney());
		} else {
			CardDialog.getCardDialog().createCardDialog(property, false);
		}
	}

	@Override
	public void startTrade() {
		final Map<String, List<Obtainable>> playerObtainableMap = new HashMap<>();
		this.model.getPlayers().stream().filter(player -> !player.getName().equals(this.getCurrentPlayer().getName()))
				.forEach(player -> {
					playerObtainableMap.put(player.getName(), player.getProperties());
				});
		TradeDialog.getTradeDialog().createTradeDialog(playerObtainableMap);
	}

	@Override
	public void diceClick() {
		final boolean doJailSound = !model.getCurrentPlayer().isInJail();
		this.sound.playSound(ClassicType.Music.GeneralMusicMap.getDiceRoll());
		final Pair<Integer> result = model.exitDice();
		this.view.updateDiceLabel(result.getFirst(), result.getSecond());
		if (result.getFirst() == 0 && result.getSecond() == 0) {
			model.endTurn();
		} else {
			this.exitDice(result.getFirst() + result.getSecond());
		/*
		 * if the two dice have same result the player have to roll dices again, even if
		 * you are going out of jail
		 */
		this.view.updateButton(!(result.areSame()) && result.getFirst() != 0);
		if (this.model.getCurrentPlayer().isInJail()) {
			if (doJailSound) {
				this.sound.playSound(ClassicType.Music.GeneralMusicMap.getJailDoorEffect());
			}

			this.model.endTurn();
			this.updateView(true);
		}
		}
	}

	public void playerPayments(final PlayerInfo player, final int moneyAmount) {
		try {
			if (!model.playerPayment(player, moneyAmount)) {
				this.startMortgage(moneyAmount, player);
			}
		} catch (NotEnoughMoneyException e) {
			this.sound.playSound(ClassicType.Music.GeneralMusicMap.getLoseGame());
			this.view.createInformationAlert("D:", "I'm sorry " + player.getName()
					+ " you lost everything\nyou are out of the game...\n(all your properties will be auctioned!)");
			player.getProperties().forEach(prop -> {
				prop.setOwner(Optional.absent());
				this.startAuciton(prop);
			});

			this.view.removePlayer(player.getName());
			this.model.removePlayer(player);
		}
	}

	private Optional<String> execConsequence() {
		if (!this.model.supplierConsequence().isPresent()) {
			Obtainable tmpPtoperty = (Obtainable) this.model.getTileOf(this.getCurrentPlayer().getPosition());

			this.showContract(tmpPtoperty);

			if (!tmpPtoperty.getOwner().isPresent()) {
				this.startAuciton(tmpPtoperty);
			}
		} else {
			ConcrateConsequences consequence = this.model.supplierConsequence().get();

			Tile tile = this.model.getTileOf(this.getCurrentPlayer().getPosition());

			tile.setConsequence(consequence);
			tile.doConsequence();
			this.updateView(false);
			return Optional.of(consequence.getTextConsequence());
		}

		this.updateView(false);

		return Optional.absent();
	}

	public void exitDice(final int value) {
		if (!this.getCurrentPlayer().isInJail() || !this.view.isInJail(this.getCurrentPlayer().getName())) {
			this.view.movement(value);
		}

		this.model.movement(value);

		Optional<String> cardEffect = this.execConsequence();

		if (cardEffect.isPresent() && !cardEffect.get().equals("")) {
			this.view.createCardConsequencePane(cardEffect.get());
		}
	}

	@Override
	public void goToJail() {
		this.view.goToJail();

		this.model.goToJail();
	}

	@Override
	public void settingsClick() {
		SettingsDialog.getSettingsDialog().createSettingDialog(this.sound);
	}

	@Override
	public void updateView(boolean isTurnEnded) {
		if (isTurnEnded) {
			this.view.updateButton(!isTurnEnded);
		}

		this.view.updateLabels();
	}

	@Override
	public void endGame() {
		this.sound.playSound(ClassicType.Music.GeneralMusicMap.getGameWin());
		this.view.createInformationAlert("Congratulations!",
				this.getCurrentPlayer() + " is the winner!\n\nClick OK to exit the game.");
		System.exit(0);
	}

	@Override
	public void startAuciton(Obtainable property) {
		AuctionDialog.getAuctionDialog().createAuctionDialog(property);
	}

	@Override
	public void startMortgage(int minimumExpense, PlayerInfo player) {
		this.view.createInformationAlert("Warnings!", "You have to mortgage some properties\nto afford this payment.");
		MortgageDialog.getMortgageDialog().createMortgageDialog(minimumExpense, player);
		this.playerPayments(player, minimumExpense);
	}

	@Override
	public List<PlayerInfo> getPlayers() {
		return this.model.getPlayers();
	}

	@Override
	public List<String> getGameMode() {
		return Arrays.asList(ModeGame.values()).stream().map(t -> String.valueOf(t)).collect(Collectors.toList());
	}

	@Override
	public Set<Tile> getGameBoard() {
		return this.model.getBoard();
	}

	public PlayerInfo getCurrentPlayer() {
		return this.model.getCurrentPlayer();
	}
}
