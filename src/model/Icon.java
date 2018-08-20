package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *  
 * @author Matteo Alesiani 
 */

public class Icon {
	
	private ImageView image;
	
	public Icon(final String path) {
		this(new Image(path));
	}
	
	private Icon(final Image image) {
		this.image = new ImageView(image);
	}
	
	public ImageView get() {
		return this.image;
	}
	
	public Image getImage() {
		return this.get().getImage();
	}
}
