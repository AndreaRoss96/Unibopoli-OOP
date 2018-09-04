package model.tiles;

import javafx.scene.image.ImageView;
import utilities.IconLoader;
import utilities.enumerations.ClassicType;
import utilities.enumerations.Color;
import view.Icon;

/**
 * 
 * @author Matteo Alesiani 
 */

public class NotBuildableImpl extends ObtainableImpl implements NotBuildable{

	private static final long serialVersionUID = 4892673225735533821L;
	
	private static final String TRAIN = ClassicType.Board.GeneralImagesMap.getTrainImagePath();
	private static final String AZIENDA_ELETTRICA = ClassicType.Board.GeneralImagesMap.getBulbImagePath();
	private static final String AZIENDA_ACQUA = ClassicType.Board.GeneralImagesMap.getWaterImagePath();
	private static final Integer RENT = 25;
	
	transient private Icon image;
	
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
