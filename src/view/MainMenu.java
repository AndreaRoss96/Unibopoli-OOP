package view;

import java.awt.Toolkit;
import java.util.Optional;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utilities.AlertFactory;
import view.handlers.HandleFileChooser;

/**
 * Principal menu, here you can choose whether to start a new perch or upload
 * one
 * 
 * @author Rossolini Andrea
 *
 */
public class MainMenu extends StackPane {

	private static final double MAIN_MENU_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.70;
	private static final double MAIN_MENU_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 0.80;
	private static final double STANDARD_ANCHOR = 15d;
	private static final double BUTTON_BOX_WIDTH = MAIN_MENU_WIDTH/2 - STANDARD_ANCHOR*2;
	
	public MainMenu(Stage stage) {
		Scene scene = new Scene(this, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
		scene.getStylesheets().add(getClass().getResource("/style/styled-unique-expanded-tree.css").toExternalForm());
		stage.setScene(scene);
		stage.setWidth(MAIN_MENU_WIDTH);
		stage.setHeight(MAIN_MENU_HEIGHT);
		stage.centerOnScreen();
		stage.show();
		
		final ImageView background = new ImageView(new Image("unibopoli-logo.jpg"));
		background.fitWidthProperty().bind(stage.widthProperty());
		background.fitHeightProperty().bind(stage.heightProperty());
		this.getChildren().add(background);
		
		final AnchorPane anchorPane = new AnchorPane();
		
		final VBox leftButtonBox = new VBox();
		leftButtonBox.setPrefSize(BUTTON_BOX_WIDTH, MAIN_MENU_HEIGHT/4);
		final Button newGameBtn = new Button("New game");
		newGameBtn.setPrefWidth(BUTTON_BOX_WIDTH);
		newGameBtn.setPrefHeight(leftButtonBox.getPrefHeight()/2);
		final Button creditsBtn = new Button("Credits");
		creditsBtn.setPrefWidth(BUTTON_BOX_WIDTH);
		creditsBtn.setPrefHeight(leftButtonBox.getPrefHeight()/2);
		
		leftButtonBox.getChildren().addAll(newGameBtn, creditsBtn);
		anchorPane.getChildren().add(leftButtonBox);
		AnchorPane.setLeftAnchor(leftButtonBox, STANDARD_ANCHOR);
		AnchorPane.setBottomAnchor(leftButtonBox, STANDARD_ANCHOR);
		
		final VBox rightButtonBox = new VBox();
		rightButtonBox.setPrefSize(BUTTON_BOX_WIDTH, MAIN_MENU_HEIGHT/4);
		final Button loadGameBtn = new Button("Load game");
		loadGameBtn.setPrefWidth(BUTTON_BOX_WIDTH);
		loadGameBtn.setPrefHeight(rightButtonBox.getPrefHeight()/2);
		final Button cancelBtn = new Button("Cancel");
		cancelBtn.setPrefWidth(BUTTON_BOX_WIDTH);
		cancelBtn.setPrefHeight(rightButtonBox.getPrefHeight()/2);
		rightButtonBox.getChildren().addAll(loadGameBtn, cancelBtn);
		anchorPane.getChildren().add(rightButtonBox);
		AnchorPane.setRightAnchor(rightButtonBox, STANDARD_ANCHOR);
		AnchorPane.setBottomAnchor(rightButtonBox, STANDARD_ANCHOR);
		
		final Button helpBtn = new Button("?");
		anchorPane.getChildren().add(helpBtn);
		AnchorPane.setTopAnchor(helpBtn, STANDARD_ANCHOR);
		AnchorPane.setRightAnchor(helpBtn, STANDARD_ANCHOR);

		this.getChildren().add(anchorPane);	
		
		newGameBtn.setOnAction(e -> {
			//new PlayerSetup(stage);
		});
		
		loadGameBtn.addEventFilter(MouseEvent.MOUSE_CLICKED, new HandleFileChooser());
		
		cancelBtn.setOnAction(e -> {
			Optional<ButtonType> result = AlertFactory.createConfirmationAlert("Confirm exit", "Are you sure you want to quit from Unibopoli?", null).showAndWait();
			if (result.get().equals(ButtonType.OK)) {
				stage.close();
			}
			else e.consume();
		});
		
		creditsBtn.setOnAction(e -> {
			Alert dialog = new Alert(AlertType.INFORMATION);
			dialog.setHeaderText(null);
			dialog.setTitle("Credits");
			Label message = new Label("Alesiani Matteo: Movement and consequences, management of contracts and contingencies/probabilities, game Board graphics, initialization of the game\n"
					+ "Rossolini Andrea: Player Management, Trading & Auction, box & contracts Graphics, saving & loading of the Game, main menu and initialization of the game.");
			message.setStyle("-fx-font-family: Kabel");
			dialog.getDialogPane().setContent(message);
			dialog.show();
		});
	}

}
