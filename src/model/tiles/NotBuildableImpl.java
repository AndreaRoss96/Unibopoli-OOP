package model.tiles;

import utilities.enumerations.TiteTypes;

/**
 * 
 * @author Matteo Alesiani 
 */

public class NotBuildableImpl extends ObtainableImpl implements AdaprterPathImage{

	private static final long serialVersionUID = 4892673225735533821L;
	private static final Integer RENT = 50;
	
	public NotBuildableImpl(final int positionTile, final int price, 
			  final int mortgage, final TiteTypes titeType) {
		super(positionTile, price, mortgage, titeType);
		
		this.setNameOf(this.getTiteType().getTypeName());
	}
	
	@Override
	int rentValue() {
		/**
		 *Controllare se Mortgage va bene oppure inserire RENT. 
		 */
		return this.getMortgage();
	}
	
	@Override
	public String getPathImage() {
		return this.getTiteType().getPathImage().orElseThrow(() -> new IllegalArgumentException());
	}
}
