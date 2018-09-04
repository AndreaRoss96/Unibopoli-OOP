package view.gameDialog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.common.base.Optional;

import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import model.player.PlayerInfo;

public class PlayersContractListView extends ListView<Text> {

	private final Map<Text, Paint> map = new HashMap<>();

	/**
	 * Empty constructor for the PlayerContractListView to generate an empty
	 * ListView.
	 */
	public PlayersContractListView() {

	}

	/**
	 * Constructor for the PlayerContractListView to show all player's Property,
	 * ordinated by color.
	 * 
	 * @param player
	 *            selected
	 */
	public PlayersContractListView(final PlayerInfo player) {
		player.getProperties().forEach(c -> {
			Text property = new Text(c.getNameOf());
			property.setStyle("-fx-font-family: kabel;");
			property.setFill(c.getColorOf().getPaint().orElse(Color.BLACK));
			this.getItems().add(property);
		});
		this.getSelectionModel().selectedItemProperty().addListener(l -> {
			this.getSelectionModel().getSelectedItem().setFill(listViewClick(this.getSelectionModel().getSelectedItem(),
					this.getSelectionModel().getSelectedItem().getFill()));
		});
	}

	private Paint listViewClick(Text text, Paint paint) {
		if (this.map.containsKey(text)) {
			Paint tmpPaint = this.map.get(text);
			this.map.remove(text);
			return tmpPaint;
		} else {
			this.map.put(text, paint);
			return Color.GREY;
		}
	}

	/**
	 * Getter for the selected items.
	 * 
	 * @return a list of selected items
	 */
	public List<Optional<String>> getSelected() {
		return this.map.keySet().stream().map(Text::getText).map(value -> Optional.of(value))
				.collect(Collectors.toList());
	}

}
