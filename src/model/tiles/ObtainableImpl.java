package model.tiles;



/**
 * @author Matteo Alesiani 
 */
import com.google.common.base.Optional;

import utilities.enumerations.Color;

public abstract class ObtainableImpl implements Obtainable{

	private static final long serialVersionUID = -2712555186344805312L;
	
	private Integer positionTile;
	private String nameTile;
	private Integer price;	
	// mortgage è sempre la metà ?? in tal caso potremmo inserire un bool 
	private Integer mortgage;
	/**
	 * TODO: aggiunto ora. Check con Rosso. finire di gestire l'attributo.
	 * */
	private StatusTile hasMortgage;
	private Optional<String> owner;
	private Color colorTile;
	
	public ObtainableImpl(final int positionTile, final int price, 
					  final int mortgage, final Color colorTile){
		this.positionTile = positionTile;
		this.price = price;
		this.mortgage = mortgage;
		this.hasMortgage = StatusTile.NOT_MORTGAGE;
		this.owner = Optional.absent();
		this.colorTile = colorTile;
	}
	
	@Override
	public int getPosition() {
		return this.positionTile;
	}
	
	@Override
	public String getNameOf() {
		return this.nameTile;
	}
	
	@Override
	public boolean hasMortgage() {
		return this.hasMortgage == StatusTile.MORTGAGE;
	}
	
	@Override
	public void changeMortgageStatus() {
		this.hasMortgage = this.hasMortgage() ? StatusTile.NOT_MORTGAGE: StatusTile.MORTGAGE;
	}
	
	@Override
	public void setNameOf(String nameTile) {
		this.nameTile = nameTile;
	}
	
	@Override
	public int getMortgage() {
		return this.mortgage;
	}
	
	@Override
	public Optional<String> getOwner() {
		return this.owner;
	}
	
	@Override
	public void setOwner(Optional<String> owner) {
		this.owner = owner;
	}
	
	@Override
	public int getPrice() {
		return this.price;
	}
	
	@Override
	public Color getColorOf() {
		return this.colorTile;
	}

	@Override
	public String toString() {
		return "";
	}
}
