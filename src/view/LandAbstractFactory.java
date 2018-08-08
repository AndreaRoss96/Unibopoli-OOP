package view;

import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import model.tiles.Tile;

public interface LandAbstractFactory {

	public Pane createLand(final Tile tile, final Pos position);
	
	/**
	 * 
	 * Inserire immagine nelle Tile apposita. 
	 * public Pane createCornerLand(final Tile tile, final Pos position, final Icon image);
	 * 
	 * 
	 */
}
