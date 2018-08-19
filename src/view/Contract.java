package view;

import java.awt.Toolkit;
import java.util.Optional;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
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
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import model.tiles.Buildable;
import model.tiles.NotBuildable;
import model.tiles.Obtainable;

public class Contract extends AnchorPane {

	private static final double WIDTH_ANCHOR = Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.0044;
	private static final double HEIGHT_ANCHOR = Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 0.007;
	private static final double H_GAP = Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.0029;
	private static final double V_GAP = Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 0.0047;
	private static final double V_PADDING = Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.009;
	private static final double PREF_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.18;
	private static final double PREF_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 0.40;
	private static final double LABEL_HEIGHT = PREF_HEIGHT * 0.045;
	private static final double LABEL_WIDTH = PREF_WIDTH / 2 - H_GAP * 3;
	private static final double LABEL_BIG_WIDTH = 255;
	private static final Font PROPERTY_FONT = Font.font("Kabel", FontWeight.BOLD, 20);

	public Contract(Obtainable property) { //aggiungi lo stato per vedere se una proprietà è ipotecata o meno 
		final FlowPane insidePane = new FlowPane(H_GAP, V_GAP);
		insidePane.setPadding(new Insets(0, V_PADDING, 0, V_PADDING));
		insidePane.setPrefSize(PREF_WIDTH, PREF_HEIGHT);
		insidePane.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
		insidePane.setAlignment(Pos.TOP_CENTER);
		insidePane.setStyle("-fx-border-color: black;");

		final Image cardBoard = new Image("/images/cardBoard/white_cardstock.jpg");
		final BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true,
				true);
		final Background background = new Background(new BackgroundImage(cardBoard, BackgroundRepeat.REPEAT,
				BackgroundRepeat.ROUND, BackgroundPosition.CENTER, bSize));
		final DropShadow ds = new DropShadow();
		ds.setOffsetY(3.0f);
		ds.setColor(Color.color(0.4f, 0.4f, 0.4f));

		if (property instanceof Buildable) {
			createContractForBuildable(insidePane, (Buildable) property);
		} else {
			createContractForNotBuildable(insidePane, (NotBuildable) property);
		}

