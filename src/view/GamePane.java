package view;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import controller.ControllerImpl;
import controller.MovementController;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.TextAlignment;
import model.tiles.BuildableImpl;
import model.tiles.Obtainable;
import utilities.Pair;
import utilities.PaneDimensionSetting;
import utilities.enumerations.Consequences;
import utilities.enumerations.TileTypes;
import view.tiles.LandAbstractFactoryImp;
import view.Pawn;

/**
* 
* @author Matteo Alesiani 
*/
public class GamePane extends StackPane{
	
	private static final double ROTATE = 90.0;
	private static final double FIXLEFT = 5.0;
	private static final double LABEL_WIDTH = 190;
	
	private static GamePane GAMEPANE = null;
	private FlowPane contractPane;
	private StackPane mainPane = new StackPane();
	
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
		backGround.setId("backgroudBoard");
		return backGround;
	}
	
	private BorderPane boardLayer() {
		BorderPane boardLayer = new BorderPane();
		boardLayer.setMinWidth(PaneDimensionSetting.getInstance().getGamePaneWidth());
		boardLayer.setMinHeight(PaneDimensionSetting.getInstance().getGamePaneHeight());
		
		boardLayer.setMaxWidth(PaneDimensionSetting.getInstance().getGamePaneWidth());
		boardLayer.setMaxHeight(PaneDimensionSetting.getInstance().getGamePaneHeight());
				
		boardLayer.setBottom(this.getBottomNode());
		boardLayer.setLeft(this.getLeftNode());
		boardLayer.setCenter(this.getCenterNode());
		boardLayer.setRight(this.getRightNode());
		boardLayer.setTop(this.getTopNode());
		
		return boardLayer;
	}
	
	private GridPane builder(long skip, long limit, double rotate) {
		GridPane pane = new GridPane();
		
		ControllerImpl.getController().getGameBoard().stream().sorted((o1, o2) -> o1.getPosition() - o2.getPosition())
			 .skip(skip).limit(limit)
			 .map(tile -> new LandAbstractFactoryImp().createLand(tile))
			 .forEach(land -> pane.addRow(0, land));
		
		pane.setRotate(rotate);
		pane.setId("gridPane");
		
		return pane;
	}
	
	private GridPane getTopNode() {
		return this.builder(20, 11, 0.0);
	}
	
	private GridPane getLeftNode() {
		return this.builder(11, 9, ROTATE * 3);
	}
	
	private FlowPane getCenterNode() {
		this.contractPane = new FlowPane(FIXLEFT, FIXLEFT);

		this.contractPane.setAlignment(Pos.CENTER);
		this.contractPane.setOrientation(Orientation.VERTICAL);
		this.contractPane.setMinWidth(PaneDimensionSetting.getInstance().getGamePaneWidth() * 0.7);

		updateContractPane();
		
		return this.contractPane;
	}
	
	private GridPane getRightNode() {
		return this.builder(31, 9, ROTATE);
	}
	
	private GridPane getBottomNode() {
		return this.builder(0, 11, ROTATE * 2);
	}
	
	private LineTo getElement(final Pawn pawn, final int movement) {
		final LineTo lineTo = pawn.move(movement);
		pawn.setCoordinates(new Pair<Double>(lineTo.getX(), lineTo.getY()));
		
		return lineTo;
	}
	
	/**
	 * TODO: controllare se esce 12 e quando va in carcere.
	 * */
	public void movement(int movement) {
		Pawn tempIcon = (Pawn) this.iconMap.get(ControllerImpl.getController().getCurrentPlayer().getName());
		int position = ControllerImpl.getController().getCurrentPlayer().getPosition();
		int corner = this.getNextCorner(position);
		Path path = new Path();
		
		path.getElements().add(new MoveTo(tempIcon.getCoordinates().getFirst(), tempIcon.getCoordinates().getSecond()));
		
		if(position + movement >= corner) {			
			path.getElements().add(this.getElement(tempIcon, corner-position-1));
			tempIcon.rotate();
			path.getElements().add(this.getElement(tempIcon, 0));
			tempIcon.rotate();
			movement = movement - (corner-position);
				
			if(corner == 40) {
				Consequences.RECEIVE.exec(Arrays.asList("200"));
			}
		}
		
		path.getElements().add(this.getElement(tempIcon, movement));
		MovementController control = new MovementController().setMovement(path).setIcon(tempIcon);
		control.start();
	}
	
	/**
	 * TODO: Ricordarsi di gestire l'eliminazione del giocatore
	 * */
	public void deletePlayer(final String player) {
		this.iconMap.remove(player);
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
			final Label propertyName = new Label(prop.getNameOf());
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
			
			this.contractPane.getChildren().add(propertyName);
		});
	}
	
	public Pane getRoot() {
		return this.mainPane;
	}
}
