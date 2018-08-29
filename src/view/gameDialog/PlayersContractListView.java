package view.gameDialog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import model.player.PlayerInfo;

public class PlayersContractListView extends ListView<Text> {
	
	private final Map<Text, Paint> map =  new HashMap<>();
	
	public PlayersContractListView() {
		
	}
	
	public PlayersContractListView(PlayerInfo player) {
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
	
	public List<String> getSelected(){
		return this.map.keySet().stream().map(Text::getText).collect(Collectors.toList());
	}
	

}
