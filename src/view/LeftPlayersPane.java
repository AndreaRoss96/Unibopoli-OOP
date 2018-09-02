package view;

import java.util.List;
import java.util.stream.Collectors;

import controller.ControllerImpl;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.player.PlayerInfo;
import model.tiles.Obtainable;
import utilities.PaneDimensionSetting;
import javafx.scene.layout.StackPane;

/**
 * Lateral pane that show names and money of all player.
 * 
 * @author Rossolini Andrea
 */
public class LeftPlayersPane {
	
	private static final LeftPlayersPane SINGLETON = new LeftPlayersPane();
	
	private static final double PREF_WIDTH = PaneDimensionSetting.getInstance().getLateralPaneWidth();
	private static final double PREF_HEIGH = PaneDimensionSetting.getInstance().getLateralPaneHeight();

	private static final Insets INSETS = new Insets(0, 0, 10, 0);
	
	private static VBox root;
	
	public static LeftPlayersPane getLeftPlayersPane() {
		return SINGLETON;
	}
	
	private LeftPlayersPane() {
		root = new VBox();
		root.setPrefSize(PaneDimensionSetting.getInstance().getLateralPaneWidth(), PREF_HEIGH); //PERCHé NON MI PRENDE LA MACRO?
		root.setId("LeftPlayersPane");
		root.getStylesheets().add("/style/style.css");
		
		updatePane();
	}
	
	/**
	 * Update the pane, in this way it can show exact amount of money owned by each
	 * player and eventually if a player leave the game.
	 */
	public void updatePane() {
		root.getChildren().clear();
		
		List<PlayerInfo> playerList = ControllerImpl.getController().getPlayers(); 
		playerList.forEach(e -> {
			final Label playerLabel = new Label(e.getName() + "\n" + e.getMoney() + "$");
			playerLabel.setGraphic(new ImageView(new Image(e.getIconPath())));
			playerLabel.setStyle("-fx-border-color: black; -fx-border-radius: 10px;");
			playerLabel.setPrefWidth(PREF_WIDTH);
			
			final StackPane shadowedPane = new StackPane(playerLabel);
			
			if(e.getName().equals(ControllerImpl.getController().getCurrentPlayer().getName())) {
				final DropShadow ds = new DropShadow(10.0, 0.0, 0.0, Color.GOLD);
				ds.setSpread(0.7);
				shadowedPane.setEffect(ds);
			}
			
			final String propertiesName = e.getProperties().stream().map(Obtainable::getNameOf).collect(Collectors.toList()).toString();
			final Tooltip tooltip = new Tooltip(propertiesName.substring(1, propertiesName.length() - 1).replaceAll(",", "\n"));
			
			playerLabel.setTooltip(tooltip);
			root.getChildren().add(shadowedPane);
			VBox.setMargin(shadowedPane, INSETS);
		});
	}

	/**
	 * Getter for the lateral player list
	 * 
	 * @return VBox
	 */
	public VBox get() {
		updatePane();
		return root;
	}
}
