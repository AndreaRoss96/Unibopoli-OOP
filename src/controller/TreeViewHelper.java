package controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javafx.scene.control.TreeItem;
import model.*;
import utilities.enumerations.Color;

/**
 * This class creates treeItem Objects that will be used in buying and selling dialogs
 * and mortgage.
 * 
 * @author Rossolini Andrea
 *
 */
public class TreeViewHelper {
	private final Map<Color, List<Obtainable>> playersProperties;

	public TreeViewHelper(final Map<Color, List<Obtainable>> playersProperties) {
		this.playersProperties = new HashMap<>();
		this.playersProperties.putAll(playersProperties);
	}

	public List<TreeItem<String>> getProducts() {
//		this.playersProperties.forEach((k, v) -> {
//			TreeItem<String> fatherItem = new TreeItem<String>(k.getName());
//			this.playersProperties.get(k).forEach(e -> {
//				fatherItem.getChildren().add(new TreeItem<String>(e.getNameOf()));
//			});
//			this.product.add(fatherItem);
//		});
//		return product;
		
		//List<TreeItem<String>> product =
		return this.playersProperties.entrySet().stream().map(
			    t -> {
			          TreeItem<String>father = new TreeItem<>(t.getKey().getName());
			          List<TreeItem<String>> children = t.getValue()
			                .stream()
			                .map(u -> u.getNameOf())
			                .map(TreeItem::new)
			                .collect(Collectors.toList());
			         father.getChildren().addAll(children);
			         return father;
			     }
			    ).collect(Collectors.toList());
		//return product;
	}

}
