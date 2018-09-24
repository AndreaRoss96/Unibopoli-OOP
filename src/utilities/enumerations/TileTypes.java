package utilities.enumerations;

import java.util.Optional;

public enum TileTypes {
	
	BUILDABLE(Optional.empty(), "Buildable"), 
	
	STATION(Optional.ofNullable(ClassicType.Board.GENERALIMAGEMAP.getTrainImagePath()), "STAZIONE "),
	
	WATER_AGENCY(Optional.ofNullable(ClassicType.Board.GENERALIMAGEMAP.getWaterImagePath()), "SOCIETA' ACQUA POTABILE"),
	
	LIGHT_AGENCY(Optional.ofNullable(ClassicType.Board.GENERALIMAGEMAP.getBulbImagePath()), "SOCIETA' ELETTRICA"),
	
	INCOME_TAX(Optional.ofNullable(ClassicType.Board.GENERALIMAGEMAP.getRingImagePath()), "Income Tax\nPAY $200"),
	
	LUXURY_TAX(Optional.ofNullable(ClassicType.Board.GENERALIMAGEMAP.getRingImagePath()), "Luxury Tax\nPAY $100"),
	
	GO(Optional.ofNullable(ClassicType.Board.GENERALIMAGEMAP.getGoImagePath()), "COLLECT $200\nAS YOU PASS"),
	
	FREE_TRANSIT(Optional.ofNullable(ClassicType.Board.GENERALIMAGEMAP.getFreeTransitPath()), "IN\nJAIL"),
	
	FREE_PARKING(Optional.ofNullable(ClassicType.Board.GENERALIMAGEMAP.getFreeParkingPath()), "FREE\nPARKING"),
	
	GO_JAIL(Optional.ofNullable(ClassicType.Board.GENERALIMAGEMAP.getGoJailPath()), "GO TO\nJAIL"),
	
	UNEXPECTED(Optional.ofNullable(ClassicType.Board.GENERALIMAGEMAP.getUnexpectedImage()), "UNEXPECTED"),
	
	PROBABILITY(Optional.ofNullable(ClassicType.Board.GENERALIMAGEMAP.getProbabilityImagePath()), "PROBABILITY");
	
	private final Optional<String> pathImage;
	private final String typeName;
	
	private TileTypes(final Optional<String> pathImage, final String typeName) {
		this.pathImage = pathImage;
		this.typeName = typeName;
	}
	
	public Optional<String> getPathImage(){
		return this.pathImage;
	}
	
	public String getTypeName() {
		return this.typeName;
	}
}
