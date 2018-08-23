package view;

import java.util.Comparator;
import java.util.function.BiConsumer;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import model.Board;
import model.tiles.Tile;
import utilities.PaneDimensionSetting;

import view.tiles.LandAbstractFactoryImp;

/**
* 
* @author Matteo Alesiani 
*/
public class GamePane extends StackPane{
 
	private Board board = new Board("CLASSIC");
	
	private GamePane() {
		super(new StackPane());
		
		this.setMinWidth(PaneDimensionSetting.getInstance().getGamePaneWidth());
		this.setMinHeight(PaneDimensionSetting.getInstance().getGamePaneHeight());
		
		this.setMaxWidth(PaneDimensionSetting.getInstance().getGamePaneWidth());
		this.setMaxHeight(PaneDimensionSetting.getInstance().getGamePaneHeight());
		
		StackPane mainPane = new StackPane();
		mainPane.getChildren().addAll(background(), lowerLayer());
		
		this.getChildren().add(mainPane);
	}
	
	private AnchorPane background() {
		AnchorPane backGround = new AnchorPane();
		backGround.setId("backgroudBoard");
		
		return backGround;
	}
	
	private AnchorPane lowerLayer() {
		AnchorPane lowerLayer = new AnchorPane();
		
//		lowerLayer.setTop(this.getTopNode());
//		lowerLayer.setLeft(this.getLeftNode());
//		lowerLayer.setCenter(this.getCenterNode());
//		lowerLayer.setRight(this.getRightNode());
//		lowerLayer.setBottom(this.getBottomNode());
		GridPane topNode = this.getTopNode();
		GridPane leftNode = this.getLeftNode();
		GridPane rightNode = this.getRightNode();
		GridPane bottomNode = this.getBottomNode();
		lowerLayer.getChildren().addAll(topNode, leftNode, rightNode, bottomNode);
		AnchorPane.setTopAnchor(topNode, -1.0);
		AnchorPane.setTopAnchor(leftNode, PaneDimensionSetting.getInstance().getGamePaneHeight() / 16 * 1.5);
		AnchorPane.setLeftAnchor(leftNode, PaneDimensionSetting.getInstance().getGamePaneHeight() / 17);
		AnchorPane.setBottomAnchor(leftNode, PaneDimensionSetting.getInstance().getGamePaneHeight() / 16 * 1.5);
		AnchorPane.setTopAnchor(rightNode, PaneDimensionSetting.getInstance().getGamePaneHeight() / 16 * 1.5);
		AnchorPane.setRightAnchor(rightNode, PaneDimensionSetting.getInstance().getGamePaneHeight() / 17);
		AnchorPane.setBottomAnchor(rightNode, PaneDimensionSetting.getInstance().getGamePaneHeight() / 16 * 1.5);
		AnchorPane.setBottomAnchor(bottomNode, -1.0);
		
		return lowerLayer;
	}
	
	private GridPane builder(long skip, long limit, Pos position, BiConsumer<Pane, GridPane> consumer, boolean order) {
		GridPane pane = new GridPane();
		
		board.getTiles(t -> true).stream().sorted(orderCre()).skip(skip).limit(limit)
			 .sorted(order ? orderCre() : orderDec()).map(tile -> new LandAbstractFactoryImp().createLand(tile, position))
			 .forEach(land -> consumer.accept(land, pane));
		
		 if(position == Pos.BOTTOM_CENTER) {
			 pane.setRotate(90 * 2);
		} else if(position == Pos.CENTER_LEFT) {
			pane.setRotate(-90);
		} else if(position == Pos.CENTER_RIGHT) {
			pane.setRotate(90);
		}
		
		pane.setId("gridPane");
		return pane;
	}
	
	private Comparator<? super Tile> orderDec(){
		return (o1, o2) -> o2.getPosition() - o1.getPosition();
	}
	
	private Comparator<? super Tile> orderCre(){
		return (o1, o2) -> o1.getPosition() - o2.getPosition();
	}
	
	private GridPane getTopNode() {
		return this.builder(20, 11, Pos.TOP_CENTER, (land, pane) -> pane.addRow(0, land), true);
	}
	
	private GridPane getLeftNode() {
		return this.builder(11, 9, Pos.CENTER_LEFT, (land, pane) -> pane.addRow(0, land), false);
	}
	
	private GridPane getCenterNode() {
		return null;
	}
	
	private GridPane getRightNode() {
		return this.builder(31, 9, Pos.CENTER_RIGHT, (land, pane) -> pane.addRow(0, land), true);
	}
	
	private GridPane getBottomNode() {
		return this.builder(0, 11, Pos.BOTTOM_CENTER, (land, pane) -> pane.addRow(0, land), false);
	}
	
	public static GamePane get() {        
		return new GamePane();
	}
}
