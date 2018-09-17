package model.exceptions;

import view.AlertFactory;

public class NotEnoughMoneyException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 168269057664513476L;
	
	public NotEnoughMoneyException (Integer moneyAmount) {
		super();
		AlertFactory.createErrorAlert("You tried it!", "You don't have enough money!\nYou have only " + moneyAmount);
	}
}
