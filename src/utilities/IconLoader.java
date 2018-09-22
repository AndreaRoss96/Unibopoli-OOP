package utilities;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javafx.scene.image.ImageView;
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
	 * 
	 * @return the current Singleton instance of the ImageLoader.
	 */
	public static IconLoader getLoader() {
		if (IconLoader.ICONS == null) {
			IconLoader.ICONS = new IconLoader();
		}
		return IconLoader.ICONS;
	}

	public ImageView getImageFromPath(final String path) {
		try {
			if (!this.iconsMap.containsKey(path)) {
				this.iconsMap.put(path, new Icon(path));
			}

			return new ImageView(this.iconsMap.get(path).get().getImage());
		} catch (final Exception e) {
			System.err.println("Path not found");
		}

		return null;
	}

	/**
	 *  Get all the sprite for the players, used by the <i>playerSetupMenu</i>
	 * initialize the players.
	 * 
	 * @param path
	 *            the folder where are placed the sprites
	 * 
	 * @return Map<String, String> having in the key value the name of the sprite
	 *         and as a value its path
	 */
	public Map<String, String> getAvatarMap(final String path) {
		Objects.requireNonNull(path, "NullPointerException: path required non-null.");
		Map<String, String> imageMap = new HashMap<>();
		final File folder = new File(path);
		final File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				String fileName = listOfFiles[i].getName().replaceAll(".png", "");
				String filePath = listOfFiles[i].getPath().replaceAll("res", "");
				imageMap.put(fileName, filePath);
			}
		}
		
		return imageMap;
	}
}