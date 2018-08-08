package view.handlers;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 * @author admin
 *
 */
public class HandleFileChooser implements EventHandler<MouseEvent> {
	
	private static final String SAVE_DIRECTORY_PATH = System.getProperty("user.home") + File.separator + ".unibopoli";

	@Override
	public void handle(MouseEvent e) {
		final FileChooser  fileChooser = new FileChooser();

        fileChooser.setTitle("Choose a game to load:");
        fileChooser.setInitialDirectory(Files.notExists(Paths.get(SAVE_DIRECTORY_PATH), LinkOption.NOFOLLOW_LINKS) ? new File(System.getProperty("user.home")) : new File(SAVE_DIRECTORY_PATH));

        final FileChooser.ExtensionFilter ubpFilter = new ExtensionFilter("UBP files (*.ubp)", "*.ubp");
        fileChooser.getExtensionFilters().add(ubpFilter);

        final File file = fileChooser.showSaveDialog(null); //bisogna controllare se così funziona
        if (file != null) {
//            Controller.getController().loadGameFromFile(file);
        }
    }

}
