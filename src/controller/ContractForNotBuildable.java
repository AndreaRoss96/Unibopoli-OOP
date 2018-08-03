/**
 * 
 */
package controller;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import model.tiles.NotBuildable;
import model.tiles.Obtainable;

/**
 * Template for unbuildable properties's contract.
 * 
 * @author Rossolini Andrea
 *
 */
public class ContractForNotBuildable extends AbstractContract {

	private static final double STANDARD_HEIGHT = AbstractContract.getPrefHeight() * 0.045;
	private static final double STANDARD_WIDTH = AbstractContract.getPrefWidth() / 2 - AbstractContract.getHGap() * 3;
	private static final double BIG_WIDTH = 255;
	private static final Font PROPERTY_FONT = Font.font("Arial", FontWeight.BOLD, 24);

	/**
	 * @param property
	 */
	public ContractForNotBuildable(NotBuildable property) {
		super(property);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see controller.AbstractContract#inseideFiller(javafx.scene.layout.FlowPane,
	 * model.tiles.Obtainable)
	 */
	@Override
	protected void inseideFiller(FlowPane insidePane, Obtainable property) {
		Label contractValue = new Label("Contract worth  " + property.getPrice() + "  $");
		contractValue.setPrefWidth(insidePane.getPrefWidth());
		contractValue.setPrefHeight(STANDARD_HEIGHT * 1.24);
		contractValue.setPrefSize(BIG_WIDTH, STANDARD_HEIGHT);
		insidePane.getChildren().add(contractValue);

		// mi servono le posizioni nella mappa per capire se si tratta di società della
		// luce o dell'acqua
		//imageView.setFitHeight/Width

		Label propertyName = new Label("STAZIONE CHE NON FUNZIONA"); // nome della stazione
		propertyName.setWrapText(true);
		propertyName.setPrefSize(BIG_WIDTH, STANDARD_HEIGHT * 4.5);
		propertyName.setAlignment(Pos.CENTER);
		propertyName.setTextAlignment(TextAlignment.CENTER);
		propertyName.setFont(PROPERTY_FONT);
		insidePane.getChildren().add(propertyName);

		for (int i = 1; i <= property.getColorOf().getNumTiles(); i++) {
			Label leftLabel = new Label(i == 1 ? "RENT " : "if " + i + " are owned: ");
			leftLabel.setPrefHeight(STANDARD_HEIGHT);
			leftLabel.setPrefWidth(STANDARD_WIDTH);
			Label rightLabel = new Label(property.getRent() * i + " $");
			rightLabel.setPrefHeight(STANDARD_HEIGHT);
			rightLabel.setPrefWidth(STANDARD_WIDTH);
			rightLabel.setAlignment(Pos.CENTER_RIGHT);
			insidePane.getChildren().add(leftLabel);
			insidePane.getChildren().add(rightLabel);
		}
		Label mortgageValue = new Label("Mortgage value: " + property.getMortgage() + " $");
		mortgageValue.setPrefSize(BIG_WIDTH, STANDARD_HEIGHT);
		mortgageValue.setAlignment(Pos.CENTER);
		insidePane.getChildren().add(mortgageValue);
	}

}
