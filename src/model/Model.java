package model;

import java.util.List;
import java.util.Set;

import model.player.PlayerInfo;
import model.tiles.Obtainable;
import model.tiles.Tile;
import utilities.Pair;

public interface Model {

	/**
	 * @return the player who is currently playing.
	 */
	PlayerInfo getCurrentPlayer();

	/**
	 * @return the list of all player in game.
	 */
	List<PlayerInfo> getPlayers();

	/**
	 * remove a player out of the game.
	 */
	void removePlayer(PlayerInfo player);

	/**
	 * Saves the game.
	 */
	void saveGame();

	/**
	 * @return game's dice
	 */
	Pair<Integer, Integer> exitDice();

	Set<Obtainable> getProperties();

	Set<Tile> getBoard();

	/**
	 * this method allows the player to buy a property.
	 * 
	 * @param Integer
	 *            price of the property.
	 * @param Obtainable
	 *            the interested property
	 */
	void buyProperty(PlayerInfo player, Obtainable property);

	/**
	 * It saves a list of properties owned by the player but mortgaged.
	 * 
	 * @param mortgaged
	 *            the list of mortgaged properties
	 * @param PlayerInfo
	 *            the player who suffers the action
	 */
	void mortgageProperties(PlayerInfo player, List<Obtainable> mortgaged);

	/**
	 * Adds a property to player's properties.
	 * 
	 * @param PlayerInfo
	 *            the player who suffers the action
	 * @param property
	 *            the interested property
	 */
	void addProperty(PlayerInfo player, Obtainable property);

	/**
	 * this method allows the player to pay a certain sum of money, caused by a
	 * purchase, payment of a rent or any other type of tax.
	 * 
	 * @param PlayerInfo
	 *            the player who suffers the action
	 * @param Integer
	 *            sum of money
	 */
	void payment(PlayerInfo player, Integer moneyAmount);

	/**
	 * This method allows the player to earn some money.
	 * 
	 * @param PlayerInfo
	 *            the player who suffers the action
	 * @param Integer
	 *            sum of money
	 */
	void gainMoney(PlayerInfo player, Integer moneyAmount);

	/**
	 * This method allows the player to earn some money.
	 * 
	 * @param Integer
	 *            sum of money
	 */
	void removeProperties(PlayerInfo player, List<Obtainable> removedProperties);
}
