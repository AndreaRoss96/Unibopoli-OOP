package utilities.enumerations;

import javafx.scene.shape.LineTo;
import utilities.PaneDimensionSetting;
import view.Pawn;
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
		public LineTo moveLocation(Pawn player, int position) {
			return new LineTo(player.getCoordinates().getFirst() - (STEP_SIZE*position) , player.getCoordinates().getSecond());			
		}
	},
	
	/**
	 *	Corner West-North.
	 */
	WN(1){
		@Override
		public LineTo moveLocation(Pawn player, int position) {
			return new LineTo(STEP_SIZE*0.75, PaneDimensionSetting.getInstance().getGamePaneHeight()  - (STEP_SIZE*1.7));
		}
	},
	
	/**
	 * North.
	 */
	N(2) {
		@Override
		public LineTo moveLocation(Pawn player, int position) {
			return new LineTo(player.getCoordinates().getFirst(), player.getCoordinates().getSecond()  - (STEP_SIZE*position));			
		}
	},
	
	/**
	 *	Corner North-East.
	 */
	NE(3){
		@Override
		public LineTo moveLocation(Pawn player, int position) {
			return new LineTo(STEP_SIZE*1.7, STEP_SIZE*0.75);
		}
	},
	
    /**
     * East.
     */
	E(4) {
		@Override
		public LineTo moveLocation(Pawn player, int position) {
			return new LineTo(player.getCoordinates().getFirst()+ (STEP_SIZE*position), player.getCoordinates().getSecond());			      
		}
	},
	
	/**
	 *	Corner East-South.
	 */
	ES(5){

		@Override
		public LineTo moveLocation(Pawn player, int position) {
			return new LineTo(PaneDimensionSetting.getInstance().getGamePaneHeight() - (STEP_SIZE*0.75) , STEP_SIZE*1.7);
		
		}
	},
	
	 /**
     * South.
     */
	S(6) {
		@Override
		public LineTo moveLocation(Pawn player, int position) {
			return new LineTo(player.getCoordinates().getFirst(), player.getCoordinates().getSecond() + (STEP_SIZE*position));			
		}
	},
	
	/**
	 *	Corner South-West.
	 */
	SW(7){

		@Override
		public LineTo moveLocation(Pawn player, int position) {
			return new LineTo(PaneDimensionSetting.getInstance().getGamePaneHeight() - (STEP_SIZE*1.7), PaneDimensionSetting.getInstance().getGamePaneHeight()  - (STEP_SIZE*0.75));
		}		
	};
	
	private static final int STEP_SIZE = (int) ComponentFactory.LandSimpleWIDTH;
	
	/**internal index*/
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
	public abstract LineTo moveLocation(Pawn player, int position);
	
	/**
	 * Get the index of the direction
	 * @return index the index of the direction
	 */
	private int getIndex() {
		return this.index;
	}

	/**
	 * @return Direction
	 */
	public Direction rotation() {
		return Direction.values()[(this.getIndex() + 1) % Direction.values().length];
	}
}
