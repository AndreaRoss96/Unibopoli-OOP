/**
 * 
 */
package view.gameDialog;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import model.tiles.Buildable;
import model.tiles.Obtainable;
import view.Contract;

/**
 * This dialog allows the players to interact directly with the property of the
 * game. In this dialog the player can buy a mortgage, build and remove houses
 * and mortgage a determinated property.
 * 
 * @author Rossolini Andrea
 *
 */
public class CardDialog extends Dialog {
	
	private static final CardDialog SINGLETON = new CardDialog();
	
	private static final Font TITLE_FONT = Font.font("Kabel", FontWeight.BOLD, 22);
	private static final Font VALUE_FONT = Font.font("Arial", FontPosture.ITALIC, 18); //da modificare in setStyle(-fx-font-family: kabel)
	private static final double BOTTOM_MARGIN = Dialog.getScreenH() * 0.048;
	private static final double LEFT_MARGIN = Dialog.getScreenW() * 0.009;

	private Stage stage;

	/**
	 * Instance of CardDialog.
	 * 
	 * @return the instance
	 */
	public static CardDialog getCardDialog() {
		return SINGLETON;
	}

	/**
	 * Creation of the pane for the cardDialog.
	 * 
	 * @param property
	 *            the property that have to be shown.
	 * 
	 */
	public void createCardDialog(Obtainable property) {
		this.stage = setStage("Whats'up?");
		final BorderPane root = new BorderPane();
		root.setRight(addRightBox(property));
		// root.setLeft(DialogController.getController().getContract(property));
		root.setLeft(new Contract(property));
		root.setBottom(addBottom(property));
		final Scene scene = new Scene(root);
		BorderPane.setMargin(root.getBottom(), new Insets(0, 0, BOTTOM_MARGIN, LEFT_MARGIN));
		root.setBackground(getBackground());
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Pane with the informations of the interested card
	 * 
	 * @param property
	 * @return AnchorPane
	 */
	private AnchorPane addRightBox(Obtainable property) {
		AnchorPane anchor = new AnchorPane();
		GridPane grid = new GridPane();
		Integer column = 1;

		Label title = new Label("Contract Infos:\n\n");
		title.setFont(TITLE_FONT);
		title.setTextFill(property.getColorOf().getPaint().orElse(Color.BLACK));
		
		Label labelOwner = new Label("Owner: ");
		labelOwner.setFont(getPrincipalFont());
		Label effectiveOwner = new Label(property.getOwner().orElse(" - "));
		effectiveOwner.setFont(VALUE_FONT);
		
		Label propertyStatus = new Label("Status: ");
		propertyStatus.setFont(getPrincipalFont());
		Label effectivePropertyStatus = new Label(property.hasMortgage().toString());
		effectivePropertyStatus.setFont(VALUE_FONT);
		// insertion in the gridPAne
		grid.add(title, 0, 0, 2, 2);
		grid.add(labelOwner, 0, ++column);
		grid.add(effectiveOwner, 1, column);
		grid.add(propertyStatus, 0, ++column);
		grid.add(effectivePropertyStatus, 1, column);
		// if the property is buildable the dialog will show the number of houses/HOTEL
		if (property instanceof Buildable) {
			Label building = new Label((((Buildable) property).getBuildingNumber() == 5 ? "HOTEL"
					: "Building" + (((Buildable) property).getBuildingNumber() != 0 ? "s" : "")) + ": ");
			building.setFont(getPrincipalFont());
			Label buildingNumer = new Label(Integer.toString(((Buildable) property).getBuildingNumber()));
			buildingNumer.setFont(VALUE_FONT);
			grid.add(building, 0, ++column);
			grid.add(buildingNumer, 1, column);
		}

		anchor.getChildren().add(grid);
		anchor.setPrefSize(getPrefWSize() / 2, getPrefHSize() / 2);
		AnchorPane.setTopAnchor(grid, 5.0);
		AnchorPane.setLeftAnchor(grid, 5.0);
		return anchor;
	}

	private GridPane addBottom(Obtainable property) {
		GridPane grid = new GridPane();
		grid.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
		grid.setHgap(Dialog.getScreenH() * 0.1);
		if (!property.getOwner().isPresent()) {
			gridWithNoOwner(grid);
		} else {
			gridWithOwner(grid, property, !(property instanceof Buildable));
		}
		return grid;
	}

	/**
	 * Update the gridPane with the necessary button to interact with a contract
	 * without owner. In short words, allows you to purchase the property in case it
	 * was not already purchased by someone else.
	 * 
	 * @param grid
	 */
	private void gridWithNoOwner(GridPane grid) {
		ImageView cashImage = new ImageView(new Image("/images/dialogButton/cash-in-hand-50.png"));
		Button buyProperty = new Button("", cashImage);
		// buyPreporty.setEnable(Controller.GetController().currentCanBuy(property);
		Tooltip tooltip = new Tooltip(
				(buyProperty.isDisable()) ? "You don't have enought money to buy this!" : "Buy this property!");
		tooltip.setWrapText(true);
		buyProperty.setTooltip(tooltip);
		buyProperty.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
		buyProperty.setOnAction(e -> {
			// Controller.BuyProperty(property) da implementare
			//bisogna disabilitare il bottone se il giocatore non è nella stessa casella della carta
			this.stage.close();
		});
		grid.add(buyProperty, 0, 0);
	}

	/**
	 * update the gridPane with the necessary button to interact with a contract
	 * without owner. Allows the current player to add or remove houses from his
	 * property, or to mortgage a determinated property.
	 * 
	 * @param grid
	 * 
	 * @param property
	 *            the interested property
	 * @param disableButton
	 *            if the property is unbuildable is setted false
	 */
	private void gridWithOwner(GridPane grid, Obtainable property, Boolean disableButton) {
		Button addHouseButton = new Button("", new ImageView(new Image("/images/dialogButton/aggiungi_casa.png")));
		Button removeHouseButton = new Button("", new ImageView(new Image("/images/dialogButton/rimuovi_casa.png")));
		Button mortgageProperty = new Button("",
				new ImageView(new Image("/images/dialogButton/icons8-contract-50.png")));
		// metterei anche un piccolo tooltip
		// addHouseButton.setDisable(disableButton ? true : Controller.getCOntroller().getCurrentPlayer().canPay((buildable) property.getHousePrice()) vale
		// anche per rimuovi
		// ricordati di mettere un controllo sul currentPlayer, se non è lo stesso
		// proprietario della proprietà non deve poter costruire
		// property.getOwner().get() == 
		addHouseButton.setOnAction(e -> {
			//addHouseButton.seDisable(COntroller.buildhouse());
			//per comodità non disabilito, ma esco un dialog
		});
		removeHouseButton.setOnAction(e -> {
		});
		// mortgageProperty.setDisable(property.isMortgage());
		// bisognerebbe solamnente cambiare icona al pulsante quanfo una classe viene ipotecata, in questo modo è possibile "spotecare" la propietà
		grid.add(removeHouseButton, 0, 0);
		grid.add(addHouseButton, 1, 0);
		grid.add(mortgageProperty, 2, 0);
	}
}
