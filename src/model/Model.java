package model;

import java.util.List;
import java.util.Set;

import com.google.common.base.Optional;

import model.player.Player;
import model.player.PlayerInfo;
import model.tiles.Obtainable;
import model.tiles.Tile;
import utilities.Pair;

public interface Model {

	/**
	 * Getter for the player in turn.
	 * 
	 * @return the player who is currently playing
	 */
	PlayerInfo getCurrentPlayer();

	/**
	 * Getter for the player.
	 * 
	 * @return the list of all player in game
	 */
	List<PlayerInfo> getPlayers();

	/**
	 * remove a player out of the game.
	 */
	void removePlayer(PlayerInfo player);

	/**
	 * Save the current game.
	 */
	void saveGame();

	/**
	 * Execute the dice throws, whereas if the player makes double dice he/she rolls
	 * again, but if this happens three times in a row he/she will go to jail.
	 * 
	 * @return game's dice result
	 */
	Pair<Integer> exitDice();

	/**
	 * Getter for properties.
	 * 
	 * @return the properties of the game
	 */
	Set<Obtainable> getProperties();

	/**
	 * Getter for the board.
	 * 
	 * @return the game board
	 */
	Set<Tile> getBoard();

	/**
	 * Move the current player of <b>value</b> positions.
	 * 
	 * @param value
	 */
	void movement(final int value);

	/**
	 * End the current player's turn and shift the player list.
	 */
	void endTurn();

	/**
	 * Allows the current player to exit from jail.
	 * 
	 * @param true if the player have to pay a fee, false instead
	 */
	void exitFromJail(boolean withFee);

	/**
	 * Allows the current player to go to jail.
	 */
	void goToJail();

	void executeTrade(Player secondPlayer, int firstMoney, int secondMoney, List<Obtainable> firstProperties,
			List<Obtainable> secondProperties);

	void unbuild(Obtainable property, Player player);

	boolean playerPayment(PlayerInfo player, int moneyAmount);

	void playerGainMoney(PlayerInfo player, int moneyAmount);

	void buyProperty(PlayerInfo player, Obtainable property);

	void playerAddProperty(PlayerInfo player, Obtainable property);
	
	Optional<ConsequencesImpl> supplierConsequence();
	
	Tile getTileOf(int position);

}
