package view.gameSetup;

import java.awt.Toolkit;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import view.ShapeCell;
import view.ShapeCellFactory;

public class PlayerSetupBox extends HBox{

	private final TextField nameField;
	private final ComboBox<String> icons;
	private final Button removePlayer;
	
	public PlayerSetupBox() {
		this.setPrefWidth(Region.USE_COMPUTED_SIZE);
		nameField = new TextField("Insert player name...");
		
		this.icons = new ComboBox<>();
		this.icons.setPrefWidth(Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.10);
		this.icons.setCellFactory(new ShapeCellFactory());
		this.icons.setButtonCell(new ShapeCell());
		HBox.setMargin(this.icons, new Insets(0, 15, 0, 15));
		
		removePlayer = new Button("", new ImageView(new Image("/Icone/icons8-trash-40 (1).png")));
		
		this.getChildren().addAll(nameField, icons, removePlayer);
		this.setAlignment(Pos.CENTER);
		this.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;" + "-fx-border-width: 2;"
				+ "-fx-border-insets: 5;" + "-fx-border-radius: 5;" + "-fx-border-color: blue;"
				+ "-fx-background-color: white;");
	}
	
	/**
	 * 
	 * @return
	 */
	public TextField getNameField() {
		return this.nameField;
	}
	
	/**
	 * 
	 * @return
	 */
	public ComboBox<String> getIcons() {
		return this.icons;
	}
	
	/**
	 * 
	 * @return
	 */
	public Button getRemovePlayer() {
		return this.removePlayer;
	}

}
