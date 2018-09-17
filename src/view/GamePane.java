package view;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import controller.ControllerImpl;
import controller.MovementController;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.TextAlignment;
import model.tiles.BuildableImpl;
import model.tiles.Obtainable;
import model.tiles.Tile;
import utilities.PaneDimensionSetting;
import utilities.enumerations.TileTypes;
import view.gameDialog.CardDialog;
import view.tiles.LandAbstractFactoryImp;
import view.Pawn;

/**
* 
* @author Matteo Alesiani 
*/
public class GamePane extends StackPane{
	
	private static final double ROTATE_LEFT = +90.0;
	private static final double ROTATE_RIGHT = -90.0;
	private static final double FIXLEFT = 10.6;
	private static final double FIXRIGHT = 17.0;
	private static final double LABEL_WIDTH = 190;
	
	private static GamePane GAMEPANE = null;
	private FlowPane contractPane = new FlowPane(FIXLEFT, FIXLEFT);
	private StackPane mainPane = new StackPane();
	
	
	/**
	 * TODO: Ricordarsi di gestire l'eliminazione del giocatore
	 * */
	private Map<String, Icon> iconMap;
	
	private GamePane() {
		super(new StackPane());
		this.iconMap = new HashMap<>();
		ControllerImpl.getController().getPlayers().stream()
									  .forEach(player -> iconMap.put(player.getName(), new Pawn(player.getIconPath(), player.getPosition())));
		
		this.setMinWidth(PaneDimensionSetting.getInstance().getGamePaneWidth());
		this.setMinHeight(PaneDimensionSetting.getInstance().getGamePaneHeight());
		
		this.setMaxWidth(PaneDimensionSetting.getInstance().getGamePaneWidth());
		this.setMaxHeight(PaneDimensionSetting.getInstance().getGamePaneHeight());
		
		mainPane.getChildren().addAll(background(), playerLayer(), boardLayer());

		this.getChildren().add(mainPane);
		
		contractPane.setPrefSize(PaneDimensionSetting.getInstance().getGamePaneWidth() * 0.5, PaneDimensionSetting.getInstance().getGamePaneHeight() * 0.5);
	}
	
	private Pane playerLayer() {
		Pane pane = new Pane();
		
		ControllerImpl.getController().getPlayers()
					  .stream().forEach(player -> { 
						  Pawn icon = (Pawn) this.iconMap.get(player.getName());
						  
						  icon.get().setTranslateX(icon.getCoordinates().getFirst());
						  icon.get().setTranslateY(icon.getCoordinates().getSecond());
						  icon.get().setX(-20);
						  icon.get().setY(-20);
						  
					  });
		
		pane.getChildren().addAll(this.iconMap.values().stream().map(Icon::get).collect(Collectors.toList()));
		
		return pane;
	}
	
	private AnchorPane background() {
		AnchorPane backGround = new AnchorPane();
		updateContractPane();
		
		AnchorPane.setBottomAnchor(this.contractPane, PaneDimensionSetting.getInstance().getGamePaneHeight() / 15);
		AnchorPane.setLeftAnchor(this.contractPane, PaneDimensionSetting.getInstance().getGamePaneHeight() / 12 * 2);
		AnchorPane.setRightAnchor(this.contractPane, PaneDimensionSetting.getInstance().getGamePaneHeight() / 12 * 2);
		backGround.getChildren().add(contractPane);
		
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
		AnchorPane.setTopAnchor(topNode, 0.0);
		AnchorPane.setTopAnchor(leftNode, PaneDimensionSetting.getInstance().getGamePaneHeight() / FIXLEFT);
		AnchorPane.setLeftAnchor(leftNode, PaneDimensionSetting.getInstance().getGamePaneHeight() / FIXRIGHT);
		AnchorPane.setBottomAnchor(leftNode, PaneDimensionSetting.getInstance().getGamePaneHeight() / FIXLEFT);
		AnchorPane.setTopAnchor(rightNode, PaneDimensionSetting.getInstance().getGamePaneHeight() / FIXLEFT);
		AnchorPane.setRightAnchor(rightNode, PaneDimensionSetting.getInstance().getGamePaneHeight() / FIXRIGHT);
		AnchorPane.setBottomAnchor(rightNode, PaneDimensionSetting.getInstance().getGamePaneHeight() / FIXLEFT);
		AnchorPane.setBottomAnchor(bottomNode, 0.0);
		
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
		Pawn tempIcon = (Pawn) this.iconMap.get(ControllerImpl.getController().getCurrentPlayer().getName());
		int position = ControllerImpl.getController().getCurrentPlayer().getPosition();
		int corner = this.getNextCorner(position);
		Path path = new Path();
		
		path.getElements().add(new MoveTo(tempIcon.get().getTranslateX(), tempIcon.get().getTranslateY()));
		
		if(position + movement >= corner) {			
			path.getElements().add(tempIcon.move(corner-position, 0));
			tempIcon.rotate();
			movement = movement - (corner-position);
			path.getElements().add(tempIcon.move(movement,corner-position));
			tempIcon.rotate();
			
		}else {
			path.getElements().add(tempIcon.move(movement, 0));
		}
		
		MovementController control = new MovementController().setMovement(path).setIcon(tempIcon);
		control.start();
		try {
			control.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	
	public void updateContractPane() {
		this.contractPane.getChildren().clear();
		List<Obtainable> contractList = ControllerImpl.getController().getCurrentPlayer().getProperties();
		contractList.forEach(prop -> {
			Label propertyName = new Label(prop.getNameOf());
			propertyName.setPrefSize(LABEL_WIDTH, PaneDimensionSetting.getInstance().getCommandBridgeHeight() * 0.05);
			propertyName.setAlignment(Pos.CENTER);
			propertyName.setTextAlignment(TextAlignment.CENTER);
			
			if(prop.getTileType() == TileTypes.BUILDABLE) {
				propertyName.setStyle("-fx-background-color: " + ((BuildableImpl) prop).getColorOf().getPaintValue().get() + ";");
			}else {
				propertyName.setStyle("-fx-background-color: " + Color.GREY.toString().replaceAll("0x", "#") + ";");
			}
			
			propertyName.getStyleClass().add("contractLabel");
			propertyName.setWrapText(true);
			propertyName.setOnMouseClicked(e -> CardDialog.getCardDialog().createCardDialog(prop, false)); //non funziona perché background sta sotto gli altri pane
			this.contractPane.getChildren().add(propertyName);
		});
	}
	
//	public void playProbUnexAnimation(final String message) {
//		this.getChildren().add(new ProbabilityDialog(message));
//	}
}
