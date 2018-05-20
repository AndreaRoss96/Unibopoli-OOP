package utilities.enumerations;

/**
 * @author Matteo Alesiani
 * 
 */
public enum ClassicType {

    GeneralPurposeMap("/mode/staticValuesTile/ValuesTile.txt", "/mode/classic/Classic.txt");
 
    private final String staticValuesInitFile;
    private final String classicModeInitFile;

    private ClassicType(final String staticValuesInitFile, final String classicModeInitFile) {
        this.staticValuesInitFile = staticValuesInitFile;
        this.classicModeInitFile = classicModeInitFile;
    }

    /**
     * @return the path for the initialization tile's values of the map. 
     */
    public String getStaticValuesInitFile() {
        return this.staticValuesInitFile;
    }

    /**
     * @return the path for the initialization name of the tile in the board.
     */
    public String getClassicModeInitFile() {
        return this.classicModeInitFile;
    }
}
