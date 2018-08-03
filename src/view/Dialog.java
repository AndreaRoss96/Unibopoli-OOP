/**
 * 
 */
package view;

import java.awt.Toolkit;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
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
    private static final double PREF_H_SIZE = SCREEN_H * 0.40;
    private static final double PREF_W_SIZE = SCREEN_W * 0.70;

    /**
     * Getter for SCREEN_H.
     * 
     * @return SCREEN_H.
     */
    protected double getScreenH() {
        return SCREEN_H;
    }

    /**
     * Getter for SCREEN_W.
     * 
     * @return SCREEN_W.
     */
    protected double getScreenW() {
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
     * Getter for preferred new Insets dimension
     * 
     * @return new Insets
     */
    protected Insets getInsets() {
    	return new Insets(V_PADDING, H_PADDING, V_PADDING, H_PADDING);
    }

    /**
     * Method to set the stage in the dialogs to remove redundant code.
     * 
     * @return the stage with settings.
     */
    protected Stage setStage() {
        final Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
		stage.setWidth(PREF_W_SIZE);
		stage.setHeight(PREF_H_SIZE);
        return stage;
    }
    
    protected Background getBackground() {//da modificare per rendere moddabile lo sfondo delle dialog
    	Image cardBoard = new Image("/images/backgrounds/monopoli_cfu.png");
		BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true);
		Background background = new Background(new BackgroundImage(cardBoard, BackgroundRepeat.ROUND,
				BackgroundRepeat.ROUND, BackgroundPosition.CENTER, bSize));
		return background;
    }
}
