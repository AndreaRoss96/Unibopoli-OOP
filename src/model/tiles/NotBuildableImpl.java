package model.tiles;

import javafx.scene.image.ImageView;
import model.Icon;
import utilities.IconLoader;
import utilities.enumerations.ClassicType;
import utilities.enumerations.Color;

/**
 * 
 * @author Matteo Alesiani 
 */

public class NotBuildableImpl extends ObtainableImpl implements NotBuildable{

	private static final String TRAIN = ClassicType.GeneralPurposeMap.getTrainImagePath();
	private static final String AZIENDA_ELETTRICA = ClassicType.GeneralPurposeMap.getBulbImagePath();
	private static final String AZIENDA_ACQUA = ClassicType.GeneralPurposeMap.getWaterImagePath();
	private static final int RENT = 25;
	
	private Icon image;
	
	public NotBuildableImpl(final int positionTile, final int price, 
			  final int mortgage, final Color colorTile) {
		super(positionTile, price, mortgage, colorTile);
		
		this.initImage();
	}
	
	/**
	 * TODO: Controllare se è effettivamente così.
	 * */
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
	 * Inserire Commento.
	 * */
	private void initImage() {
		if(this.getType().equals("Station")) {
			this.setImage(IconLoader.getLoader().getImageFromPath(TRAIN));
		}else if(this.getPosition() == 12) {
			this.setImage(IconLoader.getLoader().getImageFromPath(AZIENDA_ELETTRICA));
		}else {
			this.setImage(IconLoader.getLoader().getImageFromPath(AZIENDA_ACQUA));
		}
	}
}
