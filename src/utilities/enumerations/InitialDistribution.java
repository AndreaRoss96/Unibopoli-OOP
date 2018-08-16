package utilities.enumerations;

import java.util.ArrayList;
import java.util.List;


public enum InitialDistribution {
	TWO(2, 8750, 7),
	THREE(3, 7500, 6),
	FOUR(4, 6250, 5),
	FIVE(5, 5000, 4),
	SIX(6, 3750, 3);
	
	private int playerNumber;
	private int moneyAmount;
	private int contractNumber;
	
	public int getPlayerNumber() {
		return playerNumber;
	}
	
	public int getMoneyAmount() {
		return moneyAmount;
	}
	
	public int getContractNumber() {
		return contractNumber;
	}
	
	public List<InitialDistribution> getAll(){
		List<InitialDistribution> list = new ArrayList<>();
		list.add(TWO);
		list.add(THREE);
		list.add(FOUR);
		list.add(FIVE);
		list.add(SIX);
		return list;
	}
	
	private InitialDistribution(int playerNumber, int moneyAmount, int contractNumber) {
		this.playerNumber = playerNumber;
		this.moneyAmount = moneyAmount;
		this.contractNumber = contractNumber;
	}
}
