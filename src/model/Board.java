package model;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Board {
	
	private static final int MAXINDEXBOARD = 40;
	
	private Set<TileInterface> gameBoard;
	
	public Board() {
		this.gameBoard = new HashSet<>();
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
}
