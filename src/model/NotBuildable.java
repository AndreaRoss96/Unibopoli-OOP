package model;

import utilities.enumerations.Color;

/**
 * @author Matteo Alesiani 
 */

import utilities.enumerations.Status.NotBiuldableType;

public class NotBuildable extends Obtainable implements NotBuildableInterface{

	private NotBiuldableType typeOf;
	private int rent;
	
	public NotBuildable(final int positionTile, final int price, 
			  final int mortgage, final Color colorTile) {
		super(positionTile, price, mortgage, colorTile);
		
	}
	
	@Override
	public int getRent() {
		return this.rent;
	}

	@Override
	public NotBiuldableType getType() {
		return this.typeOf;
	}
}
