package view.comboBoxCell;

import java.util.Map;

import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ShapeCell extends ListCell<String> {
	
	private final Map<String, String> iconPath;
	
	public ShapeCell(Map<String, String> iconPath) {
		this.iconPath = iconPath;
	}

	/**
	 * This method will show icon choose by player in the combobox.
	 */
	@Override
	public void updateItem(String item, boolean empty)
	{
		super.updateItem(item, empty);

		if (empty)
		{
			setText(null);
			setGraphic(null);
		}
		else
		{
			setText(item);
			setGraphic(new ImageView(new Image(this.iconPath.get(item))));
		}
	}
}
