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

import utilities.PaneDimensionSetting;

public class ComponentFactory {
	
	public static final double LandSimpleWIDTH = PaneDimensionSetting.getInstance().getGamePaneHeight() / 13;
	public static final double LandHEIGHT = LandSimpleWIDTH * 2;
	public static final double LandCornerDimension = LandHEIGHT;
	private static final double ROTATE_LEFT = +90.0;
	private static final double ROTATE_RIGHT = -90.0;
	private static final double NOT_ROTATE = 0.0;
	
	public static AnchorPane getAnchorPane(final boolean isCorner, final Pos position) {
		AnchorPane landPane = new AnchorPane();
		
		/**
		 * Utilizzare setRotate.
		 * 
		 * **/
		
		if(isCorner) {
			landPane.setMinHeight(LandCornerDimension);
			landPane.setMinWidth(LandCornerDimension);
			landPane.setMaxHeight(LandCornerDimension);
			landPane.setMaxWidth(LandCornerDimension);
//		}else if(position == Pos.CENTER_LEFT || position == Pos.CENTER_RIGHT) {
//			landPane.setMinHeight(LandHEIGHT);
//			landPane.setMinWidth(LandSimpleWIDTH);
//			landPane.setMaxHeight(LandHEIGHT);
//			landPane.setMaxWidth(LandSimpleWIDTH);
//			if(position == Pos.CENTER_LEFT) {
//				landPane.setRotate(ROTATE_RIGHT);
//			} else if(position == Pos.CENTER_RIGHT) {
//				landPane.setRotate(ROTATE_LEFT);
//			}
		} else {
			landPane.setMinHeight(LandHEIGHT);
			landPane.setMinWidth(LandSimpleWIDTH);
			landPane.setMaxHeight(LandHEIGHT);
			landPane.setMaxWidth(LandSimpleWIDTH);
			
//			 if(position == Pos.BOTTOM_CENTER) {
//				landPane.setRotate(ROTATE_LEFT * 2);
//			} else if(position == Pos.CENTER_LEFT) {
//				landPane.setRotate(ROTATE_RIGHT);
//			} else if(position == Pos.CENTER_RIGHT) {
//				landPane.setRotate(ROTATE_LEFT);
//			}
		}
		landPane.setStyle("-fx-border-color: blue");
		return landPane;
	}
	
	public static Label getLabelColor(final Paint paint, final Pos position) {
		Label colorFamily = new Label();
		colorFamily.setBackground(new Background(new BackgroundFill(paint, CornerRadii.EMPTY, Insets.EMPTY)));
		setAlignmentNode(colorFamily, position);
		
		return colorFamily;
	}

	public static Label getLabelString(final String textLabel, final Pos position) {
		Label label = new Label(textLabel);
		
//		if(position == Pos.TOP_CENTER || position == Pos.BOTTOM_CENTER) {
//			label.setRotate(NOT_ROTATE);
//		}else {
//			label.setRotate(position == Pos.CENTER_LEFT ? ROTATE_LEFT : ROTATE_RIGHT);
//		}
		
		label.setFont(Font.loadFont("file:res/font/kabel.ttf", 8));
		label.setAlignment(Pos.CENTER);
		label.setTextAlignment(TextAlignment.CENTER);
		label.setOnMouseEntered(value -> label.setFont(Font.loadFont("file:res/font/kabel.ttf", 10)));
		label.setOnMouseExited(value -> label.setFont(Font.loadFont("file:res/font/kabel.ttf", 8)));
		label.setWrapText(true);
		
<<<<<<< HEAD
=======
//		label.setMaxWidth(LandHEIGHT);
>>>>>>> b066df91a573c77c5bab26c23c9a72334d5bd4a1
		setAlignmentNode(label, position);
		
		/*AnchorPane.setLeftAnchor(label, 0.0);
		AnchorPane.setRightAnchor(label, 0.0);
		
		/*if(position == Pos.TOP_CENTER || position == Pos.BOTTOM_CENTER){
			AnchorPane.setLeftAnchor(label, 0.0);
			AnchorPane.setRightAnchor(label, 0.0);
		}else {
			/*AnchorPane.setLeftAnchor(label, 0.0);
			AnchorPane.setLeftAnchor(label, 0.0);
		}
		//setAlignmentNode(label, position);*/
		
		return label;
	}

	/**
	 * Migliorare la dimensione dell'immagine
	 * 
	 * */
	public static Label getLabelImage(final ImageView ImageView, final Pos position) {
		Label image = new Label();
		ImageView imageView = ImageView;
		imageView.setFitWidth(LandSimpleWIDTH * 0.7);
		imageView.setFitHeight(LandSimpleWIDTH * 0.7);
		
<<<<<<< HEAD
		if(position == Pos.TOP_CENTER || position == Pos.BOTTOM_CENTER) {
			imageView.setRotate(NOT_ROTATE);
		}else {
			imageView.setRotate(position == Pos.CENTER_LEFT ? ROTATE_LEFT : ROTATE_RIGHT);
		}
		
		image.setGraphic(imageView);
		image.setAlignment(Pos.BASELINE_CENTER);
=======
//		if(position == Pos.TOP_CENTER || position == Pos.BOTTOM_CENTER) {
//			image.setRotate(NOT_ROTATE);
//		}else {
//			image.setRotate(position == Pos.CENTER_LEFT ? ROTATE_LEFT : ROTATE_RIGHT);
//		}
>>>>>>> b066df91a573c77c5bab26c23c9a72334d5bd4a1
		setAlignmentNode(image, position);
		
		return image;
	}

	public static Separator getSeparator(final Orientation orientation) {
		Separator seperator = new Separator(orientation);
		setAlignmentNode(seperator, orientation == Orientation.VERTICAL ? Pos.CENTER_LEFT :  Pos.TOP_CENTER);
		
		return seperator;
	}

	private static void setAlignmentNode(Control node, final Pos position) {
//		if(position == Pos.CENTER_LEFT || position == Pos.CENTER_RIGHT) {
//			AnchorPane.setTopAnchor(node, 0.0);
//			AnchorPane.setBottomAnchor(node, 0.0);
//		}else {
			AnchorPane.setLeftAnchor(node, 0.0);
			AnchorPane.setRightAnchor(node, 0.0);		
//		}
	}
}
