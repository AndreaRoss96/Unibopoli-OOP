package utilities;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.stage.StageStyle;

public class AlertFactory{

	public static void createErrorAlert(String title, String contentText) {
		buildAlert(AlertType.ERROR, title, contentText).showAndWait();
	}
	
	public static void createInformationAlert(String title, String contentText) {
		buildAlert(AlertType.INFORMATION, title, contentText).showAndWait();
	}
	
	public static boolean createConfirmationAlert(String title, String contentText) {
		Optional<ButtonType> choice = buildAlert(AlertType.CONFIRMATION, title, contentText).showAndWait();
		return choice.get() == ButtonType.OK;
	}
	
	private static Alert buildAlert(AlertType type, String title,  String contentText) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(null);
		Label content = new Label(contentText);
		content.setFont(Font.loadFont("file:res/font/kabel.ttf", 13));
		alert.setGraphic(content);
		alert.initStyle(StageStyle.UTILITY);
		return alert;
	}
	
}
