package model;

import java.io.Serializable;

/**
 * This interface contains all getters and some minimum method for a limited
 * communication with the controller. With this solution encapsulation is
 * preserved.
 * 
 * @author Rossolini Andrea
 *
 */
public interface PlayerInfo extends Serializable {
	/**
	 * Returns player name.
	 * 
	 * @return player name
	 */
	String getName();

	/**
	 * Returns the capital in money.
	 * 
	 * @return Player's money
	 */
	Integer getMoney();

	/**
	 * Returns a set of all player's properties.
	 * 
	 * @return the properties of the player
	 */
	// set<Properties> getProperties();

	/**
	 * Returns the number of all houses of the player.
	 * 
	 * @return player's houses number
	 */
	Integer getHouseNumber();

	/**
	 * Return the number of all all hotels of the player.
	 * 
	 * @return player's hotels number
	 */
	Integer getHotelNumber();

	/**
	 * Returns the position of the player inside the boerd game.
	 * 
	 * @return Position
	 */
	Integer getPosition();
	//se esistesse una classe tile (o simile) sarebbe: Tile getPosition();

	/**
	 * Returns the avatar of the player.
	 * 
	 * @return player's avatar
	 */
	// icon getSprite();

	/**
	 * Checks if the player is in jail.
	 * 
	 * @return true if player is in jail, false instead
	 */
	boolean isInJail();
}
