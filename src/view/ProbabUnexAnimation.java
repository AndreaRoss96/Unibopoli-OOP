package view;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class ProbabUnexAnimation extends AnchorPane {
	
	private static final ProbabUnexAnimation SINGLETON = new ProbabUnexAnimation();
	
	private static final double ANCHOR_VALUE = 10;
	private static final double DURATION_MS = 1000;	
	private static final double HEIGHT = 160;
	private static final double WIDTH = 280;
	
	private ProbabUnexAnimation() {
		
	}
	
	public static ProbabUnexAnimation getProbabilityDialog() {
		return SINGLETON;
	}
	
	public ProbabUnexAnimation createProbabilityDialog(final String message) {
		this.setMinSize(WIDTH, HEIGHT);
		this.setMaxSize(WIDTH, HEIGHT);
		
		final Label labelMessage = new Label(message);
		labelMessage.setWrapText(true);
		labelMessage.setPrefWidth(WIDTH/2);
		this.getChildren().add(labelMessage);
		AnchorPane.setRightAnchor(labelMessage, ANCHOR_VALUE);
		AnchorPane.setTopAnchor(labelMessage, ANCHOR_VALUE * 2);
		
		final Rectangle rect = new Rectangle(WIDTH, HEIGHT);
		this.getChildren().add(rect);
		
		final ParallelTransition parallelTransition = new ParallelTransition(
				createRotateTransition(this),
				createScaleTransition(this),
				createPathTransition(this),
				createFadeTransition(this, false),
				createFadeTransition(rect, true));
		parallelTransition.play();
		
		this.setOnMouseEntered(e -> createFadeTransition(this, false).playFrom(Duration.millis(DURATION_MS/2)));
		
		this.setId("ProbabUnexAnimation");
		this.getStylesheets().add(getClass().getResource("/style/style.css").toExternalForm());
		return this;
	}
	
	private Animation createFadeTransition(final Node card, final Boolean isShape) {
		final FadeTransition ft = new FadeTransition(Duration.millis(isShape ? DURATION_MS/2 : DURATION_MS*8), card);
		ft.setFromValue(1.0);
		if(isShape) {
			ft.setInterpolator(Interpolator.DISCRETE);
		}
		ft.setToValue(0.0);
		
		return ft;
	}

	private Animation createScaleTransition(final Node card) {
	    final ScaleTransition scaleTrans = new ScaleTransition(Duration.millis(DURATION_MS), card);
	    scaleTrans.setFromX(0.5);
	    scaleTrans.setFromY(0.5);
	    scaleTrans.setToX(2);
	    scaleTrans.setToY(2);
	    
		return scaleTrans;
	}

	private PathTransition createPathTransition(final Node card) {
		final Path path = new Path();
		path.getElements().add(new MoveTo(125, 50));
		path.getElements().add(new LineTo(125, 250));
		path.getElements().add(new LineTo(125, 50));
		
		return new PathTransition(Duration.millis(DURATION_MS), path, card);
	}

	private RotateTransition createRotateTransition(final Node card) {
		final RotateTransition rotator = new RotateTransition(Duration.millis(DURATION_MS), card);
		rotator.setAxis(Rotate.X_AXIS);
		rotator.setFromAngle(180);
		rotator.setToAngle(0);
		rotator.setInterpolator(Interpolator.LINEAR);

		return rotator;
	}
}
