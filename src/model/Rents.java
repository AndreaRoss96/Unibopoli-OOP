package model;

/**
 * @author Matteo Alesiani 
 */

import java.util.HashMap;
import java.util.Map;

public class Rents {

	private Map<Integer, Integer> rentsManagement;
	
	public Rents() {
		this.rentsManagement = new HashMap<>();
	}
	
	public int getRent(int numHouse) {
		return this.rentsManagement.get(numHouse);
	}
}
