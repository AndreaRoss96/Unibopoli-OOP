package model;

/**
 * @author Matteo Alesiani 
 */

public class Buildable extends Obtainable implements BuildableInterface{
	
	private Rents rents;
	private int buildingNum;
	private int priceBuilding;
	
	public Buildable() {
		super();
	}
	
	@Override
	public int getRent() {
		return this.rents.getRent(this.buildingNum);
	}

	@Override
	public int getBuildingNumber() {
		return this.buildingNum;
	}

	@Override
	public int getPriceForBuilding() {
		return this.priceBuilding;
	}
}
