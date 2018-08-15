package view;

import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import model.player.PlayerInfo;
import utilities.PaneDimensionSetting;

/**
 * Lateral pane that show names and money of all player.
 * 
 * @author Rossolini Andrea
 */
public class LeftPlayersPane extends VBox{
	
	public LeftPlayersPane(List<PlayerInfo> playerList) {
		super(new VBox());
		this.setPrefSize(PaneDimensionSetting.getInstance().getLateralPaneWidth(),
				PaneDimensionSetting.getInstance().getLateralPaneHeight());
//		this.setStyle("-fx-background-color: Red");
		updatePane(playerList);
	}
	
	/**
	 * Update the pane, in this way it can show exact amount of money owned by each
	 * player and eventually if a player leave the game.
	 */
	public void updatePane(List<PlayerInfo> playerList) {
		playerList.forEach(e -> {
			Label playerLabel = new Label(e.getName() + "\n" + e.getMoney());
			playerLabel.setGraphic(new ImageView(new Image("")));//imagePath
			playerLabel.setPrefSize(PaneDimensionSetting.getInstance().getLateralPaneWidth()/2, Region.USE_COMPUTED_SIZE);
			playerLabel.setStyle("-fx-border-color: black; -fx-border-radius: 10px; -fx-effect: dropshadow( one-pass-box , black , 8 , 0.0 , 2 , 0 )");
			this.getChildren().add(playerLabel);
			VBox.setMargin(playerLabel, new Insets(0, 0, 10, 0));
		});
	}

	/**
	 * Getter for the lateral player list
	 * 
	 * @return VBox
	 */
	public PlayersPane get() {
		return this;
	}
}
