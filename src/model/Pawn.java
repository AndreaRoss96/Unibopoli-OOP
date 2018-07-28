package model;

import utilities.Point;
import utilities.Vector;
import utilities.enumerations.Direction;

public class Pawn  {

	
	/**
	 * TODO: Potrebbe essere interessante inserire la classe Icon qua.
	 * 
	 * */
	private Point position;
	private Vector velocity;
	//private BoundingBox bbox;
	private Icon avatar;
	private Direction direction;
	
	public Pawn(Point position) {
		this.position = position;
		this.refresh(Direction.LEFT);
		//this.avatar = null;
	}
	
	protected Pawn(Point position, Vector velocity, Icon avatar, Direction direction){
		this.position = position;
		this.velocity = velocity;
		this.avatar = avatar;
		this.direction = direction;
	}
	
	public void setPos(Point pos){
		this.position = pos;
	}

	public void refresh(Direction direction) {
		this.direction = direction;
		this.velocity = this.direction.exec();
	}
	
	public Direction getDirection() {
		return this.direction;
	}
	
	public void setVel(Vector vel){
		this.velocity = vel;
	}

	public void changeDirection() {
		this.direction = Direction.DOWN;
	}
	
	public void stop() {
		this.velocity = new Vector(0, 0);
	}
	
	public void flipVelOnY(){
		this.velocity = new Vector(this.velocity.getX(), -this.velocity.getY());
	}

	public void flipVelOnX(){
		this.velocity = new Vector(-this.velocity.getX(), this.velocity.getY());
	}
	
	public void updateState(int dt){
		this.position = this.position.sum(this.velocity.mul(0.001*dt));
	}
	
	
	/*public BoundingBox getBBox(){
		return bbox;
	}*/
	
	public Point getCurrentPos(){
		return this.position;
	}
	
	public Vector getCurrentVel(){
		return this.velocity;
	}
	
	public Icon getAvatar() {
		return this.avatar;
	}
}
