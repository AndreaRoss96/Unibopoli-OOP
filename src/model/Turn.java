package model;

import java.io.Serializable;
import java.util.List;

import model.player.Player;
import model.player.PlayerInfo;

/**
 * This interface contains the implementation of the turn, the rotation of the player
 * will be managed by a circular list.
 * @author Matteo Alesiani
 *
 */

public interface Turn extends Serializable{
	
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
	void nextPlayer();
	
	/**
	 * This method will return true of the current player is in jail
	 * @return boolean
	 */
	boolean isInJail();
	
	void turnInJail();
	
	boolean exitFromJail();
	
	boolean remove(Player player);
	
	List<PlayerInfo> getPlayers();
	
	boolean isThrows();
	
}
