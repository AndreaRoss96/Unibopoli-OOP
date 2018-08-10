package model;

import java.util.List;
import java.util.Set;

import model.player.Player;
import model.tiles.Tile;
import utilities.Pair;

public interface Model {

	Player getCurrentPlayer();
	
	List<Player> getPlayers();
	
	void startGame();
	
	void saveGame();
	
	void loadGame();
	
	Pair<Integer, Integer> exitDice();
	
	Set<Tile> getBoard();
	
	void addPlayer(final Player player);
}
