package model.tiles;

import utilities.enumerations.Color;
import utilities.enumerations.TiteTypes;

/**
 * @author Matteo Alesiani 
 */

public class BuildableImpl extends ObtainableImpl implements AdapterBuildable{
	
	private static final long serialVersionUID = 7806152148586048326L;
	
	private Rents rents;
	private Color colorTile;
	
	public BuildableImpl(final int positionTile, final int price, final int mortgage, 
			final Rents rents, final Color colorTile, final TiteTypes titeType) {
		super(positionTile, price, mortgage, titeType);
		this.rents = rents;
		this.colorTile = colorTile;
	}

	@Override
	protected int rentValue() {
		return this.rents.getRent();
	}

	@Override
	public int getBuildingNumber() {
		return this.rents.getBuildingNumber();
	}
	
	@Override
	public void incBuildings() {
		this.rents.incBuildings();
	}
	
	@Override
	public int getPriceForBuilding() {
		return this.rents.getPriceForBuilding();
	}

	@Override
	public void decBuildings() {
		this.rents.decBuildings();
	}
	
	/**
	 * Returns the type of the Color enum, that specific the family of belonging inside the board. 
	 * 
	 * @return <tt>Color</tt> of the family Tile.
	 */
	public Color getColorOf() {
		return this.colorTile;
	}
}
