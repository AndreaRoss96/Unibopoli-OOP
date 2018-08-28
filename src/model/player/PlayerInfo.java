package model.player;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import model.Icon;
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
	
	enum Prison { PRISON, NOT_PRISON }
	
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
	 * Returns a set of all player's mortgaged properties.
	 * 
	 * @return the mortgaged properties of the player
	 */
	List<Obtainable> getMortgagedProperties();
	
	/**
	 * Calculate the total of the player's assets
	 * (all the properties's mortgages plus his money)
	 * 
	 * @return player's total assets
	 */
	Integer totalAssets();

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
	
	/**
	 * Check if the can pay an amount of money
	 * 
	 * @return true if he/she can pay, false instead
	 */
	boolean canPay(Integer moneyAmount);

	/**
	 * Return all the properties of the player collected by their belonging color.
	 * 
	 * @return map that have for each color the list of properties owned by the
	 *         player
	 */
	Map<Color, List<Obtainable>> getPopertiesByColor();
	
	
	Icon getIcon();
}
