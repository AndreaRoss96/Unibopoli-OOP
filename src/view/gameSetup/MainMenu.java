package view.gameSetup;


import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import controller.SoundController;
import utilities.IconLoader;
import utilities.PaneDimensionSetting;
import utilities.enumerations.ClassicType;
import view.AlertFactory;
import view.CommandBridge;
import view.handlers.HandleFileChooser;

/**
 * Principal menu, here you can choose whether to start a new play or upload
 * an old one.
 * 
 * @author Rossolini Andrea
 *
 */
public final class MainMenu extends Scene{

	private static final double MAIN_MENU_WIDTH = PaneDimensionSetting.getInstance().getCommandBridgeWidth() * 0.70;
	private static final double MAIN_MENU_HEIGHT = PaneDimensionSetting.getInstance().getCommandBridgeHeight() * 0.80;
	private static final double STANDARD_ANCHOR = 15d;
	private static final double BUTTON_BOX_WIDTH = MAIN_MENU_WIDTH / 2 - STANDARD_ANCHOR * 2;
	
	private static SoundController music;
	private static Stage mainStage;
	
	private MainMenu() {
		super(new StackPane(), MAIN_MENU_WIDTH, MAIN_MENU_HEIGHT);
		StackPane root = new StackPane();
	
		final AnchorPane anchorPane = new AnchorPane();
		
		final VBox leftButtonBox = new VBox();
		leftButtonBox.setPrefSize(BUTTON_BOX_WIDTH, MAIN_MENU_HEIGHT / 4);
		final Button newGameBtn = new Button("New game");
		newGameBtn.getStyleClass().add("mainMenuButton");
		newGameBtn.setPrefWidth(BUTTON_BOX_WIDTH);
		newGameBtn.setPrefHeight(leftButtonBox.getPrefHeight() / 2);
		final Button creditsBtn = new Button("Credits");
		creditsBtn.getStyleClass().add("mainMenuButton");
		creditsBtn.setPrefWidth(BUTTON_BOX_WIDTH);
		creditsBtn.setPrefHeight(leftButtonBox.getPrefHeight() / 2);
		leftButtonBox.getChildren().addAll(newGameBtn, creditsBtn);
		AnchorPane.setLeftAnchor(leftButtonBox, STANDARD_ANCHOR);
		AnchorPane.setBottomAnchor(leftButtonBox, STANDARD_ANCHOR);

		final VBox rightButtonBox = new VBox();
		rightButtonBox.setPrefSize(BUTTON_BOX_WIDTH, MAIN_MENU_HEIGHT / 4);
		final Button loadGameBtn = new Button("Load game");
		loadGameBtn.setPrefWidth(BUTTON_BOX_WIDTH);
		loadGameBtn.setPrefHeight(rightButtonBox.getPrefHeight() / 2);
		loadGameBtn.getStyleClass().add("mainMenuButton");
		final Button cancelBtn = new Button("Cancel");
		cancelBtn.setPrefWidth(BUTTON_BOX_WIDTH);
		cancelBtn.setPrefHeight(rightButtonBox.getPrefHeight() / 2);
		cancelBtn.getStyleClass().add("mainMenuButton");
		rightButtonBox.getChildren().addAll(loadGameBtn, cancelBtn);
		AnchorPane.setRightAnchor(rightButtonBox, STANDARD_ANCHOR);
		AnchorPane.setBottomAnchor(rightButtonBox, STANDARD_ANCHOR);

		final Button helpBtn = new Button("?");
		AnchorPane.setTopAnchor(helpBtn, STANDARD_ANCHOR);
		AnchorPane.setRightAnchor(helpBtn, STANDARD_ANCHOR);

		final Button musicBtn = new Button("", IconLoader.getLoader().getImageFromPath(ClassicType.Other.GeneralOthersMap.getMusicImage()).get());
		AnchorPane.setTopAnchor(musicBtn, STANDARD_ANCHOR);
		AnchorPane.setLeftAnchor(musicBtn, STANDARD_ANCHOR);

		final Button soundBtn = new Button("", IconLoader.getLoader().getImageFromPath(ClassicType.Other.GeneralOthersMap.getSoundImage()).get());
		AnchorPane.setTopAnchor(soundBtn, STANDARD_ANCHOR);
		AnchorPane.setLeftAnchor(soundBtn, STANDARD_ANCHOR * 5.5);
		
		anchorPane.getChildren().addAll(leftButtonBox, rightButtonBox, helpBtn, musicBtn, soundBtn);
		root.getChildren().add(anchorPane);

		newGameBtn.setOnAction(e -> {
			mainStage.setScene(PlayerSetupMenu.get(mainStage));
			mainStage.centerOnScreen();
			
		});

		loadGameBtn.addEventFilter(MouseEvent.MOUSE_CLICKED, new HandleFileChooser());
		loadGameBtn.setOnMouseClicked(e -> {
			mainStage.setScene(CommandBridge.get(mainStage));
			mainStage.centerOnScreen();
		});
		
		cancelBtn.setOnAction(e -> {
			if(AlertFactory.createConfirmationAlert("Confirm exit", "Are you sure you want to quit from Unibopoli?")) {
				mainStage.close();
			} else {
				e.consume();
			}
		});

		creditsBtn.setOnAction(e -> {
			AlertFactory.createInformationAlert("Credits",
					"Alesiani Matteo: Movement and consequences, management of contracts and contingencies/probabilities, game Board graphics and game turn\n" +
					"Rossolini Andrea: Player Management, Trading & Auction, box & contracts Graphics, saving & loading of the Game, main menu and initialization of the game.");
		});

		musicBtn.setOnAction(e -> {
			music.changeMusicMute();
			if (music.isMusicMute()) {
				musicBtn.setGraphic(IconLoader.getLoader().getImageFromPath(ClassicType.Other.GeneralOthersMap.getNoMusicImage()).get());
			} else {
				musicBtn.setGraphic(IconLoader.getLoader().getImageFromPath(ClassicType.Other.GeneralOthersMap.getMusicImage()).get());
			}
		});

		soundBtn.setOnAction(e -> {
			music.changeSoundsMute();
			if (music.isSoundMute()) {
				soundBtn.setGraphic(IconLoader.getLoader().getImageFromPath(ClassicType.Other.GeneralOthersMap.getNoSoundImage()).get());
			} else {
				soundBtn.setGraphic(IconLoader.getLoader().getImageFromPath(ClassicType.Other.GeneralOthersMap.getSoundImage()).get());
			}
		});
		
		root.setId("mainMenu");
		this.getStylesheets().add(getClass().getResource("/style/style.css").toExternalForm());
		this.setRoot(root);
	}

	public static void setBeckgroundMusic(final SoundController backgroundMusic) {
		music = backgroundMusic;
	}

	public static MainMenu get(Stage stage) {
		mainStage = stage;
		//mainStage.centerOnScreen();
		return new MainMenu();
	}
}
