package model;

import utilities.enumerations.Status.NotBiuldableType;

/**
 * This interface is a sub-set of Tile, obtainable to the Player but they can't 
 * be buildable.
 * The method getRent() return a value proportional to the number 
 * exited from the Dice.  
 * 
 * @author Matteo Alesiani 
 */

public interface NotBuildable extends Obtainable {
	
	/**
	 * Return a type of NotBuildableType enum, there are STATION or SOCIETY.
	 * 
	 * @return <tt>NotBiuldableType</tt> type of enum.
	 */
	NotBiuldableType getType(); 
}
