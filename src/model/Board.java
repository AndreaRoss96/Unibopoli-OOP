package model;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import utilities.Parse;
import utilities.ReadFile;
import utilities.enumerations.ClassicType;

public class Board {
	
	private static final int MAXINDEXBOARD = 40;
	
	private Set<TileInterface> gameBoard;
	private String mode;
	
	public Board(final String mode) {
		this.gameBoard = new HashSet<>();
		this.mode = mode;
		this.initializationSetTile();
	}
	
	public Set<ObtainableInterface> getObtainable(){
		return this.gameBoard.stream()
				   .filter(t -> t instanceof ObtainableInterface)
				   .map(obj -> (Obtainable) obj)
				   .collect(Collectors.toSet());
	} 
	
	public TileInterface getTileOf(final int index) {
		if(index >= MAXINDEXBOARD) {
			throw new IllegalArgumentException();
		}
		
		return this.gameBoard.stream()
				   .filter(t -> t.getPosition() == index)
				   .findFirst().get();
	}
	
	public Set<TileInterface> getTileBoard(){
		return this.gameBoard;
	}
	
	public String getModeGame() {
		return this.mode;
	}
	
	private void initializationSetTile() {
		try {
			ReadFile.readFile(ClassicType.GeneralPurposeMap.getStaticValuesInitFile())
					.map(Parse.PARSING_INIT_TILE_BOARD::apply)
					.forEach(gameBoard::add);
			
			//TODO: Inserire nome della Tile
			
		}catch (IOException e) {}
		catch (Exception e) {}
		
		
	}
}
