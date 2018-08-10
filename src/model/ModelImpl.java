package model;

import java.util.List;
import java.util.Set;

import model.player.Player;
import model.player.PlayerInfo;
import model.tiles.Obtainable;


public class ModelImpl implements Model {
	
	private final Board board;
	private final List<Player> players;
	private final Set<Obtainable> properties;
	private final Player currentPlayer;
	//imprevisti e probabilità
	

	public ModelImpl(final Board board, final List<Player> players, final Set<Obtainable> properties, final Player currentPlayer) {
		this.board = board;
		this.players = players;
		this.properties = properties;
		this.currentPlayer = currentPlayer;
		// TODO Auto-generated constructor stub
	}

	@Override
	public PlayerInfo getCurrentPlayer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PlayerInfo> getPlayerList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removePlayer(PlayerInfo player) {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveGame() {
		ResourceManager.getInstance().saveOnFile(CareMementoTaker.getMementoInstance().getMemento());
	}

	@Override
	public void endGame() {
		// TODO Auto-generated method stub

	}

}
