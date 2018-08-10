package utilities.enumerations;

/**
 * 
 * @author Matteo Alesiani 
 */
public enum ClassicType {
	
    GeneralPurposeMap("res/mode/staticValuesTile/BuildableValues.txt", "res/mode/classic/ClassicMode.txt", 
    				  "res/mode/staticValuesTile/NotBuildableValues.txt", "res/images/Logo/Mr_Monopoly2.png", "images/Icons/Via.png", 
    				  "images/Icons/Prigione.png", "images/Icons/Poliziotto.png", "images/Icons/Treno.png");
 
    private final String staticBuildableValuesInitFile;
    private final String classicModeInitFile;
    private final String staticNotBuildableValuesInitFile;
    private final String goImage;
    private final String transitJail;
    private final String goJail;
    private final String transitTrain;
    private final String iconWindows;
    
    private ClassicType(final String staticBuildableValuesInitFile, final String classicModeInitFile, 
    					final String staticNotBuildableValuesInitFile, final String iconWindows, final String goImage,
    					final String transitJail, final String goJail, final String transitTrain) {
        this.staticBuildableValuesInitFile = staticBuildableValuesInitFile;
        this.classicModeInitFile = classicModeInitFile;
        this.staticNotBuildableValuesInitFile = staticNotBuildableValuesInitFile;
        this.iconWindows = iconWindows;
        this.goImage = goImage;
        this.transitJail = transitJail;
        this.goJail = goJail;
        this.transitTrain = transitTrain;
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
    	return this.transitTrain;
    }
}
