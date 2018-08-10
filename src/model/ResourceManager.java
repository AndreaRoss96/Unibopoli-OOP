package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;

import utilities.AlertFactory;

/**
 * Singleton class used to save and load game.
 * 
 * @author Rossolini Andrea
 */
public class ResourceManager {

	private static final ResourceManager SINGLETON = new ResourceManager();

	private static final Path SAVE_DIRECTORY_PATH = Paths.get(System.getProperty("user.home"), File.separator,
			".unibopoli");

	/**
	 * @return Instance of resource manager
	 */
	public static ResourceManager getInstance() {
		return SINGLETON;
	}

	private ResourceManager() {
		if (!Files.exists(SAVE_DIRECTORY_PATH)) {
			try {
				Files.createDirectory(SAVE_DIRECTORY_PATH);
			} catch (IOException e) {
				e.printStackTrace();
				AlertFactory
						.createErrorAlert("ERROR", "There's been an error during the creation of save directory!", null)
						.showAndWait();
			}
		}
	}

	/**
	 * @param memento
	 *            The ModelMemento to save on file.
	 * @param fileName
	 *            The name of the file which need to be saved.
	 */
	public void saveOnFile(final ModelMemento memento) {
		String fileName = "Unibopoli_" + Instant.now();
		fileName = fileName.trim();
		fileName = fileName.replace(':', '-');
		fileName = fileName.substring(0, fileName.lastIndexOf('.'));
		File file = new File(fileName);
		try {
			final FileOutputStream fileOutput = new FileOutputStream(
					SAVE_DIRECTORY_PATH.toString() + File.separator + file + ".ubp");
			final ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput);
			objectOutput.writeObject(memento);
			objectOutput.close();
			fileOutput.close();
		} catch (IOException ioEx) {
			ioEx.printStackTrace();
			AlertFactory.createErrorAlert("ERROR", "There's been an error during the file saving!", null).showAndWait();
		}
	}

	/**
	 * 
	 * @param file
	 *            The path where the file is allocated.
	 * @return The ModelMemento stored in that file path.
	 */
	public ModelMemento loadGameFromFile(final File file) {
		ModelMemento memento = null;
		try {
			final FileInputStream fileInput = new FileInputStream(file);
			final ObjectInputStream objectInput = new ObjectInputStream(fileInput);
			memento = (ModelMemento) objectInput.readObject();
			objectInput.close();
			fileInput.close();
		} catch (IOException e) {
			e.printStackTrace();
			AlertFactory.createErrorAlert("ERROR", "There's been an error during the file loading!", null)
					.showAndWait();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			AlertFactory.createErrorAlert("ERROR", "There's been an error during the file loading!", null)
					.showAndWait();
		}
		return memento;
	}

	public String getSaveDirectory() {
		return SAVE_DIRECTORY_PATH.toString();
	}
}