package utilities.enumerations;

import java.util.Optional;
import javafx.scene.paint.Paint;

/**
 * Enumeration that contains the possible colors of the tiles.
 */
public enum Color {
    
	/**
     * Brown color.
     */
	BROWN(Optional.of(Paint.valueOf("#800000")), "Brown", 2),
	
	/**
     * Light Blue color.
     */
	LIGHT_BLUE(Optional.of(Paint.valueOf("#b0e0e6")), "Light Blue", 3),

	/**
     * Purple color.
     */
    PURPLE(Optional.of(Paint.valueOf("#B01653")), "Purple", 3),

    /**
     * Orange color.
     */
    ORANGE(Optional.of(Paint.valueOf("#F04100")), "Orange", 3),
    
    /**
     * Red color.
     */
    RED(Optional.of(Paint.valueOf("#D90000")), "Red", 3),
    
	/**
     * Yellow color.
     */
    YELLOW(Optional.of(Paint.valueOf("#FFF710")), "Yellow", 3),

    /**
     * Green color.
     */
    GREEN(Optional.of(Paint.valueOf("#075E10")), "Green", 3),

    /**
     * Blue color.
     */
    BLUE(Optional.of(Paint.valueOf("#00008A")), "Blue", 2),
    
    /**
     * Station color.
     */
    STATIONS(Optional.empty(), "Station", 4),
    
    /**
     * Society color.
     */
    SOCIETY(Optional.empty(), "Society", 2);
    
    private final Optional<Paint> value;
    private final String name;
    private final int numTileFamily;

    Color(final Optional<Paint> value, final String name, final int numTileFamily) {
        this.value = value;
        this.name = name;
        this.numTileFamily = numTileFamily;
    }

    /**
     * @return Paint value of a determined Color.
     */
    public Optional<Paint> getPaint() {
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
    
    @Override
    public String toString() {
        return this.name();
    }
}
