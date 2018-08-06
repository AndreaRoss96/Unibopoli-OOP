package view.gameDialog;

import com.sun.xml.internal.ws.message.RootElementSniffer;

import controller.PlayersContractListView;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.stage.Stage;
import model.Model;

/**
 * This dialog allows the current player to mortgage it's properties to pay a
 * sum of money.
 * 
 * @author Rossolini Andrea
 *
 */
public class MortgageDialog extends Dialog {

	private static final MortgageDialog SINGLETON = new MortgageDialog();

	private static final double SPACING = 10;
	private static final Font INFORMATION_FONT = Font.font("Arial", FontPosture.ITALIC, 18);

	/**
	 * Instance of MortgageDialog.
	 * 
	 * @return the instance.
	 */
	public static MortgageDialog getMortgageDialog() {
		return SINGLETON;
	}

	/**
	 * Creation of the pane for the MortgageDialog
	 * 
	 * @param minimumExpense
	 *            the minimum amount of money that the player have to pay.
	 */
	public void createMortgageDialog(int minimumExpense) {
		final Stage stage = setStage("Choose the properties to be mortgaged");

		final BorderPane rootPane = new BorderPane();
		rootPane.setBackground(getBackground());
		final PlayersContractListView playerListView = new PlayersContractListView(Model.getCurrentPlayer());
		rootPane.setLeft(playerListView);

		final VBox vBox = new VBox();
		vBox.setPadding(getButtonInsets());
		vBox.setSpacing(SPACING);
		final Label title = new Label(Model.currentPlayer().getName());
		title.setFont(getPrincipalFont());
		final Label obtainedMoney = new Label("Accumulated money:\n0");
		vBox.getChildren().add(title);
		vBox.getChildren().add(new Label(Model.getCurrentPlayer().getMoney()));
		vBox.getChildren().add(new Label(Model.getCurrentPlayer.totalAssets()));
		vBox.getChildren().add(new Label("Minimum expense:\n" + minimumExpense + "$"));
		vBox.getChildren().add(obtainedMoney);
		rootPane.setCenter(vBox);

		final BorderPane bottomPane = addButtonBox(stage, "Red", ""); // aggiungi Path
		final Button mortgageButton = new Button("Mortgage");
		mortgageButton.setFont(getPrincipalFont());
		bottomPane.setLeft(mortgageButton);
		rootPane.setBottom(bottomPane);

		playerListView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			// Controller
		});

		mortgageButton.setOnAction(e -> {
			DialogController.getController().setMortgaged(currentPlayer, playerListView.getSelected());
		});

	}
}
