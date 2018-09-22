package view;

import javafx.scene.shape.LineTo;
import utilities.Pair;
import utilities.PaneDimensionSetting;
import utilities.enumerations.Direction;
import view.tiles.ComponentFactory;

public class Pawn extends Icon{

	private static final double SPACE_BOARD = 50.0;
	
	private Direction direction;
	private Pair<Double> coordinates;
	
	public Pawn(String path, int position) {
		super(path);
		this.direction = this.initDirection(position);
		this.coordinates = this.initCoordinates(position);
	}

	private Direction initDirection(final int position) {
		if(position >= 0 && position < 10) {
			return Direction.W;
		}else if(position >= 10 && position < 20) {
			return Direction.N;
		}else if(position >= 20 && position < 30) {
			return Direction.E;
		}else {
			return Direction.S;
		}
	}
	
	private Pair<Double> initCoordinates(final int position){
		if(this.getDirection().equals(Direction.W)) {
			return new Pair<>(PaneDimensionSetting.getInstance().getGamePaneWidth() - (ComponentFactory.LandSimpleWIDTH * (position+1.5)), PaneDimensionSetting.getInstance().getGamePaneHeight() - SPACE_BOARD);
		}else if(this.getDirection().equals(Direction.N)) {
			return new Pair<>(ComponentFactory.LandSimpleWIDTH * 0.7, PaneDimensionSetting.getInstance().getGamePaneHeight() - (ComponentFactory.LandSimpleWIDTH * ((position+1.5)%10)));
		}else if(this.getDirection().equals(Direction.E)) {
			return new Pair<>((ComponentFactory.LandSimpleWIDTH * ((position+1.5)%20)), SPACE_BOARD);			
		}else {
			return new Pair<>(PaneDimensionSetting.getInstance().getGamePaneWidth() - ComponentFactory.LandSimpleWIDTH * 0.5, ComponentFactory.LandSimpleWIDTH * ((position+1.5)%30));			
		}
	}
	
	public void setCoordinates(Pair<Double> coordinates) {
		this.coordinates = coordinates;
	}
	
	public Pair<Double> getCoordinates(){
		return this.coordinates;
	}
	
	public void rotate() {
		this.direction = this.direction.rotation();
	}
	
	public LineTo move(int position) {
		return this.direction.moveLocation(this, position);
	}
	
	public Direction getDirection() {
		return this.direction;
	}
}
