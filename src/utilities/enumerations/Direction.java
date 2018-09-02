package utilities.enumerations;

import javafx.animation.PathTransition;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
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
		public LineTo moveLocation(Scene scene, Node player, int position) {
				return new LineTo(player.getLayoutX() - (STEP_SIZE*position), player.getLayoutY() +20.0);			
//			
//			return path;
////			transition.setNode(player);
////			transition.setDuration(DURATION);
//			transition.setPath(path);
//			transition.play();
//            transition.playFromStart();	
		}
	},
	/**
     * North.
     */
	N(1) {
		@Override
		public LineTo moveLocation(Scene scene, Node player, int position) {
			return new LineTo(player.getLayoutX()+ 20.0, player.getLayoutY()  - (STEP_SIZE*position));			
//			
//			return path;
//			
////			transition.setNode(player);
////			transition.setDuration(DURATION);
//			transition.setPath(path);
//			
//			transition.setFromX(player.getTranslateX());
//            transition.setFromY(player.getTranslateY());
//            transition.setToX(player.getTranslateX());
//            transition.setToY(player.getTranslateY() - (STEP_SIZE*position));
//            transition.playFromStart();
		}
	},
    /**
     * East.
     */
	E(2) {
		@Override
		public LineTo moveLocation(Scene scene, Node player, int position) {
		return new LineTo(player.getLayoutX()+ (STEP_SIZE*position), player.getLayoutY());			
//			
//			return path;
//			
////			transition.setNode(player);
////			transition.setDelay(DURATION);
////			transition.setFromX(player.getTranslateX());
////            transition.setFromY(player.getTranslateY());
////            transition.setToX(player.getTranslateX() + (STEP_SIZE*position));
////            transition.setToY(player.getTranslateY());
//            transition.playFromStart();            
		}
	},
	 /**
     * South.
     */
	S(3) {
		@Override
		public LineTo moveLocation(Scene scene, Node player, int position) {
			return new LineTo(player.getLayoutX(), player.getLayoutY() + (STEP_SIZE*position));			
			
//			return path;
//			
////			transition.setNode(player);
////			transition.setDelay(DURATION);
////			transition.setFromX(player.getTranslateX());
////            transition.setFromY(player.getTranslateY());
////            
////            transition.setToX(player.getTranslateX());
////            transition.setToY(player.getTranslateY() + (STEP_SIZE*position));
//            transition.playFromStart();
		}
	};
	
	private static final double STEP_SIZE = ComponentFactory.LandSimpleWIDTH;
//private static final Duration DURATION = Duration.millis(1000);
//	
//	private static PathTransition  transition = new PathTransition();
    
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
	public abstract LineTo moveLocation(Scene scene, Node player, int position);
	
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
