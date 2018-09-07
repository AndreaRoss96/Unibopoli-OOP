package model.tiles;

/**
 * @author Matteo Alesiani 
 */
import com.google.common.base.Optional;

import utilities.enumerations.Color;
import utilities.enumerations.TileTypes;

public abstract class ObtainableImpl implements Obtainable{

	private static final long serialVersionUID = -2712555186344805312L;
	
	private final Color color;
	private Integer positionTile;
	private String nameTile;
	private Integer price;	
	private Integer mortgage;
	private StatusTile hasMortgage;
	private Optional<String> owner;
	private TileTypes titeType;
	
	public ObtainableImpl(final int positionTile, final int price, 
					  final int mortgage, final TileTypes titeType, final Color color){
		this.positionTile = positionTile;
		this.price = price;
		this.mortgage = mortgage;
		this.hasMortgage = StatusTile.NOT_MORTGAGE;
		this.titeType = titeType;
		this.owner = Optional.absent();
		this.color = color;
	}
	
	abstract int rentValue();
	
	@Override
	public int getPosition() {
		return this.positionTile;
	}
	
	@Override
	public void setNameOf(String nameTile) {
		this.nameTile = nameTile;
	}
	
	@Override
	public String getNameOf() {
		return this.nameTile;
	}
	
	@Override
	public int getRent() {
		return rentValue();
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
	public TileTypes getTiteType() {
		return this.titeType;
	}
	
	@Override
	public Color getColorOf() {
		return this.color;
	}
}
