package controller;

import java.util.List;

import com.google.common.base.Optional;

import model.Model;
import view.View;

/**
 * This interface contains all method used by Dialog's classes.
 */
public interface DialogObserver {

	/**
	 * Execute the auction.
	 */
	void executeAuction();

	/**
	 * Increase the houses in the interested property (if are < 5).
	 */
	void incHouseClick();

	/**
	 * Decrement the number of houses in the selected property.
	 */
	void decHouseClick();

	/**
	 * Mortgage the selected property.
	 */
	void mortgageDialogClick();

	/**
	 * The current player buy the interested property.
	 */
	void buyPropertyClick();

	/**
	 * Computes the money obtained by the player that have to mortgage his/her
	 * properties.
	 */
	void accumulatedMoney();

	/**
	 * Set a list of properties to the mortgage status.
	 * 
	 * @param list
	 *            the list of the name of the properties selected (if there are).
	 */
	void setMortgage(List<Optional<String>> list);

	/**
	 * execute the trade.
	 */
	void dialogTradeClick();

	/**
	 * This method is called by the controller that initialize this Controller.
	 * 
	 * @param model
	 *            same model of the principal controller
	 * @param sound
	 *            same SoundController of the principal controller
	 * @param view
	 *            the same View of the principal controller
	 */
	void setDialogController(Model model, SoundController sound, View view);

}
