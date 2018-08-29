package view;

import controller.ControllerImpl;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import utilities.PaneDimensionSetting;

/**
* 
*  
* @author Matteo Alesiani 
*/

public class CommandBridge extends Scene{

	private static final String TITLE = "UNIBOPOLY - La versione Universitaria di Monopoli.";

	private static Stage mainStage;
    
	private CommandBridge() {
		super(new BorderPane(), PaneDimensionSetting.getInstance().getCommandBridgeWidth(), PaneDimensionSetting.getInstance().getCommandBridgeHeight());
		mainStage.centerOnScreen();
		final BorderPane commandBridge = new BorderPane();
//		commandBridge.setLeft(new LeftPlayersPane(playerList));
		commandBridge.setCenter(GamePane.get());
		commandBridge.setRight(new RightInormationPane());
		
		this.setRoot(commandBridge);
		this.getStylesheets().add(getClass().getResource("/style/style.css").toExternalForm());
	}
	
	/**
     * 
     * @param 
     *            
     * @return 
     */
    public static CommandBridge get(final Stage initialWindow) {
        mainStage = initialWindow;
        mainStage.setTitle(TITLE);
        
        return new CommandBridge();
    }
}
