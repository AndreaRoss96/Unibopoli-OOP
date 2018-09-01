package view;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.sun.scenario.effect.light.Light;

import controller.ControllerImpl;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.Light.Point;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.player.PlayerInfo;
import model.tiles.Obtainable;
import utilities.PaneDimensionSetting;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Lateral pane that show names and money of all player.
 * 
 * @author Rossolini Andrea
 */
public class LeftPlayersPane extends VBox{
	
	private static Label playerLabel;
	private static Tooltip tooltip;
	
	public LeftPlayersPane(List<PlayerInfo> playerList) {
		super(new VBox());
		playerLabel = new Label();
		playerLabel.setPrefSize(PaneDimensionSetting.getInstance().getLateralPaneWidth(), Region.USE_PREF_SIZE);
		playerLabel.setStyle("-fx-border-color: black; -fx-border-radius: 10px; -fx-effect: dropshadow( three-pass-box, purple, 10, 0, 0, 0 )");
		
		tooltip = new Tooltip();
		playerLabel.setTooltip(tooltip);
		
		this.setPrefSize(PaneDimensionSetting.getInstance().getLateralPaneWidth(),
				PaneDimensionSetting.getInstance().getLateralPaneHeight());
		this.setId("LeftPlayersPane");
		this.getStylesheets().add("/style/style.css");
		this.getChildren().add(playerLabel);
		VBox.setMargin(playerLabel, new Insets(0, 0, 10, 0));
		updatePane(playerList);
	}
	
	/**
	 * Update the pane, in this way it can show exact amount of money owned by each
	 * player and eventually if a player leave the game.
	 */
	public static void updatePane(List<PlayerInfo> playerList) {
		playerList.forEach(e -> {
			playerLabel.setText(
					e.getName() + "\n" + e.getMoney() + "$");
			playerLabel.setGraphic(new ImageView(new Image(e.getIconPath())));
			if(e.getName().equals(ControllerImpl.getController().getCurruntPlayer().getName())) {
				 	Point lightPoint = new Point();
			        lightPoint.setColor(Color.GOLD);
			        Lighting lighting = new Lighting();
			        lighting.setLight(lightPoint);
			        lighting.setSurfaceScale(5.0);
			        lighting.setDiffuseConstant(1.0);
			        playerLabel.setEffect(lighting);
			}
			
			final String propertiesName = e.getProperties().stream().map(Obtainable::getNameOf).collect(Collectors.toList()).toString();
			tooltip.setText(propertiesName.substring(1, propertiesName.length() - 1).replaceAll(",", "\n"));
		});
	}

	/**
	 * Getter for the lateral player list
	 * 
	 * @return VBox
	 */
	public LeftPlayersPane get() {
		return this;
	}
}
