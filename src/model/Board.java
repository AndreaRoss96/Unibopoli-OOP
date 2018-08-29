package model;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
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
import model.tiles.*;

/**
 * This class manage the game board. 
 * 
 * @author Matteo Alesiani
 */

public class Board implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private static final int MAXINDEXBOARD = 40;
	
	private static Map<Integer, Boolean> chances = new HashMap<>();
	static {
		chances.put(2, false);
		chances.put(17, false);
		chances.put(33, false);
		chances.put(7, true);
		chances.put(22, true);
		chances.put(36, true);
	}
	
	private Set<Tile> gameBoard;
	private String mode;
	
	public Board(final String mode) {
		this.gameBoard = new HashSet<>();
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
	 * Return the String corresponding to the mode of game.
	 * 
	 * @return <tt>mode</tt> of game.
	 */
	public String getModeGame() {
		return this.mode;
	}
	
	public int getTilesNumber() {
		return MAXINDEXBOARD;
	}
	
	private void initializationSetTile() {
		try {
			ReadFile.readFile(ClassicType.Files.GeneralFilesMap.getStaticBuildableValuesInitFile())
					.map(Parse.PARSING_BUILDABLE_TILE_BOARD::apply)
					.forEach(gameBoard::add);
		
			ReadFile.readFile(ClassicType.Files.GeneralFilesMap.getStaticNotBuildableValuesInitFile())
					.map(Parse.PARSING_NOTBUILDABLE_TILE_BOARD::apply)
					.forEach(gameBoard::add);
								
			ReadFile.readFile(ClassicType.Files.GeneralFilesMap.getModeGame(this.getModeGame()))
					.forEach(record -> Parse.PARSING_LOAD_MODEGAME.accept(record, this.gameBoard.stream()));
			
		}catch (IOException e) {
			System.out.println("IOExce");
		} catch (Exception e) { 
			System.out.println(e.getCause()); 
		}
		
		IntStream.range(0, 4).mapToObj(t->t).map(Parse.PARSING_CORNER::apply).forEach(gameBoard::add);

		chances.entrySet().stream().map(entry -> new Chance(entry.getKey(), entry.getValue())).forEach(gameBoard::add);

		Arrays.asList(4, 38).stream().map(t -> new Tax(t)).forEach(gameBoard::add);
	}
}
