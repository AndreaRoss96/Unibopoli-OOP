package model.tiles;

import java.util.ArrayList;

import model.Consequences;
import model.ConsequencesImpl;
import utilities.enumerations.TileTypes;

public class OtherButRename extends NotObtainableImpl {

	private static final long serialVersionUID = -2299842436155243538L;

	public OtherButRename(final int positionTile, final TileTypes titeType) {
		super(positionTile, titeType);
	}

	@Override
	protected void setConsequence() {
		this.setConsequence(this.provideConsequence());
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
		default: 
			return null; // Inserire NOT_CONSEQUENCE 
		}
	}
}
