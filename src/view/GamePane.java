package view;

import java.util.Comparator;
import java.util.stream.IntStream;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import model.Board;
import model.tiles.Tile;
import utilities.PaneDimensionSetting;

/**
* 
*  
* @author Matteo Alesiani 
*/
public class GamePane extends StackPane{
 
	private GamePane() {
		super(new StackPane());
		this.setMaxWidth(PaneDimensionSetting.getInstance().getGamePaneWidth());
		//PrefWidth(PaneDimensionSetting.getInstance().getGamePaneWidth());
		this.setPrefHeight(PaneDimensionSetting.getInstance().getGamePaneHeight());
		
		StackPane mainPane = new StackPane();
		mainPane.getChildren().addAll(background(), lowerLayer());
		
		this.getChildren().add(mainPane);
	}
	
	private AnchorPane background() {
		AnchorPane backGround = new AnchorPane();
		backGround.setId("backgroudBoard");
		//backGround.getStylesheets().add("style.css");
		
		return backGround;
	}
	
	private BorderPane lowerLayer() {
		BorderPane lowerLayer = new BorderPane();
		
		lowerLayer.setTop(this.getTopNode());
		lowerLayer.setLeft(this.getLeftNode());
		lowerLayer.setCenter(this.getCenterNode());
		lowerLayer.setRight(this.getRightNode());
		lowerLayer.setBottom(this.getBottomNode());
		
		//lowerLayer.getStylesheets().add("style.css");
		/**
		 * oppure lowerLayer.getStylesheets().add("style.css");
		 * 
		 * */
		return lowerLayer;
	}
	
	private GridPane getTopNode() {
		GridPane topGridPane = new GridPane();
		Button button1 = new Button("Button 1");
		Button button2 = new Button("Button 2");
		Button button3 = new Button("Button 3");
		Button button4 = new Button("Button 4");
		Button button5 = new Button("Button 5");
		Button button6 = new Button("Button 6");
		
		/*
		Board board = new Board("CLASSIC");
		
		IntStream.range(0, 40).skip(0)
				 .limit(11).mapToObj(t->t)
				 .forEach(values -> {topGridPane.add(new LandAbstractFactoryImp().createLand(board.getTileOf(values), 
						 																	 Pos.TOP_CENTER), 
						 							 values, 0);
			});

		/**
		 * 
		 * 	this.gameBoard.stream().sorted(Comparator.comparing(Tile::getPosition)).forEach(System.out::println);
		 *	new Board("CLASSIC").getTileBoard().
		 */
		
		topGridPane.add(button1, 0, 0, 1, 1);
		topGridPane.add(button2, 1, 0, 1, 1);
		topGridPane.add(button3, 2, 0, 1, 1);
		topGridPane.add(button4, 3, 0, 1, 1);
		topGridPane.add(button5, 4, 0, 1, 1);
		topGridPane.add(button6, 5, 0, 1, 1);
		
		return topGridPane;
	}
	
	private GridPane getLeftNode() {
		return null;
	}
	
	private GridPane getCenterNode() {
		return null;
	}
	
	private GridPane getRightNode() {
		return null;
	}
	
	private GridPane getBottomNode() {
		return null;
	}
	
	public static GamePane get() {        
		return new GamePane();
	}
}
