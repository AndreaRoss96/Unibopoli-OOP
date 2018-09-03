package utilities.enumerations;

import javafx.scene.Node;
import javafx.scene.shape.LineTo;
import view.tiles.ComponentFactory;

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
     * West.
     */
	W(0) {
		@Override
		public LineTo moveLocation(Node player, int position, int second) {
			return new LineTo(player.getTranslateX() - (STEP_SIZE*position) , player.getTranslateY());			
		}
	},
	WN(1){

		@Override
		public LineTo moveLocation(Node player, int position, int second) {
			return new LineTo(player.getTranslateX() - (STEP_SIZE*(second + 0.75)), player.getTranslateY()  - (STEP_SIZE*(position + 0.50)));
		}
		
	},
	/**
	 * North.
	 */
	N(2) {
		@Override
		public LineTo moveLocation(Node player, int position, int second) {
			return new LineTo(player.getTranslateX(), player.getTranslateY()  - (STEP_SIZE*position));			
		}
	},
	NE(3){

		@Override
		public LineTo moveLocation(Node player, int position, int second) {
			return new LineTo(player.getTranslateX() + (STEP_SIZE*(position +0.75)), player.getTranslateY()  - (STEP_SIZE*(second+0.75)));
		}
		
	},
	
    /**
     * East.
     */
	E(4) {
		@Override
		public LineTo moveLocation(Node player, int position, int second) {
			return new LineTo(player.getTranslateX()+ (STEP_SIZE*position), player.getTranslateY());			      
		}
	},
	ES(5){

		@Override
		public LineTo moveLocation(Node player, int position, int second) {
			return new LineTo(player.getTranslateX() + (STEP_SIZE*(second+0.75)), player.getTranslateY() + (STEP_SIZE*(position+0.75)));
		
		}
		
	},	
	 /**
     * South.
     */
	S(6) {
		@Override
		public LineTo moveLocation(Node player, int position, int second) {
			return new LineTo(player.getTranslateX(), player.getTranslateY() + (STEP_SIZE*position));			
		}
	},
	SW(7){

		@Override
		public LineTo moveLocation(Node player, int position, int second) {
			return new LineTo(player.getTranslateX() - (STEP_SIZE*(position+0.75)), player.getTranslateY()  + (STEP_SIZE*(second+0.75)));
		}
		
	};
	
	private static final double STEP_SIZE = ComponentFactory.LandSimpleWIDTH;

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
	public abstract LineTo moveLocation(Node player, int position, int second);
	
	/**
	 * Get the index of the direction
	 * @return index the index of the direction
	 */
	private int getIndex() {
		return this.index;
	}

	/**
	 * 
	 * @return Direction
	 */
	public Direction rotation() {
		return Direction.values()[(this.getIndex() + 1) % Direction.values().length];
	}
}
