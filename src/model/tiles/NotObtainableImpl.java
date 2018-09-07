package model.tiles;

import utilities.enumerations.TileTypes;

public abstract class NotObtainableImpl implements NotObtainable, AdaprterPathImage{

	private static final long serialVersionUID = 6004220435515803475L;
	
	private Integer positionTile;
	private TileTypes titeType;
	
	public NotObtainableImpl(final int positionTile, final TileTypes titeType) {
		this.positionTile = positionTile;
		this.titeType = titeType;
	}
	
	@Override
	public int getPosition() {
		return this.positionTile;
	}
	
	@Override
	public String getNameOf() {
		return this.getTiteType().getTypeName();
	}
	
	@Override
	public void setNameOf(String nameTile) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public TileTypes getTiteType() {
		return this.titeType;
	}
	
	@Override
	public String getPathImage() {
		return this.getTiteType().getPathImage().orElseThrow(() -> new IllegalArgumentException());
	}
}
