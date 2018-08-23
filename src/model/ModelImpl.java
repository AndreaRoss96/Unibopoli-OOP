package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import model.player.Player;
import model.player.PlayerInfo;
import model.player.RealPlayer;
import model.tiles.Obtainable;
import model.tiles.Tile;
import utilities.Pair;

public class ModelImpl implements Model{
	
	private final Board board;
	/**
	 * Se vogliamo tenere qui players, è necessario eliminare la mappa da dentro la classe Board.
	 * Vedendo lo sviluppo di Player credo che sia la scelta migliore.
	 * */
	private final List<Player> players;
	/**
	 * 
	 * A cosa serve Set<Obtainable> properties ?
	 * -rosso: presumo sia utile tenere traccia delle properietà presenti nel gioco
	 * per accedere rapidamente alle loro posizioni, prezzo, affitto ecc, poi dipende se queste informazioni
	 * possono essere estratte dalla board di gioco si possono canellare
	 * */
	private final Set<Obtainable> properties; //Board.getTiles(Tile -> tile instanceof Obtainable) ritorna un bellissimo set quindi togli questo attributo
//	private Player currentPlayer;
	private final List<Player> loserList;
	//imprevisti e probabilità
	private Turn turnPlayer;
	
	public ModelImpl(final Board board, final List<Player> players, final Set<Obtainable> properties, final Player currentPlayer, final List<Player> loserList) {
		this.board = board;
		this.players = players;
		this.properties = properties;
		this.currentPlayer = currentPlayer;
		this.loserList = loserList;
		// TODO Auto-generated constructor stub
	}

	public ModelImpl(final String mode, final List<Player> players) {
		this.board = new Board(mode);
		this.players = players;
		this.properties = new HashSet<>();
		/**
		 * Può essere utilite avere un costruttore diverso ??
		 * */
		this.currentPlayer = new RealPlayer("", 10);
		this.loserList = new ArrayList<>();
		this.turnPlayer = new TurnImpl(this.players);
	}
	
	@Override
	public List<PlayerInfo> getPlayers() {
		return this.players.stream().map(player -> (PlayerInfo) player).collect(Collectors.toList());
	}

	@Override
	public void saveGame() {
		ResourceManager.getInstance().saveOnFile(CareMementoTaker.getMementoInstance().getMemento());
	}

	/**
	 * La modifica della posizione possiamo farla qui ??
	 * Oppure passiamo tramite il Controller...
	 * 
	 * */
	@Override
	public Pair<Integer, Integer> exitDice() {
		return Dice.getInstance().getDice();
	}

	/**
	 * 
	 * non so che metodo utilizzare, magari ne esiste una specifico.
	public void movePlayer(int movePosition) {
		this.turnPlayer.getCurrentPlayer().setPosition(movePosition);
	}
	*/
	
	
	@Override
	public Set<Tile> getBoard() {
		return this.board.getTiles(tile -> true).stream().collect(Collectors.toSet());
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

	/**
	 * Trovare un nome migliore se necessario.
	 * Verificare se è necessario currentuPlayer.
	 * */
	public void endTurn() {
		this.turnPlayer.nextPlayer();
		this.currentPlayer = this.turnPlayer.getCurrentPlayer();
	}
	
	/**
	 * OSSERVAZIONI:
	 * -  LA gestione del turno dei giocatori dove va ?? Si potrebbe sapere il curruntPlayer tramite quella classe. 
	 *    Ciò non significa eliminare l'attributo, bensì inizializzrlo continuamente tramite quella classe.
	 * - Aggiungere un metodo che passa il turno al giocatore successivo. FATTO, ma verificare
	 * - 
	 */
}
