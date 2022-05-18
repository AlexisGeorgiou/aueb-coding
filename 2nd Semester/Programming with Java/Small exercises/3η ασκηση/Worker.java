 class Worker {
	String name;
	int age;
	double wage;

	Worker(String name, int age, double wage) {
	this.name = name;
	this.age = age;
	wage=wage;
	}
	
	public static void main(String[] args){
	Worker mc = new Worker("Peter",25,235.0);
	Worker mc2 = new Worker("Alan",64,434.0);
	Worker mc3 = new Worker("Emily",36,320.0);
	System.out.print(mc3.age + ", " + mc2.wage + ", " + mc2.name);
	}
} 