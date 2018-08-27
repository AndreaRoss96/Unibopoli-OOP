package model;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import utilities.enumerations.Direction;

/**
 *  
 * @author Matteo Alesiani 
 */

public class Icon {
	
	private ImageView image;
	private Direction direction;
	private Location location;
	private Scene scene;
	
	public Icon(final String path) {
		this(new Image(path));
	}
	
	/**
	 * TODO: Aggiungere ad IconLoader gli avatar
	 * 	 
	 * */
	public Icon(final Image image) {
		this.image = new ImageView(image);
		this.direction = Direction.W;
	}
	
	public ImageView get() {
		return this.image;
	}
	
	public Image getImage() {
		return this.get().getImage();
	}
	
	public void setScene(Scene scene) {
		this.scene = scene;
	}
	
	public void rotate() {
		this.direction = this.direction.rotation();
	}
	
	public void move(int position) {
		this.direction.moveLocation(this.scene, this.image, position);
	}
	
	public Direction getDirection() {
		return this.direction;
	}
	
	public Location getLocation() {
		return this.location;
	}
}
