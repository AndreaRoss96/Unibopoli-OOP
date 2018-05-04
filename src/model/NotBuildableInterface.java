package model;

/**
 * @author Matteo Alesiani 
 */

import utilities.enumerations.Status.NotBiuldableType;;

public interface NotBuildableInterface extends ObtainableInterface {
	
	NotBiuldableType getType(); 
}
