package model.tiles;

import model.ConcrateConsequences;
import utilities.enumerations.TileTypes;

public class NotObtainableImpl implements NotObtainable, AdaprterPathImage{

	private static final long serialVersionUID = 6004220435515803475L;
	
	private Integer positionTile;
	private TileTypes tileType;
	private ConcrateConsequences consequences;
	
	public NotObtainableImpl(final int positionTile, final TileTypes titeType) {
		this.positionTile = positionTile;
		this.tileType = titeType;
	}
	
	@Override
	public int getPosition() {
		return this.positionTile;
	}
	
	@Override
	public String getNameOf() {
		return this.getTileType().getTypeName();
	}
	
	@Override
	public void setNameOf(String nameTile) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public TileTypes getTileType() {
		return this.tileType;
	}
	
	@Override
	public String getPathImage() {
		return this.getTileType().getPathImage().orElseThrow(() -> new IllegalArgumentException());
	}

	@Override
	public void setConsequence(ConcrateConsequences consequence) {
		this.consequences = consequence;
	}
	
	@Override
	public void doConsequence() {
		this.consequences.doConsequence();
	}
}
