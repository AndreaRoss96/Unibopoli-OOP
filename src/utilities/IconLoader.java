package utilities;

import java.util.HashMap;
import java.util.Map;
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
}