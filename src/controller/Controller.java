package controller;

import java.io.File;
import java.util.List;
import java.util.Set;

import model.player.PlayerInfo;
import model.tiles.Obtainable;
import model.tiles.Tile;
import view.View;

public interface Controller {

	/**
	 * Initialization of the game
	 * 
	 * @param mode
	 *            the mode of the game
	 * @param playersName
	 *            the name of all players
	 * @param playersIcon
	 *            the list of the path of all icon of the players
	 */
	void newGameInit(final String mode, final List<String> playersName, final List<String> playersIcon);

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
	 *            the file to be load
	 */
	void loadGameFromFile(File file);

	/**
	 * Shows the "card dialog" of a specific property
	 * 
	 * @param property
	 *            property selected by the player
	 */
	void showContract(Obtainable property);

	/**
	 * Shows the "trade dialog" that allows the player to make trading
	 */
	void startTrade();

	/**
	 * The player throws dices.
	 */
	void diceClick();

	/**
	 * Shows settings pane.
	 */
	void settingsClick();

	/**
	 * End game and close it.
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
	 * Get all players in game.
	 */
	List<PlayerInfo> getPlayers();

	/**
	 * Get the game mode.
	 */
	List<String> getGameMode();

	/**
	 * Get the board of the game.
	 */
	Set<Tile> getGameBoard();

	/**
	 * Update the information in the right panel, like money, player, dice ecc...
	 * 
	 * @param isTurnEnded
	 *            True if the update interest dice, false instead.
	 */
	void updateView(boolean isTurnEnded);
	
	void setView(final View view);
}
