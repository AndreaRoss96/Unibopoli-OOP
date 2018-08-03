package controller;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import model.Player;

public class PlayersContractListView extends ListView<Text> {
	
	Map<Text, Paint> map;
	
	public PlayersContractListView(Player player) {
		this.map = new HashMap<>();
		player.getProperties().forEach(c -> {
			Text property = new Text(c.getNameOf());
			property.setFill(c.getColorOf().getPaint().orElse(Color.BLACK));
			this.getItems().add(property);
			this.getSelectionModel().selectedItemProperty().addListener(l -> {
				this.getSelectionModel().getSelectedItem().setFill(listViewClick(this.getSelectionModel().getSelectedItem(), this.getSelectionModel().getSelectedItem().getFill()));
			});
			
		});
	}
	
	private Paint listViewClick(Text text, Paint paint) {
		if (this.map.containsKey(text)) {
			Paint paint_tmp = this.map.get(text);
			this.map.remove(text);
			return paint_tmp;
		} else {
			this.map.put(text, paint);
			return Color.GREY;
		}
	}
	
//	public List<Obtainable> getSelected(){
//		return this.map.keySet().stream().map(Text::getText).collect(Collectors.toList());
//	} manca il "getPropertisByName()" da implementare nel controller
	

}
