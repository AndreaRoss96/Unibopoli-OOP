package model.player;

import model.tiles.*;

import java.util.List;

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
	void payments(Integer moneyAmount);

	/**
	 * This method allows the player to earn some money.
	 * 
	 * @param Integer
	 *            sum of money.
	 */
	void gainMoney(Integer moneyAmount);

	/**
	 * this method allows the player to buy a property.
	 * 
	 * @param Integer
	 *            price of the property.
	 * @param String
	 *            name of property.
	 */
	void buyProperty(ObtainableImpl property);

	/**
	 * this method is for the movement of the player.
	 * 
	 * @param Tile
	 *            (?) new position
	 */
	// void move(Tile position); ci sarà una classe "tile", in caso affermativo
	// basterà controllare dove si trova il tile

	/**
	 * Use this method if the player is forced to pay a rent too big
	 * 
	 * @param Integer
	 *            minimum amount of money to gain to meet any mandatory requests
	 */
	void toMortgage(Integer minimumAmount);

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
	void setPosition(Tile newPosition);

	/**
	 * It save a list of properties owned by the player but mortgaged.
	 * 
	 * @param mortgaged
	 *            the list of mortgaged properties
	 */
	void mortgageProperties(List<ObtainableImpl> mortgaged);

}
