package model;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import utilities.Parse;
import utilities.ReadFile;
import utilities.enumerations.ClassicType;
import model.tiles.*;

/**
 * This class manage the game board. 
 * 
 * @author Matteo Alesiani
 */

public class BoardImpl implements Board {
	
	private static final long serialVersionUID = 1L;
	private static final int MAXINDEXBOARD = 40;
	
	private Set<Tile> gameBoard;
	private String mode;
	
	public BoardImpl(final String mode) {
		this.gameBoard = new HashSet<>();
		this.mode = mode;
		this.initializationSetTile();
	}
	
	@Override
	public Set<? extends Tile> getTiles(Predicate<Tile> predicate){
		return this.gameBoard.stream()
				   .filter(predicate::test)
				   .collect(Collectors.toSet());
	}

	@Override
	public Tile getTileOf(final int index) {
		if(index >= MAXINDEXBOARD) { throw new IllegalArgumentException(); }
		
		return this.gameBoard.stream()
				   .filter(t -> t.getPosition() == index)
				   .findFirst().get();
	}
	
	@Override
	public String getModeGame() {
		return this.mode;
	}
	
	@Override
	public int getTilesNumber() {
		return MAXINDEXBOARD;
	}
	
	private void initializationSetTile() {
		try {
			ReadFile.readFile(ClassicType.Files.GENERALFILEMAP.getStaticBuildableValuesInitFile())
					.map(Parse.PARSING_BUILDABLE_TILE_BOARD::apply)
					.forEach(gameBoard::add);
		
			ReadFile.readFile(ClassicType.Files.GENERALFILEMAP.getStaticNotBuildableValuesInitFile())
					.map(Parse.PARSING_NOTBUILDABLE_TILE_BOARD::apply)
					.forEach(gameBoard::add);
								
			ReadFile.readFile(ClassicType.Files.GENERALFILEMAP.getModeGame(this.getModeGame()))
					.forEach(record -> Parse.PARSING_LOAD_MODEGAME.accept(record, this.gameBoard.stream()));
			
			ReadFile.readFile(ClassicType.Files.GENERALFILEMAP.getStaticNotObtainableValuesInitFile())
					.map(Parse.PARSING_NOTOBTAINABLE_TILE_BOARD::apply)
					.forEach(gameBoard::add);
		}catch (IOException e) {
		} catch (Exception e) { 
		}
	}
}
