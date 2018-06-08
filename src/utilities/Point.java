package utilities;

/**
 *
 * 2-dimensional point
 * objects are completely state-less
 *
 */
public class Point implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Pair<Double, Double> point;

	public double getX() {
		return this.point.getX();
	}
	
	public double getY() {
		return this.point.getY();
	}
	
    public Point(double x,double y){
    	this.point = new Pair<>(x, y);
    }

    public Point sum(Vector v){
        return new Point(this.point.getX() +v.getX(), this.point.getY() +v.getY());
    }

    public Vector sub(Point v){
        return new Vector(this.point.getX() - v.getX(), this.point.getY() -v.getY());
    }

    public String toString(){
        return "Point: " + this.point.toString();
    }

}
