package model;

import java.util.List;

import model.player.Player;
import model.player.PlayerInfo;

public interface Model {
	
	/**
	 * @return the player who is currently playing.
	 */
	PlayerInfo getCurrentPlayer();
	
	/**
	 * @return the list of all player in game.
	 */
	List<PlayerInfo> getPlayerList();
	
	/**
	 * remove a player out of the game.
	 */
	void removePlayer(PlayerInfo player);
	
	/**
	 * Saves the game.
	 */
	void saveGame();
	
	/**
	 * Quits the game.
	 */
	void endGame();
	
	//immagino move player o simili
}
