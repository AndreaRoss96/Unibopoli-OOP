package view.comboBoxCell;

import java.util.Map;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class ShapeCellFactory implements Callback<ListView<String>, ListCell<String>> {

	private final Map<String, String> imageMap;

	/**
	 * constructor
	 * 
	 * @param imageMap
	 *            map with name of the icon and icon's path
	 */
	public ShapeCellFactory(Map<String, String> imageMap) {
		this.imageMap = imageMap;
	}

	/**
	 * Call ShapeCell to show icons in the comboBox.
	 */
	@Override
	public ListCell<String> call(ListView<String> listview) {
		return new ShapeCell(this.imageMap);
	}
}
