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
import javafx.scene.text.TextAlignment;
import model.player.PlayerInfo;
import utilities.IconLoader;
import utilities.PaneDimensionSetting;
import utilities.enumerations.ClassicType;

/**
 * Right lateral pane, with useful information for the current player
 * 
 * @author Rossolini Andrea
 *
 */
public class RightInormationPane extends VBox {

	private static final RightInormationPane SINGLETON = new RightInormationPane();
	
	private static final double H_DISTANCE = 0.43;
	private static final double V_DISTANCE = 0.21;
	private static final double SETTINGS_DISTANCE = 14;
	private static final String END_TURN_PATH = "/images/Icons/next_player.png";
	private static final String EXIT_JAIL_PATH = "/images/Icons/hammer_law.png";
	
	private final double PANE_WIDTH = PaneDimensionSetting.getInstance().getLateralPaneWidth();
	private final double PANE_HEIGHT = PaneDimensionSetting.getInstance().getLateralPaneHeight();

	private static Label leftDiceResult;
	private static Label rightDiceResult;
	private static Label playerLabel;
	private static Label cashLabel;
	private static Label netWorthLabel;
	private static Label prisonLabel;
	private static Button diceBtn;
	private static Button endTurn;
	
	public static RightInormationPane getRinghtInformationPane() {
		return SINGLETON;
	}

	private RightInormationPane() {
		this.setAlignment(Pos.TOP_CENTER);
		this.setStyle("-fx-border-color: black");
		final AnchorPane topAnchorPane = new AnchorPane();
		topAnchorPane.setPrefWidth(PANE_WIDTH);
		topAnchorPane.setPrefHeight(PANE_WIDTH / 2);

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

		diceBtn = new Button("", IconLoader.getLoader().getImageFromPath(ClassicType.Other.GENERALOTHERIMAGEMAP.getDiceImage()));
		AnchorPane.setRightAnchor(diceBtn, PANE_WIDTH * H_DISTANCE);
		AnchorPane.setLeftAnchor(diceBtn, PANE_WIDTH * H_DISTANCE);
		AnchorPane.setBottomAnchor(diceBtn, PANE_WIDTH * V_DISTANCE);
		AnchorPane.setTopAnchor(diceBtn, PANE_WIDTH * V_DISTANCE);
		topAnchorPane.getChildren().add(diceBtn);

		final Button setting = new Button("", IconLoader.getLoader().getImageFromPath(ClassicType.Other.GENERALOTHERIMAGEMAP.getGearImage()));
		setting.getStyleClass().add("settingButton");
		AnchorPane.setRightAnchor(setting, SETTINGS_DISTANCE * 1.5);
		AnchorPane.setTopAnchor(setting, SETTINGS_DISTANCE);
		topAnchorPane.getChildren().add(setting);

		this.getChildren().add(topAnchorPane);

		final Button tradeBtn = new Button("TRADE");
		tradeBtn.setPrefWidth(PANE_WIDTH);
		this.getChildren().add(tradeBtn);

		playerLabel = new Label();
		playerLabel.setWrapText(true);
		playerLabel.setTextAlignment(TextAlignment.CENTER);
		cashLabel = new Label();
		cashLabel.setWrapText(true);
		cashLabel.setTextAlignment(TextAlignment.CENTER);
		netWorthLabel = new Label();
		netWorthLabel.setWrapText(true);
		netWorthLabel.setTextAlignment(TextAlignment.CENTER);
		prisonLabel = new Label();
		prisonLabel.setWrapText(true);
		prisonLabel.setTextAlignment(TextAlignment.CENTER);
		
		this.getChildren().addAll(playerLabel, new Line(-100, 0, 100, 0), cashLabel, new Line(-100, 0, 100, 0), netWorthLabel, new Line(-100, 0, 100, 0), prisonLabel);

		endTurn = new Button("", new ImageView(END_TURN_PATH));
		endTurn.getStyleClass().add("roundButton");
		endTurn.setTooltip(new Tooltip("Next Player"));
		this.updateButton(false);

		AnchorPane.setBottomAnchor(endTurn, SETTINGS_DISTANCE);
		AnchorPane.setRightAnchor(endTurn, SETTINGS_DISTANCE * 2.5);
		final AnchorPane bottomAnchorPane = new AnchorPane(endTurn);
		bottomAnchorPane.setPrefHeight(PANE_HEIGHT / 3);
		bottomAnchorPane.setPrefWidth(PANE_WIDTH);

		this.getChildren().add(bottomAnchorPane);

		diceBtn.setOnAction(e -> {
			ControllerImpl.getController().diceClick();
		});

		endTurn.setOnAction(e -> {
			ControllerImpl.getController().endTurnClick();
		});

		tradeBtn.setOnAction(e -> {
			ControllerImpl.getController().startTrade();
		});
		
		setting.setOnAction(e -> {
			ControllerImpl.getController().settingsClick();
		});
		
		this.setId("RightInformationPane");
		this.getStylesheets().add(getClass().getResource("/style/style.css").toExternalForm());
		updateLabels();
	}

	/**
	 * Update the right and the left label that shown the results of dices rolls.
	 * 
	 * @param leftResult
	 *            the result of first dice
	 * @param rightResult
	 *            the result of second dice
	 */
	public void updateDiceLabel(int leftResult, int rightResult) {
		leftDiceResult.setText(String.valueOf(leftResult));
		rightDiceResult.setText(String.valueOf(rightResult));
	}

	/**
	 * update the labels with the information about the player.
	 * 
	 * @param player
	 *            current player in turn
	 */
	public void updateLabels() {
		final PlayerInfo player = ControllerImpl.getController().getCurrentPlayer();
		playerLabel.setText("Player:\n" + player.getName());
		cashLabel.setText("Cash:\n" + player.getMoney());
		netWorthLabel.setText("Net Worth:\n" + player.totalAssets());
		prisonLabel.setText(player.isInJail() ? "In jail" : "Free");
	}

	/**
	 * Set disable dice button if the player have done (rolled dices), and enable
	 * the endTurn button. If the player have done a pair throws he/she have to
	 * re-roll dices.
	 * 
	 * @param done
	 *            true if player has thrown dices this turn
	 */
	public void updateButton(boolean done) {
		diceBtn.setDisable(done);
		endTurn.setDisable(!done);
	}

	/**
	 * Set the right label if the player is in prison
	 * 
	 * @param isJail
	 */
	public void updateJailButton(boolean isJail) {
		endTurn.setGraphic(new ImageView(isJail ? EXIT_JAIL_PATH : END_TURN_PATH));
		endTurn.setTooltip(isJail ? new Tooltip("jail fee: 125 $") : new Tooltip("Next Player"));
		if (isJail) {
			endTurn.setDisable(!isJail);
		}
	}
}
