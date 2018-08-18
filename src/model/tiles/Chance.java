package model.tiles;

import utilities.IconLoader;
import utilities.enumerations.ClassicType;

public class Chance extends NotObtainableImpl{

	public static final String UNEXPECTED = ClassicType.GeneralPurposeMap.getUnexpectedImage();
	public static final String PROBABILITY = ClassicType.GeneralPurposeMap.getCofferImagePath();
	
	public Chance(final int positionTile, final boolean type) {
		super(positionTile, type ? "Unexpected" : "Probability");
		
		this.image = IconLoader.getLoader().getImageFromPath(type ? UNEXPECTED : PROBABILITY);
	}
}
