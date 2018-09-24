package model.tiles;

import utilities.enumerations.Color;
import utilities.enumerations.TileTypes;

public class Station extends NotBuildableImpl {

	private static final long serialVersionUID = 4279627963113787173L;

	public Station(final int positionTile, final int price, final int mortgage, final TileTypes titeType, final String location, final Color color) {
		super(positionTile, price, mortgage, titeType, color);
		
		this.setNameOf(this.getNameOf() + location);
	}
}
