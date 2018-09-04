package model.tiles;

import utilities.IconLoader;
import utilities.enumerations.ClassicType;

public class Chance extends NotObtainableImpl{
	
	private static final long serialVersionUID = -8570331408186127119L;
	
	public static final String UNEXPECTED = ClassicType.Board.GeneralImagesMap.getUnexpectedImage();
	public static final String PROBABILITY = ClassicType.Board.GeneralImagesMap.getCofferImagePath();
	
	public Chance(final int positionTile, final boolean type) {
		super(positionTile, type ? "Unexpected" : "Probability");
		
		this.image = IconLoader.getLoader().getImageFromPath(type ? UNEXPECTED : PROBABILITY);
	}
}
