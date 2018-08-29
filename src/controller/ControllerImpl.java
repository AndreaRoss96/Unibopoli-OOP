package controller;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import model.GameInitializer;
import model.Model;
import model.ResourceManager;
import model.player.Player;
import model.player.PlayerInfo;
import model.tiles.Obtainable;
import utilities.Pair;
import utilities.enumerations.ModeGame;
import view.Icon;
import view.RightInormationPane;
import view.View;
import view.gameDialog.AuctionDialog;
import view.gameDialog.CardDialog;
import view.gameDialog.MortgageDialog;
import view.gameDialog.TradeDialog;
import view.gameSetup.MainMenu;

public class ControllerImpl implements Controller {

	private static final ControllerImpl SINGLETON = new ControllerImpl();
	private static final int JAIL_FEE = 125;
	
	
	private Model model;
	private View view;
	private final SoundController sound;

	private ControllerImpl() {
		this.sound = new SoundController("/music/Monopoly-MainMusic.wav");
		this.sound.play(true);
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

	private void setBackgroundMusic() { //bisogna chiamarlo quando si inizializza la view, in modo da fare poter controllare il thread della musica
		MainMenu.setBeckgroundMusic(sound);
	}

	@Override
	public void newGameInit(final String mode, final List<String> playersName, final List<String> playersIcon) {
		try {
			this.model = GameInitializer.getInstance().newGame(mode, IntStream.range(0, playersName.size()).boxed().collect(Collectors.toMap(playersName::get, playersIcon::get)));
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	@Override
	public void saveGame() {
		model.saveGame();
	}

	@Override
	public void endTurnClick() {
		if (model.getCurrentPlayer().isInJail()) {
			((Player) model.getCurrentPlayer()).payments(JAIL_FEE);
			model.exitFromJail();
			this.updateButtons();
		} else {
			this.model.endTurn();
			this.updateButtons();
		}
	}

	@Override
	public void loadGameFromFile(final File file) {
		Objects.requireNonNull(file, "NullPointerException, file required non-null.");
		this.model = GameInitializer.getInstance().loadGame(ResourceManager.getInstance().loadGameFromFile(file));
	}

	@Override
	public void showContract(Obtainable property) {
		/* If the current player is the same owner of the selected property and he/she have all the property of that color he will be able to build */
		if(property.getOwner().isPresent() || property.getOwner().get() == model.getCurrentPlayer().getName()) {
			int numProperties = model.getCurrentPlayer().getPopertiesByColor().get(property.getColorOf()).size();
			CardDialog.getCardDialog().createCardDialog(property, property.getColorOf().getNumTiles() == numProperties);
		/* If the current player is in the dame position of the property without any owner he/she will be able to buy that property */
		} else if(!property.getOwner().isPresent() && property.getPosition() == model.getCurrentPlayer().getPosition()) {
			CardDialog.getCardDialog().createCardDialog(property,  property.getPrice() <= model.getCurrentPlayer().getMoney());
		} else {
			CardDialog.getCardDialog().createCardDialog(property, false);
		}

	}

	@Override
	public void tradeClick() {
		List<PlayerInfo> treadePlayerList = model.getPlayers();
		TradeDialog.getTradeDialog()
		.createTradeDialog(treadePlayerList.remove(treadePlayerList.indexOf(model.getCurrentPlayer())), treadePlayerList); //non so se così funziona, in caso contrario basta passare al primo attributo il currentPlayer
	}

	@Override
	public void diceClick() {
		new SoundController("/music/Dice-roll.wav").play(false);
		Pair<Integer> result = model.exitDice();
		RightInormationPane.updateDiceLabel(result.getFirst(), result.getSecond());
		model.movement(result.getFirst() + result.getSecond()); // se il giocatore finisce in carcere non si muove ed il turno
														// finisce automaticamente
		/*
		 * if the two dice have same result the player have to roll dices again, even if
		 * you are going out of jail
		 */
		RightInormationPane.updateButton(!(result.areSame()));
		if (model.getCurrentPlayer().isInJail()) {
			new SoundController("/music/Jail_Door_sound_effect.wav").play(false);
			model.endTurn();
			this.updateButtons();
		}
	}

	@Override
	public void settingsClick() {
		// TODO Auto-generated method stub

	}
	
	/**
	 * 
	 * Platform.runLater();
	 * */
	private void updateButtons() {
		
		RightInormationPane.updateButton(false);
		RightInormationPane.updateLabels(model.getCurrentPlayer());
		RightInormationPane.updateJailButton(model.getCurrentPlayer().isInJail());
	}

	@Override
	public void endGame() {

	}
	
	public void startAuciton(Obtainable property) {
		AuctionDialog.getAuctionDialog().createAuctionDialog(property, model.getPlayers());
	}
	
	public void startMortgage (int minimumExpense, PlayerInfo player) {
		MortgageDialog.getMortgageDialog().createMortgageDialog(minimumExpense, player);
	}
	
	public PlayerInfo getCurruntPlayer() {
		return this.model.getCurrentPlayer();
	}
	
	public List<PlayerInfo> getPlayers(){
		return this.model.getPlayers();
	}
	
	public DialogController getDialogController() {
		return DialogController.getDialogController();
	}
	
	public List<Obtainable> getProperties(){
		return model.getProperties().stream().collect(Collectors.toList());
	}
	
	public List<String> getGameMode() {
		return Arrays.asList(ModeGame.values()).stream().map(t -> String.valueOf(t)).collect(Collectors.toList());
	}
}
