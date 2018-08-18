package model;

public class World  {
	
	private Pawn ball;
	private RectBoundingBox mainBBox;
	
	public World(RectBoundingBox bbox){
		mainBBox = bbox;
	}

	public void setPawn(Pawn ball){
		this.ball = ball;
	}
	
	public void updateState(int dt){
		ball.updateState(dt);
		
		//checkBoundaries();
		//checkCollisions();
	}
	/*
	private void checkBoundaries(){
		Point pos = ball.getCurrentPos();
		Point ul = mainBBox.getULCorner();
		Point br = mainBBox.getBRCorner();
		double r = ball.getRadius();
		if (pos.y + r> ul.y){
			ball.setPos(new Point(pos.x, ul.y - r));
			ball.flipVelOnY();
		} else if (pos.y - r < br.y){
			ball.setPos(new Point(pos.x, br.y + r));
			ball.flipVelOnY();
		}
		if (pos.x + r > br.x){
			ball.setPos(new Point(br.x - r, pos.y));
			ball.flipVelOnX();
		} else if (pos.x - r < ul.x){
			ball.setPos(new Point(ul.x + r, pos.y));
			ball.flipVelOnX();
		}
	}

	/*
	private void checkCollisions(){
		P2d ballpos = ball.getCurrentPos();
		double radius = ball.getRadius();
		PickUpObj found = null;
		for (PickUpObj obj: picks){
			if (obj.getBBox().isCollidingWith(ballpos,radius)){
				found = obj;
				break;
			}
		}
		if (found != null){
			picks.remove(found);
		}
	}*/
		
	public RectBoundingBox getBBox(){
		return mainBBox;
	}
	
	public Pawn getPawn(){
		return ball;
	}
}
