package model;

import utilities.enumerations.Color;

/**
 * @author Matteo Alesiani 
 */

public class BuildableImpl extends ObtainableImpl implements Buildable{
	
	private Rents rents;
	private int buildingNum;
	private int priceBuilding;
	
	public BuildableImpl(final int positionTile, final int price, final int mortgage, 
			final Rents rents, final Color colorTile, final int priceBuilding) {
		super(positionTile, price, mortgage, colorTile);
		this.rents = rents;
		this.buildingNum = 0;
		this.priceBuilding = priceBuilding;
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
	public void incBuildings() {
		this.buildingNum++;
	}
	
	@Override
	public int getPriceForBuilding() {
		return this.priceBuilding;
	}
}
