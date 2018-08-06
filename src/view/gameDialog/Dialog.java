/**
 * 
 */
package view.gameDialog;

import java.awt.Toolkit;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Class for dialogs, other classes can extends this class to initiate the stage
 * 
 * @author Rossolini Andrea
 *
 */
public class Dialog {
	private static final int V_PADDING = 2;
    private static final int H_PADDING = 5;
    private static final double SCREEN_H = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
    private static final double SCREEN_W = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    private static final double PREF_H_SIZE = SCREEN_H * 0.70;
    private static final double PREF_W_SIZE = SCREEN_W * 0.40;
    private static final Font PRINCIPAL_FONT = Font.font("Arial", FontWeight.BOLD, 18);
    private static final Insets BUTTON_INSETS = new Insets(SCREEN_H * 0.02);
    private static final int BUTTON_WIDTH = 100;

    /**
     * Getter for SCREEN_H.
     * 
     * @return SCREEN_H.
     */
    protected static double getScreenH() {
        return SCREEN_H;
    }

    /**
     * Getter for SCREEN_W.
     * 
     * @return SCREEN_W.
     */
    protected static double getScreenW() {
        return SCREEN_W;
    }

    /**
     * Getter for HORIZONTAL PADDING.
     * 
     * @return H_PADDING.
     */
    protected int getHPadding() {
        return H_PADDING;
    }
    
    /**
     * Getter for VERTICAL PADDING.
     * 
     * @return V_PADDING.
     */
    protected int getVPadding() {
        return V_PADDING;
    }
    
    /**
     * Getter for Preferred height size
     * 
     * @return PREF_H_SIZE
     */
    protected double getPrefHSize() {
    	return PREF_H_SIZE;
    }
    
    /**
     * Getter for Preferred width size
     * 
     * @return PREF_W_SIZE
     */
    protected double getPrefWSize() {
    	return PREF_W_SIZE;
    }
    
    /**
     * Getter for preferred new Insets dimension.
     * 
     * @return new Insets
     */
    protected Insets getPrefInsets() {
    	return new Insets(V_PADDING, H_PADDING, V_PADDING, H_PADDING);
    }
    
    /**
     * Getter for the most common used font.
     * 
     * @return Font
     */
    protected Font getPrincipalFont() {
    	return PRINCIPAL_FONT;
    }
    
    /**
     * Getter for button width size.
     * 
     * @return double
     */
    protected double getButtonWidth() {
    	return BUTTON_WIDTH;
    }
    
    /**
     * Getter for button insets
     * 
     * @return Insets
     */
    protected Insets getButtonInsets() {
    	return BUTTON_INSETS;
    }

    /**
     * Method to set the stage in the dialogs to remove redundant code.
     * 
     * @return the stage with settings.
     */
    protected Stage setStage(String title) {
        final Stage stage = new Stage();
        stage.setTitle(title);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
		stage.setWidth(PREF_W_SIZE);
		stage.setHeight(PREF_H_SIZE);
        return stage;
    }
    
    /**
     * method to set an homogeneous background in all dialogs
     * 
     * @return Background
     */
    protected Background getBackground() {//da modificare per rendere moddabile lo sfondo delle dialog
    	Image cardBoard = new Image("/images/backgrounds/monopoli_cfu.png");
		BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true);
		Background background = new Background(new BackgroundImage(cardBoard, BackgroundRepeat.ROUND,
				BackgroundRepeat.ROUND, BackgroundPosition.CENTER, bSize));
		return background;
    }
    
    /**
     * 
     * 
     */
    protected BorderPane addButtonBox(Stage stage,String color, String ImagePath) {
    	final BorderPane buttonBox = new BorderPane();
    	buttonBox.setPadding(BUTTON_INSETS);
    	buttonBox.setStyle("-fx-background-color: " + color);
    	
    	final Button cancel = new Button ("Cancel");
    	cancel.setPrefWidth(BUTTON_WIDTH);
    	buttonBox.setRight(cancel);
    	
    	final ImageView centerView = new ImageView(new Image(ImagePath));
    	buttonBox.setCenter(centerView);
    	
    	cancel.setOnAction(e -> {
    		stage.close();
    	});
    	
    	return buttonBox;
    }
}
