import java.util.Scanner;  

public class Combinations {
	public static void main(String[] args){
		
		Scanner in = new Scanner(System.in);
		System.out.print("Enter number of objects in the set (n): ");
		int n = in.nextInt();
		System.out.print("Enter number to be chosen (k): ");
		int k = in.nextInt();
		if (n>=k && k>=0)
			System.out.println("C("+n+", "+k+") = "+combinations(n, k));
		else
			System.out.println ("Please enter n >= k >= 0");
	} // end of run

/*
* Returns the mathematical combinations function C(n, k),
* which is the number of ways of selecting k objects
* from a set of n distinct objects.
*/


static int combinations(int n, int k) {
	int i;
	int factorial_n = 1;
	for (i = 1; i <= n; i++) {
		factorial_n *= i;
	}

	int factorial_k = 1;
	for (i = 1; i <= k; i++) {
	factorial_k *= i;
	}

	int factorial_n_k = 1;
	for (i = 1; i <= n-k; i++) {
	factorial_n_k *= i;
	}

	return factorial_n / (factorial_k*factorial_n_k);


} 
} 