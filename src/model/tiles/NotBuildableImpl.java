package model.tiles;

import utilities.enumerations.Color;

/**
 * 
 * @author Matteo Alesiani 
 */

public class NotBuildableImpl extends ObtainableImpl implements NotBuildable{

	private static final int RENT = 50;
	
	public NotBuildableImpl(final int positionTile, final int price, 
			  final int mortgage, final Color colorTile) {
		super(positionTile, price, mortgage, colorTile);
		
	}
	
	@Override
	public int getRent() {
		return RENT;
	}
	
	@Override
	public Color getType() {
		return this.getColorOf();
	}
}
