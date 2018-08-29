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
import controller.DialogController;
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
	
	private PlayersContractListView allPlayersListView;
	
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
	public void createTradeDialog(PlayerInfo currentPlayer, List<PlayerInfo> playerList) {
		final DialogController controller = DialogController.getDialogController();
		
		final Stage stage = setStage();

		final BorderPane rootPane = new BorderPane();
		rootPane.setBackground(getBackground());

		final GridPane gridA = new GridPane();
		gridA.setPadding(new Insets(2, 5, 2, 5));

		Label currPlayerLabel = new Label("Curr Player");
		currPlayerLabel.setStyle("-fx-font-family: Kabel");
		TextField currMoneyToTrade = new TextField("0");
		currMoneyToTrade.setPrefWidth(currPlayerLabel.getWidth());
		PlayersContractListView currListView = new PlayersContractListView(currentPlayer);
		gridA.add(new Label("Player: "), 0, 0);
		gridA.add(currPlayerLabel, 1, 0, 1, 1);
		gridA.add(new Label("Trade: "), 0, 1, 2, 1);
		gridA.add(currMoneyToTrade, 1, 1, 2, 1);
		gridA.add(new Label("$"), 2, 1);
		gridA.add(currListView, 0, 2, 4, 1);
		rootPane.setLeft(gridA);

		ComboBox<String> playerBox = new ComboBox<>();
		playerBox.setStyle("-fx-font-family: Kabel");
		playerList.forEach(e -> playerBox.getItems().add(e.getName()));

		TextField moneyToTrade = new TextField("0");
		moneyToTrade.setPrefWidth(currPlayerLabel.getWidth());
		this.allPlayersListView = new PlayersContractListView();
		
		GridPane gridB = new GridPane();
		gridB.setGridLinesVisible(true);
		gridB.add(new Label("Player: "), 0, 0);
		gridB.add(playerBox, 1, 0, 1, 1);
		gridB.add(new Label("Trade: "), 0, 1, 2, 1);
		gridB.add(moneyToTrade, 1, 1, 2, 1);
		gridB.add(new Label("$"), 2, 1);
		gridB.add(this.allPlayersListView, 0, 2, 4, 1);
		rootPane.setRight(gridB);

		final BorderPane bottomPane = addButtonBox(stage, "Green", "/images/Icons/dialog/shopping_cart.png");
		final Button tradeButton = new Button("<==TRADE==>");
		tradeButton.setFont(getPrincipalFont());
		bottomPane.setLeft(tradeButton);
		rootPane.setBottom(bottomPane);

		playerBox.setOnAction(e -> {
			if(playerBox.getValue() != null) {
				updateGrid(controller.getPlayerByName(playerBox.getValue()));
			}
			//è commentato perché adesso c'è il metodo updateGrid
//			gridB.add(
//					new PlayersContractListView(playerList.stream()
//							.filter(player -> player.getName().equals(playerBox.getValue())).findFirst().get()),
//					0, 2, 4, 1);
		});

		tradeButton.setOnAction(e -> {
			if(AlertFactory.createConfirmationAlert("Are you sure?", null, "Do both players agree?")) {
				if (playerBox.getValue() != null) {
				controller.executeTrade(playerBox.getValue(), currListView.getSelected(),
						allPlayersListView.getSelected(), currMoneyToTrade.getText(), moneyToTrade.getText());
				} else {
					AlertFactory.createErrorAlert("???", null, "Choose a player!");
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
	
	private void updateGrid(PlayerInfo player) {
		this.allPlayersListView = new PlayersContractListView(player);
	}
}
