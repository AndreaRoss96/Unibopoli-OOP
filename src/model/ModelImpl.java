package model;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
	private final List<Player> loserList;
	//imprevisti e probabilità
	
	public ModelImpl(final Board board, final List<Player> players, final Set<Obtainable> properties, final Player currentPlayer, final List<Player> loserList) {
		this.board = board;
		this.players = players;
		this.properties = properties;
		this.currentPlayer = currentPlayer;
		this.loserList = loserList;
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<PlayerInfo> getPlayers() {
		return this.players.stream().map(player -> (PlayerInfo) player).collect(Collectors.toList());
	}

	@Override
	public void saveGame() {
		ResourceManager.getInstance().saveOnFile(CareMementoTaker.getMementoInstance().getMemento());
	}

	@Override
	public Pair<Integer, Integer> exitDice() {
		return Dice.getInstance().getDice();
	}

	@Override
	public Set<Tile> getBoard() {
		return this.board.getTileBoard();
	}

	@Override
	public PlayerInfo getCurrentPlayer() {
		return this.currentPlayer;
	}
	
	public Set<Obtainable> getProperties(){
		return this.properties;
	}

	//riguarda se è corretto playerInfo/player
	@Override
	public void removePlayer(PlayerInfo player) {
		this.loserList.add(this.players.remove(this.players.indexOf(player)));
	}


}
