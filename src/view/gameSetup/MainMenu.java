package view.gameSetup;

import java.awt.Toolkit;
import java.util.Optional;

import controller.SoundController;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
import view.handlers.HandleFileChooser;

/**
 * Principal menu, here you can choose whether to start a new play or upload
 * one
 * 
 * @author Rossolini Andrea
 *
 */
public final class MainMenu {

	private static final double MAIN_MENU_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.70;
	private static final double MAIN_MENU_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 0.80;
	private static final double STANDARD_ANCHOR = 15d;
	private static final double BUTTON_BOX_WIDTH = MAIN_MENU_WIDTH / 2 - STANDARD_ANCHOR * 2;
	private static SoundController music;

	public MainMenu(Stage stage) {
		StackPane root = new StackPane();
		Scene scene = new Scene(root, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
		scene.getStylesheets().add(getClass().getResource("/style/styled-unique-expanded-tree.css").toExternalForm());
		stage.setScene(scene);
		stage.setWidth(MAIN_MENU_WIDTH);
		stage.setHeight(MAIN_MENU_HEIGHT);
		stage.centerOnScreen();
		stage.show();

		final ImageView background = new ImageView(new Image("unibopoli-logo.jpg"));
		background.fitWidthProperty().bind(stage.widthProperty());
		background.fitHeightProperty().bind(stage.heightProperty());
		root.getChildren().add(background);

		final AnchorPane anchorPane = new AnchorPane();

		final VBox leftButtonBox = new VBox();
		leftButtonBox.setPrefSize(BUTTON_BOX_WIDTH, MAIN_MENU_HEIGHT / 4);
		final Button newGameBtn = new Button("New game");
		newGameBtn.setPrefWidth(BUTTON_BOX_WIDTH);
		newGameBtn.setPrefHeight(leftButtonBox.getPrefHeight() / 2);
		final Button creditsBtn = new Button("Credits");
		creditsBtn.setPrefWidth(BUTTON_BOX_WIDTH);
		creditsBtn.setPrefHeight(leftButtonBox.getPrefHeight() / 2);

		leftButtonBox.getChildren().addAll(newGameBtn, creditsBtn);
		anchorPane.getChildren().add(leftButtonBox);
		AnchorPane.setLeftAnchor(leftButtonBox, STANDARD_ANCHOR);
		AnchorPane.setBottomAnchor(leftButtonBox, STANDARD_ANCHOR);

		final VBox rightButtonBox = new VBox();
		rightButtonBox.setPrefSize(BUTTON_BOX_WIDTH, MAIN_MENU_HEIGHT / 4);
		final Button loadGameBtn = new Button("Load game");
		loadGameBtn.setPrefWidth(BUTTON_BOX_WIDTH);
		loadGameBtn.setPrefHeight(rightButtonBox.getPrefHeight() / 2);
		final Button cancelBtn = new Button("Cancel");
		cancelBtn.setPrefWidth(BUTTON_BOX_WIDTH);
		cancelBtn.setPrefHeight(rightButtonBox.getPrefHeight() / 2);
		rightButtonBox.getChildren().addAll(loadGameBtn, cancelBtn);
		anchorPane.getChildren().add(rightButtonBox);
		AnchorPane.setRightAnchor(rightButtonBox, STANDARD_ANCHOR);
		AnchorPane.setBottomAnchor(rightButtonBox, STANDARD_ANCHOR);

		final Button helpBtn = new Button("?");
		anchorPane.getChildren().add(helpBtn);
		AnchorPane.setTopAnchor(helpBtn, STANDARD_ANCHOR);
		AnchorPane.setRightAnchor(helpBtn, STANDARD_ANCHOR);

		final Button musicBtn = new Button("", new ImageView(new Image("/res/images/Icons/Music.png")));
		anchorPane.getChildren().add(musicBtn);
		AnchorPane.setTopAnchor(musicBtn, STANDARD_ANCHOR);
		AnchorPane.setLeftAnchor(musicBtn, STANDARD_ANCHOR);

		final Button soundBtn = new Button("", new ImageView(new Image("/res/images/Icons/Sound.png")));
		anchorPane.getChildren().add(soundBtn);
		AnchorPane.setTopAnchor(soundBtn, STANDARD_ANCHOR);
		AnchorPane.setLeftAnchor(soundBtn, STANDARD_ANCHOR * 2);

		root.getChildren().add(anchorPane);

		newGameBtn.setOnAction(e -> {
			// new PlayerSetup(stage);
		});

		loadGameBtn.addEventFilter(MouseEvent.MOUSE_CLICKED, new HandleFileChooser());

		cancelBtn.setOnAction(e -> {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirm exit");
			alert.setHeaderText(null);
			alert.setContentText("Are you sure you want to quit from Unibopoli?");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get().equals(ButtonType.OK)) {
				stage.close();
			} else {
				e.consume();
			}
		});

		creditsBtn.setOnAction(e -> {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText(null);
			alert.setTitle("Credits");
			Label message = new Label(
					"Alesiani Matteo: Movement and consequences, management of contracts and contingencies/probabilities, game Board graphics and game turn\n" +
					"Rossolini Andrea: Player Management, Trading & Auction, box & contracts Graphics, saving & loading of the Game, main menu and initialization of the game.");
			message.setStyle("-fx-font-family: Kabel");
			alert.getDialogPane().setContent(message);
			alert.show();
		});

		musicBtn.setOnAction(e -> {
			music.changeMusicMute();
			if (music.isMusicMute()) {
				musicBtn.setGraphic(new ImageView(new Image("/res/images/Icons/No_Music.png")));
			} else {
				musicBtn.setGraphic(new ImageView(new Image("/res/images/Icons/Music.png")));
			}
		});

		soundBtn.setOnAction(e -> {
			music.changeSoundsMute();
			if (music.isSoundMute()) {
				soundBtn.setGraphic(new ImageView(new Image("/res/images/Icons/No_sound.png")));
			} else {
				soundBtn.setGraphic(new ImageView(new Image("/res/images/Icons/Sound.png")));
			}
		});
	}

	public static void setBeckgroundMusic(final SoundController backgroundMusic) {
		music = backgroundMusic;
	}
}
