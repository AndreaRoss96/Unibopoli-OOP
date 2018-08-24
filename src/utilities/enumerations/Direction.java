package utilities.enumerations;

import model.Location;

/**
 * Enumeration for the 4 possible directions of the entities.<br>
 * List of Possible Direction:<br>
 * <b>North (N)</b><br>
 * <b>East (E)</b><br>
 * <b>South (S)</b><br>
 * <b>West (W)</b><br>
 */
public enum Direction {
   
    /**
     * North.
     */
	N(0) {
		@Override
		public void moveLocation(final Location loc, final double v) {
			loc.setY(loc.getY() - v);
		}
	},
    /**
     * East.
     */
	E(1) {
		@Override
		public void moveLocation(final Location loc, final double v) {
			loc.setX(loc.getX() + v);
		}
	},
	 /**
     * South.
     */
	S(2) {
		@Override
		public void moveLocation(final Location loc, final double v) {
			loc.setY(loc.getY() + v);
		}
	},
	 /**
     * West.
     */
	W(3) {
		@Override
		public void moveLocation(final Location loc, final double v) {
			loc.setX(loc.getX() - v);
		}
	};
    
    //internal index
    private int index; 
    

	/**
	 * Constructor
	 * @param initindex index of the Direction
	 */
	private Direction(final int initindex) {
		this.index = initindex;
	}

	/**
	 * Move the input location (loc) in the specified direction from which this method is called.<br>
	 * If the Direction is oblique, the movement velocity used is DIAGONALVELOCITY.
	 * @param loc Location to move
	 * @param d Velocity of the movement
	 */
	public abstract void moveLocation(final Location loc, final double d);

	/**
	 * Get the index of the direction
	 * @return index the index of the direction
	 */
	private int getIndex() {
		return this.index;
	}

	/**
	 * 
	 * @return 
	 */
	public Direction rotation() {
		return Direction.values()[(this.getIndex() + 1) % Direction.values().length];
	}
}

