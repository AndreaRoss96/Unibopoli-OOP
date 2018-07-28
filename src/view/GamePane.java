package view;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import utilities.PaneDimensionSetting;

public class GamePane extends StackPane{
 
	private GamePane() {
		super(new StackPane());
		this.setPrefWidth(PaneDimensionSetting.getInstance().getGamePaneWidth());
		this.setPrefHeight(PaneDimensionSetting.getInstance().getGamePaneHeight());
		
		StackPane mainPane = new StackPane();
		mainPane.getChildren().addAll(lowerLayer());
		
		this.getChildren().add(mainPane);
	}
	
	private BorderPane lowerLayer() {
		BorderPane lowerLayer = new BorderPane();
		
		//lowerLayer.getStylesheets().add();
		return lowerLayer;
	}
	
	
	public static GamePane get() {        
		return new GamePane();
	}
}
