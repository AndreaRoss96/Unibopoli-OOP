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

import model.GameInitializer;
import model.Model;
import model.ResourceManager;
import model.player.PlayerInfo;
import model.tiles.Obtainable;
import model.tiles.Tile;
import utilities.IconLoader;
import utilities.Pair;
import utilities.enumerations.ModeGame;
import view.AlertFactory;
import view.RightInormationPane;
import view.View;
import view.ViewImpl;
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
		this.sound = new SoundController("/music/Monopoly-MainMusic.wav");
		this.view = new ViewImpl();
		setBackgroundMusic();
		DialogController.getDialogController().setModel(model);
	}

	/**
	 * Method that return the only one instance of Controller.
	 * 
	 * @return instance of Controller.
	 */
	public static ControllerImpl getController() {
		return SINGLETON;
	}

	private void setBackgroundMusic() { //bisogna chiamarlo quando si inizializza la view, in modo da fare poter controllare il thread della musica
		MainMenu.setBeckgroundMusic(sound);
	}

	@Override
	public void newGameInit(final String mode, final List<String> playersName, final List<String> playersIcon) {
		try {
			this.model = GameInitializer.getInstance().newGame(mode, IntStream.range(0, playersName.size()).boxed().collect(Collectors.toMap(playersName::get, playersIcon::get)));
			DialogController.getDialogController().setModel(model);
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	@Override
	public void saveGame() {
		this.model.saveGame();
	}

	@Override
	public void endTurnClick() {
		if (this.getCurrentPlayer().isInJail()) {
			model.exitFromJail(true);
			this.updateView();
		} else {
			this.model.endTurn();
			this.updateView();
		}
	}

	@Override
	public void loadGameFromFile(final File file) {
		Objects.requireNonNull(file, "NullPointerException, file required non-null.");
		IconLoader.getLoader().getAvatarMap("res/mode/classic/avatars");
		this.model = GameInitializer.getInstance().loadGame(ResourceManager.getInstance().loadGameFromFile(file));
		DialogController.getDialogController().setModel(model);
	}

	@Override
	public void showContract(Obtainable property) {
		/* If the current player is the same owner of the selected property and he/she have all the property of that color he will be able to build */
		if(property.getOwner().isPresent() && property.getOwner().get() == this.getCurrentPlayer().getName()) {
			int numProperties = this.getCurrentPlayer().getPopertiesByColor().get(property.getColorOf()).size();
			CardDialog.getCardDialog().createCardDialog(property, property.getColorOf().getNumTiles() == numProperties);
		/* If the current player is in the same position of the property without any owner he/she will be able to buy that property */
		} else if(!property.getOwner().isPresent() && property.getPosition() == this.getCurrentPlayer().getPosition()) {
			CardDialog.getCardDialog().createCardDialog(property,  property.getPrice() <= this.getCurrentPlayer().getMoney());
		} else {
			CardDialog.getCardDialog().createCardDialog(property, false);
		}

	}

	@Override
	public void startTrade() {
		final Map<String, List<Obtainable>> playerObtainableMap = new HashMap<>();
		this.model.getPlayers().stream().filter(player -> !player.getName().equals(this.getCurrentPlayer().getName())).forEach(player -> {
			playerObtainableMap.put(player.getName(), player.getProperties());
		});
		TradeDialog.getTradeDialog().createTradeDialog(playerObtainableMap);
	}

	@Override
	public void diceClick() {
		final boolean doJailSound = !model.getCurrentPlayer().isInJail();
		this.sound.playSound("/music/Dice-roll.wav");
		Pair<Integer> result = model.exitDice();
		RightInormationPane.getRinghtInformationPane().updateDiceLabel(result.getFirst(), result.getSecond());
		this.exitDice(result.getFirst() + result.getSecond());
		/*
		 * if the two dice have same result the player have to roll dices again, even if
		 * you are going out of jail
		 */
		RightInormationPane.getRinghtInformationPane().updateButton(!(result.areSame()));
		if (model.getCurrentPlayer().isInJail()) {
			if(doJailSound) {
				this.sound.playSound("/music/Jail_Door_sound_effect.wav");
			}
			model.endTurn();
			this.updateView();
		}
	}

	public void exitDice(final int value) {
		view.movement(value);
		
		model.movement(value); // se il giocatore finisce in carcere non si muove ed il turno
						// finisce automaticamente		
	}
	
	@Override
	public void settingsClick() {
		SettingsDialog.getSettingsDialog().createSettingDialog();
	}
	
	@Override
	public void updateView() {
		this.view.updateLabels();
	}

	@Override
	public void endGame() {

	}
	
	public void startAuciton(Obtainable property) {
		AuctionDialog.getAuctionDialog().createAuctionDialog(property);
	}
	
	public void startMortgage (int minimumExpense, PlayerInfo player) {
		AlertFactory.createInformationAlert("Warnings!", "You have to mortgage some properties\nto afford this payment.");
		MortgageDialog.getMortgageDialog().createMortgageDialog(minimumExpense, player);
	}
	
	public PlayerInfo getCurrentPlayer() {
		return this.model.getCurrentPlayer();
	}
	
	public List<PlayerInfo> getPlayers(){
		return this.model.getPlayers();
	}
	
	public List<Obtainable> getProperties(){
		return model.getProperties().stream().collect(Collectors.toList());
	}
	
	public List<String> getGameMode() {
		return Arrays.asList(ModeGame.values()).stream().map(t -> String.valueOf(t)).collect(Collectors.toList());
	}

	@Override
	public Set<Tile> getGameBoard() {
		return this.model.getBoard();
	}
	
	@Override
	public SoundController getSound() {
		return this.sound;
	}
}
