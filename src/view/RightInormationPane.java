package view;

import controller.ControllerImpl;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import model.player.PlayerInfo;
import utilities.PaneDimensionSetting;

public class RightInormationPane extends VBox {

	private static final double H_DISTANCE = 0.43;
	private static final double V_DISTANCE = 0.21;
	private static final double SETTINGS_DISTANCE = 14;
	private static final double PANE_WIDTH = PaneDimensionSetting.getInstance().getLateralPaneWidth();
	private static final double PANE_HEIGHT = PaneDimensionSetting.getInstance().getLateralPaneHeight();
	private static final String END_TURN_PATH = "/images/Icons/next_player.png";
	private static final String EXIT_JAIL_PATH = "/images/Icons/hammer_law.png";

	private static Label leftDiceResult;
	private static Label rightDiceResult;
	private static Label playerLabel;
	private static Label cashLabel;
	private static Label netWorthLabel;
	private static Label prisonLabel;
	private static Button diceBtn;
	private static Button endTurn;

	public RightInormationPane() {
		this.setAlignment(Pos.TOP_CENTER);
		this.setStyle("-fx-border-color: black");
		final AnchorPane topAnchorPane = new AnchorPane();
		topAnchorPane.setPrefWidth(PANE_WIDTH);
		topAnchorPane.setPrefHeight(PANE_WIDTH / 2); // sicuro ?

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

		diceBtn = new Button("", new ImageView("/images/Icons/dices.png"));
		AnchorPane.setRightAnchor(diceBtn, PANE_WIDTH * H_DISTANCE);
		AnchorPane.setLeftAnchor(diceBtn, PANE_WIDTH * H_DISTANCE);
		AnchorPane.setBottomAnchor(diceBtn, PANE_WIDTH * V_DISTANCE);
		AnchorPane.setTopAnchor(diceBtn, PANE_WIDTH * V_DISTANCE);
		topAnchorPane.getChildren().add(diceBtn);

		final Button setting = new Button("", new ImageView("/images/Icons/gear.png"));
		setting.setStyle("-fx-background-radius: 5em; " + "-fx-min-width: 40px; " + "-fx-min-height: 40px; "
				       + "-fx-max-width: 40px; " + "-fx-max-height: 40px;");
		AnchorPane.setRightAnchor(setting, SETTINGS_DISTANCE * 1.5);
		AnchorPane.setTopAnchor(setting, SETTINGS_DISTANCE);
		topAnchorPane.getChildren().add(setting);

		this.getChildren().add(topAnchorPane);

		final Button tradeBtn = new Button("Trade");
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
		this.getChildren().addAll(playerLabel, new Line(), cashLabel, new Line(), netWorthLabel, new Line(), prisonLabel);

		endTurn = new Button("", new ImageView(END_TURN_PATH));
		endTurn.setStyle("-fx-background-radius: 5em; " + "-fx-min-width: 52px; " + "-fx-min-height: 52px; "
				       + "-fx-max-width: 52px; " + "-fx-max-height: 52px;");
		endTurn.setTooltip(new Tooltip("Next Player"));
		endTurn.setDisable(true);

		AnchorPane.setBottomAnchor(endTurn, SETTINGS_DISTANCE);
		AnchorPane.setRightAnchor(endTurn, SETTINGS_DISTANCE * 2.5);
		final AnchorPane bottomAnchorPane = new AnchorPane(endTurn);
		bottomAnchorPane.setPrefHeight(PANE_HEIGHT);
		bottomAnchorPane.setPrefWidth(PANE_WIDTH);

		this.getChildren().add(bottomAnchorPane);

		diceBtn.setOnAction(e -> {
			ControllerImpl.getController().diceClick();
		});

		endTurn.setOnAction(e -> {
			ControllerImpl.getController().endTurnClick();
		});

		tradeBtn.setOnAction(e -> {
			ControllerImpl.getController().tradeClick();
		});
		
		this.setId("RightInformationPane");
		this.getStylesheets().add(getClass().getResource("/style/style.css").toExternalForm());
	}

	/**
	 * Update the right and the left label that shown the results of dices rolls.
	 * 
	 * @param leftResult
	 *            the result of first dice
	 * @param rightResult
	 *            the result of second dice
	 */
	public static void updateDiceLabel(int leftResult, int rightResult) {
		leftDiceResult.setText(String.valueOf(leftResult));
		rightDiceResult.setText(String.valueOf(rightResult));
	}

	/**
	 * update the labels with the information about the player.
	 * 
	 * @param player
	 *            current player in turn
	 */
	public static void updateLabels(PlayerInfo player) {
		playerLabel.setText("Player: " + player.getName());
		cashLabel.setText("Cash: " + player.getMoney());
		netWorthLabel.setText("Net Worth: " + player.totalAssets());
		prisonLabel.setText("da gestire");
	}

	/**
	 * Set disable dice button if the player have done (rolled dices), and enable
	 * the endTurn button. If the player have done a pair throws he/she have to
	 * re-roll dices.
	 * 
	 * @param done
	 *            true if player has thrown dices this turn
	 */
	public static void updateButton(boolean done) {
		diceBtn.setDisable(done);
		endTurn.setDisable(!done);
	}

	/**
	 * Set the right label if the player is in prison
	 * 
	 * @param isJail
	 */
	public static void updateJailButton(boolean isJail) {
		endTurn.setGraphic(new ImageView(isJail ? EXIT_JAIL_PATH : END_TURN_PATH));
		endTurn.setTooltip(isJail ? new Tooltip("jail fee: 125 $") : null);
		endTurn.setDisable(!isJail);
	}

}
