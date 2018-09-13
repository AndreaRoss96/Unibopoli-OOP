package model.tiles;

import controller.ControllerImpl;
import model.ConsequencesImpl;
import utilities.enumerations.TileTypes;

public abstract class NotObtainableImpl implements NotObtainable, AdaprterPathImage{

	private static final long serialVersionUID = 6004220435515803475L;
	
	private Integer positionTile;
	private TileTypes tileType;
	protected ConsequencesImpl consequences;
	
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
	
	protected abstract void setConsequence();
	
	@Override
	public void setConsequence(ConsequencesImpl consequence) {
		this.consequences = consequence;
	}
	
	@Override
	public void doConsequence() {
		this.setConsequence();
		this.consequences.doConsequences();
	}
}
