package utilities.enumerations;

import com.google.common.base.Optional;

/**
 * Enumeration that contains the possible colors of the tiles.
 */
public enum Color {
    
	/**
     * Brown color.
     */
	BROWN(Optional.of("#800000"), "Brown", 2),
	
	/**
     * Light Blue color.
     */
	LIGHT_BLUE(Optional.of("#b0e0e6"), "Light Blue", 3),

	/**
     * Purple color.
     */
    PURPLE(Optional.of("#B01653"), "Purple", 3),

    /**
     * Orange color.
     */
    ORANGE(Optional.of("#F04100"), "Orange", 3),
    
    /**
     * Red color.
     */
    RED(Optional.of("#D90000"), "Red", 3),
    
	/**
     * Yellow color.
     */
    YELLOW(Optional.of("#FFF710"), "Yellow", 3),

    /**
     * Green color.
     */
    GREEN(Optional.of("#075E10"), "Green", 3),

    /**
     * Blue color.
     */
    BLUE(Optional.of("#00008A"), "Blue", 2),
	
    /**
     * 
     */
	STATION(Optional.absent(), "Station", 4),

	/**
	 * 
	 */
	SOCIETY(Optional.absent(), "Society", 2);
    
    private final Optional<String> value;
    private final String name;

    private final int numTileFamily;
    
    Color(final Optional<String> value, final String name, final int numTileFamily) {
        this.value = value;
        this.name = name;
        this.numTileFamily = numTileFamily;
    }

    /**
     * @return String value of a determined Color.
     */
    public Optional<String> getPaintValue() {
        return this.value;
    }

    /**
     * @return a string containing the color name.
     */
    public String getName() {
        return this.name;
    }
    
    /**
    * @return a integer represents the numbers of tiles in the family color.
    */
    public int getNumTiles() {
    	return this.numTileFamily;
    } 
}
