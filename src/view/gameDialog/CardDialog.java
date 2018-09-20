package view.gameDialog;

import controller.DialogController;
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
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.tiles.AdapterBuildable;
import model.tiles.Obtainable;
import utilities.enumerations.ClassicType;
import utilities.enumerations.TileTypes;

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
	private static final Font VALUE_FONT = Font.font("Kabel", FontPosture.ITALIC, 18);
	private static final int NUM_BUILD_MAX = 5;	
	private static final String BLACK = "#000000";				

	private Stage stage;
	private Obtainable property;
	private Label buildingNumer;
	private Button addHouseButton;
	private Button removeHouseButton;

	private CardDialog() {

	}

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
	public void createCardDialog(final Obtainable property, final boolean canAct) {
		this.stage = setStage();
		this.stage.initModality(Modality.WINDOW_MODAL);
		this.property = property;
		this.buildingNumer = new Label();
		this.buildingNumer.setFont(VALUE_FONT);
		this.addHouseButton = new Button("", new ImageView(ClassicType.Dialog.GENERALDIALOGMAP.getAddHouseImage()));
		this.removeHouseButton = new Button("", new ImageView(ClassicType.Dialog.GENERALDIALOGMAP.getRemoveHouseImage()));

		final BorderPane root = new BorderPane();
		root.setRight(addRightBox());
		root.setLeft(new Contract(this.property));
		root.setBottom(addBottom(canAct));
		root.setBackground(getBackground());
		BorderPane.setMargin(root.getBottom(), new Insets(0, 0, getScreenH() * 0.048, getScreenW() * 0.009));

		final Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();

		stage.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
			if (!isNowFocused) {
				stage.close();
			}
		});
	}

	/**
	 * Pane with the informations of the interested card
	 * 
	 * @param property
	 * @return AnchorPane
	 */
	private AnchorPane addRightBox() {
		final AnchorPane anchor = new AnchorPane();
		final GridPane grid = new GridPane();
		Integer column = 1;

		final Label title = new Label("Contract Infos:\n\n");
		title.setFont(TITLE_FONT);
		title.setTextFill(Paint.valueOf(property.getColorOf().getPaintValue().or(BLACK)));

		final Label labelOwner = new Label("Owner: ");
		labelOwner.setFont(getPrincipalFont());
		final Label effectiveOwner = new Label(property.getOwner().or(" - "));
		effectiveOwner.setFont(VALUE_FONT);

		final Label propertyStatus = new Label("Status: ");
		propertyStatus.setFont(getPrincipalFont());
		final Label effectivePropertyStatus = new Label(
				property.hasMortgage() ? Obtainable.StatusTile.MORTGAGE.toString()
						: Obtainable.StatusTile.NOT_MORTGAGE.toString());
		effectivePropertyStatus.setFont(VALUE_FONT);

		/* insertion in the gridPAne */
		grid.add(title, 0, 0, 2, 2);
		grid.add(labelOwner, 0, ++column);
		grid.add(effectiveOwner, 1, column);
		grid.add(propertyStatus, 0, ++column);
		grid.add(effectivePropertyStatus, 1, column);

		/*
		 * if the property is buildable the dialog will show the number of houses/HOTEL
		 */
		if (property instanceof AdapterBuildable) {
			final Label building = new Label("Buildings: ");
			building.setFont(getPrincipalFont());
			updateCardDialog();
			grid.add(building, 0, ++column);
			grid.add(this.buildingNumer, 1, column);
		}

		anchor.getChildren().add(grid);
		anchor.setPrefSize(getPrefWSize() / 2, getPrefHSize() / 2);
		AnchorPane.setTopAnchor(grid, 5.0);
		AnchorPane.setLeftAnchor(grid, 5.0);
		return anchor;
	}

	private GridPane addBottom(final boolean canAct) {
		final GridPane grid = new GridPane();
		grid.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
		grid.setHgap(getScreenH() * 0.1);
		if (!this.property.getOwner().isPresent()) {
			gridWithNoOwner(grid, canAct);
		} else {
			gridWithOwner(grid, canAct);
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
	private void gridWithNoOwner(final GridPane grid, final boolean canBuy) {
		final ImageView cashImage = new ImageView(new Image(ClassicType.Dialog.GENERALDIALOGMAP.getCashImage()));
		final Button buyProperty = new Button("", cashImage);
		buyProperty.setDisable(!canBuy);
		Tooltip tooltip = new Tooltip(
				(buyProperty.isDisable()) ? "You can't buy this property!" : "Buy this property!");
		tooltip.setWrapText(true);
		buyProperty.setTooltip(tooltip);
		buyProperty.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
		buyProperty.setOnAction(e -> {
			DialogController.getDialogController().buyPropertyClick();
			this.stage.close();
		});
		grid.add(buyProperty, 0, 0);
	}

	/**
	 * Update the gridPane with the necessary button to interact with a contract
	 * without owner. Allows the current player to add or remove houses from his
	 * property, or to mortgage a determined property.
	 * 
	 * @param grid
	 * 
	 * @param property
	 *            the interested property
	 * @param disableButton
	 *            if the property is unBuildable is set false
	 */
	private void gridWithOwner(final GridPane grid, final boolean canBuild) {
		final Button mortgageProperty = new Button("", new ImageView(ClassicType.Dialog.GENERALDIALOGMAP.getContractImage()));

		if (this.property.getTileType() != TileTypes.BUILDABLE || !canBuild || this.property.hasMortgage()) {
			addHouseButton.setDisable(true);
			removeHouseButton.setDisable(true);
		} else {
			addHouseButton.setDisable((((AdapterBuildable) this.property).getBuildingNumber() >= NUM_BUILD_MAX));
			removeHouseButton.setDisable(!(((AdapterBuildable) this.property).getBuildingNumber() != 0));
		}

		addHouseButton.setOnAction(e -> {
			DialogController.getDialogController().incHouseClick();
			removeHouseButton.setDisable(!(((AdapterBuildable) this.property).getBuildingNumber() != 0));
		});

		removeHouseButton.setOnAction(e -> {
			DialogController.getDialogController().decHouseClick();
			addHouseButton.setDisable((((AdapterBuildable) this.property).getBuildingNumber() >= NUM_BUILD_MAX));
		});
		
		mortgageProperty.setOnAction(e -> {
			DialogController.getDialogController().mortgageDialogClick();
			this.stage.close();
		});

		grid.add(removeHouseButton, 0, 0);
		grid.add(addHouseButton, 1, 0);
		grid.add(mortgageProperty, 2, 0);
	}

	/**
	 * Getter for the interested property.
	 * 
	 * @return the interested property
	 */
	public Obtainable getProperty() {
		return this.property;
	}

	/**
	 * Update the state of the label of the buildings in this properly property.
	 */
	public void updateCardDialog() {
		this.buildingNumer.setText(((AdapterBuildable) this.property).getBuildingNumber() >= NUM_BUILD_MAX ? "HOTEL"
				: String.valueOf(((AdapterBuildable) this.property).getBuildingNumber()));
		if(this.property instanceof AdapterBuildable) {
			this.addHouseButton.setDisable(((AdapterBuildable) this.property).getBuildingNumber() >= NUM_BUILD_MAX);
			this.removeHouseButton.setDisable(((AdapterBuildable) this.property).getBuildingNumber() == 0);
		}
	}
}
