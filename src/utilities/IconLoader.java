package utilities;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

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
	 * @throws IOException 
	 */
	public Map<String, String> getAvatarMap(final String path) {
		Objects.requireNonNull(path, "NullPointerException: path required non-null.");
		final Map<String, String> imageMap = new HashMap<>();
		
		try(Stream<Path> paths = Files.walk(Paths.get(path))) {
			paths.filter(Files::isRegularFile).forEach(e -> {
				String correctPath = e.toString().replaceAll("/", File.separator);
				String fileName = e.getFileName().toString().replaceAll(".png", "");
				String filePath = correctPath.toString().replaceAll("res", "");
				imageMap.put(fileName, filePath);
			});
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		return imageMap;
	}
}