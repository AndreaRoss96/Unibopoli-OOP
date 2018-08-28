package view.tiles;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
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

import utilities.PaneDimensionSetting;

public class ComponentFactory {
	
	public static final double LandSimpleWIDTH = PaneDimensionSetting.getInstance().getGamePaneHeight() / 13;
	public static final double LandHEIGHT = LandSimpleWIDTH * 2;
	public static final double LandCornerDimension = LandHEIGHT;
	
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
	
	public static Label getLabelColor(final Paint paint) {
		Label colorFamily = new Label();
		colorFamily.setBackground(new Background(new BackgroundFill(paint, CornerRadii.EMPTY, Insets.EMPTY)));
		setAlignmentNode(colorFamily);
		
		return colorFamily;
	}

	public static Label getLabelString(final String textLabel) {
		Label label = new Label(textLabel);
	
		label.setFont(Font.loadFont("file:res/font/kabel.ttf", 8));
		label.setAlignment(javafx.geometry.Pos.CENTER);
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
	public static Label getLabelImage(final ImageView ImageView) {
		Label image = new Label();
		ImageView imageView = ImageView;
		imageView.setFitWidth(LandSimpleWIDTH * 0.7);
		imageView.setFitHeight(LandSimpleWIDTH * 0.7);

		setAlignmentNode(image);
		
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
