package model.tiles;

import utilities.enumerations.TiteTypes;

public class Station extends NotBuildableImpl {

	private static final long serialVersionUID = 4279627963113787173L;

	public Station(final int positionTile, final int price, final int mortgage, final TiteTypes titeType, final String location) {
		super(positionTile, price, mortgage, titeType);
		
		this.setNameOf(this.getNameOf() + location);
	}
}
