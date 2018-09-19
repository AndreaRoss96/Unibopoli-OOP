package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import model.player.Player;
import model.player.PlayerInfo;
import utilities.CircularListImpl;

public class TurnImpl implements Turn {

	private static final long serialVersionUID = -754218580225904481L;
	private static final int TURN_IN_JAIL = 3;
	
	private CircularListImpl<Player> turnManagement;
	private List<PlayerInfo> loserPlayer;
	private Map<PlayerInfo, Integer> jailMap;
	private Integer rolls;
	private boolean done;
	
	public TurnImpl(List<Player> players) {
		this.turnManagement = new CircularListImpl<>(players);
		this.jailMap = new HashMap<>();
		this.loserPlayer = new ArrayList<>();
		//this.nextPlayer();
		this.clear();
	}
	
	@Override
	public Player getCurrentPlayer() {
		return this.turnManagement.getHead();
	}

	@Override
	public boolean isInJail() {
		return this.getCurrentPlayer().isInJail();
	}
	
	@Override
	public void turnInJail() {
		this.jailMap.merge(this.getCurrentPlayer(), 0, (oldV, newV) -> oldV++);		
	}
	
	@Override
	public boolean exitFromJail() {
		if(this.jailMap.get(this.getCurrentPlayer()) == TURN_IN_JAIL) {
			this.jailMap.remove(this.getCurrentPlayer());
			
			return true;
		}
		
		return false;
	}
	
	@Override
	public void nextPlayer() {
		this.clear();
		this.turnManagement.shift();
	}

	@Override
	public boolean remove(PlayerInfo player) {
		this.loserPlayer.add(player);
		return this.turnManagement.remove(player);
	}
	
	@Override
	public List<PlayerInfo> getPlayers(){
		return this.turnManagement.stream().collect(Collectors.toList());
	}
	
	private void clear() {
		this.rolls = 0;
		this.done = false;
	}
	
	/**
	 * TODO: Check!
	 * */
	@Override
	public boolean isThrows() {
		this.rolls++;
		return this.rolls <= 3;
	}
	
	@Override
	public void thrown (boolean areSame) {
		this.done = !areSame;
	}
	
	@Override
	public boolean hasDone() {
		return this.done;
	}
}
