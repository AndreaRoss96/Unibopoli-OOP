package utilities;

import model.Icon;

import java.util.HashMap;
import java.util.Map;

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
	 * Necessaria ?!?! 
	 * Utilizzare Icon al posto di Image
	 * TODO: Aggiungere commente e ricorsarsi di specificare -> (lazy creation).
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
		} catch (final Exception e) {System.out.println("Path non trovato");}
		
		return null;
	}
}