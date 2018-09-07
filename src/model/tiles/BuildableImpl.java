package model.tiles;

import utilities.enumerations.Color;
import utilities.enumerations.TileTypes;

/**
 * @author Matteo Alesiani 
 */

public class BuildableImpl extends ObtainableImpl implements AdapterBuildable{
	
	private static final long serialVersionUID = 7806152148586048326L;
	
	private Rents rents;
	
	public BuildableImpl(final int positionTile, final int price, final int mortgage, 
			final Rents rents, final Color colorTile, final TileTypes tileType) {
		super(positionTile, price, mortgage, tileType, colorTile);
		this.rents = rents;
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

	@Override
	public int getRent(int buildingsNr) {
		return this.rents.getRent(buildingsNr);
	}
	
	/**
	 * Returns the type of the Color enum, that specific the family of belonging inside the board. 
	 * 
	 * @return <tt>Color</tt> of the family Tile.
	 */
}
