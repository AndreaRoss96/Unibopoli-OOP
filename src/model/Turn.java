package model;
/**
 * This interface contains the implementation of the turn, the rotation of the player
 * will be managed by a circular list.
 * @author Edoardo Doglioni
 *
 */

public interface Turn {
	
	/**
	 * This method will return the reference to the player whose 
	 * have to play his turn
	 * @return Player
	 * 			whose turn is
	 */
	Player getCurrentPlayer();
	
	
	/**
	 * This method will return the reference to the next player whose have
	 * to play his turn
	 * @return Player
	 * 			whose next turn is
	 */
	Player nextPlayer();
	/**
	 * This method will return true of the current player is in jail
	 * @return boolean
	 */
	boolean isInJail();
}
