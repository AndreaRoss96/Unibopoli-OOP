package model;

import java.util.List;
import java.util.Set;

import model.player.Player;
import model.player.PlayerInfo;
import model.tiles.Tile;
import utilities.Pair;

public interface Model {

	/**
	 * @return the player who is currently playing.
	 */
	PlayerInfo getCurrentPlayer();
	
	/**
	 * @return the list of all player in game.
	 */
	List<Player> getPlayers();
	
	/**
	 * remove a player out of the game.
	 */
	void removePlayer(PlayerInfo player);
	
	/**
	 * Saves the game.
	 */
	void saveGame();
		
	/**
	 * @return game's dice
	 */
	Pair<Integer, Integer> exitDice();
	
	Set<Tile> getBoard();
}
