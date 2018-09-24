package utilities.enumerations;

/**
 * Enum which indicates, based on the number of players, the amount of money and
 * the properties assigned to each one.
 * 
 * @author Rossolini Andrea
 *
 */
public enum InitialDistribution {
	TWO(2, 8750, 7), 
	
	THREE(3, 7500, 6), 
	
	FOUR(4, 6250, 5), 
	
	FIVE(5, 5000, 4), 
	
	SIX(6, 3750, 3);

	private int playersNumber;
	private int moneyAmount;
	private int contractNumber;

	private InitialDistribution(int playersNumber, int moneyAmount, int contractNumber) {
		this.playersNumber = playersNumber;
		this.moneyAmount = moneyAmount;
		this.contractNumber = contractNumber;
	}
	
	public int getPlayerNumber() {
		return this.playersNumber;
	}

	public int getMoneyAmount() {
		return this.moneyAmount;
	}

	public int getContractNumber() {
		return this.contractNumber;
	}
}
