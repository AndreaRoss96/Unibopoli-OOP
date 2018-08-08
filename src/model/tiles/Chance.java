package model.tiles;

import model.Icon;
import utilities.IconLoader;
import utilities.Pair;

public class Chance implements NotObtainable {

	/**
	 * Da rivedere ...
	 * */
	public static final String UNEXPECTED = "";
	public static final String PROBABILITY = "";
	private Pair<Icon, Icon> images;
	private int positionTile;
	private String nameTile;
	
	public Chance(final int positionTile) {
		this.positionTile = positionTile;
		this.images = new Pair<Icon, Icon>(IconLoader.getLoader().getImageFromPath(UNEXPECTED), 
										   IconLoader.getLoader().getImageFromPath(PROBABILITY));
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
		this.nameTile = nameTile;
	}

	public Icon getUnexpectedIcon() {
		return this.images.getX();
	}
	
	public Icon getProbabilityIcon() {
		return this.images.getY();
	}

	@Override
	public Icon getImage() {
		// TODO Auto-generated method stub
		return null;
	}
}
