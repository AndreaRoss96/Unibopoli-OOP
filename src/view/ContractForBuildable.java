/**
 * 
 */
package view;

import java.util.Optional;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.tiles.Buildable;
import model.tiles.Obtainable;

/**
 * Template for buildable properties's contract.
 * 
 * @author Rossolini Andrea
 *
 */
public class ContractForBuildable extends AbstractContract {

	private static final double STANDARD_HEIGHT = AbstractContract.getThisHeight() * 0.045;
	private static final double STANDARD_WIDTH = AbstractContract.getThisWidth() / 2 - AbstractContract.getHGap() * 3;
	private static final double BIG_WIDTH = 255;
	private static final Font PROPERTY_FONT = Font.font("Arial", FontWeight.BOLD, 24);

	public ContractForBuildable(Buildable property) {
		super(property);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.AbstractContract#inseideFiller(javafx.scene.layout.FlowPane,
	 * model.tiles.Obtainable)
	 */
	@Override
	protected void inseideFiller(FlowPane insidePane, Obtainable abstractProperty) {
		Buildable property = (Buildable) abstractProperty;
		labelCreator("Contract worth  " + property.getPrice() + "  $", Optional.empty(), STANDARD_HEIGHT * 1.24,
				insidePane);
		Label propertyName = new Label(property.getNameOf());
		propertyName.setPrefSize(BIG_WIDTH, STANDARD_HEIGHT * 4.5);
		propertyName.setAlignment(Pos.CENTER);
		propertyName.setFont(PROPERTY_FONT);
		propertyName
				.setStyle("-fx-border-color: black; -fx-background-color: " + property.getColorOf().getName() + ";");
		insidePane.getChildren().add(propertyName);
		FlowPane.setMargin(propertyName, new Insets(0, 0, STANDARD_HEIGHT, 0));
		labelCreator("RENT", Optional.of(property.getRent() + " $"), STANDARD_HEIGHT, insidePane);
		for (int i = 1; i <= 5; i++) {
			labelCreator("With " + (i != 5 ? i + " House" + (i == 1 ? "" : "s") : "HOTEL"),
					Optional.of(String.valueOf(property.getRent())), STANDARD_HEIGHT, insidePane); //mi serve il costo per ogni casa
		}
		Line line = new Line();
		line.setStartX(-100);
		line.setEndX(100);
		line.setStrokeType(StrokeType.OUTSIDE);
		labelCreator("House cost", Optional.of(String.valueOf(property.getPriceForBuilding())), STANDARD_HEIGHT, insidePane);
		labelCreator("Hotel cost\n(plus 4 more houses).", Optional.of(String.valueOf(property.getPriceForBuilding())), STANDARD_HEIGHT * 2, insidePane);
		labelCreator("Mortgage value", Optional.of(property.getMortgage() + ""), STANDARD_HEIGHT, insidePane);
	}

	private static void labelCreator(String leftLabel, Optional<String> rightLabel, Double labelHeight,
			FlowPane insidePane) {

		Label left = new Label(leftLabel);
		left.setPrefSize(rightLabel.isPresent() ? STANDARD_WIDTH : STANDARD_WIDTH * 2, labelHeight);
		left.setWrapText(true);
		insidePane.getChildren().add(left);
		if (rightLabel.isPresent()) {
			Label right = new Label(rightLabel.get());
			right.setPrefSize(STANDARD_WIDTH, labelHeight);
			right.setAlignment(Pos.CENTER_RIGHT);
			insidePane.getChildren().add(right);
		}
	}

}
