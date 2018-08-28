package view;

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
	private static final CommandBridge BRIDGE = new CommandBridge();

	private static Stage mainStage;
    
	private CommandBridge() {
		super(new BorderPane(), PaneDimensionSetting.getInstance().getCommandBridgeWidth(), PaneDimensionSetting.getInstance().getCommandBridgeHeight());
		
		final BorderPane commandBridge = new BorderPane();
//		commandBridge.setLeft(new LeftPlayersPane(playerList));
		commandBridge.setCenter(GamePane.get());
		//cambiare con ActionsPane
		commandBridge.setRight(new RightInormationPane());
		this.setRoot(commandBridge);
		this.getStylesheets().add("style.css");
		
		
		
		this.setOnKeyReleased(value -> {
			switch (value.getCode()) {
			case UP:
				Platform.runLater(() -> GamePane.prova.get(0).getIcon().move(1));
				break;
			case DOWN: 
				GamePane.prova.get(1).getIcon().move(1);
				break;
			default:
				break;
			}
		});
	}
	
	/**
     * 
     * @param 
     *            
     * @return 
     */
    static CommandBridge get(final Stage initialWindow) {
        mainStage = initialWindow;
//        mainStage.initStyle(StageStyle.TRANSPARENT);
        mainStage.setFullScreen(false);
        mainStage.centerOnScreen();
        mainStage.setTitle(TITLE);
        return BRIDGE;
    }
}
