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
	 * Allows the current player to exit from jail, if the player don't have enough
	 * money his/her money become 0.
	 * 
	 * @param true
	 *            if the player have to pay a fee, false instead
	 */
	void exitFromJail(boolean withFee);

	/**
	 * Allows the current player to go to jail.
	 */
	void goToJail();

	/**
	 * Execute the trade started by the current player.
	 * 
	 * @param secondPlayer
	 *            the second player in the trade
	 * @param firstMoney
	 *            the current's player quantity of money
	 * @param secondMoney
	 *            the second's player quantity of money
	 * @param firstProperties
	 *            the current'player selected properties
	 * @param secondProperties
	 *            the second's player selected properties
	 */
	void executeTrade(Player secondPlayer, int firstMoney, int secondMoney, List<Obtainable> firstProperties,
			List<Obtainable> secondProperties);

	/**
	 * If a property is mortgaged of it has changed the owner it have to unbuild all
	 * of his houses and all houses of its color family.
	 * 
	 * @param property
	 *            the interested property
	 * @param player
	 *            the player that have to unbuild his property
	 */
	void unbuild(Obtainable property, Player player);

	/**
	 * Allows a player to do possibly dangerous payments.
	 * 
	 * @param player
	 *            the player that have to pay
	 * @param moneyAmount
	 *            the amount of money that the player have to pay
	 * @return true if the payments is go well, false if the player don't have
	 *         enough money but enough total effort
	 * 
	 * @throws NotEnoughtMoneyException
	 *             if the player cannot pay in any way
	 */
	boolean playerPayment(PlayerInfo player, int moneyAmount);

	/**
	 * Allows a player to gain money.
	 * 
	 * @param player
	 *            the player that have to gain money
	 * @param moneyAmount
	 *            the amount of money that the player have to receive
	 */
	void playerGainMoney(PlayerInfo player, int moneyAmount);

	/**
	 * Allows a player to buy a property
	 * 
	 * @param player
	 *            the player that have to buy the property
	 * @param property
	 *            the interested property
	 */
	void buyProperty(PlayerInfo player, Obtainable property);

	/**
	 * Add a property to a player
	 * 
	 * @param player
	 *            the player that receive the property
	 * @param property
	 *            the interested property
	 */
	void playerAddProperty(PlayerInfo player, Obtainable property);

	Optional<ConcrateConsequences> supplierConsequence();

	/**
	 * Get a tile in a determinate position
	 * 
	 * @param position
	 *            the position of the tile
	 * @return the Tile in the position
	 */
	Tile getTileOf(int position);
}
