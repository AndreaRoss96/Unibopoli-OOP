package model;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.time.Instant;

import view.AlertFactory;

/**
 * Singleton class used to save and load game.
 * 
 * @author Rossolini Andrea
 */
public class ResourceManager {

	private static final ResourceManager SINGLETON = new ResourceManager();

	private final String SAVE_DIRECTORY_PATH = System.getProperty("user.home") + System.getProperty("file.separator")
			+ ".unibopoli" + System.getProperty("file.separator");

	/**
	 * @return Instance of resource manager
	 */
	public static ResourceManager getInstance() {
		return SINGLETON;
	}

	private ResourceManager() {
		System.out.println(SAVE_DIRECTORY_PATH);
		final File dir = new File(SAVE_DIRECTORY_PATH);
		if (!dir.exists()) {
			final boolean success = dir.mkdir();
			if (!success) {
				AlertFactory.createInformationAlert("ERROR", "There's been an error during the creation of save directory!");
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
		try {
			final OutputStream fileStream = new FileOutputStream(SAVE_DIRECTORY_PATH + fileName + ".ubp");
			final OutputStream outputStream = new BufferedOutputStream(fileStream);
			final ObjectOutputStream objectOutput = new ObjectOutputStream(outputStream);
			objectOutput.writeObject(memento);
			objectOutput.close();
		} catch (Exception ioEx) {
			ioEx.printStackTrace();
			AlertFactory.createInformationAlert("ERROR", "There's been an error during the file saving!");
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
			final InputStream fileStream = new FileInputStream(file);
			final InputStream inputStream = new BufferedInputStream(fileStream);
			final ObjectInputStream objectInput = new ObjectInputStream(inputStream);
			memento = (ModelMemento) objectInput.readObject();
			objectInput.close();
		} catch (IOException e) {
			e.printStackTrace();
			AlertFactory.createInformationAlert("ERROR", "There's been an error during the file loading!");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			AlertFactory.createInformationAlert("ERROR", "There's been an error during the file loading!");
		}
		return memento;
	}
}