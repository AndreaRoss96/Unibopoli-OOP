package model;

/**
 * @author Matteo Alesiani 
 */

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Rents {

	private static final int MAX_HOUSE = 5;
	
	private Map<Integer, Integer> rentsManagement;
	
	public Rents(List<Integer> record) {
		this.rentsManagement = IntStream.rangeClosed(0, MAX_HOUSE)
										.mapToObj(t->t)
										.collect(Collectors.toMap(Integer::new, record::get));
	}
	
	public int getRent(int numHouse) {
		return this.rentsManagement.get(numHouse);
	}
}
