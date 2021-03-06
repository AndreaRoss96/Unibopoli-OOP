package model.tiles;

/**
 * This interface shape a sub-set of Tile, those allow the buil of house and 
 * hotel above the property.
 * 
 * @author Matteo Alesiani 
 */

public interface AdapterBuildable{
	
	/**
	 * Return the number of buildings build above the property.
	 * 
	 * @return <tt>int</tt> number of buildings.
	 */
	int getBuildingNumber();
	
	/**
	 * Return the price to pay for build a new building.
	 * 
	 * @return <tt>int</tt> mortgage of tile.
	 */
	int getPriceForBuilding();
	
	/**
	 * The method increment the number of buildings.
	 */
	void incBuildings();
	
	/**
	 * The method decrement the number of buildings.
	 */
	void decBuildings();
	
	/**
	 * The method return the rent infunction of buildingsNr.
	 */
	int getRent(final int buildingsNr);
}
