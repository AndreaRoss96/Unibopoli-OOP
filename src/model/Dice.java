package model;

/**
 * @author Matteo Alesiani
 */

import java.util.Random;
import utilities.Pair;

public class Dice {

	private static final int MAX_NUM_DICE = 7;
	
	private Random random = new Random();
	
	private static class DiceSafe{
		private static final Dice DICE_SINGLETON = new Dice();
	}
	
	private Dice(){
		
	}
	
	public Dice getIstance() {
		return DiceSafe.DICE_SINGLETON; 
	}
	
	public Pair<Integer, Integer> getDice(){
		return new Pair<>(this.random.nextInt(MAX_NUM_DICE), this.random.nextInt(MAX_NUM_DICE));
	}
}
