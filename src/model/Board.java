package model;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import utilities.Parse;
import utilities.ReadFile;
import utilities.enumerations.ClassicType;

public class Board {
	
	private static final int MAXINDEXBOARD = 40;
	
	private Set<Tile> gameBoard;
	private String mode;
	
	public Board(final String mode) {
		this.gameBoard = new HashSet<>();
		this.mode = mode;
		this.initializationSetTile();
	}
	
	public Set<Obtainable> getObtainable(){
		return this.gameBoard.stream()
				   .filter(t -> t instanceof Obtainable)
				   .map(obj -> (ObtainableImpl) obj)
				   .collect(Collectors.toSet());
	} 
	
	public Set<NotBuildable> getNotBuildable(){
		return this.gameBoard.stream()
				   .filter(t -> t instanceof NotBuildable)
				   .map(obj -> (NotBuildableImpl) obj)
				   .collect(Collectors.toSet());
	}
	
	public Tile getTileOf(final int index) {
		if(index >= MAXINDEXBOARD) {
			throw new IllegalArgumentException();
		}
		
		return this.gameBoard.stream()
				   .filter(t -> t.getPosition() == index)
				   .findFirst().get();
	}
	
	public Set<Tile> getTileBoard(){
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
			/**
			 *	TODO: Gestire la funzione in modo da renderla ancora più generale !!! 
			 */
			ReadFile.readFile(ClassicType.GeneralPurposeMap.getClassicModeInitFile())
					.forEach(record -> this.gameBoard.stream()
													 .filter(t -> t.getPosition() == Integer.parseInt(record.substring(0, 0)))
													 .findFirst().get()
													 .setNameOf(record.substring(2)));
		}catch (IOException e) {}
		catch (Exception e) {}
		
		//Collections.nCopies(4, new NotBuildable(positionTile, price, mortgage, colorTile, typeOf))
		
	}
}
