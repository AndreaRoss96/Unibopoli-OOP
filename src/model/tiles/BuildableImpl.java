package model.tiles;

import utilities.enumerations.Color;

/**
 * @author Matteo Alesiani 
 */

public class BuildableImpl extends ObtainableImpl implements Buildable{
	
	private Rents rents;
	private int buildingsNr;
	private int priceBuilding;
	
	public BuildableImpl(final int positionTile, final int price, final int mortgage, 
			final Rents rents, final Color colorTile, final int priceBuilding) {
		super(positionTile, price, mortgage, colorTile);
		this.rents = rents;
		this.buildingsNr = 0;
		this.priceBuilding = priceBuilding;
	}

	@Override
	public int getRent(int buildingsNr) {
		return this.rents.getRent(buildingsNr);
	}

	@Override
	public int getRent() {
		return this.rents.getRent(this.buildingsNr);
	}

	@Override
	public int getBuildingNumber() {
		return this.buildingsNr;
	}
	
	@Override
	public void incBuildings() {
		this.buildingsNr++;
	}
	
	@Override
	public int getPriceForBuilding() {
		return this.priceBuilding;
	}

	@Override
	public void decBuildings() {
		this.buildingsNr--;
		
	}
}
