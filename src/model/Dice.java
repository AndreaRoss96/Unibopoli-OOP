package model;

import java.util.Random;
import utilities.Pair;

/**
 * 
 * Ricontrollare tramite singleton VIROLI.
 * @author Matteo Alesiani
 */
public class Dice {

	private static final int MAX_NUM_DICE = 6;
	private static Dice DICE_SINGLETON;
	
	private Random random;

	private Dice(){
		this.random = new Random();
	}
	
	public static Dice getInstance() {
		if(Dice.DICE_SINGLETON == null) {
			Dice.DICE_SINGLETON = new Dice();
		}
		
		return Dice.DICE_SINGLETON; 
	}
	
	public Pair<Integer> getDice(){
		return new Pair<>(this.random.nextInt(MAX_NUM_DICE) + 1, this.random.nextInt(MAX_NUM_DICE) + 1);
	}
}
