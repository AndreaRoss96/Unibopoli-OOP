package model;

/**
 * @author Matteo Alesiani 
 */

public interface Buildable extends Obtainable{
	
	int getBuildingNumber();
	
	int getPriceForBuilding();
	
	void incBuildings();
}
