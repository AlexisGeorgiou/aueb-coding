import java.lang.Math;

public class Point {
	
	private int x, y;

	public int x(){
		return x;
	} // return the x-coordinate

	public int y(){
		return y;
	} // return the y-coordinate
 

	
	public double distanceTo(Point z){
		 return Math.sqrt((x-z.x())*(x-z.x()) + (y - z.y())*(y - z.y()));
	}	 // Euclidean distance between two points

	 
	public int squareDistanceTo(Point z){
		return (int)(distanceTo(z) * distanceTo(z));
	}// square of the Euclidean distance between two points
	
	public String toString(){
		return "(" + x +"," + y + ")";
	}	 
	 
	 // string representation: (x, y)
	 
	 
	Point(int x, int y){
		this.x = x;
		this.y = y;
	}
 
}