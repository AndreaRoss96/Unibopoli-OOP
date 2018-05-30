package utilities.enumerations;

import java.util.function.Supplier;

import utilities.Vector;

public enum Direction {

	/**
	 * TODO: modificare l'ordine in base alla direzone che si vuole dare alla board! 
	 */
	
	UP(() -> new Vector(0, 1)), 
	
	DOWN(() -> new Vector(0, -1)), 
	
	LEFT(() -> new Vector(-1, 0)), 
	
	RIGHT(() -> new Vector(1, 0));
	
	private final Supplier<Vector> direction;
	
	private Direction(final Supplier<Vector> command) {
		this.direction = command;
	}
	
	public Vector exec(){
        return this.direction.get();
    }
	
	/**
	 * TODO: IMPROVE METHOD !! 
	 */
	public Direction change() {
		Direction ret; 
		
		if(this == UP) {
			ret = DOWN;
		}else if (this == DOWN) {
			ret = LEFT;
		}else if (this == LEFT) {
			ret = RIGHT;
		}else {
			ret = UP;
		} 
		
		 return ret;
	}
	
	/*
	public Direction next() {
		return this++;
	}*/
}
