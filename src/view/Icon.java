package view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.LineTo;
import model.Location;
import utilities.enumerations.Direction;

/**
 *  
 * @author Matteo Alesiani 
 */

public class Icon {
	
	private ImageView image;
	private Direction direction;
	private Location location;
	
	public Icon(final String path) {
		this(new Image(path));
	}

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
	
	public void rotate() {
		this.direction = this.direction.rotation();
	}
	
	public LineTo move(int position, int second) {
		return this.direction.moveLocation(this.image, position, second);
	}
	
	public Direction getDirection() {
		return this.direction;
	}
	
	public Location getLocation() {
		return this.location;
	}
}
