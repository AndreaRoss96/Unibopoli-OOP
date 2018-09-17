package controller;

import java.io.File;
import java.util.List;
import java.util.Set;

import model.player.PlayerInfo;
import model.tiles.Obtainable;
import model.tiles.Tile;

public interface Controller {

	/**
	 * Initialization of the game
	 */
	void newGameInit(final String mode, final List<String> playersName, final List<String> playersIcon); // probabilmente in ingresso vorrà un
																			// Map<String, Image> -> nome giocatore e
																			// avatar, tutti devono avere un avatar, i
																			// nomi devonon essere tutti diversi

	/**
	 * allows to save the game.
	 */
	void saveGame();

	/**
	 * To terminate the current player's turn
	 */
	void endTurnClick();

	/**
	 * Allows to load an old game.
	 * 
	 * @param file
	 */
	void loadGameFromFile(File file);

	/**
	 * Shows the "card dialog" of a specific property
	 * 
	 * @param property
	 *            property selected by the player
	 */
	void showContract(Obtainable property); // il cardDialog dovrà controllare che currPl e proprietario siano gli
											// stessi per utilizzare i bottoni

	/**
	 * Shows the "trade dialog" that allows the player to make trading
	 */
	void startTrade();

	/**
	 * The player throws dices.
	 */
	void diceClick(); // andrà ad aggiornare i label presenti nella parte destra della schermata di
						// gioco

	/**
	 * Shows settings pane.
	 */
	void settingsClick(); // da implementare

	/**
	 * TODO: aggiungere documetazione 
	 */
	void endGame();

	/**
	 * Shows the "AuctionDialog" for start an auction for a determinated property.
	 * 
	 * @param property
	 *            the property put up for auction
	 */
	void startAuciton(Obtainable property);

	/**
	 * Show the "MortgageDialog" that obliges a determinated player who can't pay
	 * something to mortgage his/her properties to reach the required amount.
	 * 
	 * @param minimumExpense
	 *            minimum amount of money to be reach
	 * @param player
	 *            the player that needs money
	 */
	void startMortgage(int minimumExpense, PlayerInfo player);
	
	/**
	 * TODO: aggiungere documetazione 
	 */
	List<PlayerInfo> getPlayers();
	
	/**
	 * TODO: aggiungere documetazione 
	 */
	List<String> getGameMode();
	
	/**
	 * TODO: aggiungere documetazione 
	 */
	List<Obtainable> getProperties();
	
	/**
	 * TODO: aggiungere documetazione 
	 */
	Set<Tile> getGameBoard();

	/**
	 * TODO: aggiungere documetazione 
	 */
	void updateView();

	/**
	 * TODO: aggiungere documetazione 
	 */
	SoundController getSound();
}
