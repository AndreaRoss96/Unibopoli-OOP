package view.tiles;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import controller.ControllerImpl;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import model.tiles.BuildableImpl;
import model.tiles.NotBuildableImpl;
import model.tiles.NotObtainableImpl;
import model.tiles.Tile;
import utilities.enumerations.TiteTypes;
import view.gameDialog.CardDialog;

/**
* 
* @author Matteo Alesiani 
*/

public class LandAbstractFactoryImp{

	public Pane createLand(final Tile tile) {
		if(tile.getTiteType() == TiteTypes.BUILDABLE) {
			return this.getBuildable((BuildableImpl) tile);
		}else if(tile.getTiteType() == TiteTypes.STATION || tile.getTiteType() == TiteTypes.LIGHT_AGENCY || tile.getTiteType() == TiteTypes.WATER_AGENCY ){
			return this.getNotBuildable((NotBuildableImpl) tile);
		}else {
			return this.getNotObtainables((NotObtainableImpl) tile);
		}
	}
	
	private AnchorPane getBuildable(final BuildableImpl buildableTile) {
		AnchorPane landPane = ComponentFactory.getAnchorPane(false);
		
		this.getBuildables(landPane, buildableTile); 
		landPane.setOnMouseClicked(value -> ControllerImpl.getController().showContract(buildableTile));
		
		return landPane;
	}
	
	private void getBuildables(final AnchorPane landPane, final BuildableImpl buildableTile)
	{
		Label colorFamily = ComponentFactory.getLabelColor(buildableTile.getColorOf().getPaintValue());
		Separator seperator = ComponentFactory.getSeparator(Orientation.HORIZONTAL);		
		Label textHeader = ComponentFactory.getLabelString(buildableTile.getNameOf().replace(' ', '\n'));
		Label textRent = ComponentFactory.getLabelString("$" + buildableTile.getPrice());

		AnchorPane.setBottomAnchor(colorFamily, 0.0);
		AnchorPane.setBottomAnchor(seperator, 17.0);
		AnchorPane.setBottomAnchor(textHeader, 22.0);	
		AnchorPane.setTopAnchor(textRent, 5.0);

		landPane.getChildren().addAll(colorFamily, seperator, textHeader, textRent);
	}
	
	private AnchorPane getNotBuildable(final NotBuildableImpl notBuildableTile) {
		AnchorPane landPane = ComponentFactory.getAnchorPane(false);
		
		this.getNotBuildables(landPane, notBuildableTile);
		
		landPane.setOnMouseClicked(value -> CardDialog.getCardDialog().createCardDialog(notBuildableTile, false));
		
		return landPane;
	}
	
	private void getNotBuildables(final AnchorPane landPane, final NotBuildableImpl notBuildableTile) {
		Label top = ComponentFactory.getLabelString(notBuildableTile.getNameOf().replace(' ', '\n'));
		Label image = ComponentFactory.getLabelImage(notBuildableTile.getPathImage());		
		Label bottom = ComponentFactory.getLabelString("$" + String.valueOf(notBuildableTile.getPrice()));
		
		AnchorPane.setBottomAnchor(top, 5.0);					
		AnchorPane.setTopAnchor(bottom, 5.0);
		AnchorPane.setTopAnchor(image, 40.0);		
		
		landPane.getChildren().addAll(top, image, bottom);
	}

	private boolean isCorner(final Tile tile) {
		return tile.getTiteType() == TiteTypes.GO || tile.getTiteType() ==  TiteTypes.GO_JAIL || 
			   tile.getTiteType() == TiteTypes.FREE_PARKING || tile.getTiteType() == TiteTypes.FREE_TRANSIT;
	}
	
	private AnchorPane getNotObtainables(final NotObtainableImpl notObtainableTile) {
		AnchorPane landPane;
		
		if(this.isCorner(notObtainableTile)) {
			landPane = ComponentFactory.getAnchorPane(true);
		}else {
			landPane = ComponentFactory.getAnchorPane(false);
		}
		
		List<String> temp = Arrays.stream(notObtainableTile.getNameOf().split("\n")).collect(Collectors.toList());
		
		Label top = ComponentFactory.getLabelString(temp.get(0));
		Label image = ComponentFactory.getLabelImage(notObtainableTile.getPathImage());
		
		if(temp.size() > 1) {
			Label bottom = ComponentFactory.getLabelString(temp.get(1));
			/**
			 * Modificare eventualmtne con setTop
			 * */
			AnchorPane.setBottomAnchor(bottom, 10.0);
			landPane.getChildren().add(bottom);
		}
		
		AnchorPane.setBottomAnchor(image, 40.0);
		
		/**
		 * Modificare eventualmtne con setBottom
		 * */
		AnchorPane.setTopAnchor(top, 10.0);
		
		landPane.getChildren().addAll(top, image);
		return landPane;
	}
}
