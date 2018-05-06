package model;

public class NotEnoughMoneyException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 168269057664513476L;
	
	private final Integer moneyAmount;
	
	public NotEnoughMoneyException (Integer moneyAmount) {
		super();
		this.moneyAmount = moneyAmount;
	}
	
	public String toString() {
		 return "Impossible to pay, you have less than " + this.moneyAmount;
	}
}
