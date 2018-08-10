package model.tiles;

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

public class Rents {

	private static final int MAX_HOUSE = 5;
	
	private Map<Integer, Integer> rentsManagement;
	
	public Rents(List<Integer> record) {
		this.rentsManagement = IntStream.rangeClosed(0, MAX_HOUSE)
										.mapToObj(t->t)
										.collect(Collectors.toMap(Integer::new, record::get));
	}
	
	/**
	 * Return the rent in based of how many buildings there are in the property.
	 * 
	 * @param int <tt>numHouse</tt> of buildings.
	 * @return int <tt>rent</tt> of relative numHouse.
	 */
	public int getRent(int numHouse) {
		
		
		/***
		 * 
		 * Aggiungere eccezione.
		 * */
		return this.rentsManagement.get(numHouse);
	}
}
