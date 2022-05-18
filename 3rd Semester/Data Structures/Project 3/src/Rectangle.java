/* 
Rectangle = [xmin, xmax] x [ymin, ymax]

----------------------------
----------------------------
---*********B---------------
---*--------*---------------
---A*********---------------
----------------------------

A = Point(xmin, ymin)
B = Point(xmax, ymax)

You can define a rectangle just with 2 points.
*/

public class Rectangle {
	private int xmin, ymin, xmax, ymax;
	
	public int xmin(){
		return xmin;
	}		// minimum x-coordinate of rectangle
	public int xmax(){
		return xmax;
	}		// maximum x-coordinate of rectangle
	public int ymin(){
		return ymin;
	}		// minimum y-coordinate of rectangle
	public int ymax(){
		return ymax;
	}		// maximum y-coordinate of rectangle
	
	public boolean contains(Point p){
		return (p.x() >= xmin && p.x() <= xmax && p.y() >= ymin && p.y() <= ymax);
	}	 //does p belong to the rectangle?
	public boolean intersects(Rectangle r){
		return !(r.ymin() > ymax || r.ymax() < ymin || r.xmin() > xmax || r.xmax() < xmin);
	}// do the two rectangles intersect?
 
	public double distanceTo(Point p){
		// corners
		if (p.x() <= xmin && p.y() >= ymax)
			return p.distanceTo(new Point(xmin, ymax));
		else if (p.x() >= xmax && p.y() >= ymax)
			return p.distanceTo(new Point(xmax, ymax));
		else if (p.x() <= xmin && p.y() <= ymin)
			return p.distanceTo(new Point(xmin, ymin));
		else if (p.x() >= xmax && p.y() <= ymin)
			return p.distanceTo(new Point(xmax, ymin));
		// not-corners
		else if (p.y() >= ymax) 
			return p.y() - ymax;
		else if (p.x() <= xmin)
			return xmin - p.x();
		else if (p.x() >= xmax)
			return p.x() - xmax;
		else if (p.y() <= ymin) 
			return ymin - p.y();
		else
			return 0;
			
	} // Euclidean distance from p to closest point in rectangle
	public int squareDistanceTo(Point p){
		return (int)(distanceTo(p) * distanceTo(p));
	} // square of Euclidean distance from p to closest point in rectangle
	public String toString(){
		return "[" + xmin + "," + xmax + "] x [" + ymin + "," + ymax + "]";
	}// string representation: [xmin, xmax] x [ymin, ymax]
	
	public Rectangle(int xmin, int xmax, int ymin, int ymax){
		this.xmin = xmin;
		this.xmax = xmax;
		this.ymin = ymin;
		this.ymax = ymax;
	}
}

