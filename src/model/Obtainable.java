package model;

/**
 * @author Matteo Alesiani 
 */

import java.util.Optional;

public abstract class Obtainable implements ObtainableInterface{
	
	private int positionTile;
	private String nameTile;
	private int mortgage;
	private Optional<String> owner;
	private int price;
	
	public Obtainable() {
	
	}
	
	@Override
	public int getPosition() {
		return this.positionTile;
	}
	
	@Override
	public String getNameOf() {
		return this.nameTile;
	}
	
	@Override
	public int getMortgage() {
		return this.mortgage;
	}
	
	@Override
	public Optional<String> getOwner() {
		return this.owner;
	}
	
	@Override
	public int getPrice() {
		return this.price;
	}
	
}
