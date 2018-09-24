package model.player;

import model.tiles.*;


/**
 * This interface contains the logical implementations of the player. It will
 * communicate only with the model party
 * 
 * @author Rossolini Andrea
 *
 */

public interface Player extends PlayerInfo {

	/**
	 * this method allows the player to pay a certain sum of money, caused by a
	 * purchase, payment of a rent or any other type of tax.
	 * 
	 * @param Integer
	 *            sum of money
	 */
	void payments(int moneyAmount);

	/**
	 * This method allows the player to earn some money.
	 * 
	 * @param Integer
	 *            sum of money.
	 */
	void gainMoney(int moneyAmount);

	/**
	 * the player must go to jail
	 */
	void goToJail();

	/**
	 * the player must exit from jail
	 */
	void exitFromJail();

	/**
	 * Set the destination of the player.
	 * 
	 * @param newPosition
	 *            the new position of the player
	 */
	void setPosition(int newPosition);

	/**
	 * Adds a property to player's properties.
	 * 
	 * @param Obtainable
	 *            the interested property
	 */
	void addProperty(Obtainable property);

	/**
	 * It removes a property from the player.
	 * 
	 * @param property
	 *            the property to be removed
	 * @return the removed property
	 */
	Obtainable removeProperty(Obtainable property);
}
