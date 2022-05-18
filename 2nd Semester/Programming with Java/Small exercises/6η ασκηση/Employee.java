class Employee {
	public String employeeName;
	public int salary;
	public String address;
	
	public Employee() {
		this("Bob Day");
	}
	
	public Employee(String name) {
		this(name, 12000);
	} 
 
	public Employee(String name, int salary) {
		this(name, salary, "Patission 76");
	}
 
	public Employee(String name, int salary, String address) {
		this.employeeName=name;
		this.salary=salary;
		this.address=address;
	}
 
	void disp() {
		System.out.println("Employee Name: " + employeeName);
		System.out.println("Employee Salary: " + salary);
		System.out.println("Employee Address: " + address);
	}
 
	public static void main(String[] args) {
		Employee obj = new Employee();
		obj.disp();
	}
} 