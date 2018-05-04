package utilities.enumerations;

import javafx.scene.paint.Paint;

/**
 * 
 * Enumeration that contains the possible colors of the players.
 *
 */
public enum Color {
    /**
     * Yellow color.
     */
    YELLOW(Paint.valueOf("#FDD835"), "Yellow"),

    /**
     * Red color.
     */
    RED(Paint.valueOf("#D32F2F"), "Red"),

    /**
     * Green color.
     */
    GREEN(Paint.valueOf("#388E3C"), "Green"),

    /**
     * Blue color.
     */
    BLUE(Paint.valueOf("#303F9F"), "Blue"),

    /**
     * Purple color.
     */
    PURPLE(Paint.valueOf("#C2185B"), "Purple"),

    /**
     * Black color.
     */
    BLACK(Paint.valueOf("#000000"), "Black");

    private final Paint value;
    private final String name;

    Color(final Paint value, final String name) {
        this.value = value;
        this.name = name;
    }

    /**
     * @return Paint value of a determined Color.
     */
    public Paint getPaint() {
        return this.value;
    }

    /**
     * @return a string containing the color name.
     */
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return this.name();

    }
}
