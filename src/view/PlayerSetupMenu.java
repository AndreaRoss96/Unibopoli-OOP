package view;

import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import utilities.AlertFactory;

public class PlayerSetupMenu extends BorderPane {

	private static final double STANDARD_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.40;
	private static final double STANDARD_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 0.80;
	private static final int PLAYER_MIN = 2;
	private static final int PLAYER_MAX = 6;

	private List<String> iconList;
	private List<String> chosenList;

	public PlayerSetupMenu(Stage stage) {
		iconList = new ArrayList<>();
		chosenList = new ArrayList<>();
		iconList.add("Mushroom");
		iconList.add("Hat");
		iconList.add("Wine");
		iconList.add("Iron");
		iconList.add("Boot");
		iconList.add("Car");

		Scene scene = new Scene(this);
		scene.getStylesheets().add(getClass().getResource("setupPlayer.css").toExternalForm());
		stage.setScene(scene);
		stage.setWidth(STANDARD_WIDTH);
		stage.setHeight(STANDARD_HEIGHT);
		stage.show();
		stage.centerOnScreen();
		stage.setResizable(true);

		Image cardBoard = new Image("/ImmaginiProva/mopoli_cfu.png");
		BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true);
		Background background = new Background(new BackgroundImage(cardBoard, BackgroundRepeat.ROUND,
				BackgroundRepeat.ROUND, BackgroundPosition.CENTER, bSize));
		this.setBackground(background);

		final FlowPane flowPane = new FlowPane();
		final Button addPlayer = new Button("", new ImageView(new Image("/Icone/icons8-plus-50.png")));
		flowPane.getChildren().add(addPlayer);
		flowPane.getChildren().add(0, addPlayerSetupBox(flowPane));
		// flowPane.setAlignment(Pos.TOP_CENTER);
		this.setCenter(flowPane);

		final HBox hBox = new HBox();
		final Label mapLabel = new Label("Choose map:");
		final ComboBox<String> mapBox = new ComboBox<>();
		mapBox.getItems().add("Classical"); // ci dovrebbe essere un metodo che va a leggere un ifle ocn tutte le mappe
		final Button startGame = new Button("Start Game");
		final Button cancel = new Button("Cancel");
		hBox.getChildren().add(mapLabel);

		hBox.getChildren().add(mapBox);
		HBox.setMargin(mapBox, new Insets(0, 15, 0, 15));
		hBox.getChildren().add(startGame);
		hBox.getChildren().add(cancel);
		HBox.setMargin(cancel, new Insets(0, 15, 0, 15));
		hBox.setAlignment(Pos.CENTER_RIGHT);
		hBox.setPadding(new Insets(0, 15, 10, 0));

		this.setBottom(hBox);

		addPlayer.setOnAction(e -> {
			if (flowPane.getChildren().size() <= PLAYER_MAX) {
				flowPane.getChildren().add(0, addPlayerSetupBox(flowPane));
			} else {
				AlertFactory.createErrorAlert("Warning", "you have reached the maximum number of players!", null)
						.showAndWait();
			}
		});

		startGame.setOnAction(e -> {
			flowPane.getChildren().stream().filter(element -> element instanceof HBox).map(bBox -> (HBox) bBox)
					.forEach(bBox -> bBox.getChildren().stream().filter(txtField -> txtField instanceof TextField)
							.map(txtField -> (TextField) txtField).forEach(txtField -> {
								if (txtField.getText().equals("Insert player name...")) {
									System.out.println("Accidenti, un po' di fantasia");
								}
								System.out.println(txtField.getText());
							}));
		});

		cancel.setOnAction(e -> {
			new MainMenu(stage);
		});

		stage.setTitle("Choose players");
		stage.show();
	}

	private HBox addPlayerSetupBox(FlowPane flowPane) {
		final HBox hBox = new HBox();
		hBox.setPrefWidth(Region.USE_COMPUTED_SIZE);
		final TextField nameField = new TextField("Insert player name...");

		final ComboBox<String> icons = new ComboBox<>();
		icons.setPrefWidth(Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.10);
		icons.setCellFactory(new ShapeCellFactory());
		icons.setButtonCell(new ShapeCell());
		icons.getItems().addAll(iconList);
		HBox.setMargin(icons, new Insets(0, 15, 0, 15));

		final Button removePlayer = new Button("X");
		removePlayer.setFont(Font.font("Arial", FontWeight.BOLD, 19));
		removePlayer.setStyle("-fx-background-radius: 5em; " + "-fx-min-width: 45px; " + "-fx-min-height: 45px; "
				+ "-fx-max-width: 45px; " + "-fx-max-height: 45px;");

		hBox.getChildren().addAll(nameField, icons, removePlayer);
		hBox.setAlignment(Pos.CENTER);
		hBox.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;" + "-fx-border-width: 2;"
				+ "-fx-border-insets: 5;" + "-fx-border-radius: 5;" + "-fx-border-color: blue;"
				+ "-fx-background-color: white;");

		removePlayer.setOnAction(a -> {
			if (icons.getValue() != null) {
				this.iconList.add(icons.getValue());
			}
			if (flowPane.getChildren().size() > PLAYER_MIN) {
				flowPane.getChildren().remove(hBox);
			}
		});

		nameField.setOnMouseClicked(e -> {
			nameField.clear();
		});

		icons.setOnMouseClicked(e -> {
			icons.getItems().clear();
			icons.getItems().addAll(iconList);
		});

		icons.setOnMousePressed(e -> {
			if (icons.getValue() != null) {
				this.chosenList.removeIf(x -> icons.getValue().equals(x));
				this.iconList.add(icons.getValue());
			}
		});

		icons.setOnAction(e -> {
			if (icons.getValue() != null) {
				if (this.iconList.removeIf(x -> icons.getValue().equals(x))) {
					this.chosenList.add(icons.getValue());
				} else {
					this.chosenList.removeIf(x -> icons.getValue().equals(x));
					this.iconList.add(icons.getValue());
				}
			}
		});

		return hBox;

	}

}
