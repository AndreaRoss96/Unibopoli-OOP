package view.gameSetup;

import java.io.File;
import java.util.Map;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import utilities.PaneDimensionSetting;
import view.comboBoxCell.*;

public class PlayerSetupBox extends HBox {

	private static final double INSETS_VALUE = 15;

	private final TextField nameField;
	private final ComboBox<String> icons;
	private final Button removePlayer;

	public PlayerSetupBox(Map<String, String> imageMap) {
		this.setPrefWidth(Region.USE_COMPUTED_SIZE);
		nameField = new TextField("Insert player name...");
		this.icons = new ComboBox<>();
		this.icons.setPrefWidth(PaneDimensionSetting.getInstance().getCommandBridgeWidth() * 0.10);
		this.icons.setCellFactory(new ShapeCellFactory(imageMap));
		this.icons.setButtonCell(new ShapeCell(imageMap));
		HBox.setMargin(this.icons, new Insets(0, INSETS_VALUE, 0, INSETS_VALUE));

		removePlayer = new Button("",
				new ImageView(new Image("images" + File.separator + "Icons" + File.separator + "trash.png")));

		this.getChildren().addAll(nameField, icons, removePlayer);
		this.setAlignment(Pos.CENTER);
		this.setId("setupBox");
		this.getStylesheets().add(getClass().getResource("/style/style.css").toExternalForm());
	}

	/**
	 * Returns the textField where the player write his/her name
	 * 
	 * @return TextField
	 */
	public TextField getNameField() {
		return this.nameField;
	}

	/**
	 * Returns the comboBox where the player choose his/her avatar
	 * 
	 * @return ComboBox<String>
	 */
	public ComboBox<String> getIcons() {
		return this.icons;
	}

	/**
	 * Return the button to remove a player
	 * 
	 * @return button
	 */
	public Button getRemovePlayer() {
		return this.removePlayer;
	}

}
