import java.util.*;
import java.io.*;

public class FolderGenerator {
	public static String[] generate(int size){
		Random rand = new Random();//Creating Random object
		String[] folders = new String[size];//100 500 or 1000 folders
		for (int i = 0; i < folders.length; i++)
			folders[i] = String.valueOf(1 + rand.nextInt(1000000)); //One folder written
		return folders;
	}//generate
	public static void main(String args[]){
		
		
		//Save the Files in corresponding subdirectories in data
		try{
			String[] folders;
			
			Writer writer = null;
			for (int i = 0; i < 10; i++){//Create 10 files of folders
				folders = generate(Integer.parseInt(args[0]));//Generate folders equal to command line arg (N)
				File file = new File("../data/" + args[0] + "/" + args[0] + "folders_" + i + ".txt");
				writer = new BufferedWriter(new FileWriter(file)); //Write next file
				for (int j = 0; j < folders.length - 1; j++){
					writer.write (folders[j] + "\n");
				}
				writer.write(folders[folders.length - 1]);
				writer.close();//One File Written
			}
			
		}
		catch(IOException e){
			System.out.println("Error Writing file");
		}
	}
}