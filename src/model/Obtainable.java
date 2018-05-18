package model;

/**
 * @author Matteo Alesiani 
 */

import java.util.Optional;
import utilities.enumerations.Color;

public interface Obtainable extends Tile{
	
	int getRent();
	
	int getPrice();
	
	int getMortgage();
	
	Optional<String> getOwner();
	
	void setOwner(final Optional<String> owner);
	
	Color getColorOf();
	
}
