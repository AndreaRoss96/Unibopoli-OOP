package view.gameDialog;

import java.util.stream.Collectors;

import controller.DialogController;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.player.PlayerInfo;
import utilities.IconLoader;
import utilities.enumerations.ClassicType;

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

	private MortgageDialog() {

	}

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
	public void createMortgageDialog(final int minimumExpense, final PlayerInfo player) {
		final Stage stage = setStage();

		final BorderPane rootPane = new BorderPane();
		rootPane.setBackground(getBackground());

		final PlayersContractListView playerListView = new PlayersContractListView(player);
		rootPane.setLeft(playerListView);

		final VBox vBox = new VBox();
		vBox.setPadding(getButtonInsets());
		vBox.setSpacing(SPACING);

		final Label title = new Label(player.getName());

		title.setFont(getPrincipalFont());
		final Label obtainedMoney = new Label("Accumulated money:\n0");
		vBox.getChildren().add(title);
		vBox.getChildren().add(new Label(String.valueOf(player.getMoney())));
		vBox.getChildren().add(new Label(String.valueOf(player.totalAssets())));
		vBox.getChildren().add(new Label("Minimum expense:\n" + minimumExpense + "$"));
		vBox.getChildren().add(obtainedMoney);
		rootPane.setCenter(vBox);

		final BorderPane bottomPane = addButtonBox(stage, "Red",
				IconLoader.getLoader().getImageFromPath(ClassicType.Dialog.GeneralDialogMap.getMortgageImage()).get());
		final Button mortgageButton = new Button("Mortgage");
		mortgageButton.setFont(getPrincipalFont());
		mortgageButton.setDisable(true);
		bottomPane.setLeft(mortgageButton);
		rootPane.setBottom(bottomPane);

		playerListView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			obtainedMoney.setText(obtainedMoney.getText().replaceAll("0",
					String.valueOf(DialogController.getDialogController().accumulatedMoney(playerListView.getSelected()
							.stream().map(value -> value.get()).collect(Collectors.toList())))));
			mortgageButton.setDisable(minimumExpense > Integer.parseInt(obtainedMoney.getText()));
		});

		mortgageButton.setOnAction(e -> {
			DialogController.getDialogController().setMortgage(playerListView.getSelected());
			stage.close();
		});
	}
}
