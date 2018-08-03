package controller;

import java.awt.Toolkit;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import model.tiles.Obtainable;

/**
 * This abstract class is used to build the typical contract characteristic of
 * the original "Monopoly".
 * 
 * @author Rossolini Andrea
 *
 */
public abstract class AbstractContract {
	private static final double WIDTH_ANCHOR = Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.0044;
	private static final double HEIGHT_ANCHOR = Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 0.007;
	private static final double H_GAP = Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.0029;
	private static final double V_GAP = Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 0.0047;
	private static final double V_PADDING = Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.009;
	private static final double PREF_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.17;
	private static final double PREF_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 0.38;

	private final AnchorPane outsidePane;

	public AbstractContract(Obtainable property) {
		FlowPane insidePane = new FlowPane(H_GAP, V_GAP);
		insidePane.setPadding(new Insets(0, V_PADDING, 0, V_PADDING));
		insidePane.setPrefSize(PREF_WIDTH, PREF_HEIGHT);
		insidePane.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
		insidePane.setAlignment(Pos.TOP_CENTER);
		insidePane.setStyle("-fx-border-color: black;");
		Image cardBoard = new Image("/images/cardBoard/white_cardstock.jpg");
		BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true);
		Background background = new Background(new BackgroundImage(cardBoard, BackgroundRepeat.REPEAT,
				BackgroundRepeat.ROUND, BackgroundPosition.CENTER, bSize));
		DropShadow ds = new DropShadow();
		ds.setOffsetY(3.0f);
		ds.setColor(Color.color(0.4f, 0.4f, 0.4f));
		inseideFiller(insidePane, property);
		this.outsidePane = new AnchorPane(insidePane);
		this.outsidePane.setBackground(background);
		this.outsidePane.setStyle("-fx-border-color: black; -fx-border-radius: 10px; ");
		this.outsidePane.setEffect(ds);
		this.outsidePane.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
		AnchorPane.setTopAnchor(insidePane, WIDTH_ANCHOR);
		AnchorPane.setLeftAnchor(insidePane, HEIGHT_ANCHOR);
		AnchorPane.setRightAnchor(insidePane, HEIGHT_ANCHOR);
		AnchorPane.setBottomAnchor(insidePane, WIDTH_ANCHOR);
	}

	/**
	 * the card itself
	 * 
	 * @return AnchorPane
	 */
	public AnchorPane getPane() {
		return this.outsidePane;
	}

	/**
	 * The subclasses will complete this method. They will fill the contract with
	 * labels and images.
	 * 
	 * @param insidePane
	 * @param property
	 */
	protected abstract void inseideFiller(FlowPane insidePane, Obtainable property);

	protected static double getPrefHeight() {
		return PREF_HEIGHT;
	}
	
	protected static double getPrefWidth() {
		return PREF_WIDTH;
	}
	
	protected static double getHGap() {
		return H_GAP;
	}
}
