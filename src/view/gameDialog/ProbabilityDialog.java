package view.gameDialog;

import javafx.animation.Animation;
import javafx.animation.FillTransition;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class ProbabilityDialog extends AnchorPane {

//	private static final ProbabilityDialog SINGLETON = new ProbabilityDialog(); 	
	private static final double ANCHOR_VALUE = 10;
	private static final double DURATION_MS = 1000;
	
	private static final double WIDTH = 300;
	private static final double HEIGHT = 400;
	
	
	//LA SOLUZIONE SAREBBE QUELLA DI METTERE QUESTA COSA NEL GAME PANE,
	//IN MODO DA AVERE UN EFFETTO DECENTE
//	private ProbabilityDialog() {
//		
//	}
	
//	public static ProbabilityDialog getProbabilityDialog() {
//		return SINGLETON;
//	}
	
	public ProbabilityDialog(final String message) {
//		final Stage stage = setStage();
//		this.setStage().initModality(Modality.WINDOW_MODAL);
		
//		final StackPane root = new StackPane();
//		root.setStyle("-fx-background-color: #CDE6D0;");
		
//		final AnchorPane cardPane = new AnchorPane();
//		this.setBackground(getBackground());
		final Image cardBoard = new Image("/images/backgrounds/probab.png");
		BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true);
		Background background = new Background(new BackgroundImage(cardBoard, BackgroundRepeat.ROUND,
				BackgroundRepeat.ROUND, BackgroundPosition.CENTER, bSize));
		this.setBackground(background);
		this.setMinSize(HEIGHT/2, WIDTH/2.5);
		this.setMaxSize(HEIGHT/2, WIDTH/2.5);
		
		final Label labelMessage = new Label(message);
		labelMessage.setWrapText(true);
		this.getChildren().add(labelMessage);
		AnchorPane.setRightAnchor(labelMessage, ANCHOR_VALUE);
		AnchorPane.setTopAnchor(labelMessage, ANCHOR_VALUE * 2);
		
		final Rectangle rect = new Rectangle(HEIGHT/2, WIDTH/2.5);
		this.getChildren().add(rect);
//		root.getChildren().addAll(cardPane, rect);
		
		final ParallelTransition parallelTransition = new ParallelTransition(
				createRotator(this),
				createScaleTransition(this),
				createPathTransition(this),
//				createRotator(rect),
//				createScaleTransition(rect),
//				createPathTransition(rect),
				createFadeTransition(rect));
		parallelTransition.play();
		
//		final Scene scene = new Scene(root, 400, 200);
//		scene.setFill(Color.TRANSPARENT);
//		stage.setScene(scene);
//		stage.sizeToScene();
//		stage.showAndWait();
	}
	
	private Animation createFadeTransition(final Shape rect) { 
		final FillTransition ft = new FillTransition();
		ft.setShape(rect);
		ft.setDuration(Duration.millis(DURATION_MS/2));
		ft.setInterpolator(Interpolator.DISCRETE);
		ft.setToValue(Color.TRANSPARENT);
		return ft;
	}

	private Animation createScaleTransition(final Node card) {
	    final ScaleTransition scaleTrans = new ScaleTransition(Duration.millis(DURATION_MS), card);
	    scaleTrans.setByX(1f);
	    scaleTrans.setByY(1f);
		return scaleTrans;
	}

	private PathTransition createPathTransition(final Node card) {
		final Path path = new Path();
		path.getElements().add(new MoveTo(125, 50));//WIDTH/3, HEIGHT/1.5));
		path.getElements().add(new LineTo(125, 250));//WIDTH/3, HEIGHT/3));
		path.getElements().add(new LineTo(125, 50));//WIDTH/3, HEIGHT/1.5));
		
		return new PathTransition(Duration.millis(DURATION_MS), path, card);
	}

	private RotateTransition createRotator(Node card) {
		final RotateTransition rotator = new RotateTransition(Duration.millis(DURATION_MS), card);
		rotator.setAxis(Rotate.X_AXIS);
		rotator.setFromAngle(180);
		rotator.setToAngle(0);
		rotator.setInterpolator(Interpolator.LINEAR);

		return rotator;
	}
	
//	private Background getBackground() {
//		final Image cardBoard = new Image("/images/backgrounds/probab.png");
//		BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true);
//		Background background = new Background(new BackgroundImage(cardBoard, BackgroundRepeat.ROUND,
//				BackgroundRepeat.ROUND, BackgroundPosition.CENTER, bSize));
//		return background;
//	}

}
