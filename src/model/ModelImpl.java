package model;

import java.util.List;
import java.util.Set;

import model.player.Player;
import model.player.PlayerInfo;
import model.tiles.Obtainable;
import model.tiles.Tile;
import utilities.Pair;

public class ModelImpl implements Model{
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
	public Player getCurrentPlayer1() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Player> getPlayers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void startGame() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveGame() {
		ResourceManager.getInstance().saveOnFile(CareMementoTaker.getMementoInstance().getMemento());
	}

	@Override
	public void loadGame() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Pair<Integer, Integer> exitDice() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Tile> getBoard() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addPlayer(Player player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PlayerInfo getCurrentPlayer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removePlayer(PlayerInfo player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endGame() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addPlayer(Player player) {
		// TODO Auto-generated method stub
		
	}


}
