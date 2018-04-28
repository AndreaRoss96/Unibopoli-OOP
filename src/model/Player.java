package model;

/**
 * This interface contains the logical implementations of the player. It will
 * communicate only with the model party
 * 
 * @author Rossolini Andrea
 *
 */

public interface Player extends PlayerInfo {

	public enum Status { // metterei un package all'interno delle utilities che comprende tutte le
							// enumerazioni che facciamo
		FREE(true), JAIL(false);

		private boolean value;

		private Status(boolean value) {
			this.value = value;
		}

		public boolean getValue() {
			return this.value;
		}
	}

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
	void buyProperty(Integer propertyPrice, String propertyName);
	// void buyProperty(Property property);

	/**
	 * this method is for the movement of the player.
	 * 
	 * @param Tile
	 *            (?) new position
	 */
	// void move(Tile position); ci sarà una classe "tile", in caso affermativo
	// basterà controllare dove si trova il tile

	/**
	 * A method to mortgage the properties
	 * 
	 * @param Integer
	 *            minimum amount of money to gain to meet any mandatory requests
	 */
	void toMortgage(Integer minimumAmount);

	/**
	 * the player must go to jail
	 */
	void goToJail();

}
