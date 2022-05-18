public class Point {
	int x,y;
	public Point(int i, int j) {
		x=i;
		y=j;
	}
	public void tricky(Point arg1, Point arg2) {
		arg1.x = arg2.x;
		arg1.y = arg2.y;
		arg2.x = 100;
		arg2.y = 200;


	}
	public static void main(String [] args) {
		Point pnt1 = new Point(1,2);
		Point pnt2 = new Point(3,4);
		System.out.println("1. X: " + pnt1.x + " Y: " +pnt1.y);
		System.out.println("2. X: " + pnt2.x + " Y: " +pnt2.y);
		pnt1.tricky(pnt1,pnt2);
		System.out.println("3. X: " + pnt1.x + " Y: " + pnt1.y);
		System.out.println("4. X: " + pnt2.x + " Y: " +pnt2.y);
	}
} 