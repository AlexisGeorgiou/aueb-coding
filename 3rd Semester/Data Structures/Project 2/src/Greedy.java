import java.util.*;
import java.io.*;

public class Greedy{
	static int[] readFile(String fileName){
		int[] errorFlag = {-1};// Return this if any errors;
		try{
			BufferedReader reader = null; //file reader
			String line;
		
			int listSize = 0;
			reader = new BufferedReader(new FileReader(new File(fileName))); //Read file from the beginning
			line = reader.readLine(); //Next Line
		
			while (line !=null){ //File iteration necessary to create the array of folders
			
				line = reader.readLine();
				listSize++;
			}//while
			int[] folders = new int[listSize]; //Creating array of folders with a size of listSize
			reader = new BufferedReader(new FileReader(new File(fileName))); //Read file again from the beginning
		
			for (int i = 0; i <= listSize - 1; i++){
				line = reader.readLine();
				if (Integer.parseInt(line.trim()) > 1000000 || Integer.parseInt(line.trim()) <0){
					System.out.print("Folder sizes are not between 0 and 1,000,000 MB.");
					return errorFlag;//error!
				}//if
				folders[i] = Integer.parseInt(line);//Store file data in the array
			}//for
			return folders;
		}//try
	
		catch(IOException e){
			System.out.println("File not found!");
		}//catch
		return errorFlag;
	}//readFile
	
	public static int Algorithm1(int[] folders){
		int folderSum = 0; //Sum of folders
		for(int i = 0; i<folders.length; i++) //Calculates total sum of folders
			folderSum += folders[i];
			
		
		MaxPQInterface<Disk> disks = new MaxPQ<>(new SpaceComparator()); //Priority queue of Disk Objects
		disks.insert(new Disk());//Initial disk
		
		for (int i = 0; i < folders.length; i++)
			if (folders[i] <= disks.peek().getFreeSpace()){
				disks.peek().addFolder(folders[i]);//adds folder to existing disks
				//Update the disk's priority in the queue.
				Disk temp = disks.peek();
				disks.getMax();
				disks.insert(temp);
			}//if
			else
				disks.insert(new Disk(folders[i]));//adds folder to new disk in PQ
			
		//Results presentation
		System.out.println("\tSum of all folders = " + (double)folderSum/1000000.0 + " TB");
		System.out.println("\tNumber of disks used = " + disks.getSize());
		int disksUsed = disks.getSize();
		if (folders.length <100)
			while(disks.getSize() !=0)
				System.out.println(disks.getMax());
		return disksUsed;//For Part D
	}//Algorithm1
	
	public static int Algorithm2(int[] folders){
		Sort.quickSort(folders, 0, folders.length - 1);
		int disksUsed = Algorithm1(folders); //Runs algorithm1 with sorted folders
		return disksUsed;//For Part D
	}//Algorithm2
	
	//This main is for single-file testing purposes. To compare larger data, go to AlgorithmCompare.java
	public static void main(String[] args){
		int[] folders = readFile(args[0]);//Folders list initialization
		if (folders[0] == -1)
			return;
		System.out.println("\nALGORITHM 1 RESULTS ARE:");
		Algorithm1(folders);
		System.out.println("\nALGORITHM 2 RESULTS ARE:");
		Algorithm2(folders);
		System.out.println();
	}//main

		
	
}