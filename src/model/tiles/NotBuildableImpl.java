package model.tiles;

import javafx.scene.image.ImageView;
import model.Icon;
import utilities.enumerations.Color;

/**
 * 
 * @author Matteo Alesiani 
 */

public class NotBuildableImpl extends ObtainableImpl implements NotBuildable{

	private static final String TRAIN = "";
	private static final String AZIENDA_ELETTRICA = "";
	private static final String AZIENDA_ACQUA = "";
	private static final int RENT = 25;
	
	private Icon image;
	
	public NotBuildableImpl(final int positionTile, final int price, 
			  final int mortgage, final Color colorTile) {
		super(positionTile, price, mortgage, colorTile);
		
		this.initImage();
	}
	
	@Override
	public int getRent() {
		return RENT;
	}

	@Override
	public String getType() {
		return this.getColorOf().getName();
	}

	@Override
	public ImageView getImage() {
		return this.image.get();
	}

	private void setImage(final Icon image) {
		this.image = image;
	}
	
	/**
	 * Inserire Path.
	 * */
	private void initImage() {
		if(this.getType().equals("Station")) {
			this.setImage(new Icon(TRAIN));
		}else if(this.getPosition() == 12) {
			this.setImage(new Icon(AZIENDA_ELETTRICA));
		}else {
			this.setImage(new Icon(AZIENDA_ACQUA));
		}
	}
}
