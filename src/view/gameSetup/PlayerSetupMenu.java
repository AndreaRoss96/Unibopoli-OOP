package view.gameSetup;

import java.awt.Toolkit;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import controller.ControllerImpl;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
import javafx.stage.Stage;
import utilities.AlertFactory;
import utilities.PaneDimensionSetting;

public class PlayerSetupMenu extends BorderPane {

	private static final double STANDARD_WIDTH = PaneDimensionSetting.getInstance().getCommandBridgeWidth() * 0.40;
	private static final double STANDARD_HEIGHT = PaneDimensionSetting.getInstance().getCommandBridgeHeight() * 0.80;
	private static final int PLAYER_MIN = 2;
	private static final int PLAYER_MAX = 6;

	private List<String> iconList;
	private List<String> chosenList;
	private Map<String, String> imageMap;

	public PlayerSetupMenu(Stage stage) {
		iconList = new ArrayList<>();
		chosenList = new ArrayList<>();
		iconList.add("Mushroom");
		iconList.add("Hat");
		iconList.add("Wine");
		iconList.add("Iron");
		iconList.add("Boot");
		iconList.add("Car"); // utilizza iconloader vd. esperimento
		/* implementare nell'iconloader, parla con matti
		IconLoader iconPath = new IconLoader();
		imageMap = iconPath.getMap();
		*/
		Scene scene = new Scene(this);
		scene.getStylesheets().add(getClass().getResource("setupPlayer.css").toExternalForm());
		stage.setScene(scene);
		stage.setWidth(STANDARD_WIDTH);
		stage.setHeight(STANDARD_HEIGHT);
		stage.show();
		stage.centerOnScreen();

		Image cardBoard = new Image("images" + File.separator + "backgrounds" + File.separator + "monopoli_cfu.png");
		BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true);
		Background background = new Background(new BackgroundImage(cardBoard, BackgroundRepeat.ROUND,
				BackgroundRepeat.ROUND, BackgroundPosition.CENTER, bSize));
		this.setBackground(background);

		final FlowPane flowPane = new FlowPane();
		final Button addPlayer = new Button("", new ImageView(new Image("/Icone/icons8-plus-50.png"))); //cambia path
		flowPane.getChildren().add(addPlayer);
		flowPane.getChildren().add(0, addPlayerSetupBox(flowPane));
		flowPane.getChildren().add(0, addPlayerSetupBox(flowPane));
		this.setCenter(flowPane);

		final HBox hBox = new HBox();
		final Label mapLabel = new Label("Choose map:");
		final ComboBox<String> mapBox = new ComboBox<>();
		mapBox.getItems().add("Classical"); // ci dovrebbe essere un metodo che va a leggere un ifle ocn tutte le mappe
		mapBox.setValue(mapBox.getItems().get(0));
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

		flowPane.getChildren().addListener((ListChangeListener<? super Node>) e -> {
			addPlayer.setDisable(flowPane.getChildren().size() > 6);
		});

		addPlayer.setOnAction(e -> {
			if (flowPane.getChildren().size() <= PLAYER_MAX) {
				flowPane.getChildren().add(0, addPlayerSetupBox(flowPane));
			}
		});

		startGame.setOnAction(e -> {
			List<PlayerSetupBox> psbList = flowPane.getChildren().stream()
					.filter(element -> element instanceof PlayerSetupBox).map(pBox -> (PlayerSetupBox) pBox)
					.collect(Collectors.toList());
			/* check if all names are presents and are all different */
			psbList.forEach(pBox -> {
				if (pBox.getNameField().getText().equals("Insert player name...")
						|| pBox.getNameField().getText().isEmpty()) {
					AlertFactory.createInformationAlert("Nope", null, "Use valid name!");
					e.consume();
				}
			});
			if (psbList.stream().map(PlayerSetupBox::getNameField).distinct().count() != flowPane.getChildren()
					.size()) {
				AlertFactory.createInformationAlert("Nope", null, "Use different names!");
				e.consume();
			}
			/* check if all players have chosen an icon */
			psbList.forEach(bBox -> {
				if (bBox.getIcons().getSelectionModel().isEmpty()) {
					AlertFactory.createInformationAlert("Nope", null, "All players must have an avatar!");
					e.consume();
				}
			});
			/* execute action */
			List<String> playersName = psbList.stream().map(PlayerSetupBox::getNameField).map(TextField::getText).collect(Collectors.toList());
			List<String> playersIcon = psbList.stream().map(PlayerSetupBox::getIcons).map(elem -> elem.getSelectionModel().getSelectedItem()).collect(Collectors.toList());
			ControllerImpl.getController().newGameInit(playersName, playersIcon);
		});

		cancel.setOnAction(e -> {
			new MainMenu(stage);
		});

		stage.setTitle("Setup players");
		stage.show();
	}

	private PlayerSetupBox addPlayerSetupBox(FlowPane flowPane) {
		PlayerSetupBox pBox = new PlayerSetupBox(this.imageMap);
		pBox.getIcons().getItems().addAll(iconList);
		
		pBox.getRemovePlayer().setDisable(flowPane.getChildren().size() <= PLAYER_MIN );


		pBox.getRemovePlayer().setOnAction(a -> {
			if (pBox.getIcons().getValue() != null) {
				this.iconList.add(pBox.getIcons().getValue());
			}
			if (flowPane.getChildren().size() > PLAYER_MIN) {
				flowPane.getChildren().remove(pBox);
			}
		});

		pBox.getNameField().setOnMouseClicked(e -> {
			pBox.getNameField().clear();
		});

		pBox.getIcons().setOnMouseClicked(e -> {
			pBox.getIcons().getItems().clear();
			pBox.getIcons().getItems().addAll(iconList);
		});

		pBox.getIcons().setOnMousePressed(e -> {
			if (pBox.getIcons().getValue() != null) {
				this.chosenList.removeIf(x -> pBox.getIcons().getValue().equals(x));
				this.iconList.add(pBox.getIcons().getValue());
			}
		});

		pBox.getIcons().setOnAction(e -> {
			if (pBox.getIcons().getValue() != null) {
				if (this.iconList.removeIf(x -> pBox.getIcons().getValue().equals(x))) {
					this.chosenList.add(pBox.getIcons().getValue());
				} else {
					this.chosenList.removeIf(x -> pBox.getIcons().getValue().equals(x));
					this.iconList.add(pBox.getIcons().getValue());
				}
			}
		});

		return pBox;
	}

}
