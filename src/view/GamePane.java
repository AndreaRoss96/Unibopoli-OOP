package view;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import controller.ControllerImpl;
import controller.GameLoop;
import controller.MovementController;
import javafx.geometry.Pos;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
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
	private static final double FIXLEFT = 10.6;
	private static final double FIXRIGHT = 17.0;
	
	private static GamePane GAMEPANE = null;
	private StackPane mainPane = new StackPane();
	
	/**
	 * Ricordarsi di gestire l'eliminazione del giocatore
	 * */
	private Map<String, Icon> iconMap;
	
	private GamePane() {
		super(new StackPane());
		this.iconMap = new HashMap<>();
		ControllerImpl.getController().getPlayers().stream()
									  .forEach(player -> iconMap.put(player.getName(), new Icon(player.getIconPath())));
		
		this.setMinWidth(PaneDimensionSetting.getInstance().getGamePaneWidth());
		this.setMinHeight(PaneDimensionSetting.getInstance().getGamePaneHeight());
		
		this.setMaxWidth(PaneDimensionSetting.getInstance().getGamePaneWidth());
		this.setMaxHeight(PaneDimensionSetting.getInstance().getGamePaneHeight());
		
		mainPane.getChildren().addAll(background(), playerLayer(), boardLayer());

		this.getChildren().add(mainPane);
	}
	
	private Pane playerLayer() {
		Pane pane = new Pane();
		
		this.iconMap.values().stream().peek(icon -> icon.setScene(mainPane.getScene()))
							 .forEach(icon -> { icon.get().setLayoutX(PaneDimensionSetting.getInstance().getGamePaneWidth() - 100);
							 					icon.get().setLayoutY(PaneDimensionSetting.getInstance().getGamePaneHeight() - 60);
							 });
		
		pane.getChildren().addAll(this.iconMap.values().stream().map(icon -> icon.get()).collect(Collectors.toList()));
		
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
		AnchorPane.setTopAnchor(leftNode, PaneDimensionSetting.getInstance().getGamePaneHeight() / FIXLEFT);
		AnchorPane.setLeftAnchor(leftNode, PaneDimensionSetting.getInstance().getGamePaneHeight() / FIXRIGHT);
		AnchorPane.setBottomAnchor(leftNode, PaneDimensionSetting.getInstance().getGamePaneHeight() / FIXLEFT);
		AnchorPane.setTopAnchor(rightNode, PaneDimensionSetting.getInstance().getGamePaneHeight() / FIXLEFT);
		AnchorPane.setRightAnchor(rightNode, PaneDimensionSetting.getInstance().getGamePaneHeight() / FIXRIGHT);
		AnchorPane.setBottomAnchor(rightNode, PaneDimensionSetting.getInstance().getGamePaneHeight() / FIXLEFT);
		AnchorPane.setBottomAnchor(bottomNode, -1.0);
		
		return lowerLayer;
	}
	
	private GridPane builder(long skip, long limit, Pos position) {
		GridPane pane = new GridPane();
		
		ControllerImpl.getController().getGameBoard().stream().sorted(orderCre()).skip(skip).limit(limit)
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
	
	public void movement(int movement) {
		Icon tempIcon = this.iconMap.get(ControllerImpl.getController().getCurruntPlayer().getName());
		int position = ControllerImpl.getController().getCurruntPlayer().getPosition();
		int corner = this.getNextCorner(position);
		
		if(position + movement > corner) {
			MovementController control = new MovementController().setIcon(tempIcon).setMovement(corner-position);
			control.start();
			
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			tempIcon.rotate();		
			movement = movement - (corner-position);
		}
		
		MovementController control = new MovementController().setIcon(tempIcon).setMovement(movement);
		control.start();
	}
	
	private int getNextCorner(int pos) {
		return (((int) pos / 10) + 1) * 10;
	}
	
	public static GamePane get() {
		if(GAMEPANE == null) {
			GAMEPANE = new GamePane();
		}
			
		return GAMEPANE;
	}
}
