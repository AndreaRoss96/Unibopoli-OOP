package view;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javafx.geometry.Pos;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import model.Board;
import model.Icon;
import model.player.PlayerInfo;
import model.player.RealPlayer;
import model.tiles.Tile;
import utilities.PaneDimensionSetting;
import view.tiles.LandAbstractFactoryImp;

/**
* 
* @author Matteo Alesiani 
*/
public class GamePane extends StackPane{
	
	private static final double ROTATE_LEFT = +90.0;
	private static final double ROTATE_RIGHT = -90.0;
	private static final double BHOO1 = 16 / 1.5;
	private static final double BHOO2 = 17;
	
	private static GamePane GAMEPANE = null;
	
	private Board board = new Board("CLASSIC");
	private StackPane mainPane = new StackPane();
	public static List<PlayerInfo> prova;
	private Pane pane;
	
	private GamePane() {
		super(new StackPane());
		
		this.setMinWidth(PaneDimensionSetting.getInstance().getGamePaneWidth());
		this.setMinHeight(PaneDimensionSetting.getInstance().getGamePaneHeight());
		
		this.setMaxWidth(PaneDimensionSetting.getInstance().getGamePaneWidth());
		this.setMaxHeight(PaneDimensionSetting.getInstance().getGamePaneHeight());
		
		mainPane.getChildren().addAll(background(), playerLayer(), boardLayer());

		this.getChildren().add(mainPane);
	}
	
	private Pane playerLayer() {
		 pane = new Pane();
		
		prova = new ArrayList<>();
		
		prova.add(new RealPlayer("Gino", 1000, new Icon("mode/classic/avatars/Car.png")));
		prova.add(new RealPlayer("Mario", 200, new Icon("mode/classic/avatars/Bowl.png")));
		prova.stream().map(player -> player.getIcon()).peek(icon -> icon.setScene(mainPane.getScene())).forEach(icon -> {
			icon.get().setLayoutX(PaneDimensionSetting.getInstance().getGamePaneWidth() - 100);
			icon.get().setLayoutY(PaneDimensionSetting.getInstance().getGamePaneHeight() - 60);
		});
		
		pane.getChildren().addAll(prova.stream().map(player -> player.getIcon().get()).collect(Collectors.toList()));
		
		return pane;
	}
	
	private AnchorPane background() {
		AnchorPane backGround = new AnchorPane();
		backGround.setId("backgroudBoard");
		
		return backGround;
	}
	
	private AnchorPane boardLayer() {
		AnchorPane lowerLayer = new AnchorPane();
		
		GridPane topNode = this.getTopNode();
		GridPane leftNode = this.getLeftNode();
		AnchorPane centreNode = this.getCenterNode();
		GridPane rightNode = this.getRightNode();
		GridPane bottomNode = this.getBottomNode();
		lowerLayer.getChildren().addAll(topNode, leftNode, centreNode, rightNode, bottomNode);
		AnchorPane.setTopAnchor(topNode, -1.0);
		AnchorPane.setTopAnchor(leftNode, PaneDimensionSetting.getInstance().getGamePaneHeight() / BHOO1);
		AnchorPane.setLeftAnchor(leftNode, PaneDimensionSetting.getInstance().getGamePaneHeight() / BHOO2);
		AnchorPane.setBottomAnchor(leftNode, PaneDimensionSetting.getInstance().getGamePaneHeight() / BHOO1);
		AnchorPane.setTopAnchor(rightNode, PaneDimensionSetting.getInstance().getGamePaneHeight() / BHOO1);
		AnchorPane.setRightAnchor(rightNode, PaneDimensionSetting.getInstance().getGamePaneHeight() / BHOO2);
		AnchorPane.setBottomAnchor(rightNode, PaneDimensionSetting.getInstance().getGamePaneHeight() / BHOO1);
		AnchorPane.setBottomAnchor(bottomNode, -1.0);
		
		return lowerLayer;
	}
	
	private GridPane builder(long skip, long limit, Pos position) {
		GridPane pane = new GridPane();
		
		board.getTiles(t -> true).stream().sorted(orderCre()).skip(skip).limit(limit)
			 .map(tile -> new LandAbstractFactoryImp().createLand(tile))
			 .forEach(land -> pane.addRow(0, land));
		
		 if(position == Pos.BOTTOM_CENTER) {
			 pane.setRotate(ROTATE_LEFT * 2);
		} else if(position == Pos.CENTER_LEFT) {
			pane.setRotate(ROTATE_RIGHT);
		} else if(position == Pos.CENTER_RIGHT) {
			pane.setRotate(ROTATE_LEFT);
		}
		
		pane.setId("gridPane");
		return pane;
	}
	
	private Comparator<? super Tile> orderCre(){
		return (o1, o2) -> o1.getPosition() - o2.getPosition();
	}
	
	private GridPane getTopNode() {
		return this.builder(20, 11, Pos.TOP_CENTER);
	}
	
	private GridPane getLeftNode() {
		return this.builder(11, 9, Pos.CENTER_LEFT);
	}
	
	private AnchorPane getCenterNode() {
		return new AnchorPane();
	}
	
	private GridPane getRightNode() {
		return this.builder(31, 9, Pos.CENTER_RIGHT);
	}
	
	private GridPane getBottomNode() {
		return this.builder(0, 11, Pos.BOTTOM_CENTER);
	}
	
	public static GamePane get() {
		if(GAMEPANE == null) {
			GAMEPANE = new GamePane();
		}
			
		return GAMEPANE;
	}
}
