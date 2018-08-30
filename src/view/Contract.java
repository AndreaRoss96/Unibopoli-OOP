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
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

import model.tiles.Buildable;
import model.tiles.NotBuildable;
import model.tiles.Obtainable;
import utilities.PaneDimensionSetting;

public class Contract extends AnchorPane {

	private static final double WIDTH_ANCHOR = PaneDimensionSetting.getInstance().getCommandBridgeWidth() * 0.0044;
	private static final double HEIGHT_ANCHOR = PaneDimensionSetting.getInstance().getCommandBridgeHeight() * 0.007;
	private static final double H_GAP = PaneDimensionSetting.getInstance().getCommandBridgeWidth() * 0.0032;
	private static final double V_GAP = PaneDimensionSetting.getInstance().getCommandBridgeHeight() * 0.0047;
	private static final double V_PADDING = PaneDimensionSetting.getInstance().getCommandBridgeWidth() * 0.009;
	private static final double PREF_WIDTH = PaneDimensionSetting.getInstance().getCommandBridgeWidth() * 0.18;
	private static final double PREF_HEIGHT = PaneDimensionSetting.getInstance().getCommandBridgeHeight() * 0.43;
	private static final double LABEL_HEIGHT = PREF_HEIGHT * 0.045;
	private static final double LABEL_WIDTH = PREF_WIDTH / 2 - H_GAP * 2;
	private static final double IMAGE_HEIGHT = LABEL_HEIGHT * 10;
	private static final double IMAGE_WIDTH = LABEL_WIDTH * 1.5;
	private static final double LABEL_BIG_WIDTH = 255;
	private static final Font PROPERTY_FONT = Font.font("Kabel", FontWeight.BOLD, 20);

	private int row;

	public Contract(Obtainable property) {
		final GridPane insidePane = new GridPane();
		insidePane.setPadding(new Insets(0, V_PADDING, 0, V_PADDING));
		insidePane.setVgap(V_GAP);
		insidePane.setPrefSize(PREF_WIDTH, PREF_HEIGHT);
		insidePane.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
		insidePane.setAlignment(Pos.TOP_CENTER);
		insidePane.setStyle("-fx-border-color: black;");

		if (property instanceof Buildable) {
			createContractForBuildable(insidePane, (Buildable) property);
		} else {
			createContractForNotBuildable(insidePane, (NotBuildable) property);
		}

		this.getChildren().add(insidePane);
		this.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
		this.setId("contract");
		this.getStylesheets().add("/style/style.css");
		AnchorPane.setTopAnchor(insidePane, WIDTH_ANCHOR);
		AnchorPane.setLeftAnchor(insidePane, HEIGHT_ANCHOR);
		AnchorPane.setRightAnchor(insidePane, HEIGHT_ANCHOR);
		AnchorPane.setBottomAnchor(insidePane, WIDTH_ANCHOR);
	}

	private void createContractForBuildable(GridPane insidePane, Buildable property) {
		labelCreator("Contract worth  " + property.getPrice() + "  $", Optional.empty(), 2, insidePane);
		final Label propertyName = new Label(property.getNameOf());
		propertyName.setPrefSize(LABEL_BIG_WIDTH, LABEL_HEIGHT * 4.5);
		propertyName.setAlignment(Pos.CENTER);
		propertyName.setTextAlignment(TextAlignment.CENTER);
		propertyName.setFont(PROPERTY_FONT);
		propertyName.setStyle("-fx-border-color: black;" + " -fx-background-color: "
				+ property.getColorOf().getPaint().get().toString().replaceAll("0x", "#") + ";");
		propertyName.setWrapText(true);
		insidePane.add(propertyName, 0, this.row++, 2, 1);
		GridPane.setMargin(propertyName, new Insets(LABEL_HEIGHT, 0, LABEL_HEIGHT, 0));

		labelCreator("RENT", Optional.of(property.getRent() + " $"), 1, insidePane);
		for (int i = 1; i <= 5; i++) {
			labelCreator("With " + (i != 5 ? i + " House" + (i == 1 ? "" : "s") : "HOTEL"),
					Optional.of(String.valueOf(property.getRent(i)) + " $"), 1, insidePane);
		}

		Line line = new Line(-100, 0, 100, 0);
		line.setStrokeType(StrokeType.OUTSIDE);
		insidePane.add(line, 0, this.row++, 2, 1);
		GridPane.setHalignment(line, HPos.CENTER);

		labelCreator("House cost", Optional.of(String.valueOf(property.getPriceForBuilding()) + " $"), 1, insidePane);
		labelCreator("Hotel cost", Optional.of(String.valueOf(property.getPriceForBuilding()) + " $"), 1, insidePane);
		labelCreator("Mortgage value", Optional.of(String.valueOf(property.getMortgage()) + " $"), 2, insidePane);
	}

	private void createContractForNotBuildable(GridPane insidePane, NotBuildable property) {
		labelCreator("Contract worth  " + property.getPrice() + "  $", Optional.empty(), 1, insidePane);
		ImageView image = property.getImage();
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
		insidePane.add(propertyName, 0, this.row++, 2, 1);

		for (int i = 1; i <= property.getColorOf().getNumTiles(); i++) {
			labelCreator(i == 1 ? "RENT" : "if " + i + " are owned: ", Optional.of(property.getRent() * i + " $"), 1,
					insidePane);
		}
		labelCreator("Mortgage value: " + property.getMortgage() + " $", Optional.empty(), 1, insidePane);
	}

	private void labelCreator(String leftLabel, Optional<String> rightLabel, int colspan, GridPane insidePane) {
		Label left = new Label(leftLabel);
		left.setWrapText(true);
		insidePane.add(left, 0, this.row, colspan, 1);
		if (rightLabel.isPresent()) {
			Label right = new Label(rightLabel.get());
			insidePane.add(right, 1, this.row);
			GridPane.setHalignment(right, HPos.RIGHT);
		}
		this.row++;
	}
}
