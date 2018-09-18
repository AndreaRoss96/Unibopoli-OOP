package controller;

import javafx.animation.PathTransition;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import view.Icon;

/**
 * @version 2.3
 * 
 * @author Matteo Alesiani
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
    }
}