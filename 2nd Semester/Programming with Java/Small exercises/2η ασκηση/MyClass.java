public class MyClass {
	private static int count = 0;
	private int x;
	
	public MyClass(int i) {
	x = i;
	}
	
	public void incrementCount() {
		count++;
	}
	
	public void printX() {
		System.out.println("Value of x : " + x);
	}
	
	public static void printCount() {
		System.out.println("Value of count : " + count); 
	}
}
