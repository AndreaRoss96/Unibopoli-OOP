package view;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import model.tiles.Buildable;
import model.tiles.Corner;
import model.tiles.NotBuildable;
import model.tiles.NotObtainable;
import model.tiles.Tile;
import view.tiles.ComponentFactory;

/**
* 
* 
* 
* @author Matteo Alesiani 
*/

public class LandAbstractFactoryImp{

	private static final double ROTATE_LEFT = +90.0;
	private static final double ROTATE_RIGHT = -90.0;
	
	public Pane createLand(final Tile tile, final Pos position) {
		if(tile instanceof Buildable) {
			return this.getBuildable((Buildable) tile, position);
		}else if(tile instanceof NotBuildable) {
			return this.getNotBuildable((NotBuildable) tile, position);
		}else if(tile instanceof Corner) {
			return this.getCorner((Corner) tile, position);
		}else if(tile instanceof NotObtainable){
			return null;
		}
		
		throw new IllegalArgumentException();
	}
	
	private AnchorPane getBuildable(final Buildable buildableTile, final Pos position) {
		AnchorPane landPane = ComponentFactory.getAnchorPane(false, position);
		
		switch(position) {
			case CENTER_LEFT: this.getHorizontalBuildable(landPane, buildableTile, position); break;
			case CENTER_RIGHT: this.getHorizontalBuildable(landPane, buildableTile, position); break;
			case TOP_CENTER: this.getVerticalBuildable(landPane, buildableTile, position); break;
			case BOTTOM_CENTER: this.getVerticalBuildable(landPane, buildableTile, position); break;
			default: throw new IllegalArgumentException();
		}
		
		return landPane;
	}
	
	private void getHorizontalBuildable(final AnchorPane landPane, final Buildable buildableTile, final Pos position)
	{		
		// Aggiungere css per definire il bordo di colore nero e specificare la grandezza (Separator)
		Label colorFamily = ComponentFactory.getLabelColor(buildableTile.getColorOf().getPaint().get(), position);
		
		/**
		 * Inserire separator anche a Destra.
		 * */
		Separator seperator = ComponentFactory.getSeparator(Orientation.VERTICAL);
		Label textHeader = ComponentFactory.getLabelString(buildableTile.getNameOf().replace(' ', '\n'), position == Pos.CENTER_LEFT ? ROTATE_LEFT : ROTATE_RIGHT, position);
		Label textRent = ComponentFactory.getLabelString("$" + buildableTile.getPrice(), position == Pos.CENTER_LEFT ? ROTATE_LEFT : ROTATE_RIGHT, position);
		
		if(position == Pos.CENTER_LEFT) {
			AnchorPane.setRightAnchor(colorFamily, 0.0);
			AnchorPane.setRightAnchor(seperator, 17.0);
			AnchorPane.setRightAnchor(textHeader, 22.0);	
			AnchorPane.setLeftAnchor(textRent, 5.0);
		}else {
			AnchorPane.setLeftAnchor(colorFamily, 0.0);
			AnchorPane.setLeftAnchor(seperator, 18.0);
			AnchorPane.setLeftAnchor(textHeader, 22.0);	
			AnchorPane.setRightAnchor(textRent, 5.0);
		}
		
		landPane.getChildren().addAll(colorFamily, seperator, textHeader, textRent);
		
	}
	
