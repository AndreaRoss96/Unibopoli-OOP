package view.tiles;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import utilities.IconLoader;
import utilities.PaneDimensionSetting;

public class ComponentFactory {
	
	private static final double WIDTH = PaneDimensionSetting.getInstance().getGamePaneWidth();
	public static final double LandSimpleWIDTH = WIDTH / 13.1;
	private static final double LandHEIGHT = LandSimpleWIDTH * 2.015;
	private static final double LandCornerDimension = LandHEIGHT;
	
	public static AnchorPane getAnchorPane(final boolean isCorner) {
		AnchorPane landPane = new AnchorPane();

		if(isCorner) {
			landPane.setMinHeight(LandCornerDimension);
			landPane.setMinWidth(LandCornerDimension);
			landPane.setMaxHeight(LandCornerDimension);
			landPane.setMaxWidth(LandCornerDimension);
		} else {
			landPane.setMinHeight(LandHEIGHT);
			landPane.setMinWidth(LandSimpleWIDTH);
			landPane.setMaxHeight(LandHEIGHT);
			landPane.setMaxWidth(LandSimpleWIDTH);
		}

		return landPane;
	}
	
	public static Label getLabelColor(final String paint) {
		Label colorFamily = new Label();
		colorFamily.setBackground(new Background(new BackgroundFill(Paint.valueOf(paint), CornerRadii.EMPTY, Insets.EMPTY)));
		setAlignmentNode(colorFamily);
		
		return colorFamily;
	}

	public static Label getLabelString(final String textLabel) {
		Label label = new Label(textLabel);
	
		label.setFont(Font.loadFont("file:res/font/kabel.ttf", 8));
		label.setAlignment(Pos.CENTER);
		label.setTextAlignment(TextAlignment.CENTER);
		label.setOnMouseEntered(value -> label.setFont(Font.loadFont("file:res/font/kabel.ttf", 10)));
		label.setOnMouseExited(value -> label.setFont(Font.loadFont("file:res/font/kabel.ttf", 8)));
		label.setWrapText(true);
		label.setRotate(180.0);
		setAlignmentNode(label);
			
		return label;
	}

	/**
	 * Migliorare la dimensione dell'immagine
	 * 
	 * */
	public static Label getLabelImage(final String pathImageView) {
		Label image = new Label();
		ImageView imageView = IconLoader.getLoader().getImageFromPath(pathImageView);
		imageView.setFitWidth(LandSimpleWIDTH * 0.75);
		imageView.setFitHeight(LandSimpleWIDTH * 0.75);
		imageView.setRotate(180.0);
		image.setGraphic(imageView);
		setAlignmentNode(image);
		
		image.setAlignment(Pos.CENTER);
		return image;
	}

	public static Separator getSeparator(final Orientation orientation) {
		Separator seperator = new Separator(orientation);
		setAlignmentNode(seperator);
		
		return seperator;
	}

	private static void setAlignmentNode(Control node) {
		AnchorPane.setLeftAnchor(node, 0.0);
		AnchorPane.setRightAnchor(node, 0.0);		
	}
}
