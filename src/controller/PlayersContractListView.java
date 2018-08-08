package controller;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import model.player.Player;
import model.player.PlayerInfo;

public class PlayersContractListView extends ListView<Text> {
	
	Map<Text, Paint> map;
	
	public PlayersContractListView() {
		
	}
	
	public PlayersContractListView(PlayerInfo player) {
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
			Paint tmpPaint = this.map.get(text);
			this.map.remove(text);
			return tmpPaint;
		} else {
			this.map.put(text, paint);
			return Color.GREY;
		}
	}
	
//	public List<Obtainable> getSelected(){
//		return this.map.keySet().stream().map(Text::getText).collect(Collectors.toList());
//	} manca il "getPropertisByName()" da implementare nel controller
	

}
