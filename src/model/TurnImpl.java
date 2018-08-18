package model;

import java.util.List;

import model.player.Player;
import model.player.PlayerInfo.Prison;
import utilities.CircularListImpl;

/**
 * @author Matteo Alesiani
 *
 */
public class TurnImpl implements Turn {

	private CircularListImpl<Player> turnManagement;
	
	public TurnImpl(List<Player> players) {
		this.turnManagement = new CircularListImpl<>(players);
		this.nextPlayer();
	}
	
	public Player getCurrentPlayer() {
		return this.turnManagement.getHead();
	}

	public boolean isInJail() {
		return this.getCurrentPlayer().isInJail() == Prison.PRISON;
	}

	@Override
	public void nextPlayer() {
		this.turnManagement.shift();
	}

	@Override
	public boolean remove(Player player) {
		return this.turnManagement.remove(player);
	}
}
