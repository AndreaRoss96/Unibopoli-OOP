package utilities.enumerations;

/**
 * @author Matteo Alesiani
 * 
 */
public enum ClassicType {

    GeneralPurposeMap("/mode/staticValuesTile/BuildableValues.txt", "/mode/classic/ClassicMode.txt", "/mode/staticValuesTile/NotBuildableValues.txt");
 
    private final String staticBuildableValuesInitFile;
    private final String classicModeInitFile;
    private final String staticNotBuildableValuesInitFile;
    
    private ClassicType(final String staticBuildableValuesInitFile, final String classicModeInitFile, final String staticNotBuildableValuesInitFile) {
        this.staticBuildableValuesInitFile = staticBuildableValuesInitFile;
        this.classicModeInitFile = classicModeInitFile;
        this.staticNotBuildableValuesInitFile = staticBuildableValuesInitFile;
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
			case "CLASSIC":
			default: this.getClassicModeInitFile();
		}
    	
    	return null;
    }
    
    /**
     * @return the path for the initialization name of the tile in the board.
     */
    private String getClassicModeInitFile() {
        return this.classicModeInitFile;
    }
}
