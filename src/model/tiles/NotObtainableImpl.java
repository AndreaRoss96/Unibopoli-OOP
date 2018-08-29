package model.tiles;

import view.Icon;

public abstract class NotObtainableImpl implements NotObtainable{

	protected Icon image;
	private int positionTile;
	private String nameTile;
	
	public NotObtainableImpl(final int positionTile, final String nameTile) {
		this.positionTile = positionTile;
		this.nameTile = nameTile;
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
	public void setNameOf(String nameTile) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public Icon getImage() {
		return this.image;
	}
	
	/**
	 * Rivedere dato che c'è già getNameOf()
	 * 
	 * */
	public String getHeaderText() {
		return this.getNameOf().toUpperCase();
	}
}
