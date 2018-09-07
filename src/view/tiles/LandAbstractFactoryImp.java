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
import model.tiles.AdapterBuildable;
import model.tiles.Corner;
import model.tiles.NotBuildable;
import model.tiles.NotObtainable;
import model.tiles.Tile;

import view.gameDialog.CardDialog;

/**
* 
* @author Matteo Alesiani 
*/

public class LandAbstractFactoryImp{

	public Pane createLand(final Tile tile) {
		if(tile instanceof AdapterBuildable) {
			return this.getBuildable((AdapterBuildable) tile);
		}else if(tile instanceof NotBuildable) {
			return this.getNotBuildable((NotBuildable) tile);
		}else if(tile instanceof Corner) {
			return this.getCorner((Corner) tile);
		}else if(tile instanceof NotObtainable){
			return this.getNotObtainables((NotObtainable) tile);
		}
		throw new IllegalArgumentException();
	}
	
	private AnchorPane getBuildable(final AdapterBuildable buildableTile) {
		AnchorPane landPane = ComponentFactory.getAnchorPane(false);
		
		this.getBuildables(landPane, buildableTile); 
		landPane.setOnMouseClicked(value -> ControllerImpl.getController().showContract(buildableTile));
		
		return landPane;
	}
	
	private void getBuildables(final AnchorPane landPane, final AdapterBuildable buildableTile)
	{
		Label colorFamily = ComponentFactory.getLabelColor(buildableTile.getColorOf().getPaint().get());
		Separator seperator = ComponentFactory.getSeparator(Orientation.HORIZONTAL);		
		Label textHeader = ComponentFactory.getLabelString(buildableTile.getNameOf().replace(' ', '\n'));
		Label textRent = ComponentFactory.getLabelString("$" + buildableTile.getPrice());

		AnchorPane.setBottomAnchor(colorFamily, 0.0);
		AnchorPane.setBottomAnchor(seperator, 17.0);
		AnchorPane.setBottomAnchor(textHeader, 22.0);	
		AnchorPane.setTopAnchor(textRent, 5.0);

		landPane.getChildren().addAll(colorFamily, seperator, textHeader, textRent);
	}
	
	private AnchorPane getNotBuildable(final NotBuildable notBuildableTile) {
		AnchorPane landPane = ComponentFactory.getAnchorPane(false);
		
		this.getNotBuildables(landPane, notBuildableTile);
		
		landPane.setOnMouseClicked(value -> CardDialog.getCardDialog().createCardDialog(notBuildableTile, false));
		
		return landPane;
	}
	
	private void getNotBuildables(final AnchorPane landPane, final NotBuildable notBuildableTile) {
		Label top = ComponentFactory.getLabelString(notBuildableTile.getNameOf().replace(' ', '\n'));
		Label image = ComponentFactory.getLabelImage(notBuildableTile.getImage());		
		Label bottom = ComponentFactory.getLabelString("$" + String.valueOf(notBuildableTile.getPrice()));
		
		AnchorPane.setBottomAnchor(top, 5.0);					
		AnchorPane.setTopAnchor(bottom, 5.0);
		AnchorPane.setTopAnchor(image, 40.0);		
		
		landPane.getChildren().addAll(top, image, bottom);
	}

	private AnchorPane getNotObtainables(final NotObtainable notObtainableTile) {
		AnchorPane landPane = ComponentFactory.getAnchorPane(false);
		List<String> temp = Arrays.stream(notObtainableTile.getNameOf().split("\n")).collect(Collectors.toList());
		
		Label top = ComponentFactory.getLabelString(temp.get(0));
		Label image = ComponentFactory.getLabelImage(notObtainableTile.getImage().get());
		
		if(temp.size() > 1) {
			Label bottom = ComponentFactory.getLabelString(temp.get(1));
			AnchorPane.setTopAnchor(bottom, 10.0);
			landPane.getChildren().add(bottom);
		}
		
		AnchorPane.setBottomAnchor(image, 40.0);
		AnchorPane.setBottomAnchor(top, 10.0);
		
		landPane.getChildren().addAll(top, image);
		return landPane;
	}
	
	private AnchorPane getCorner(final Corner cornerTile) {
		AnchorPane landPane = ComponentFactory.getAnchorPane(true);
		List<String> temp = Arrays.stream(cornerTile.getHeaderText().split("\n")).collect(Collectors.toList());
		
		Label top = ComponentFactory.getLabelString(temp.get(0));
		Label image = ComponentFactory.getLabelImage(cornerTile.getImage().get());
		Label bottom = ComponentFactory.getLabelString(temp.get(1));
		
		AnchorPane.setTopAnchor(image, 30.0);
		AnchorPane.setTopAnchor(top, 10.0);
		AnchorPane.setBottomAnchor(bottom, 10.0);
		
		landPane.getChildren().addAll(top, image, bottom);
		return landPane;
	}
}
