package view;

import java.util.Optional;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

import model.tiles.BuildableImpl;
import model.tiles.NotBuildableImpl;
import model.tiles.Obtainable;
import utilities.IconLoader;
import utilities.PaneDimensionSetting;
import utilities.enumerations.TileTypes;

public class Contract extends AnchorPane {

	private static final double WIDTH = PaneDimensionSetting.getInstance().getCommandBridgeWidth();
	private static final double HEIGHT = PaneDimensionSetting.getInstance().getCommandBridgeHeight();
	private static final double WIDTH_ANCHOR = WIDTH * 0.0044;
	private static final double HEIGHT_ANCHOR = HEIGHT * 0.007;
	private static final double H_GAP = WIDTH * 0.0032;
	private static final double V_GAP = HEIGHT * 0.0047;
	private static final double V_PADDING = WIDTH * 0.009;
	private static final double PREF_WIDTH = WIDTH * 0.18;
	private static final double PREF_HEIGHT = HEIGHT * 0.43;
	private static final double LABEL_HEIGHT = PREF_HEIGHT * 0.045;
	private static final double LABEL_WIDTH = PREF_WIDTH / 2 - H_GAP * 2;
	private static final double IMAGE_HEIGHT = LABEL_HEIGHT * 10;
	private static final double IMAGE_WIDTH = LABEL_WIDTH * 1.5;
	private static final double LABEL_BIG_WIDTH = 255;
	private static final Font PROPERTY_FONT = Font.font("Kabel", FontWeight.BOLD, 20);

	private final GridPane insidePane;
	private int row;
	
	public Contract(Obtainable property) {
		this.insidePane = new GridPane();
		this.insidePane.setPadding(new Insets(0, V_PADDING, 0, V_PADDING));
		this.insidePane.setVgap(V_GAP);
		this.insidePane.setPrefSize(PREF_WIDTH, PREF_HEIGHT);
		this.insidePane.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
		this.insidePane.setAlignment(Pos.TOP_CENTER);
		this.insidePane.setStyle("-fx-border-color: black;");

		if (property.getTileType() == TileTypes.BUILDABLE) {
			createContractForBuildable((BuildableImpl) property);
		} else {
			createContractForNotBuildable((NotBuildableImpl) property);
		}
		
		AnchorPane.setTopAnchor(insidePane, WIDTH_ANCHOR);
		AnchorPane.setLeftAnchor(insidePane, HEIGHT_ANCHOR);
		AnchorPane.setRightAnchor(insidePane, HEIGHT_ANCHOR);
		AnchorPane.setBottomAnchor(insidePane, WIDTH_ANCHOR);

		this.getChildren().add(insidePane);
		this.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
		this.setId("contract");
		this.getStylesheets().add("/style/style.css");
	}

	private void createContractForBuildable(BuildableImpl property) {
		this.labelCreator("Contract worth  " + property.getPrice() + "  $", Optional.empty(), 2, insidePane);
		final Label propertyName = new Label(property.getNameOf());
		propertyName.setPrefSize(LABEL_BIG_WIDTH, LABEL_HEIGHT * 4.5);
		propertyName.setAlignment(Pos.CENTER);
		propertyName.setTextAlignment(TextAlignment.CENTER);
		propertyName.setFont(PROPERTY_FONT);
		propertyName.setStyle("-fx-border-color: black;" + " -fx-background-color: "
				+ property.getColorOf().getPaintValue().get() + ";");
		propertyName.setWrapText(true);
		insidePane.add(propertyName, 0, this.row++, 2, 1);
		GridPane.setMargin(propertyName, new Insets(LABEL_HEIGHT, 0, LABEL_HEIGHT, 0));

		this.labelCreator("RENT", Optional.of(property.getRent() + " $"), 1, insidePane);
		for (int i = 1; i <= 5; i++) {
			labelCreator("With " + (i != 5 ? i + " House" + (i == 1 ? "" : "s") : "HOTEL"),
					Optional.of(String.valueOf(property.getRent(i)) + " $"), 1, insidePane);
		}

		final Line line = new Line(-100, 0, 100, 0);
		this.insidePane.add(line, 0, this.row++, 2, 1);
		GridPane.setHalignment(line, HPos.CENTER);

		this.labelCreator("House cost", Optional.of(String.valueOf(property.getPriceForBuilding()) + " $"), 1, insidePane);
		this.labelCreator("Hotel cost", Optional.of(String.valueOf(property.getPriceForBuilding()) + " $"), 1, insidePane);
		this.labelCreator("Mortgage value", Optional.of(String.valueOf(property.getMortgage()) + " $"), 2, insidePane);
	}

	private void createContractForNotBuildable(NotBuildableImpl property) {
		this.labelCreator("Contract worth  " + property.getPrice() + "  $", Optional.empty(), 1, insidePane);
		final ImageView image = IconLoader.getLoader().getImageFromPath(property.getPathImage()).get();
		image.setFitHeight(IMAGE_HEIGHT);
		image.setFitWidth(IMAGE_WIDTH);
		insidePane.add(image, 0, this.row++, 2, 1);
		GridPane.setHalignment(image, HPos.CENTER);

		final Label propertyName = new Label(property.getNameOf());
		propertyName.setWrapText(true);
		propertyName.setPrefSize(LABEL_BIG_WIDTH, LABEL_HEIGHT * 4);
		propertyName.setAlignment(Pos.CENTER);
		propertyName.setTextAlignment(TextAlignment.CENTER);
		propertyName.setFont(PROPERTY_FONT);
		propertyName.setWrapText(true);
		this.insidePane.add(propertyName, 0, this.row++, 2, 1);

		for (int i = 1; i <= property.getColorOf().getNumTiles(); i++) {
			labelCreator(i == 1 ? "RENT" : "if " + i + " are owned: ", Optional.of(property.getRent() * i + " $"), 1,
					insidePane);
		}
		labelCreator("Mortgage value: " + property.getMortgage() + " $", Optional.empty(), 1, insidePane);
	}

	private void labelCreator(String leftLabel, Optional<String> rightLabel, int colspan, GridPane insidePane) {
		final Label left = new Label(leftLabel);
		left.setWrapText(true);
		this.insidePane.add(left, 0, this.row, colspan, 1);
		
		if (rightLabel.isPresent()) {
			final Label right = new Label(rightLabel.get());
			this.insidePane.add(right, 1, this.row);
			GridPane.setHalignment(right, HPos.RIGHT);
		}
		this.row++;
	}
}
