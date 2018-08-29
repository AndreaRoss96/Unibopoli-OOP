package model.tiles;

import utilities.IconLoader;
import utilities.enumerations.ClassicType;

public class Tax extends NotObtainableImpl{

	public Tax(final int positionTile) {
		super(positionTile, initName(positionTile));
		
		this.image = IconLoader.getLoader().getImageFromPath(ClassicType.Board.GeneralImagesMap.getRingImagePath());
	}
	
	private static String initName(final int positionTile) {
		return positionTile == 4 ? "Income Tax\nPAY $200" : "Luxury Tax\nPAY $100";
	}
}
