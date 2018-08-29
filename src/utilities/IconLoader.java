package utilities;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import view.Icon;

public final class IconLoader {

	private static IconLoader ICONS;
	private final Map<String, Icon> iconsMap;

	/**
	 * Creates a new ImageLoader.
	 */
	private IconLoader() {
		this.iconsMap = new HashMap<>();
	}

	/**
	 * Necessaria ?!?! Utilizzare Icon al posto di Image TODO: Aggiungere commente e
	 * ricorsarsi di specificare -> (lazy creation).
	 *
	 * @return the current Singleton instance of the ImageLoader.
	 */
	public static IconLoader getLoader() {
		if (IconLoader.ICONS == null) {
			IconLoader.ICONS = new IconLoader();
		}
		return IconLoader.ICONS;
	}

	public Icon getImageFromPath(final String path) {
		try {
			if (!this.iconsMap.containsKey(path)) {
				this.iconsMap.put(path, new Icon(path));
			}

			return this.iconsMap.get(path);
		} catch (final Exception e) {
			System.out.println("Path not found");
		}

		return null;
	}

	/**IN QUESTO MODO SI PUò FARE CHE IL CONTROLLER RICHAIAM QUESTO METODO E PASSA IN INGRESSO AL PlayerSetupMenu LA MAPPA PER INIZIALIZZARE I GIOCATORIK, IN QUESTO MODO SI RENDONO LE ICONE PERSONALIZZABILI
	 * Get all the sprite for the players, used by the <i>playerSetupMenu</i>
	 * initialize the players.
	 * 
	 * @param path
	 *            the folder where are placed the sprites
	 * 
	 * @return Map<String, String> having in the key value the name of the sprite
	 *         and as a value its path
	 */
	public Map<String, String> getAvatarMap(final String path) {
		Map<String, String> imageMap = new HashMap<>();
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				String fileName = listOfFiles[i].getName().replaceAll(".png", "");
				String filePath = listOfFiles[i].getPath().replaceAll("res", "");
//				String filePath = listOfFiles[i].getPath();
				imageMap.put(fileName, filePath);
			}
		}
		
//		System.out.println(imageMap.toString());
		return imageMap;
	}
}