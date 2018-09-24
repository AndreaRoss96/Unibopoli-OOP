package view.gameSetup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import controller.ControllerImpl;
import utilities.IconLoader;
import utilities.PaneDimensionSetting;
import utilities.enumerations.ClassicType;
import view.AlertFactory;
import view.CommandBridge;

public class PlayerSetupMenu extends Scene {

	private static final double STANDARD_WIDTH = PaneDimensionSetting.getInstance().getCommandBridgeWidth() * 0.4;
	private static final double STANDARD_HEIGHT = PaneDimensionSetting.getInstance().getCommandBridgeHeight() * 0.9;
	private static final int PLAYER_MIN = 2;
	private static final int PLAYER_MAX = 6;
	private static final String TITLE = "Setup players";
	
	private static Stage mainStage;
	
	private List<String> iconList;
	private List<String> chosenList;
	private Map<String, String> imageMap;

	private PlayerSetupMenu() {
		super(new BorderPane(), STANDARD_WIDTH, STANDARD_HEIGHT);
		
		final BorderPane borderPane = new BorderPane();
		
		this.imageMap = ClassicType.Avatar.getAvatarMap();
		
		iconList = new ArrayList<>();
		chosenList = new ArrayList<>();
		
		iconList.addAll(imageMap.keySet());
		
		final FlowPane flowPane = new FlowPane();
		final Button addPlayer = new Button("", IconLoader.getLoader().getImageFromPath(ClassicType.Other.GENERALOTHERIMAGEMAP.getPlusImage()));
		addPlayer.getStyleClass().add("roundButton");
		flowPane.getChildren().add(addPlayer);
		flowPane.getChildren().add(0, addPlayerSetupBox(flowPane));
		flowPane.getChildren().add(0, addPlayerSetupBox(flowPane));
		borderPane.setCenter(flowPane);

		final HBox hBox = new HBox();
		final Label mapLabel = new Label("Choose map:");
		final ComboBox<String> mapBox = new ComboBox<>();
		mapBox.getItems().addAll(ControllerImpl.getController().getGameMode());
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

		borderPane.setBottom(hBox);

		flowPane.getChildren().addListener((ListChangeListener<? super Node>) e -> {
			addPlayer.setDisable(flowPane.getChildren().size() > PLAYER_MAX);
		});

		addPlayer.setOnAction(e -> {
			if (flowPane.getChildren().size() <= PLAYER_MAX) {
				flowPane.getChildren().add(0, addPlayerSetupBox(flowPane));
			}
		});

		startGame.setOnAction(e -> {
			final List<PlayerSetupBox> psbList = flowPane.getChildren().stream()
					.filter(element -> element instanceof PlayerSetupBox).map(pBox -> (PlayerSetupBox) pBox)
					.collect(Collectors.toList());
			final List<String> playersNames = psbList.stream().map(PlayerSetupBox::getNameField).map(TextField::getText)
					.collect(Collectors.toList());
			/* check if all names are presents and are all different and all player have an avatar */
			if (!this.checkNames(playersNames)) {
				AlertFactory.createInformationAlert("Nope", "All player need a name!");
			} else if (playersNames.stream().distinct().count() != playersNames.size()) {
				AlertFactory.createInformationAlert("Nope", "Use different names!");
			} else if (!this.checkIcon(psbList.stream().map(PlayerSetupBox::getIcons).collect(Collectors.toList()))) {
				AlertFactory.createInformationAlert("Nope", "All players must have an avatar!");
			} else { /* execute action */				
				final List<String> playersIcon = new ArrayList<String>();
				psbList.forEach(bBox -> {
					playersIcon.add(imageMap.get(bBox.getIcons().getSelectionModel().getSelectedItem()));
				});
				
				ControllerImpl.getController().newGameInit(mapBox.getSelectionModel().getSelectedItem(), playersNames,
						playersIcon);
				mainStage.setScene(CommandBridge.get(mainStage));
				mainStage.centerOnScreen();
			}
		});

		cancel.setOnAction(e -> {
			mainStage.setScene(MainMenu.get(mainStage));
			mainStage.centerOnScreen();
		});

		this.getStylesheets().add(getClass().getResource("/style/style.css").toExternalForm());
		borderPane.setId("setupPlayer");
		this.setRoot(borderPane);
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
	
	private boolean checkNames (List<String> names) {
		for (String name : names) {
			if(name.equals("Insert player name...") || name.isEmpty()) {
				return false;
			}
		}
		return true;
	}
	
	private boolean checkIcon(List<ComboBox<String>> icons) {
		for (ComboBox<String> icon : icons) {
			if(icon.getSelectionModel().isEmpty()) {
				return false;
			}
		}
		return true;
	}

	public static PlayerSetupMenu get(Stage stage) {
		mainStage = stage;
		
		mainStage.setTitle(TITLE);
		
		return new PlayerSetupMenu();	
	}
}
