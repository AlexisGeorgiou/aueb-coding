import java.io.File;

public class AlgorithmCompare{
	public static void main(String args[]){
		
		//Files to be read, with their respective paths
		File dir1 = new File("../data/100");
		File dir2 = new File("../data/500");
		File dir3 = new File("../data/1000");
		
		File[] dirs = {dir1, dir2, dir3}; //List of directories to be accessed
		int[] Nlist = {100, 500, 1000}; //Reference List
		int currentN = 0;
		for (File dir : dirs){//For each directory in directory list (dirs)
			System.out.println(" --- For N = " + Nlist[currentN] + " ---");
			int sumOfDisks1 = 0;
			int sumOfDisks2 = 0;
			for (File f: dir.listFiles()){ //For each file in directory
				System.out.println("--\nApplying Algorithms 1 and 2 to " + f.getName());
				//Updating totalDisks for each of the algorithms
				System.out.println("   ALGORITHM 1 RESULSTS ARE:");
				sumOfDisks1 += Greedy.Algorithm1(Greedy.readFile("../data/" + String.valueOf(Nlist[currentN]) + "/"+ f.getName()));
				System.out.println("   ALGORITHM 2 RESULSTS ARE:");
				sumOfDisks2 += Greedy.Algorithm2(Greedy.readFile("../data/" + String.valueOf(Nlist[currentN]) + "/"+ f.getName()));
				
			}
			//Calculating the average total Disks for both Algorithms
			System.out.println("-------------------------------------------------");
			System.out.println("\nThe average disks used with Unsorted Algorithm (1): " + sumOfDisks1/10);
			System.out.println("The average disks used with Sorted Algorithm (2): " + sumOfDisks2/10);
			System.out.println("\n-------------------------------------------------\n");
			
			currentN++;//Next number of N
		}
	}
}