		this.getChildren().add(insidePane);
		this.setBackground(background);
		this.setStyle("-fx-border-color: black; -fx-border-radius: 10px; ");
		this.setEffect(ds);
		this.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
		AnchorPane.setTopAnchor(insidePane, WIDTH_ANCHOR);
		AnchorPane.setLeftAnchor(insidePane, HEIGHT_ANCHOR);
		AnchorPane.setRightAnchor(insidePane, HEIGHT_ANCHOR);
		AnchorPane.setBottomAnchor(insidePane, WIDTH_ANCHOR);
	}

	private void createContractForBuildable(FlowPane insidePane, Buildable property) {
		labelCreator("Contract worth  " + property.getPrice() + "  $", Optional.empty(), LABEL_HEIGHT * 1.24,
				insidePane);
		final Label propertyName = new Label(property.getNameOf());
		propertyName.setPrefSize(LABEL_BIG_WIDTH, LABEL_HEIGHT * 4.5);
		propertyName.setAlignment(Pos.CENTER);
		propertyName.setFont(PROPERTY_FONT);
		propertyName
				.setStyle("-fx-border-color: black; -fx-background-color: " + property.getColorOf().getName() + ";");
		insidePane.getChildren().add(propertyName);
		FlowPane.setMargin(propertyName, new Insets(0, 0, LABEL_HEIGHT, 0));
		labelCreator("RENT", Optional.of(property.getRent() + " $"), LABEL_HEIGHT, insidePane);
		for (int i = 1; i <= 5; i++) {
			labelCreator("With " + (i != 5 ? i + " House" + (i == 1 ? "" : "s") : "HOTEL"),
					Optional.of(String.valueOf(property.getRent())), LABEL_HEIGHT, insidePane); // mi serve il costo per
																								// ogni casa
		}
		Line line = new Line();
		line.setStartX(-100);
		line.setEndX(100);
		line.setStrokeType(StrokeType.OUTSIDE);
		insidePane.getChildren().add(line);
		labelCreator("House cost", Optional.of(String.valueOf(property.getPriceForBuilding())), LABEL_HEIGHT,
				insidePane);
		labelCreator("Hotel cost\n(plus 4 more houses).", Optional.of(String.valueOf(property.getPriceForBuilding())),
				LABEL_HEIGHT * 2, insidePane);
		labelCreator("Mortgage value", Optional.of(String.valueOf(property.getMortgage())), LABEL_HEIGHT, insidePane);
	}

	private void createContractForNotBuildable(FlowPane insidePane, NotBuildable property) {
		// final Label contractValue = new Label("Contract worth " + property.getPrice()
		// + " $");
		// contractValue.setPrefWidth(insidePane.getPrefWidth());
		// contractValue.setPrefHeight(LABEL_HEIGHT * 1.24);
		// contractValue.setPrefSize(LABEL_BIG_WIDTH, LABEL_HEIGHT);
		labelCreator("Contract worth  " + property.getPrice() + "  $", Optional.empty(), LABEL_HEIGHT * 1.24,
				insidePane);
		// insidePane.getChildren().add(contractValue);

		// mi servono le posizioni nella mappa per capire se si tratta di società della
		// luce o dell'acqua
		// imageView.setFitHeight/Width

		final Label propertyName = new Label(property.getNameOf()); // nome della stazione
		propertyName.setWrapText(true);
		propertyName.setPrefSize(LABEL_BIG_WIDTH, LABEL_HEIGHT * 4.5);
		propertyName.setAlignment(Pos.CENTER);
		propertyName.setTextAlignment(TextAlignment.CENTER);
		propertyName.setFont(PROPERTY_FONT);
		insidePane.getChildren().add(propertyName);

		for (int i = 1; i <= property.getColorOf().getNumTiles(); i++) {
			// Label leftLabel = new Label(i == 1 ? "RENT " : "if " + i + " are owned: ");
			// leftLabel.setPrefHeight(LABEL_HEIGHT);
			// leftLabel.setPrefWidth(LABEL_WIDTH);
			// Label rightLabel = new Label(property.getRent() * i + " $");
			// rightLabel.setPrefHeight(LABEL_HEIGHT);
			// rightLabel.setPrefWidth(LABEL_WIDTH);
			// rightLabel.setAlignment(Pos.CENTER_RIGHT);
			// insidePane.getChildren().add(leftLabel);
			// insidePane.getChildren().add(rightLabel);
			labelCreator(i == 1 ? "RENT" : "if " + i + " are owned: ", Optional.of(property.getRent() * i + " $"),
					LABEL_HEIGHT, insidePane);
		}
		// final Label mortgageValue = new Label("Mortgage value: " +
		// property.getMortgage() + " $");
		// mortgageValue.setPrefSize(LABEL_BIG_WIDTH, LABEL_HEIGHT);
		// mortgageValue.setAlignment(Pos.CENTER);
		// insidePane.getChildren().add(mortgageValue);
		labelCreator("Mortgage value: " + property.getMortgage() + " $", Optional.empty(), LABEL_HEIGHT, insidePane);
	}
	
	private void labelCreator(String leftLabel, Optional<String> rightLabel, Double labelHeight,
			FlowPane insidePane) {

		Label left = new Label(leftLabel);
		left.setPrefSize(rightLabel.isPresent() ? LABEL_WIDTH : LABEL_WIDTH * 2, labelHeight);
		left.setWrapText(true);
		insidePane.getChildren().add(left);
		if (rightLabel.isPresent()) {
			Label right = new Label(rightLabel.get());
			right.setPrefSize(LABEL_WIDTH, labelHeight);
			right.setAlignment(Pos.CENTER_RIGHT);
			insidePane.getChildren().add(right);
		}
	}
}
