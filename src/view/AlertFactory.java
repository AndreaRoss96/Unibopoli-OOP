package view;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.StageStyle;
import utilities.FontFactory;

public class AlertFactory{

	public static void createErrorAlert(final String title, final String contentText) {
		buildAlert(AlertType.ERROR, title, contentText).showAndWait();
	}
	
	
	public static void createInformationAlert(final String title, final String contentText) {
		buildAlert(AlertType.INFORMATION, title, contentText).showAndWait();
	}
	
	public static boolean createConfirmationAlert(final String title, final String contentText) {
		Optional<ButtonType> choice = buildAlert(AlertType.CONFIRMATION, title, contentText).showAndWait();
		return choice.get() == ButtonType.OK;
	}
	
	private static Alert buildAlert(final AlertType type, final String title, final String contentText) {
		final Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(null);
		final Label content = new Label(contentText);
		content.setFont(FontFactory.getDefaultFont(13));
		content.setTextFill(alert.getAlertType() == AlertType.ERROR ? Color.RED : Color.CORNFLOWERBLUE);
		alert.setGraphic(content);
		alert.initStyle(StageStyle.UTILITY);
		return alert;
	}
}
