package view.gameDialog;

import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import controller.ControllerImpl;
import controller.DialogController;
import model.player.PlayerInfo;
import utilities.AlertFactory;
import utilities.Pair;

/**
 * This dialog allows the current player and the other players to make
 * commercial trade: they can exchange property and money
 * 
 * @author Rossolini Andrea
 *
 */
public class TradeDialog extends Dialog {

	private static final TradeDialog SINGLETON = new TradeDialog();

	private static final Insets INSETS = new Insets(2, 5, 2, 5);

	private PlayersContractListView allPlayersListView;
	private PlayersContractListView currListView;
	private ComboBox<String> playerBox;
	private TextField currMoneyToTrade;
	private TextField secPlayersMoneyToTrade;

	/**
	 * Istance of TradeDialog.
	 * 
	 * @return the instance.
	 */
	public static TradeDialog getTradeDialog() {
		return SINGLETON;
	}

	/**
	 * Creation of the pane for the dialog.
	 */
	public void createTradeDialog(List<PlayerInfo> playerList) {
		final Stage stage = setStage();
		final DialogController controller = DialogController.getDialogController();

		final PlayerInfo currentPlayer = ControllerImpl.getController().getCurrentPlayer();

		final BorderPane rootPane = new BorderPane();
		rootPane.setBackground(getBackground());

		final GridPane gridA = new GridPane();
		gridA.setPadding(INSETS);

		final Label currPlayerLabel = new Label(currentPlayer.getName());
		currPlayerLabel.setFont(getPrincipalFont());

		this.currMoneyToTrade = new TextField("0");
		currMoneyToTrade.setPrefWidth(currPlayerLabel.getWidth());

		this.currListView = new PlayersContractListView(currentPlayer);
		gridA.add(new Label("Player: "), 0, 0);
		gridA.add(currPlayerLabel, 1, 0, 1, 1);
		gridA.add(new Label("Trade: "), 0, 1, 2, 1);
		gridA.add(this.currMoneyToTrade, 1, 1, 2, 1);
		gridA.add(new Label("$"), 2, 1);
		gridA.add(this.currListView, 0, 2, 4, 1);
		rootPane.setLeft(gridA);

		this.playerBox = new ComboBox<>();
		this.playerBox.setStyle("-fx-font-family: Kabel");
		playerList.forEach(e -> this.playerBox.getItems().add(e.getName()));

		this.secPlayersMoneyToTrade = new TextField("0");
		this.secPlayersMoneyToTrade.setPrefWidth(currPlayerLabel.getWidth());

		final GridPane gridB = new GridPane();
		gridB.add(new Label("Player: "), 0, 0);
		gridB.add(this.playerBox, 1, 0, 1, 1);
		gridB.add(new Label("Trade: "), 0, 1, 2, 1);
		gridB.add(this.secPlayersMoneyToTrade, 1, 1, 2, 1);
		gridB.add(new Label("$"), 2, 1);
		gridB.add(new PlayersContractListView(), 0, 2, 4, 1);
		rootPane.setRight(gridB);

		final BorderPane bottomPane = addButtonBox(stage, "Green", "/images/Icons/dialog/shopping_cart.png");
		final Button tradeButton = new Button("<==TRADE==>");
		tradeButton.setFont(getPrincipalFont());
		bottomPane.setLeft(tradeButton);
		rootPane.setBottom(bottomPane);

		this.playerBox.setOnAction(e -> {
			if (this.playerBox.getValue() != null) {
				this.allPlayersListView = new PlayersContractListView(controller.getPlayerByName(playerBox.getValue()));
				gridB.add(this.allPlayersListView, 0, 2, 4, 1);
				this.secPlayersMoneyToTrade.setText("0");
			}
		});

		tradeButton.setOnAction(e -> {
			if (AlertFactory.createConfirmationAlert("Are you sure?", "Do both players agree?")) {
				if (this.playerBox.getValue() != null) {
					controller.executeTrade();
				} else {
					AlertFactory.createErrorAlert("???", "Choose a player!");
					e.consume();
				}
			} else {
				e.consume();
			}
		});

		final Scene scene = new Scene(rootPane);
		stage.setScene(scene);
		stage.show();
	}

	public String getSecondPlayer() {
		return this.playerBox.getValue();
	}

	public Pair<String> getPlayersMoneyToTrade() {
		return new Pair<String>(this.currMoneyToTrade.getText(), this.secPlayersMoneyToTrade.getText());
	}

	public Pair<List<String>> getSelectedProperties() {
		return new Pair<List<String>>(this.currListView.getSelected(), this.allPlayersListView.getSelected());
	}
}
