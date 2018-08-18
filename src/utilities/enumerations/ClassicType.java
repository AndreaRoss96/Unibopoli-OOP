package utilities.enumerations;

/**
 * 
 * @author Matteo Alesiani 
 */
public enum ClassicType {
	
	
	/**
	 * Dividere in file ed immagini
	 * */
    GeneralPurposeMap("res/mode/staticValuesTile/BuildableValues.txt", "res/mode/classic/ClassicMode.txt", 
    				  "res/mode/staticValuesTile/NotBuildableValues.txt", "images/Logo/Mr_Monopoly.png", "images/Icons/Via.png", 
    				  "images/Icons/Prigione.png", "images/Icons/Poliziotto.png", "images/Icons/Treno.png", "images/Icons/Anello.png",
    				  "images/Icons/Train.png", "images/Icons/Lampadina.png", "images/Icons/Rubinetto.png", "images/Icons/Scrigno.png",
    				  "images/Icons/PuntoInterrogativo.png");
 
    private final String staticBuildableValuesInitFile;
    private final String classicModeInitFile;
    private final String staticNotBuildableValuesInitFile;
    private final String iconWindows;
    private final String goImage;
    private final String transitJail;
    private final String goJail;
    private final String transitCar;
    private final String ringImage;
    private final String trainImage;
    private final String bulbImage;
    private final String waterImage;
    private final String cofferImage;
    private final String unexpectedImage;
    
    private ClassicType(final String staticBuildableValuesInitFile, final String classicModeInitFile, 
    					final String staticNotBuildableValuesInitFile, final String iconWindows, final String goImage,
    					final String transitJail, final String goJail, final String transitCar, final String ringImage, 
    					final String trainImage, final String bulbImage, final String waterImage, final String cofferImage,
    					final String unexpectedImage) {
        this.staticBuildableValuesInitFile = staticBuildableValuesInitFile;
        this.classicModeInitFile = classicModeInitFile;
        this.staticNotBuildableValuesInitFile = staticNotBuildableValuesInitFile;
        this.iconWindows = iconWindows;
        this.goImage = goImage;
        this.transitJail = transitJail;
        this.goJail = goJail;
        this.transitCar = transitCar;
        this.ringImage = ringImage;
        this.trainImage = trainImage;
        this.bulbImage = bulbImage;
        this.waterImage = waterImage;
        this.cofferImage = cofferImage;
        this.unexpectedImage = unexpectedImage;
    }

    /**
     * @return the path for the initialization tile's values of the map. 
     */
    public String getStaticBuildableValuesInitFile() {
        return this.staticBuildableValuesInitFile;
    }

    /**
     * @return TODO: da fare.
     */
    public String getStaticNotBuildableValuesInitFile() {
    	return this.staticNotBuildableValuesInitFile;
    }
    
    /**
     * @return TODO: da fare.
     */
    public String getModeGame(String mode) {
    	//return "res/mode/" + mode + "/ClassicMode.txt";
    	
    	switch (mode) {    	
			case "Case": return "Ciao";
			case "CLASSIC": return this.getClassicModeInitFile();
			default: return this.getClassicModeInitFile();
		}
    }
    
    /**
     * @return the path for the initialization name of the tile in the board.
     */
    private String getClassicModeInitFile() {
        return this.classicModeInitFile;
    }
    
    /**
     * TODO: inserire java-doc
     * */
    public String getIconWindows() {
    	return this.iconWindows;
    }
    
    public String getGoImagePath() {
    	return this.goImage;
    }
    
    public String getTransitJailPath() {
    	return this.transitJail;
    }
    
    public String getGoJailPath() {
    	return this.goJail;
    }
    
    public String getTransitTrainPath() {
    	return this.transitCar;
    }
    
    public String getRingImagePath() {
    	return this.ringImage;
    }
    
    public String getTrainImagePath() {
    	return this.trainImage;
    }
    public String getBulbImagePath() {
    	return this.bulbImage;
    }
    
    public String getWaterImagePath() {
    	return this.waterImage;
    }
    
    public String getCofferImagePath() {
    	return this.cofferImage;
    }
    
    public String getUnexpectedImage() {
    	return this.unexpectedImage;
    }
}
