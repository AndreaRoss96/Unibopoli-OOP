package model;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import utilities.Parse;
import utilities.ReadFile;
import utilities.enumerations.ClassicType;
import model.player.PlayerInfo;
import model.tiles.*;

/**
 * This class manage the game board. 
 * 
 * @author Matteo Alesiani
 */

public class Board implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final int MAXINDEXBOARD = 40;
	
	private Set<Tile> gameBoard;
	private Map<PlayerInfo, Integer> pawns;
	private String mode;
	
	public Board(final String mode) {
		this.gameBoard = new HashSet<>();
		this.pawns = new HashMap<>();
		this.mode = mode;
		this.initializationSetTile();
	}
	
	/**
	 * Return the Set of all Obtainable Tile.
	 * 
	 * @return <tt>Set<Obtainable></tt> of all Obtainable.
	 *
	 * TODO: può ottimizzare gli altri.
	 * 
	 */
	public Set<? extends Tile> getTiles(Predicate<Tile> predicate){
		return this.gameBoard.stream()
				   .filter(predicate::test)
				   .collect(Collectors.toSet());
	}
	
	/**
	 * Return the Tile of the specific position.
	 * 
	 * @param  index  index of the position.
     * @throws IllegalArgumentException if the specified index is over the limit.
	 * @return <tt>Tile</tt> of the specific position.
	 */
	public Tile getTileOf(final int index) {
		if(index >= MAXINDEXBOARD) { throw new IllegalArgumentException(); }
		
		return this.gameBoard.stream()
				   .filter(t -> t.getPosition() == index)
				   .findFirst().get();
	}
	
	/**
	 * Return the Set of all Tile.
	 * 
	 * @return <tt>Set<Tile></tt> of all Tile.
	 */
	public Set<Tile> getTileBoard(){
		return this.gameBoard.stream()
				   .collect(Collectors.toSet());
	}
	
	/**
	 * Return the String corresponding to the mode of game.
	 * 
	 * @return <tt>mode</tt> of game.
	 */
	public String getModeGame() {
		return this.mode;
	}
	
	public void addPawn(PlayerInfo player) {
		this.addPawn(player, new Integer(0));
	}
	
	public void addPawn(PlayerInfo player, Integer position) {

		this.pawns.put(player, position);
	}
	
	/**
	 * TODO: Finire questa inizializzazione ed eliminare metodi non necessari
	 * 
	 * */
	private void initializationSetTile() {
		try {
			ReadFile.readFile(ClassicType.GeneralPurposeMap.getStaticBuildableValuesInitFile())
					.map(Parse.PARSING_BUILDABLE_TILE_BOARD::apply)
					.forEach(gameBoard::add);
		
			ReadFile.readFile(ClassicType.GeneralPurposeMap.getStaticNotBuildableValuesInitFile())
					.map(Parse.PARSING_NOTBUILDABLE_TILE_BOARD::apply)
					.forEach(gameBoard::add);
								
			ReadFile.readFile(ClassicType.GeneralPurposeMap.getModeGame(this.mode))
					.forEach(record -> Parse.PARSING_LOAD_MODEGAME.accept(record, this.gameBoard.stream()));
			
		}catch (IOException e) {System.out.println("IOExce");}
		catch (Exception e) { System.out.println(e.getCause()); }
		
		IntStream.range(0, 4).mapToObj(t->t).map(Parse.PARSING_CORNER::apply).forEach(gameBoard::add);
		
		
		//this.getTiles(t -> true).stream().sorted(Comparator.comparing(Tile::getPosition)).forEach(t -> System.out.println(t.getPosition() + ": " + t.getNameOf()));
		//Collections.nCopies(4, new NotBuildable(positionTile, price, mortgage, colorTile, typeOf))
	}
}
