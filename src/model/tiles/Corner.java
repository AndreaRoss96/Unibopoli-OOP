package model.tiles;

import model.Icon;
import utilities.IconLoader;
import utilities.enumerations.ClassicType;

public class Corner implements NotObtainable {

	public enum CornerTile{
		GO(ClassicType.GeneralPurposeMap.getGoImagePath(),"COLLECT $200\nAS YOU PASS"),
		FREE_TRANSIT(ClassicType.GeneralPurposeMap.getTransitJailPath(),"IN\nJAIL"),
		FREE_PARKING(ClassicType.GeneralPurposeMap.getTransitTrainPath(),"FREE\nPARKING"),
		GO_JAIL(ClassicType.GeneralPurposeMap.getGoJailPath(),"GO TO\nJAIL");
		
		private String path;
		private String headerText;
		
		private CornerTile(final String path, final String headerText) {
			this.path = path;
			this.headerText = headerText;	
		}

		public String getPath() {
			return this.path;
		}

		public String getHeaderText() {
			return this.headerText;
		}
		
		public static CornerTile get(final int element) {
			switch (element) {
			case 3: return GO_JAIL; 
			case 2: return FREE_PARKING;
			case 1: return FREE_TRANSIT;
			case 0:
			default: return GO;
			}
		}
	}
	
	private int positionTile;
	private String nameTile;
	private Icon image;
	private CornerTile cornerTile;
	
	public Corner(final int positionTile, final String nameTile, final CornerTile cornerTile) {
		this.positionTile = positionTile;
		this.nameTile = nameTile;
		this.cornerTile = cornerTile;
		this.image = IconLoader.getLoader().getImageFromPath(this.cornerTile.getPath());
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
	
	public String getHeaderText() {
		return this.cornerTile.getHeaderText();
	}
}
