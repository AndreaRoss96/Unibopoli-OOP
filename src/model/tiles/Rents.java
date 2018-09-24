package model.tiles;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * This class facilitates the return of the rent in the Buildable class, because 
 * inside, like a Wrapper, for each building there is a relative rent.
 * 
 * @author Matteo Alesiani 
 */

public class Rents implements Serializable {

	private static final long serialVersionUID = -644559450026866488L;

	private static final Integer MAX_HOUSE = 5;
	
	private Map<Integer, Integer> rentsManagement;
	private Integer buildingsNr;
	private Integer priceBuilding;
	
	public Rents(List<Integer> record) {
		this.rentsManagement = IntStream.rangeClosed(0, MAX_HOUSE)
										.mapToObj(t->t)
										.collect(Collectors.toMap(Integer::intValue, record::get));
		this.buildingsNr = 0;
	}
	
	/**
	 * Return the rent in based of how many buildings there are in the property.
	 * 
	 * @param int <tt>numHouse</tt> of buildings.
	 * @return int <tt>rent</tt> of relative numHouse.
	 */
	public int getRent() {
		return this.rentsManagement.get(this.buildingsNr);
	}
	
	public int getBuildingNumber() {
		return this.buildingsNr;
	}
	
	public void incBuildings() {
		if(this.buildingsNr + 1 < this.rentsManagement.size()) {
			this.buildingsNr++;
		}
	}
	
	public void setPriceForBuilding(final Integer priceBuilding) {
		this.priceBuilding = priceBuilding;
	}
	
	public int getPriceForBuilding() {
		return this.priceBuilding;
	}

	public void decBuildings() {
		if(this.buildingsNr != 0) {
			this.buildingsNr--;
		}
	}
	
	public int getRent(int buildingsNr) {
		return this.rentsManagement.get(buildingsNr);
	}
}
