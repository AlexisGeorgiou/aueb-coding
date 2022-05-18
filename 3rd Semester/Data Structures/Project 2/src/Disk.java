

public class Disk implements Comparable<Disk> {

	private static int currentId = 1;
	private int id;
	private static int maxDiskSpace = 1000000;
	private int currentDiskSpace;
	public List folders = new List();
	
	
	Disk(){//Default constructor
		this.id = currentId;
		currentId++;
		currentDiskSpace = maxDiskSpace;
	}//Disk
	Disk(int folder){ //Constructor for dynamic file addition
		this.id = currentId;
		currentId++;
		currentDiskSpace = maxDiskSpace;
		addFolder(folder);
	}//Disk

	int getFreeSpace() {return currentDiskSpace;}
	
	int getId(){return id;}
	
	void addFolder(int folder){
		folders.insertAtFront(folder); //List Function
		currentDiskSpace -= folder; //Update free space
	}//addFolder
	
	public int compareTo(Disk otherDisk){
		if (currentDiskSpace > otherDisk.getFreeSpace())
			return 1;
		else if (currentDiskSpace < otherDisk.getFreeSpace())
			return -1;
		else
			return 0;
	}//compareTo
	
	public String toString(){
		StringBuilder formattedArray = new StringBuilder();
		int[] tempSave = new int[folders.getSize()];
		int initialSize = folders.getSize();
		
		for (int i = 0; i < initialSize; i++){
			try{
				tempSave[i] = folders.removeFromFront(); //Temporarily saving folders to restore later
				formattedArray.append(tempSave[i]).append(" "); //Destroying folder list and storing in in a formatted array
			}//try
			catch(EmptyListException e){
				System.out.println("List is empty");
			}//catch
		}//for
		for (int i = 0; i < tempSave.length; i++){
			folders.insertAtFront(tempSave[i]); //Restoring folder list
		}//for
		return "id " + id + " "+ currentDiskSpace + ": " + formattedArray;
	}//toString
}