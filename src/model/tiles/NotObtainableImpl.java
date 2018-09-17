package model.tiles;

import java.util.ArrayList;

import model.Consequences;
import model.ConsequencesImpl;
import model.ProbUnexSupplier;
import utilities.enumerations.TileTypes;

public class NotObtainableImpl implements NotObtainable, AdaprterPathImage{

	private static final long serialVersionUID = 6004220435515803475L;
	
	private Integer positionTile;
	private TileTypes tileType;
	protected ConsequencesImpl consequences;
	
	public NotObtainableImpl(final int positionTile, final TileTypes titeType) {
		this.positionTile = positionTile;
		this.tileType = titeType;
	}
	
	@Override
	public int getPosition() {
		return this.positionTile;
	}
	
	@Override
	public String getNameOf() {
		return this.getTileType().getTypeName();
	}
	
	@Override
	public void setNameOf(String nameTile) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public TileTypes getTileType() {
		return this.tileType;
	}
	
	@Override
	public String getPathImage() {
		return this.getTileType().getPathImage().orElseThrow(() -> new IllegalArgumentException());
	}
	
	private ConsequencesImpl provideConsequence() {
		switch (this.getTileType()) {
		case GO_JAIL: 
			return new ConsequencesImpl(Consequences.MOVING, "Vai in prigione", new ArrayList<>());
		case GO: 
			return new ConsequencesImpl(Consequences.SIMPLE_PAYMENT, "Passi dal via", new ArrayList<>());
		case LUXURY_TAX: 
			return new ConsequencesImpl(Consequences.SIMPLE_PAYMENT, "Tassa di lusso", new ArrayList<>());
		case INCOME_TAX: 
			return new ConsequencesImpl(Consequences.SIMPLE_PAYMENT, "Tassa da definire", new ArrayList<>());
		case PROBABILITY: 
			return ProbUnexSupplier.get().getNextProbability(); 
		case UNEXPECTED:
			return ProbUnexSupplier.get().getNextUnexpected();
		default: 
			return null; // Inserire NOT_CONSEQUENCE 
		}
	}
	
	@Override
	public void setConsequence(ConsequencesImpl consequence) {
		this.consequences = consequence;
	}
	
	@Override
	public void doConsequence() {
		this.setConsequence(this.provideConsequence());
		this.consequences.doConsequences();
	}
}