	private void getVerticalBuildable(final AnchorPane landPane, final Buildable buildableTile, final Pos position)
	{
		// Aggiungere css per definire il bordo di colore nero e specificare la grandezza (Separator)
		Label colorFamily = ComponentFactory.getLabelColor(buildableTile.getColorOf().getPaint().get(), position);
		//utilizzare uno StackPane per inserire le case.
		
		Separator seperator = ComponentFactory.getSeparator(Orientation.HORIZONTAL);		
		Label textHeader = ComponentFactory.getLabelString(buildableTile.getNameOf().replace(' ', '\n'), 0.0, position);		
		Label textRent = ComponentFactory.getLabelString("$" + buildableTile.getPrice(), 0.0, position);
		
		if(position == Pos.TOP_CENTER) {
			AnchorPane.setTopAnchor(colorFamily, 0.0);
			AnchorPane.setTopAnchor(seperator, 17.0);
			AnchorPane.setTopAnchor(textHeader, 22.0);	
			AnchorPane.setBottomAnchor(textRent, 5.0);
		}else {
			AnchorPane.setBottomAnchor(colorFamily, 0.0);
			AnchorPane.setBottomAnchor(seperator, 18.0);
			AnchorPane.setBottomAnchor(textHeader, 22.0);	
			AnchorPane.setTopAnchor(textRent, 5.0);
		}
		
		landPane.getChildren().addAll(colorFamily, seperator, textHeader, textRent);
	}
	
	private AnchorPane getNotBuildable(final NotBuildable buildableTile, final Pos position) {
		AnchorPane landPane = ComponentFactory.getAnchorPane(false, position);
		
		switch(position) {
			case CENTER_LEFT: break;
			case CENTER_RIGHT:  break;
			case TOP_CENTER:  this.getVerticalNotBuildable(landPane, buildableTile, position); break;
			case BOTTOM_CENTER:  break;
			default: throw new IllegalArgumentException();
		}
		
		return landPane;
	}
	
	private void getVerticalNotBuildable(final AnchorPane landPane, final NotBuildable buildableTile, final Pos position) {
		Label colorFamily = ComponentFactory.getLabelString(buildableTile.getNameOf(), 0.0, position);
		AnchorPane.setLeftAnchor(colorFamily, 0.0);
		AnchorPane.setRightAnchor(colorFamily, 0.0);
		
		/**
		 * TODO: Controllare la dimensione. Porbabilmente occorrerà diminuirla. Reandere quadrata l'immagine del treno.
		 * */
		Label image = ComponentFactory.getLabelImage(buildableTile.getImage(), 0.6, position);		
		//verificare se è possibile inserirlo nel componentFactory
		AnchorPane.setTopAnchor(image, 30.0);		
		Label bottom = ComponentFactory.getLabelString("$" + String.valueOf(buildableTile.getPrice()), 0.0, position);
		
		AnchorPane.setLeftAnchor(bottom, 0.0);
		AnchorPane.setRightAnchor(bottom, 0.0);
		AnchorPane.setBottomAnchor(bottom, 5.0);
		
		landPane.getChildren().addAll(colorFamily, image, bottom);
	}
	
	/**
	 * Realizzare un Pane apposito per free_transit.
	 * */
	private AnchorPane getCorner(final Corner cornerTile, final Pos position) {
		AnchorPane landPane = ComponentFactory.getAnchorPane(true, position);
		
		List<String> temp = Arrays.stream(cornerTile.getHeaderText().split("\n")).collect(Collectors.toList());
		
		Label top = ComponentFactory.getLabelString(temp.get(0), 0.0, position);
		Label image = ComponentFactory.getLabelImage(cornerTile.getImage().get(), 0.7, position);
		AnchorPane.setTopAnchor(image, 9.0);
		
		Label bottom = ComponentFactory.getLabelString(temp.get(1), 0.0, position);
		
		if(position == Pos.TOP_CENTER) {
			
		}else {
			
		}
		
		
		/**
		 * Da Sistemare
		 * */
		AnchorPane.setLeftAnchor(top, 0.0);
		AnchorPane.setRightAnchor(top, 0.0);
		AnchorPane.setTopAnchor(top, 0.0);
		
		AnchorPane.setLeftAnchor(bottom, 0.0);
		AnchorPane.setRightAnchor(bottom, 0.0);
		AnchorPane.setBottomAnchor(bottom, 5.0);
		
		landPane.getChildren().addAll(top, image, bottom);
		return landPane;
	}
}
