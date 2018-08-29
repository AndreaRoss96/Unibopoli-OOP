package utilities;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AlertFactory{

	
	/***
	 * TODO: SERVE??
	 * 
	 * */
	public static void createErrorAlert(String title, String headerText, String contentText) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(contentText);
		alert.showAndWait();
	}
	
	public static void createInformationAlert(String title, String header, String contentText) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(contentText);
		alert.showAndWait();
	}
	
}
