package utilities.enumerations;

import com.google.common.base.Optional;

/**
 * Enumeration that contains the possible colors of the tiles.
 */
public enum Color {
    
	/**
     * Brown color.
     */
	BROWN(Optional.of("#800000"), "Brown"),
	
	/**
     * Light Blue color.
     */
	LIGHT_BLUE(Optional.of("#b0e0e6"), "Light Blue"),

	/**
     * Purple color.
     */
    PURPLE(Optional.of("#B01653"), "Purple"),

    /**
     * Orange color.
     */
    ORANGE(Optional.of("#F04100"), "Orange"),
    
    /**
     * Red color.
     */
    RED(Optional.of("#D90000"), "Red"),
    
	/**
     * Yellow color.
     */
    YELLOW(Optional.of("#FFF710"), "Yellow"),

    /**
     * Green color.
     */
    GREEN(Optional.of("#075E10"), "Green"),

    /**
     * Blue color.
     */
    BLUE(Optional.of("#00008A"), "Blue"),
	
	STATION(Optional.absent(), "Station"),

	SOCIETY(Optional.absent(), "Society");
    
    private final Optional<String> value;
    private final String name;
  
    Color(final Optional<String> value, final String name) {
        this.value = value;
        this.name = name;
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
}
