package model;

import java.io.Serializable;
import java.util.Set;
import java.util.function.Predicate;

import model.tiles.Tile;

public interface Board extends Serializable {
	
	/**
	 * Return the Set of all Obtainable Tile.
	 * 
	 * @return <tt>Set<Obtainable></tt> of all Obtainable.
	 * 
	 */
	Set<? extends Tile> getTiles(Predicate<Tile> predicate);
	
	/**
	 * Return the Tile of the specific position.
	 * 
	 * @param  index  index of the position.
     * @throws IllegalArgumentException if the specified index is over the limit.
	 * @return <tt>Tile</tt> of the specific position.
	 */
	Tile getTileOf(final int index);
	
	/**
	 * Return the String corresponding to the mode of game.
	 * 
	 * @return <tt>mode</tt> of game.
	 */
	String getModeGame();
	
	/**
	 * @return the number of tiles of the same type.
	 */
	int getTilesNumber();
}
