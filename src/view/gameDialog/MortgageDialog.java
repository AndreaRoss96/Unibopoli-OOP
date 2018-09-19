package view.gameDialog;

import java.util.List;
import java.util.stream.Collectors;

import controller.DialogController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.player.PlayerInfo;
import model.tiles.Obtainable;
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

	private PlayerContractsListView playerListView;
	private Label obtainedMoney;
	
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

		final List<Obtainable> propertiesList = player.getProperties().stream().filter(p -> !p.hasMortgage()).collect(Collectors.toList());
		this.playerListView = new PlayerContractsListView(propertiesList);
		rootPane.setLeft(playerListView);

		final VBox vBox = new VBox();
		vBox.setPadding(getButtonInsets());
		vBox.setSpacing(SPACING);

		final Label title = new Label(player.getName());

		title.setFont(getPrincipalFont());
		this.obtainedMoney = new Label("0$");
		vBox.getChildren().add(title);
		vBox.getChildren().add(new Label("Player's money:\n" + String.valueOf(player.getMoney() + "$")));
		vBox.getChildren().add(new Label("Player's net worth:\n" + String.valueOf(player.totalAssets() + "$")));
		vBox.getChildren().add(new Label("Minimum expense:\n" + minimumExpense + "$"));
		vBox.getChildren().add(new Label("Accumulated money:"));
		vBox.getChildren().add(obtainedMoney);
		rootPane.setCenter(vBox);

		final BorderPane bottomPane = addButtonBox(stage, "Red",
				IconLoader.getLoader().getImageFromPath(ClassicType.Dialog.GENERALDIALOGMAP.getMortgageImage()));
		final Button mortgageButton = new Button("Mortgage");
		mortgageButton.setFont(getPrincipalFont());
		mortgageButton.setDisable(true);
		bottomPane.setLeft(mortgageButton);
		bottomPane.getRight().setDisable(true);
		rootPane.setBottom(bottomPane);

		playerListView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			DialogController.getDialogController().accumulatedMoney();
			mortgageButton.setDisable(minimumExpense > Integer.parseInt(obtainedMoney.getText().substring(0, obtainedMoney.getText().length()-1)));
		});

		mortgageButton.setOnAction(e -> {
			DialogController.getDialogController().setMortgage(playerListView.getSelected());
			stage.close();
		});
		
		final Scene scene = new Scene(rootPane);
		stage.setScene(scene);
		stage.showAndWait();
	}
	
	public List<String> getSelected(){
		return playerListView.getSelected()
		.stream().map(value -> value.get()).collect(Collectors.toList());
	}
	
	public void updateObtainedMoney(int obtainedMoney) {
		this.obtainedMoney.setText(String.valueOf(obtainedMoney) + "$");
	}
}
