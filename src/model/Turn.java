package model;

import java.io.Serializable;
import java.util.List;

import model.player.Player;
import model.player.PlayerInfo;

/**
 * This interface contains the implementation of the turn, the rotation of the
 * player will be managed by a circular list.
 * 
 * @author Matteo Alesiani
 *
 */

public interface Turn extends Serializable {

	/**
	 * This method will return the reference to the player whose have to play his
	 * turn.
	 * 
	 * @return Player whose turn is
	 */
	Player getCurrentPlayer();

	/**
	 * This method will return the reference to the next player whose have to play
	 * his turn.
	 * 
	 * @return Player whose next turn is
	 */
	void nextPlayer();

	/**
	 * This method will return true of the current player is in jail.
	 * 
	 * @return boolean
	 */
	boolean isInJail();

	/**
	 * Count the consecutive number of turns that the player has spent in jail.
	 */
	void turnInJail();

	/**
	 * Allows the current player to exit from jail.
	 * 
	 * @return boolean true if the player is exited from jail, false instead
	 */
	boolean exitFromJail();

	/**
	 * Remove a loser player from the player list.
	 * 
	 * @param player
	 *            that have lose
	 * 
	 * @return true if this list contained the specified element
	 */
	boolean remove(PlayerInfo player);

	/**
	 * Getter for the players.
	 * 
	 * @return the list of all players
	 */
	List<PlayerInfo> getPlayers();

	/**
	 * Count the number of rolls made by the current player.
	 * 
	 * @return true if he/she made less the 3 throws
	 */
	boolean isThrows();

}
