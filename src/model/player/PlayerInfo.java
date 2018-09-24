package model.player;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import model.tiles.*;
import utilities.enumerations.Color;

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
	 * Identify the status of the player (if he/she in prison or not) 
	 */
	enum Prison {
		PRISON, NOT_PRISON
	}

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
	int getMoney();

	/**
	 * Returns a set of all player's properties.
	 * 
	 * @return the properties of the player
	 */
	List<Obtainable> getProperties();

	/**
	 * Calculate the total of the player's assets (all the properties's mortgages
	 * plus his money)
	 * 
	 * @return player's total assets
	 */
	int totalAssets();

	/**
	 * Returns the number of all houses of the player.
	 * 
	 * @return player's houses number
	 */
	int getHouseNumber();

	/**
	 * Return the number of all all hotels of the player.
	 * 
	 * @return player's hotels number
	 */
	int getHotelNumber();

	/**
	 * Returns the position of the player inside the boerd game.
	 * 
	 * @return Position
	 */
	int getPosition();

	/**
	 * Checks if the player is in jail.
	 * 
	 * @return true if player is in jail, false instead
	 */
	boolean isInJail();

	/**
	 * Check if the can pay an amount of money
	 * 
	 * @return true if he/she can pay, false instead
	 */
	boolean canPay(int moneyAmount);

	/**
	 * Return all the properties of the player collected by their belonging color.
	 * 
	 * @return map that have for each color the list of properties owned by the
	 *         player
	 */
	Map<Color, List<Obtainable>> getPopertiesByColor();

	/**
	 * Getter for the path of the player's icon.
	 * 
	 * @return the String of the path where is located the icon's path
	 */
	String getIconPath();
}
