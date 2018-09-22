package view;

import javafx.application.Application;
import javafx.stage.Stage;

import controller.ControllerImpl;
import utilities.IconLoader;
import utilities.enumerations.ClassicType;
import view.gameSetup.MainMenu;

/**
 *  
 * @author Matteo Alesiani 
 */

public class InitialWindows extends Application {

	private final Stage initialWindow = new Stage();
	
	/**
     * Constructor of the class. It sets up the Stage.
     */
	public InitialWindows() {
	}
	
	/**
     * It starts the JavaFX application.
	 * @throws Exception 
     */
    @Override
    public void start(final Stage primaryStage) throws Exception {
    	this.initialWindow.getIcons().add(IconLoader.getLoader()
    												.getImageFromPath(ClassicType.Other.GENERALOTHERIMAGEMAP.getIconWindows())
    												.getImage());
        
        this.initialWindow.setOnCloseRequest(e -> {
        	if (AlertFactory.createConfirmationAlert("Confirm exit", "Are you sure you want to quit from Unibopoli?")) {
        		initialWindow.close();
			} else {
				e.consume();
			}
        });
    	
        this.initialWindow.setScene(MainMenu.get(this.initialWindow));
        this.initialWindow.setResizable(false);
        this.initialWindow.show();
        ControllerImpl.getController();
    }
}
