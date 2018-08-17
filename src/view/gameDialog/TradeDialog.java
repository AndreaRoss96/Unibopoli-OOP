package view.gameDialog;

import java.util.List;
import java.util.Optional;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.DialogController;
import model.PlayerListView;
import model.player.PlayerInfo;
import utilities.AlertFactory;

/**
 * This dialog allows the current player and the other players to make
 * commercial trade: they can exchange property and money
 * 
 * @author Rossolini Andrea
 *
 */
public class TradeDialog extends Dialog {

	private static final TradeDialog SINGLETON = new TradeDialog();

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
		final Stage stage = setStage("Choose what you want to exchange!");

		final BorderPane rootPane = new BorderPane();
		rootPane.setBackground(getBackground());

		final GridPane gridA = new GridPane();
		gridA.setPadding(new Insets(2, 5, 2, 5));

		Label currentPlayer = new Label("Curr Player");
		currentPlayer.setStyle("-fx-font-family: Kabel");
		TextField currMoneyToTrade = new TextField("0");
		currMoneyToTrade.setPrefWidth(currentPlayer.getWidth());
		gridA.add(new Label("Player: "), 0, 0);
		gridA.add(currentPlayer, 1, 0, 1, 1);
		gridA.add(new Label("Trade: "), 0, 1, 2, 1);
		gridA.add(currMoneyToTrade, 1, 1, 2, 1);
		gridA.add(new Label("$"), 2, 1);
		gridA.add(new PlayersContractListView(), 0, 2, 4, 1);
		rootPane.setLeft(gridA);

		ComboBox<String> playerBox = new ComboBox<>();
		playerBox.setStyle("-fx-font-family: Kabel");
		playerList.forEach(e -> playerBox.getItems().add(e.getName()));

		TextField moneyToTrade = new TextField("0");
		moneyToTrade.setPrefWidth(currentPlayer.getWidth());

		GridPane gridB = new GridPane();
		gridB.setGridLinesVisible(true);
		gridB.add(new Label("Player: "), 0, 0);
		gridB.add(playerBox, 1, 0, 1, 1);
		gridB.add(new Label("Trade: "), 0, 1, 2, 1);
		gridB.add(moneyToTrade, 1, 1, 2, 1);
		gridB.add(new Label("$"), 2, 1);
		gridB.add(new PlayersContractListView(), 0, 2, 4, 1);
		rootPane.setRight(gridB);

		final BorderPane bottomPane = addButtonBox(stage, "Green", ""); // aggiungi path
		final Button tradeButton = new Button("<==TRADE==>");
		tradeButton.setFont(getPrincipalFont());
		bottomPane.setLeft(tradeButton);
		rootPane.setBottom(bottomPane);

		playerBox.setOnAction(e -> {
			gridB.add(
					new PlayersContractListView(playerList.stream()
							.filter(player -> player.getName().equals(playerBox.getValue())).findFirst().get()),
					0, 2, 4, 1);
		});

		tradeButton.setOnAction(e -> {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Are you sure?");
			alert.setHeaderText(null);
			alert.setContentText("Do both players agree? ");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.CANCEL) {
				e.consume();
			} else {
				if (playerBox.getValue() != null) {
					// controller
				} else {
					AlertFactory.createErrorAlert("???", null, "Choose a player!");
					e.consume();
				}
			}
		});

		final Scene scene = new Scene(rootPane);
		stage.setScene(scene);
		stage.show();
	}

}
