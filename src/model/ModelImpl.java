package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import model.player.Player;
import model.player.PlayerInfo;
import model.tiles.Obtainable;
import model.tiles.Tile;
import utilities.Pair;

public class ModelImpl implements Model{
	
	private static final int JAIL = 30;

	private Board board;
	private List<Player> loserList;
	private Turn turnPlayer;
	
	public ModelImpl(final Board board, final List<Player> players) {
		this.board = board;
		this.loserList = new ArrayList<>();
		this.turnPlayer = new TurnImpl(players);
	}
	
	@Override
	public List<PlayerInfo> getPlayers() {
		return this.turnPlayer.getPlayers();
	}

	@Override
	public void saveGame() {
		ResourceManager.getInstance().saveOnFile(CareMementoTaker.getMementoInstance().getMemento());
	}

	/**
	 * Ricordarsi che solo i giocatori non in JAIL possono muoversi.
	 * 
	 * */
	@Override
	public Pair<Integer> exitDice() {
		if(this.turnPlayer.isThrows()) {			
			Pair<Integer> temp = Dice.getInstance().getDice();
			
			if(this.turnPlayer.isInJail()){
				this.turnPlayer.tunInJail();
				
				if(temp.areSame() || this.turnPlayer.exitFromJail()) {
					this.exitFromJail();
				}
			}
	
			return temp; 
		}else {
			this.goToJail();
			return null;
		}
	}	
	
	@Override
	public Set<Tile> getBoard() {
		return this.board.getTiles(tile -> true).stream().collect(Collectors.toSet());
	}

	@Override
	public PlayerInfo getCurrentPlayer() {
		return this.turnPlayer.getCurrentPlayer();
	}
	
	public Set<Obtainable> getProperties(){ 
		return this.board.getTiles(tile -> tile instanceof Obtainable).stream()
				   .map(tile -> (Obtainable) tile).collect(Collectors.toSet());
	}

	//riguarda se è corretto playerInfo/player
	@Override
	public void removePlayer(PlayerInfo player) {
		this.loserList.add((Player) this.turnPlayer.getPlayers().remove(this.turnPlayer.getPlayers().indexOf(player)));
	}

	public void endTurn() {
		this.turnPlayer.nextPlayer();
	}

	@Override
	public void movement(int value) {
		this.setNewPosition((this.turnPlayer.getCurrentPlayer().getPosition() + value) % this.board.getTilesNumber());
	}
	
	private void setNewPosition(int value) {
		this.turnPlayer.getCurrentPlayer().setPosition(value);
	}
	
	public void goToJail() {
		this.setNewPosition(JAIL);
		this.endTurn();
	}
	
	public void exitFromJail() {
		this.turnPlayer.getCurrentPlayer().exitFromJail();
	}
	
	/**
	 * OSSERVAZIONI:
	 * -  LA gestione del turno dei giocatori dove va ?? Si potrebbe sapere il curruntPlayer tramite quella classe. 
	 *    Ciò non significa eliminare l'attributo, bensì inizializzrlo continuamente tramite quella classe.
	 * - Aggiungere un metodo che passa il turno al giocatore successivo. FATTO, ma verificare
	 * - 
	 */
}
