package model;

/**
 * This interface is the root of the Tile hierarchy. 
 * 
 * @author Matteo Alesiani
 */

public interface Tile {
	
	/**
	 * Return the position in the game board.
	 * 
	 * @return <tt>positionTile</tt> inside of board.
	 */
	int getPosition();
	
	/**
	 * Return the String corresponding to the name of tile.
	 * 
	 * @return <tt>name</tt> of Tile.
	 */
	String getNameOf();
	
	/**
	 * The method allows to change or initialize the name of Tile
	 * 
	 * @param The String <tt>nameTile</tt>.
	 */
	void setNameOf(final String nameTile);
}
