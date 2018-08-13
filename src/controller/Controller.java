package controller;

import java.util.List;

import model.player.PlayerInfo;

public interface Controller {

	PlayerInfo getCurrentPlayer();
	
	List<PlayerInfo> getPlayerList();
	
	void saveGame();
	
	
}
