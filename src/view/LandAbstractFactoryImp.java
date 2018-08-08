package view;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import model.tiles.Buildable;
import model.tiles.Corner;
import model.tiles.NotBuildable;
import model.tiles.NotObtainable;
import model.tiles.Tile;
import utilities.PaneDimensionSetting;

/**
* 
* @author Matteo Alesiani 
*/
public class LandAbstractFactoryImp implements LandAbstractFactory{

	private static final double LandSimpleWIDTH = PaneDimensionSetting.getInstance().getGamePaneHeight() / 13;
	private static final double LandHEIGHT = LandSimpleWIDTH * 2;
	private static final double LandCornerDimension = LandHEIGHT;
	private static final double ROTATE_LEFT = +90.0;
	private static final double ROTATE_RIGHT = -90.0;
	
	/**
	 * Chiamarle tutto con lo stesso nome ?!?!
	 * Utilizzare troppi static non fa perdere l'essenza degli oggetti ?
	 * 
	 * */
	@Override
	public Pane createLand(final Tile tile, final Pos position) {
		if(tile instanceof Buildable) {
			return this.getBuildable((Buildable) tile, position);
		}else if(tile instanceof NotBuildable) {
			return this.getNotBuildable((NotBuildable) tile, position);
		}else if(tile instanceof Corner) {
			return this.getCorner((Corner) tile, position);
		}else {
			return null;
		}
	}
	
	private void setDimension(final AnchorPane landPane, final Pos position) {
		
		if(position == Pos.CENTER_LEFT || position == Pos.CENTER_RIGHT) {
			landPane.setPrefHeight(LandSimpleWIDTH);
			landPane.setPrefWidth(LandHEIGHT);
		}else {
			landPane.setPrefHeight(LandHEIGHT);
			landPane.setPrefWidth(LandSimpleWIDTH);
		}
	}
	
	private AnchorPane getBuildable(final Buildable buildableTile, final Pos position) {
		AnchorPane landPane = new AnchorPane();
		this.setDimension(landPane, position);
		
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
		Label colorFamily = new Label();
		colorFamily.setBackground(new Background(new BackgroundFill(buildableTile.getColorOf().getPaint().get(), CornerRadii.EMPTY, Insets.EMPTY)));
		AnchorPane.setTopAnchor(colorFamily, 0.0);
		AnchorPane.setBottomAnchor(colorFamily, 0.0);
		
		Separator seperator = new Separator(Orientation.VERTICAL);
		AnchorPane.setTopAnchor(seperator, 0.0);
		AnchorPane.setBottomAnchor(seperator, 0.0);
		
		Label textHeader = new Label(buildableTile.getNameOf());
		textHeader.setRotate(position == Pos.CENTER_LEFT ? ROTATE_LEFT : ROTATE_RIGHT);
		
		/**
		 * PROVAREEEE 
		 * */
		textHeader.setAlignment(Pos.CENTER);
		/**
		 * TODO: che valore va inserito ?? PRICE ?? MORTGAGE ?? RENT ??
		 * */
		Label textRent = new Label("$" + buildableTile.getPrice());
		textRent.setRotate(position == Pos.CENTER_LEFT ? ROTATE_LEFT : ROTATE_RIGHT);
		
		if(position == Pos.CENTER_LEFT) {
			AnchorPane.setRightAnchor(colorFamily, 0.0);
			AnchorPane.setRightAnchor(seperator, 18.0);
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
		Label colorFamily = new Label();
		colorFamily.setBackground(new Background(new BackgroundFill(buildableTile.getColorOf().getPaint().get(), CornerRadii.EMPTY, Insets.EMPTY)));
		AnchorPane.setLeftAnchor(colorFamily, 0.0);
		AnchorPane.setRightAnchor(colorFamily, 0.0);
		
		Separator seperator = new Separator(Orientation.HORIZONTAL);
		AnchorPane.setLeftAnchor(seperator, 0.0);
		AnchorPane.setRightAnchor(seperator, 0.0);
		
		Label textHeader = new Label(buildableTile.getNameOf());
		
		/**
		 * TODO: che valore va inserito ?? PRICE ?? MORTGAGE ?? RENT ??
		 * */
		Label textRent = new Label("$" + buildableTile.getPrice());
		
		if(position == Pos.TOP_CENTER) {
			AnchorPane.setTopAnchor(colorFamily, 0.0);
			AnchorPane.setTopAnchor(seperator, 18.0);
			AnchorPane.setTopAnchor(textHeader, 22.0);	
			AnchorPane.setBottomAnchor(textRent, 5.0);
		}else {
			AnchorPane.setBottomAnchor(colorFamily, 0.0);
			AnchorPane.setBottomAnchor(seperator, 18.0);
			AnchorPane.setBottomAnchor(textHeader, 22.0);	
			AnchorPane.setTopAnchor(textRent, 5.0);
		}
		
		/**
		 * TODO: testare se va bene metterlo anche fuori l'IF. Se non rispetta le specifiche agire di conseguenza.
		 * 
		 * Altra cosa da verificare è se inserendo un gridPane gli ultimi 3 elementi si adattano al Pane ospitante.
		 * */
		landPane.getChildren().addAll(colorFamily, seperator, textHeader, textRent);
	}
	
	private AnchorPane getNotBuildable(final NotBuildable buildableTile, final Pos position) {
		AnchorPane landPane = new AnchorPane();
		this.setDimension(landPane, position);
		
		switch(position) {
		case CENTER_LEFT: break;
		case CENTER_RIGHT:  break;
		case TOP_CENTER:  break;
		case BOTTOM_CENTER:  break;
		default: throw new IllegalArgumentException();
	}
	
	return landPane;
	}
	
	
	private AnchorPane getCorner(final Corner cornerTile, final Pos position) {
		AnchorPane landPane = new AnchorPane();
		landPane.setPrefHeight(LandCornerDimension);
		landPane.setPrefWidth(LandCornerDimension);
		
		List<String> temp = Arrays.stream(cornerTile.getHeaderText().split("\n")).collect(Collectors.toList());
		
		Label top = new Label(temp.get(0));
		top.setAlignment(Pos.CENTER);
		
		ImageView image = new ImageView(cornerTile.getImage().getImage());
		image.setFitWidth(LandCornerDimension*80/100);
		image.setFitHeight(LandCornerDimension*80/100);
		AnchorPane.setTopAnchor(image, 13.0);
		AnchorPane.setLeftAnchor(image, LandCornerDimension*25/100);
		
		Label bottom = new Label(temp.get(1));
		bottom.setAlignment(Pos.CENTER);
		
		
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
