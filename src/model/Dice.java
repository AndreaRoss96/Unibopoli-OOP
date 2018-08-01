package model;

import java.util.Random;
import utilities.Pair;

/**
 * 
 * 
 * @author Matteo Alesiani
 */
public class Dice {

	private static final int MAX_NUM_DICE = 6;
	
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
		return new Pair<>(this.random.nextInt(MAX_NUM_DICE) + 1, this.random.nextInt(MAX_NUM_DICE) + 1);
	}
}
