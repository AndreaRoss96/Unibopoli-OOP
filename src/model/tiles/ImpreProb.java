package model.tiles;

import model.ProbUnexSupplier;
import utilities.enumerations.TileTypes;

public class ImpreProb extends NotObtainableImpl{

	private static final long serialVersionUID = -1530500912056018450L;

	public ImpreProb(final int positionTile, final TileTypes titeType) {
		super(positionTile, titeType);
	}

	@Override
	protected void setConsequence() {
		this.setConsequence(this.getTileType() == TileTypes.PROBABILITY ? 
					ProbUnexSupplier.get().getNextProbability() : 
					ProbUnexSupplier.get().getNextUnexpected());
	}
}
