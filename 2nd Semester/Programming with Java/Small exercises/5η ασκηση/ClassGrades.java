public class ClassGrades{
    public double[] grades = { 7, 9, 10, 8.5, 8, 9.5, 2, 4, 7, 8};
	
	public void averageValue(){
		double a,sum = 0;
		for (double value : grades) {
			sum += value;
			}
		a = sum/(grades.length);
		System.out.println(a);
		}
		
	public void maximumGrade(){
		double max;
		max = grades[0];
		for(double i : grades){
			if (i > max){
				max = i;
			}
		}
	    System.out.println(max);
	}
	public void minimumGrade(){
		double min;
		min = grades[0];
		for(double i : grades){
			if (i < min){
				min = i;
			}
		}
		System.out.println(min);
	}
	public void myCopy( double a[] ,double grades_backup []){
		
		int b = 0;
		for ( double i : a){
			grades_backup[b] = a[b];
			System.out.println(grades_backup[b]);
			b += 1;
		}
	}
	public static void main(String args[]){
		ClassGrades a = new ClassGrades();
		a.maximumGrade();
		a.minimumGrade();
		a.averageValue();
		double grades_backup [] = new double[10];
		a.myCopy(a.grades, grades_backup );
	}
}