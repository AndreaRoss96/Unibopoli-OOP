package controller;

import java.io.File;

public interface Controller {

	/**
	 * Initialization of the game
	 */
	void gameInit(); //probabilmente in ingresso vorrà un Map<String, Image> -> nome giocatore e avatar, tutti devono avere un avatar, i nomi devonon essere tutti diversi

	/**
	 * allows to save the game.
	 */
	void saveGame();

	/**
	 * To terminate the current player's turn
	 */
	void endTurn(); // andrà a chiamare la classe nextPlayer del model

	/**
	 * Allows to load an old game.
	 * 
	 * @param file
	 */
	void loadGameFromFile(File file);

	/**
	 * Shows the "card dialog" of a specific property
	 * 
	 * @param contractName
	 */
	void showContract(String contractName, String currentPlayer); //il cardDialog dovrà controllare che currPl e proprietario siano gli stessi per utilizzare i bottoni

	/**
	 * Shows the "trade dialog" that allows the player to make trading
	 */
	void tradeClick();

	/**
	 * The player throws dices.
	 */
	void diceClick(); // andrà ad aggiornare i label presenti nella parte destra della schermata di gioco

	/**
	 * Shows settings pane.
	 */
	void settingsClick(); // da implementare

}
