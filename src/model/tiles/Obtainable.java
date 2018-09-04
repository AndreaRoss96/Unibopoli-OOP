package model.tiles;


import com.google.common.base.Optional;

import utilities.enumerations.Color;

/**
 * This interface extends Tile and shape the Obtainable Tile in the board.  
 * 
 * @author Matteo Alesiani 
 */

public interface Obtainable extends Tile{
	
	public enum StatusTile { MORTGAGE, NOT_MORTGAGE; }
	
	/**
	 * Return the total rent of the Tile.
	 * If the Tile is a Buildable the method return the related rent in based on how many 
	 * houses and hotel it owns.
	 * If the Tile is a NotBuildable the method return a value proportional to the number
	 * exited from the Dice. 
	 * 
	 * @return <tt>int</tt> rent of the Tile.
	 */
	int getRent();
	
	/**
	 * Return the Price to buy a Tile.
	 * 
	 * @return <tt>int</tt> price of Tile.
	 */
	int getPrice();
	
	/**
	 * Return the mortgage of the Tile, so far as the Player that have it need money to the Back.
	 * 
	 * @return <tt>int</tt> mortgage of tile.
	 */
	int getMortgage();
	
	boolean hasMortgage();
	
	void changeMortgageStatus();
	
	/**
	 * Return the Optional<X> of String corresponding to the name of the owner. 
	 * 
	 * @return Optional<String> the Tile owner's name.
	 */
	Optional<String> getOwner();
	
	/**
	 * The method set the owner's name.
	 * 
	 * @param The Optional<String> <tt>owner</tt>.
	 */
	void setOwner(final Optional<String> owner);
	
	/**
	 * Returns the type of the Color enum, that specific the family of belonging inside the board. 
	 * 
	 * @return <tt>Color</tt> of the family Tile.
	 */
	Color getColorOf();
}
