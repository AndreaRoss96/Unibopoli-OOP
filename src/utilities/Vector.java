package utilities;

public class Vector implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Pair<Double, Double> vector;
    
    public Vector(double x,double y){
    	this.vector = new Pair<>(x, y);
    }

    public Vector(Point to, Point from){
    	this.vector = new Pair<>(to.getX() - from.getX(), to.getY() - from.getY());
    }

    public double getX() {
    	return this.vector.getX();
    }
    
    public double getY() {
    	return this.vector.getY();
    }
    
    public Vector sum(Vector v){
        return new Vector(this.getX() + v.getX(),this.getY() + v.getY());
    }

    public double module(){
        return (double) Math.sqrt(this.getX()*this.getX() + this.getY()*this.getY());
    }

    public Vector getNormalized(){
        double module = this.module();
        return new Vector(this.getX()/module,this.getY()/module);
    }

    public Vector mul(double fact){
        return new Vector(this.getX()*fact,this.getY()*fact);
    }

    public String toString(){
        return "Vector: " + this.vector.toString();
    }
}
