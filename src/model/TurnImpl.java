package model;

import model.player.Player;
import utilities.CircularListImpl;

/**
 * @author Edoardo Doglioni
 *
 */
public class TurnImpl implements Turn {

	public CircularListImpl<Player> turnList;
	
	public Player getCurrentPlayer() {
		// TODO Auto-generated method stub
		return turnList.getHead();
	}

	public Player nextPlayer() {
		// TODO Auto-generated method stub
		 turnList.shift();
		 return turnList.getHead();
	}

	public boolean isInJail() {
		// TODO Auto-generated method stub
		/*
		 * if Player is in prision -> true
		 * else -> false
		 */
		return true;
	}

}
