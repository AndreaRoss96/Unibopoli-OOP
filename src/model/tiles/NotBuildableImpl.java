package model.tiles;

import utilities.enumerations.Color;
import utilities.enumerations.TileTypes;

/**
 * 
 * @author Matteo Alesiani 
 */

public class NotBuildableImpl extends ObtainableImpl implements AdaprterPathImage{

	private static final long serialVersionUID = 4892673225735533821L;
	private static final Integer RENT = 50;
	
	public NotBuildableImpl(final int positionTile, final int price, 
			  final int mortgage, final TileTypes titeType, final Color color) {
		super(positionTile, price, mortgage, titeType, color);
		
		this.setNameOf(this.getTiteType().getTypeName());
	}
	
	@Override
	int rentValue() {
		return RENT;
	}
	
	@Override
	public String getPathImage() {
		return this.getTiteType().getPathImage().orElseThrow(() -> new IllegalArgumentException());
	}
}
