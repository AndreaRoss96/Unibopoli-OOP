package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.control.TreeItem;
import model.*;
import utilities.enumerations.Color;

public class TreeViewHelper {
	private final Map<Color, List<Obtainable>> playersProperties;
	private List<TreeItem<String>> product;

	public TreeViewHelper(final Map<Color, List<Obtainable>> playersProperties) {
		this.playersProperties = new HashMap<>();
		this.playersProperties.putAll(playersProperties);
		product = new ArrayList<>();
	}

	public List<TreeItem<String>> getProducts() {
		this.playersProperties.forEach((k, v) -> {
			TreeItem<String> fatherItem = new TreeItem<String>(k.getName());
			this.playersProperties.get(k).forEach(e -> {
				fatherItem.getChildren().add(new TreeItem<String>(e.getNameOf()));
			});
			this.product.add(fatherItem);
		});
		return product;
	}

}
