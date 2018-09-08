package model.tiles;

import utilities.enumerations.TiteTypes;

public class NotObtainableImpl implements NotObtainable, AdaprterPathImage{

	private static final long serialVersionUID = 6004220435515803475L;
	
	private Integer positionTile;
	private TiteTypes titeType;
	
	public NotObtainableImpl(final int positionTile, final TiteTypes titeType) {
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
	public TiteTypes getTiteType() {
		return this.titeType;
	}
	
	@Override
	public String getPathImage() {
		return this.getTiteType().getPathImage().orElseThrow(() -> new IllegalArgumentException());
	}
}
