class Demo {
	final int MAX_VALUE;
	static int max_all;

	Demo(int i) {
		MAX_VALUE=i;
		max_all=i;
	}
	void myMethod(){
		System.out.println(MAX_VALUE);
		System.out.println(max_all);
	}
	public static void main(String args[]){
		Demo obj[]=new Demo[20];
		for (int i=0; i<10; i++){
			obj[i]=new Demo(100+i);
			obj[i].myMethod();
			}
		}
} 

