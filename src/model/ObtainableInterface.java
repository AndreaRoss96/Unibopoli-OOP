package model;

import java.util.Optional;

public interface ObtainableInterface extends TileInterface{
	
	int getRent();
	
	int getPrice();
	
	int getMortgage();
	
	Optional<String> getOwner();
	
}
