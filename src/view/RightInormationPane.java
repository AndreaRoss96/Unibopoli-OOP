package view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import model.player.PlayerInfo;
import utilities.PaneDimensionSetting;

public class RightInormationPane extends VBox {
	
	private static final double H_DISTANCE = 0.43;
	private static final double V_DISTANCE = 0.21;
	private static final double SPACING = 15;
	private static final double SETTINGS_DISTANCE = 14;
	private static final double PANE_WIDTH = PaneDimensionSetting.getInstance().getLateralPaneWidth();
	private static final double PANE_HEIGHT = PaneDimensionSetting.getInstance().getLateralPaneHeight();
	
	private Label leftDiceResult;
	private Label rightDiceResult;
	private Label playerLabel;
	private Label cashLabel;
	private Label netWorthLabel;
	private Label prisonLabel;

	public RightInormationPane() {
//		final VBox root = new VBox(SPACING);
//		root.setAlignment(Pos.TOP_CENTER);
		this.setAlignment(Pos.TOP_CENTER);
		this.setStyle("-fx-border-color: black");
		final AnchorPane topAnchorPane = new AnchorPane();
		topAnchorPane.setPrefWidth(PANE_WIDTH);
		topAnchorPane.setPrefHeight(PANE_WIDTH/2); // sicuro ?
		
		leftDiceResult = new Label("0");
		AnchorPane.setLeftAnchor(leftDiceResult, PANE_WIDTH * V_DISTANCE);
		AnchorPane.setBottomAnchor(leftDiceResult, PANE_WIDTH * V_DISTANCE);
		AnchorPane.setTopAnchor(leftDiceResult, PANE_WIDTH * V_DISTANCE);
		topAnchorPane.getChildren().add(leftDiceResult);
		
		rightDiceResult = new Label("0");
		AnchorPane.setRightAnchor(rightDiceResult, PANE_WIDTH * V_DISTANCE);
		AnchorPane.setBottomAnchor(rightDiceResult, PANE_WIDTH * V_DISTANCE);
		AnchorPane.setTopAnchor(rightDiceResult, PANE_WIDTH * V_DISTANCE);
		topAnchorPane.getChildren().add(rightDiceResult);
		
		final Button diceBtn = new Button("", new ImageView(new Image("/images/Icons/Anello.png"))); //serve il path dei dadi
		AnchorPane.setRightAnchor(diceBtn, PANE_WIDTH * H_DISTANCE);
		AnchorPane.setLeftAnchor(diceBtn, PANE_WIDTH * H_DISTANCE);
		AnchorPane.setBottomAnchor(diceBtn, PANE_WIDTH * V_DISTANCE);
		AnchorPane.setTopAnchor(diceBtn, PANE_WIDTH * V_DISTANCE);
		topAnchorPane.getChildren().add(diceBtn);
		
		final Button setting = new Button("/images/Icons/Anello.png"); //PATH DELL'INGRANAGGIO
		setting.setStyle("-fx-background-radius: 5em; "
				+ "-fx-min-width: 40px; " + "-fx-min-height: 40px; "
				+ "-fx-max-width: 40px; " + "-fx-max-height: 40px;");
		AnchorPane.setRightAnchor(setting, SETTINGS_DISTANCE*2.5);
		AnchorPane.setTopAnchor(setting, SETTINGS_DISTANCE);
		topAnchorPane.getChildren().add(setting);
		
		this.getChildren().add(topAnchorPane);
		
		final Button tradeBtn = new Button ("Trade");
		tradeBtn.setPrefWidth(PANE_WIDTH);
		this.getChildren().add(tradeBtn);
		
		playerLabel = new Label();
		playerLabel.setWrapText(true);
		cashLabel = new Label();
		cashLabel.setWrapText(true);
		netWorthLabel = new Label();
		netWorthLabel.setWrapText(true);
		prisonLabel = new Label();
		prisonLabel.setWrapText(true);
		this.getChildren().addAll(playerLabel, cashLabel, netWorthLabel, prisonLabel);
		
		final Button endTurn = new Button("", new ImageView(new Image("/images/Icons/Anello.png"))); //pathhh
		endTurn.setStyle("-fx-background-radius: 5em; "
				+ "-fx-min-width: 52px; " + "-fx-min-height: 52px; "
				+ "-fx-max-width: 52px; " + "-fx-max-height: 52px;");
		endTurn.setTooltip(new Tooltip("Next Player"));
		endTurn.setDisable(true);
		
		AnchorPane.setBottomAnchor(endTurn, SETTINGS_DISTANCE);
		AnchorPane.setRightAnchor(endTurn, SETTINGS_DISTANCE*2.5);
		final AnchorPane bottomAnchorPane = new AnchorPane(endTurn);
		bottomAnchorPane.setPrefHeight(PANE_HEIGHT);
		bottomAnchorPane.setPrefWidth(PANE_WIDTH);
		
		this.getChildren().add(bottomAnchorPane);
		
		diceBtn.setOnAction(e -> {
			//controller bisogna vedere se attivare o meno il pulsante endTurn -- in caso di dado doppio bisogna ritirare
		});
		
		endTurn.setOnAction(e -> {
			//controller.getController().endTurn();
		});
		
		tradeBtn.setOnAction(e -> {
			//controller.getController(). switch qualcosa che richiami il dialog per i trade
		});
	}
	
	public void updateDiceLabel(int leftResult, int rightResult) {
		this.leftDiceResult.setText(String.valueOf(leftResult));
		this.rightDiceResult.setText(String.valueOf(rightResult));
	}
	
	public void updateLabels(PlayerInfo player) {
		this.playerLabel.setText("Player: " + player.getName());
		this.cashLabel.setText("Cash: " + player.getMoney());
		this.netWorthLabel.setText("Net Worth: " + player.totalAssets());
		this.prisonLabel.setText("da gestire");
	}

}
