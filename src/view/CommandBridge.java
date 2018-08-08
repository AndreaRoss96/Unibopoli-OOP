package view;

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
	private static final CommandBridge BRIDGE = new CommandBridge();

	private static Stage mainStage;
    
	private CommandBridge() {
		super(new BorderPane(), PaneDimensionSetting.getInstance().getCommandBridgeWidth(), PaneDimensionSetting.getInstance().getCommandBridgeHeight());
		
		final BorderPane commandBridge = new BorderPane();
		//cambiare con PlayersPane
		//commandBridge.setLeft(GamePane.get());
		commandBridge.setCenter(GamePane.get());
		//cambiare con ActionsPane
		//commandBridge.setRight(GamePane.get());
		
		this.setRoot(commandBridge);
		this.getStylesheets().add("style.css");
	}
	
	/**
     * 
     * @param 
     *            
     * @return 
     */
    static CommandBridge get(final Stage initialWindow) {
        mainStage = initialWindow;
        mainStage.setFullScreen(false);
        mainStage.centerOnScreen();
        mainStage.setTitle(TITLE);
        return BRIDGE;
    }
}
