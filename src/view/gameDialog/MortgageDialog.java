package view.gameDialog;

import controller.PlayersContractListView;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.stage.Stage;

/**
 * This dialog allows the current player to mortgage it's properties to pay a sum of money.
 * 
 * @author Rossolini Andrea
 *
 */
public class MortgageDialog extends Dialog {
	
	private static final MortgageDialog SINGLETON = new MortgageDialog();
	
	private static final double SPACING = 10;
	private static final Font INFORMATION_FONT = Font.font("Arial", FontPosture.ITALIC, 18);

	public static MortgageDialog getMortgageDialog() {
		return SINGLETON;
	}

	public void createMortgageDialog(Integer money) {
		final Stage stage = setStage("choose the properties to be mortgaged");
		
		final BorderPane rootPane = new BorderPane();
		rootPane.setBackground(getBackground());
		final PlayersContractListView playerListView = new PlayersContractListView(ModelImpl.getCurrentPlayer());
		rootPane.setLeft(playerListView);
		
		final VBox vBox = new VBox();
		vBox.setPadding(getButtonInsets());
		vBox.setSpacing(SPACING);
		final Label title = new Label (ModelImpl.currentPlayer().getName());
		title.setFont(getPrincipalFont());
		
		final BorderPane bottomPane = addButtonBox(stage, "Red", ""); //aggiungi Path
		final Button mortgageButton = new Button("Mortgage");
		mortgageButton.setFont(getPrincipalFont());
		bottomPane.setLeft(mortgageButton);
		rootPane.setBottom(bottomPane);
		
	}
}
