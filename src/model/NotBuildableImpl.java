package model;

import utilities.enumerations.Color;

/**
 * @author Matteo Alesiani 
 */

import utilities.enumerations.Status.NotBiuldableType;

public class NotBuildableImpl extends ObtainableImpl implements NotBuildable{

	private NotBiuldableType typeOf;
	private int rent;
	
	public NotBuildableImpl(final int positionTile, final int price, 
			  final int mortgage, final Color colorTile, final NotBiuldableType typeOf) {
		super(positionTile, price, mortgage, colorTile);
		
		this.typeOf = typeOf;
	}
	
	//TODO: da realizzare in base all'enum e non in base all'attributo.
	@Override
	public int getRent() {
		return this.rent;
	}

	@Override
	public NotBiuldableType getType() {
		return this.typeOf;
	}
}
