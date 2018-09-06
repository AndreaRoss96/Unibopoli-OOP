package utilities.enumerations;

/**
 * Enumeration that contains the possible colors of the tiles.
 */
public enum Color {
    
	/**
     * Brown color.
     */
	BROWN("#800000", "Brown"),
	
	/**
     * Light Blue color.
     */
	LIGHT_BLUE("#b0e0e6", "Light Blue"),

	/**
     * Purple color.
     */
    PURPLE("#B01653", "Purple"),

    /**
     * Orange color.
     */
    ORANGE("#F04100", "Orange"),
    
    /**
     * Red color.
     */
    RED("#D90000", "Red"),
    
	/**
     * Yellow color.
     */
    YELLOW("#FFF710", "Yellow"),

    /**
     * Green color.
     */
    GREEN("#075E10", "Green"),

    /**
     * Blue color.
     */
    BLUE("#00008A", "Blue");
    
    private final String value;
    private final String name;
  
    Color(final String value, final String name) {
        this.value = value;
        this.name = name;
    }

    /**
     * @return String value of a determined Color.
     */
    public String getPaintValue() {
        return this.value;
    }

    /**
     * @return a string containing the color name.
     */
    public String getName() {
        return this.name;
    }
}
