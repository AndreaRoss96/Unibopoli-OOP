package controller;

import java.util.List;
import java.util.Optional;

import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import model.player.Player;
import model.tiles.Obtainable;

public class TradeBean extends BorderPane {

	private PlayersContractListView listView;
	private TextField moneyToTrade;
	private String playerSelected;

	public TradeBean(boolean currentPlayer) {
		listView = new PlayersContractListView();
		moneyToTrade = new TextField("0");
		this.setTop(addGridPane(Region.USE_COMPUTED_SIZE, currentPlayer));
		this.setCenter(vTree(Optional.empty()));
		this.setVisible(true);

		this.autosize();
	}

	/**
	 * This method create the top section of the final border pane, composed by a
	 * combobox to choose th players and a textfield where the players can trade a
	 * quantity of money.
	 * 
	 * @param width
	 * @param currentPlayer
	 * @return GridPane
	 */
	private GridPane addGridPane(double width, boolean currentPlayer) {
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(2, 5, 2, 5));
		ComboBox<String> playerBox = new ComboBox();
		// List<String> list = Model.getPlayerList()
		// list.stream().removeIf(currentPlayer ? s -> !s.equals(Model.getCurrentPlayer)
		// : s -> s.equals(Model.getCurrentPlayer));

		// playerBox.getItems().addAll(currentPlayer ? "a" : "b");
		// //Model.getCurrentPlayer()
		playerBox.setOnAction(e -> {
			this.setCenter(vTree(Optional.of(playerBox.getValue())));
			this.playerSelected = playerBox.getValue();
		});
		this.moneyToTrade = new TextField("0");
		this.moneyToTrade.setPrefWidth(playerBox.getWidth());
		this.moneyToTrade.setMaxHeight(this.getHeight() / 200);

		grid.add(new Label("Player: "), 0, 0);
		grid.add(playerBox, 1, 0, 1, 1);
		grid.add(new Label("Trade: "), 0, 1, 2, 1);
		grid.add(moneyToTrade, 1, 1, 2, 1);
		grid.add(new Label("$"), 3, 1);
		return grid;
	}

	private VBox vTree(Optional<String> giocatore) {
		VBox vBox = new VBox();
		// serve il metodo del controller che trova i giocatori dai nomi
		listView = giocatore.isPresent() ? new PlayersContractListView(giocatore.get()) : new PlayersContractListView();

		vBox.getChildren().add(listView);

		return vBox;
	}

	/**
	 * Get the panel
	 * 
	 * @return borderPane
	 */
	public BorderPane getDefaultPanel() {
		return this;
	}

	/**
	 * Get the selected player. it is optional because the combobox can be empty.
	 * 
	 * @return Player name if it has been selected.
	 */
	public Optional<String> getPlayerSelected() {
		return playerSelected.isEmpty() ? Optional.empty() : Optional.of(playerSelected);
	}

	public List<Obtainable> getSelected() {
		return this.listView.getSelected(); // da implementare nel playerContractListViewf
	}

	public Optional<Integer> getMoneyToTrade() {
		// if(Controller.checkMoney -- deve controllare se è un numero e se il giocatore
		// ha effettivamente quei soldi
		return Optional.of(Integer.parseInt(this.moneyToTrade.getText()));
		// else
		// return Optional.empty();
	}
}
