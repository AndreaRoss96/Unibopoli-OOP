package view.tiles;

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
import utilities.PaneDimensionSetting;
import view.gameDialog.CardDialog;

/**
* 
* @author Matteo Alesiani 
*/

public class LandAbstractFactoryImp{

	public Pane createLand(final Tile tile, final Pos position) {
		if(tile instanceof Buildable) {
			return this.getBuildable((Buildable) tile, position);
		}else if(tile instanceof NotBuildable) {
			return this.getNotBuildable((NotBuildable) tile, position);
		}else if(tile instanceof Corner) {
			return this.getCorner((Corner) tile);
		}else if(tile instanceof NotObtainable){
			return this.getNotObtainables((NotObtainable) tile, position);
		}
		throw new IllegalArgumentException();
	}
	
	private AnchorPane getBuildable(final Buildable buildableTile, final Pos position) {
		AnchorPane landPane = ComponentFactory.getAnchorPane(false, position);
		
		if(position == Pos.CENTER_LEFT || position == Pos.CENTER_RIGHT) {
			this.getHorizontalBuildable(landPane, buildableTile, position); 
		}else {
			this.getVerticalBuildable(landPane, buildableTile, position); 
		}
		
		//deve passare prima per il controller
		landPane.setOnMouseClicked(value -> CardDialog.getCardDialog().createCardDialog(buildableTile));
		
		return landPane;
	}
	
	private void getHorizontalBuildable(final AnchorPane landPane, final Buildable buildableTile, final Pos position)
	{		
		Label colorFamily = ComponentFactory.getLabelColor(buildableTile.getColorOf().getPaint().get(), position);
		Separator seperator = ComponentFactory.getSeparator(Orientation.VERTICAL);
		Label textHeader = ComponentFactory.getLabelString(buildableTile.getNameOf().replace(' ', '\n'), position);
		textHeader.setStyle("-fx-border-color: BLACK");
		Label textRent = ComponentFactory.getLabelString("$" + buildableTile.getPrice(), position);
		
		if(position == Pos.CENTER_LEFT) {
			AnchorPane.setRightAnchor(colorFamily, 0.0);
			AnchorPane.setRightAnchor(seperator, 17.0);
			AnchorPane.setRightAnchor(textHeader, 22.0);	
			AnchorPane.setLeftAnchor(textRent, 5.0);
		}else {
			AnchorPane.setLeftAnchor(colorFamily, 0.0);
			AnchorPane.setLeftAnchor(seperator, 17.0);
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
		Label textHeader = ComponentFactory.getLabelString(buildableTile.getNameOf().replace(' ', '\n'), position);
		textHeader.setStyle("-fx-border-color: BLACK");
		Label textRent = ComponentFactory.getLabelString("$" + buildableTile.getPrice(), position);
		
		if(position == Pos.BOTTOM_CENTER) {
			AnchorPane.setTopAnchor(colorFamily, 0.0);
			AnchorPane.setTopAnchor(seperator, 17.0);
			AnchorPane.setTopAnchor(textHeader, 22.0);	
			AnchorPane.setBottomAnchor(textRent, 5.0);
		}else {
			AnchorPane.setBottomAnchor(colorFamily, 0.0);
			AnchorPane.setBottomAnchor(seperator, 17.0);
			AnchorPane.setBottomAnchor(textHeader, 22.0);	
			AnchorPane.setTopAnchor(textRent, 5.0);
		}
		
		landPane.getChildren().addAll(colorFamily, seperator, textHeader, textRent);
	}
	
	private AnchorPane getNotBuildable(final NotBuildable notBuildableTile, final Pos position) {
		AnchorPane landPane = ComponentFactory.getAnchorPane(false, position);
		
		this.getNotBuildables(landPane, notBuildableTile, position);
		//deve passare prima per il controller
		landPane.setOnMouseClicked(value -> CardDialog.getCardDialog().createCardDialog(notBuildableTile));
		
		return landPane;
	}
	
	private void getNotBuildables(final AnchorPane landPane, final NotBuildable notBuildableTile, final Pos position) {
		Label top = ComponentFactory.getLabelString(notBuildableTile.getNameOf().replace(' ', '\n'), position);
		Label image = ComponentFactory.getLabelImage(notBuildableTile.getImage(), position);		
		Label bottom = ComponentFactory.getLabelString("$" + String.valueOf(notBuildableTile.getPrice()), position);
		
		if(position == Pos.TOP_CENTER || position == Pos.BOTTOM_CENTER) {
			AnchorPane.setTopAnchor(top, 0.0);					
			AnchorPane.setTopAnchor(image, 40.0);		
			AnchorPane.setBottomAnchor(bottom, 5.0);
		}else {
			AnchorPane.setLeftAnchor(top, 0.0);					
			AnchorPane.setLeftAnchor(image, 40.0);		
			AnchorPane.setRightAnchor(bottom, 5.0);
		}
		
		landPane.getChildren().addAll(top, image, bottom);
	}

	private AnchorPane getNotObtainables(final NotObtainable notObtainableTile, final Pos position) {
		AnchorPane landPane = ComponentFactory.getAnchorPane(false, position);
		List<String> temp = Arrays.stream(notObtainableTile.getNameOf().split("\n")).collect(Collectors.toList());
		
		Label top = ComponentFactory.getLabelString(temp.get(0), position);
		Label image = ComponentFactory.getLabelImage(notObtainableTile.getImage().get(), position);
		
		if(temp.size() > 1) {
			Label bottom = ComponentFactory.getLabelString(temp.get(1), position);
			AnchorPane.setBottomAnchor(bottom, 10.0);
			landPane.getChildren().add(bottom);
		}
		
		AnchorPane.setTopAnchor(image, 40.0);
		AnchorPane.setTopAnchor(top, 10.0);
		
		landPane.getChildren().addAll(top, image);
		return landPane;
	}
	/**
	 * Realizzare un Pane apposito per free_transit.
	 * */
	private AnchorPane getCorner(final Corner cornerTile/*, final Pos position*/) {
		AnchorPane landPane = ComponentFactory.getAnchorPane(true, Pos.TOP_CENTER);
		List<String> temp = Arrays.stream(cornerTile.getHeaderText().split("\n")).collect(Collectors.toList());
		
		Label top = ComponentFactory.getLabelString(temp.get(0), Pos.TOP_CENTER);
		Label image = ComponentFactory.getLabelImage(cornerTile.getImage().get(), Pos.TOP_CENTER);
		Label bottom = ComponentFactory.getLabelString(temp.get(1), Pos.TOP_CENTER);
		
		AnchorPane.setTopAnchor(image, 30.0);
		AnchorPane.setTopAnchor(top, 10.0);
		AnchorPane.setBottomAnchor(bottom, 10.0);
		
		landPane.getChildren().addAll(top, image, bottom);
		return landPane;
	}
}
