package view.gameDialog;

import java.util.Optional;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
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
	public void createTradeDialog() {
		final Stage stage = setStage("Choose what you want to exchange!");

		final BorderPane rootPane = new BorderPane();
		rootPane.setBackground(getBackground());

		final TradeBean leftTradeBean = new TradeBean(true);
		rootPane.setLeft(leftTradeBean);

		final TradeBean rightTradeBean = new TradeBean(false);
		rootPane.setRight(rightTradeBean);

		final BorderPane bottomPane = addButtonBox(stage, "Green", ""); //aggiungi path
		final Button tradeButton = new Button("<==TRADE==>");
		tradeButton.setFont(getPrincipalFont());
		bottomPane.setLeft(tradeButton);
		rootPane.setBottom(bottomPane);

		tradeButton.setOnAction(e -> {
			if (leftTradeBean.getPlayerSelected().isPresent() && leftTradeBean.getMoneyToTrade().isPresent()
					&& rightTradeBean.getPlayerSelected().isPresent() && rightTradeBean.getMoneyToTrade().isPresent()) {
				// operazioni nel controller
			} else {
				AlertFactory.createInformationAlert("Caution", 
						null,
						"Select two players and some contract to make a trade!");
			}
		});

		stage.setOnCloseRequest(e -> {
			Optional<ButtonType> result = AlertFactory.createConfirmationAlert("Are you sure?",
					"Are you really giving up this fantastic offer?",
					"(Good, temporize is the better strategy)")
					.showAndWait();
			if (result.get() == ButtonType.CANCEL) {
				e.consume();
			}
		});

		final Scene scene = new Scene(rootPane);
		stage.setScene(scene);
		stage.show();
	}
//riesci a mettere TradeBean qui dentro invece di creare due oggetti diversi? (tra l'altro ha un booleano nel codstruttore, che schifo)
}
