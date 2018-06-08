package controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javafx.scene.control.TreeItem;
import model.*;
import utilities.enumerations.Color;

/**
 * This class creates treeItem Objects that will be used in buying and selling
 * dialogs and mortgage.
 * 
 * @author Rossolini Andrea
 *
 */
public class TreeViewHelper {
	/*
	 * private final Map<Color, List<ObtainableImpl>> playersProperties;
	 * 
	 * public TreeViewHelper(final Map<Color, List<ObtainableImpl>>
	 * playersProperties) { this.playersProperties = new HashMap<>();
	 * this.playersProperties.putAll(playersProperties); }
	 */

	public List<TreeItem<String>> getProducts(final Map<Color, List<ObtainableImpl>> playersProperties) {
		// this.playersProperties.forEach((k, v) -> {
		// TreeItem<String> fatherItem = new TreeItem<String>(k.getName());
		// this.playersProperties.get(k).forEach(e -> {
		// fatherItem.getChildren().add(new TreeItem<String>(e.getNameOf()));
		// });
		// this.product.add(fatherItem);
		// });
		// return product;

		/*
		 * return this.playersProperties.entrySet().stream().map( t -> {
		 * TreeItem<String>father = new TreeItem<>(t.getKey().getName());
		 * List<TreeItem<String>> children = t.getValue() .stream() .map(u ->
		 * u.getNameOf()) .map(TreeItem::new) .collect(Collectors.toList());
		 * father.getChildren().addAll(children); return father; }
		 * ).collect(Collectors.toList());
		 */
		return playersProperties.entrySet().stream().map(e -> makeTree(e.getKey(), e.getValue()))
				.collect(Collectors.toList());

	}
//se gfacessi un TreeItem<Obtainable> tree = new TreeItem<>() dovrebbe prendere (non so come) il nome dell'ipoteca automaticamente, vedi la classe layer nella cartella esperimento
	private TreeItem<String> makeTree(Color root, List<ObtainableImpl> list) {
		TreeItem<String> tree = new TreeItem<>(root.getName());
		list.stream().map(ObtainableImpl::getNameOf).map(TreeItem::new).forEach(t -> tree.getChildren().add(t));
		return tree;
	}

}
