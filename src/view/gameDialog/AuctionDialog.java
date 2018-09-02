/**
 * 
 */
package view.gameDialog;

import java.util.List;
import java.util.stream.Collectors;

import controller.ControllerImpl;
import controller.DialogController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.stage.Stage;
import model.player.PlayerInfo;
import model.tiles.Obtainable;
import utilities.AlertFactory;

/**
 * This dialog allows the not purchased contracts to be auctioned.
 * 
 * @author Rossolini Andrea
 *
 */
public class AuctionDialog extends Dialog {

	private static final AuctionDialog SINGLETON = new AuctionDialog();

	private static final Font COMMENT_FONT = Font.font("Arial", FontPosture.ITALIC, 11);
	private static final int PASSWORD_LIMIT = 15;

	private ObservableList<PasswordField> passwordList;
	private Obtainable auctionedProperty;

	/**
	 * Instance of AuctionDialog.
	 * 
	 * @return the instance.
	 */
	public static AuctionDialog getAuctionDialog() {
		return SINGLETON;
	}

	/**
	 * Creation of the pane for the AuctionDialog.
	 * 
	 * @param Property
	 *            the property to be auctioned.
	 */
	public void createAuctionDialog(Obtainable property) {
		final Stage stage = setStage();

		this.auctionedProperty = property;
		this.passwordList = FXCollections.observableArrayList();
		final List<PlayerInfo> playerList = ControllerImpl.getController().getPlayers();

		final BorderPane rootPane = new BorderPane();
		rootPane.setBackground(getBackground());
		rootPane.getStylesheets().add("style.css");

		final GridPane grid = new GridPane();
		grid.setPadding(getPrefInsets());

		int counter;
		for (counter = 0; counter < playerList.size(); counter++) {
			grid.add(new Label(playerList.get(counter).getName()), 0, counter);
			PasswordField pw = new PasswordField();
			pw.setOnAction(e -> {
				String number = pw.getText();
				for (int tmp = number.length(); tmp < PASSWORD_LIMIT; tmp++) {
					number = ("0" + number);
				}
				pw.setText(number);
			});
			passwordList.add(pw);
			grid.add(passwordList.get(counter), 1, counter);
		}

		final Label commentLabel = new Label("(after typed, press enter)");
		commentLabel.setFont(COMMENT_FONT);
		grid.add(commentLabel, 0, counter, 2, 1);
		rootPane.setRight(grid);

		final Button beatButton = new Button("Beat");
		beatButton.setFont(getPrincipalFont());
		beatButton.setPrefHeight(getButtonWidth());

		final BorderPane bottomPane = addButtonBox(stage, "Yellow", "/images/Icons/dialog/Auction.png");
		bottomPane.setLeft(beatButton);
		rootPane.setBottom(bottomPane);

		beatButton.setOnAction(e -> {
			// DialogController.getDialogController().executeAuction(playerList,
			// passwordList.stream().map(PasswordField::getText).collect(Collectors.toList()),
			// property);
			DialogController.getDialogController().executeAuction();
			this.passwordList.forEach(p -> {
				p.clear();
			});
		});

		stage.setOnCloseRequest(e -> {
			if (!AlertFactory.createConfirmationAlert("Are you sure?",
					"If you exit you will no longer be able to participate in this auction\nConfirm?")) {
				e.consume();
			}
		});

		final Scene scene = new Scene(rootPane);
		stage.setScene(scene);
		stage.showAndWait();
	}

	/**
	 * Getter for all bets for this auction.
	 * 
	 * @return List<String>, the list with all values (bets)
	 */
	public List<String> getBetsList() {
		return this.passwordList.stream().map(PasswordField::getText).collect(Collectors.toList());
	}

	/**
	 * Getter for the auctioned property.
	 * 
	 * @return Obtainable, the auctioned property
	 */
	public Obtainable getAuctionedProperty() {
		return this.auctionedProperty;
	}
}
