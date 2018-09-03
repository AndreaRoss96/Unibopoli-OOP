package controller;

import javafx.animation.PathTransition;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import view.Icon;

/**
 * 
 * 
 */
public class MovementController extends Thread {

	private static final Duration DURATION = Duration.seconds(1);
	
	private PathTransition transition;
	private Icon icon;
	private Path path;
	
	public MovementController setIcon(Icon icon) {
		this.icon = icon;
		return this;
	}
	
	public MovementController setMovement(Path path) {
		this.path = path;
		return this;
	}
	
    /**
     *
     */
    @Override
    public void run() {
		transition = new PathTransition(DURATION, this.path, this.icon.get());
		transition.play();
		try {
			this.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
}