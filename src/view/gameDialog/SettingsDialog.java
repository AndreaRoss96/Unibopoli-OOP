package view.gameDialog;

import controller.ControllerImpl;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import utilities.IconLoader;
import utilities.enumerations.ClassicType;

public class SettingsDialog extends Dialog {

	private static final SettingsDialog SINGLETON = new SettingsDialog ();
	private final double PREF_W = getPrefWSize()/2;
	private final double PREF_H = PREF_W /3;
	private final double HGAP = PREF_W / 6;
	
	private SettingsDialog() {
		
	}
	
	/**
	 * Instance of SettingsDialog.
	 * 
	 * @return the instance.
	 */
	public static SettingsDialog getSettingsDialog() {
		return SINGLETON;
	}
	
	/**
	 * Creation of the pane for the dialog.
	 */
	public void createSettingDialog() {
		final Stage stage = setStage();
		stage.initModality(Modality.WINDOW_MODAL);
		final ControllerImpl controller = ControllerImpl.getController();
		
		final FlowPane root = new FlowPane();
		root.setAlignment(Pos.CENTER);
		root.setHgap(HGAP);
		
		final Button musicButton = new Button();
		musicButton.setGraphic(controller.getSound().isMusicMute() ? IconLoader.getLoader().getImageFromPath(ClassicType.Other.GeneralOthersMap.getNoMusicImage()).get() : IconLoader.getLoader().getImageFromPath(ClassicType.Other.GeneralOthersMap.getMusicImage()).get());
		musicButton.getStyleClass().add("roundButton");
		root.getChildren().add(musicButton);
		
		final Button soundButton = new Button();
		soundButton.setGraphic(controller.getSound().isSoundMute() ? IconLoader.getLoader().getImageFromPath(ClassicType.Other.GeneralOthersMap.getNoSoundImage()).get() : IconLoader.getLoader().getImageFromPath(ClassicType.Other.GeneralOthersMap.getSoundImage()).get());
		soundButton.getStyleClass().add("roundButton");
		root.getChildren().add(soundButton);
		
		final Button saveGameBtn = new Button();
		saveGameBtn.setGraphic(IconLoader.getLoader().getImageFromPath(ClassicType.Other.GeneralOthersMap.getSaveImage()).get());
		saveGameBtn.getStyleClass().add("roundButton");
		saveGameBtn.setTooltip(new Tooltip("Save the game!"));
		root.getChildren().add(saveGameBtn);
		
		musicButton.setOnAction(e -> {
			controller.getSound().changeMusicMute();
			musicButton.setGraphic(controller.getSound().isMusicMute() ? IconLoader.getLoader().getImageFromPath(ClassicType.Other.GeneralOthersMap.getNoMusicImage()).get() : IconLoader.getLoader().getImageFromPath(ClassicType.Other.GeneralOthersMap.getMusicImage()).get());
		});
		
		soundButton.setOnAction(e -> {
			controller.getSound().changeSoundsMute();
			soundButton.setGraphic(controller.getSound().isSoundMute() ? IconLoader.getLoader().getImageFromPath(ClassicType.Other.GeneralOthersMap.getNoSoundImage()).get() : IconLoader.getLoader().getImageFromPath(ClassicType.Other.GeneralOthersMap.getSoundImage()).get());
		});
		
		saveGameBtn.setOnAction(e -> {
			controller.saveGame();
		});
		
		stage.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
			if(!isNowFocused) {
				stage.close();
			}
		});
		
		final Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/style/style.css").toExternalForm());
		stage.setWidth(PREF_W);
		stage.setHeight(PREF_H);
		stage.setScene(scene);
		stage.showAndWait();
	}

}
