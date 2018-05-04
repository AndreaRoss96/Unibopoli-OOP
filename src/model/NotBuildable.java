package model;

/**
 * @author Matteo Alesiani 
 */

import utilities.enumerations.Status.NotBiuldableType;

public class NotBuildable extends Obtainable implements NotBuildableInterface{

	private NotBiuldableType typeOf;
	private int rent;
	
	public NotBuildable() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public int getRent() {
		return this.rent;
	}

	@Override
	public NotBiuldableType getType() {
		return this.typeOf;
	}
}
