package model;

/**
 * @author Matteo Alesiani 
 */

import utilities.enumerations.Status.NotBiuldableType;;

public interface NotBuildable extends Obtainable {
	
	NotBiuldableType getType(); 
}
