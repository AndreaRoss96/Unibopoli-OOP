package model.exceptions;

public class NotEnoughMoneyException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 168269057664513476L;
	
	public NotEnoughMoneyException (Integer moneyAmount) {
		super();
	}
}
