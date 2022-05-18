import java.util.*;
import java.io.*;
public class Thiseas{
	
	//Finds Neighboring zeroes to a zero and stores the value in a n by m board that corresponds to the labyrinth
	public static int[][] findNeighbors(String[][] labyrinth, int n, int m){ 
		int neighbors = 0;
		int[][] neighborsBoard = new int[n][m];
		for (int i = 1; i<n-1; i++){
			for (int j = 1; j<m-1; j++){
				if (labyrinth[i][j].equals("0")){
					if (labyrinth[i][j + 1].equals("0")|| labyrinth[i][j + 1].equals("E"))
						neighbors++;
					if (labyrinth[i][j - 1].equals("0")|| labyrinth[i][j - 1].equals("E"))
						neighbors++;
					if (labyrinth[i + 1][j].equals("0")||labyrinth[i + 1][j].equals("E"))
						neighbors++;
					if (labyrinth[i - 1][j].equals("0")||labyrinth[i - 1][j].equals("E"))
						neighbors++;
					neighborsBoard[i][j] = neighbors;
					}
				
				
				neighbors = 0;
			}
		}
		return neighborsBoard;
	}//findNeighbors
	
	//Replaces Zeroes that have the property of a junction (> 3 neighboring zeroes) with "j"
	public static void markJunctions(String[][] labyrinth, int[][] neighborsBoard,  int n, int m){
		
		for (int i = 1; i<n-1; i++)
			for (int j = 1; j<m-1; j++){
				if (labyrinth[i][j].equals("0"))
					if (neighborsBoard[i][j] >= 3)
						labyrinth[i][j] = "j";
			}
	}//markJunctions
	
	//Replaces zeroes on the edge of the labyrinth with "e" (exit)
	public static void markExit(String[][] labyrinth,int n, int m){
		for (int i = 0; i < n; i = i + n - 1)
			for (int j = 0; j < m; j++)
				if (labyrinth[i][j].equals("0"))
					labyrinth[i][j] = "e";
			
		for (int j = 0; j < m; j = j + m - 1)
			for (int i = 0; i < n; i++)
				if (labyrinth[i][j].equals("0"))
					labyrinth[i][j] = "e";
	}//markExit
	
	//Replaces zeroes that have the property of a dead end (< 1 neighbors) with "x"
	public static void markDeadEnds(String[][] labyrinth, int[][] neighborsBoard, int n, int m){		
		
			for (int i = 1; i<n-1; i++)
				for (int j = 1; j<m-1; j++){
					if (labyrinth[i][j].equals("0"))
						if (neighborsBoard[i][j] <= 1)
							labyrinth[i][j] = "x";
			}
		
	}
	public static void toString(String[][] board, int n, int m){

		for (int i = 0; i < n; i++){
			for (int j = 0; j < m; j++)
				System.out.print(board[i][j] + ' ');
			System.out.print("\n");
		}
	}//toString
	
		
		
		

	public static void main(String[] args){

		//Initialize Board
		int n, m, nEntrance, mEntrance;
		BufferedReader reader = null; //file reader
		String line;
		
		//Confirmation variables
		boolean error = false; //error in reading labyrinth
		boolean entranceFound = false; // Entrance found or not
		boolean invalidCharacters = false; //Invalid characters were found or not
		boolean wrongDimensions = false; //Invalid dimensions or not
		int entrances = 0; //Number of entrances
		int xEntranceCheck = 0; int yEntranceCheck = 0; //To validate Entrance position
		
		try{
			reader = new BufferedReader(new FileReader(new File(args[0])));
			line = reader.readLine();
			String[] splitDim = line.split("\\s+"); //Splits line to read dimensions
			
			//Storing Dimensions
			n = Integer.parseInt(splitDim[0]);
			m = Integer.parseInt(splitDim[1]);
			
		
			line = reader.readLine();
			
			String[] splitEnt = line.split("\\s+"); //Splits line to read entrance coordinates
			
			//Storint entrance coordinates
			nEntrance = Integer.parseInt(splitEnt[0]);
			mEntrance = Integer.parseInt(splitEnt[1]);
		
			String[][] labyrinth = new String[n][m]; //Labyrinth Initialization
			
			//for loop to read labyrinth from file
			for (int i = 0; i<n; i++){
				
				line = reader.readLine();
				if (line == null){
					wrongDimensions = true;
					break;
				}
					
				for (int j = 0; j<m; j++){
					labyrinth[i][j] = line.substring(2*j,2*j + 1);
					if(line.length() != 2*m - 1){
						wrongDimensions = true;
						break;
					}
					if (!labyrinth[i][j].equals("0") && !labyrinth[i][j].equals("1") && !labyrinth[i][j].equals("E"))
						invalidCharacters = true;
					if(labyrinth[i][j].equals("E")){
						entranceFound = true; //Confirms that entrance was found
						xEntranceCheck = i;
						yEntranceCheck = j;
						entrances++;
					}
					if(wrongDimensions)
						break;
				}//for
			}//for
			if(reader.readLine() != null)
				wrongDimensions = true;
			
			//Error Handling
			
			//Wrong Dimensions Error
			if (wrongDimensions){
				System.out.println("Error: Labyrinth size does not match given dimensions (n,m)!");
				error = true;
				return;
			}
			
			//Entrance not found error
			if (!entranceFound){
				System.out.println("Error: No entrance found in given file!");
				error = true;
			}
			
			//Not matching entrance coordinates erro
			if ((xEntranceCheck != nEntrance) || (yEntranceCheck != mEntrance)){
				System.out.println("Error: Given entrance coordinates do not match those read from the labyrinth!");
				error = true;
			}
			
			//Multiple Entrances Errors
			if (entrances > 1){
				System.out.println("Error: Labyrinth contains more than 1 Entrance!");
				error = true;
			}
			
			//Invalid labyrinth Characters error
			if(invalidCharacters){
				System.out.println("Error: Invalid characters found in labyrinth");
				error = true;
			}
				
			//Terminate Program if error exists
			if (error)
				return;
			
			//Marking special positions on labyrinth (junctions, exits, dead ends)
			int [][]neighborsBoard = findNeighbors(labyrinth,n,m);
			markDeadEnds(labyrinth, neighborsBoard, n, m);
			markJunctions(labyrinth, neighborsBoard, n, m);
			markExit(labyrinth,n, m);
	
			int x,y; // Current Labyrinth Coordinates
			x = nEntrance;
			y = mEntrance;
			int clock;//To avoid going back and forth (prohibits infinite backtracking)
			//First Move (Avoid getting out of bounds)
			if (nEntrance == 0){
				clock = 2;
				x++;
			}
			else if (nEntrance == n - 1){
				clock = 4;
				x--;
			}
			else if (mEntrance == 0){
				clock = 3;
				y++; 
			}
			else{
				clock = 1;
				y--; 
			}
			
				
			StringStackImpl xStack = new StringStackImpl();
			StringStackImpl yStack = new StringStackImpl();
			
			xStack.push(String.valueOf(x));
			yStack.push(String.valueOf(y));
			labyrinth[nEntrance][mEntrance] = "1"; // Entrance becomes x (dead end)
			
			
			boolean moved; //Variable to check if position is moved
			boolean jfound = false; //Variable to check if at least one junction is found (used to terminate early)
			while (true){
				
				moved = false;
				//Nowhere to move -> No exit
				if (labyrinth[x][y].equals("1")){
					System.out.println("Could not enter labyrinth (Wall)");
					break;
				}
				if (labyrinth[x][y].equals("x") && !jfound){
					System.out.println("There is no exit in the labyrinth :(");
					break;
				}
					
				
				
				//BACKTRACKING (Using stack)
				if (labyrinth[x][y].equals("x")){
					while(!labyrinth[x][y].equals("j")){
						labyrinth[x][y] = "1"; // Replaces 0 with 1 (Can't go back)
						//Gets previous position
						x = Integer.parseInt(xStack.pop()); 
						y = Integer.parseInt(yStack.pop());
						
					}
				}
				
				//MOVEMENT FUNCTION (Called everytime we're not backtracking to move further into the labyrinth_
				if (labyrinth[x][y].equals("0")||labyrinth[x][y].equals("j")){
					
					
					if(labyrinth[x][y].equals("j"))
						jfound = true;
					
					
					if ((labyrinth[x - 1][y].equals("0")||labyrinth[x - 1][y].equals("e")||labyrinth[x - 1][y].equals("j")||labyrinth[x - 1][y].equals("x"))&& (clock != 2)){
						xStack.push(String.valueOf(x));
						yStack.push(String.valueOf(y));
						x--;//up
						clock = 4; //next move can't be down
						moved = true;
					}		
					else if ((labyrinth[x + 1][y].equals("0")||labyrinth[x + 1][y].equals("e")||labyrinth[x + 1][y].equals("j")||labyrinth[x + 1][y].equals("x"))&& (clock != 4)){
						xStack.push(String.valueOf(x));
						yStack.push(String.valueOf(y));
						x++;//down
						clock = 2; //next move can't be up
						moved = true;
					}
					
					else if ((labyrinth[x][y - 1].equals("0")||labyrinth[x][y - 1].equals("e") ||labyrinth[x][y - 1].equals("j")||labyrinth[x][y - 1].equals("x")) && (clock != 3) ){
						xStack.push(String.valueOf(x));
						yStack.push(String.valueOf(y));
						y--;//left
						clock = 1; //next move can't be right
						moved = true;
					}
					
					else if ((labyrinth[x][y + 1].equals("0")||labyrinth[x][y + 1].equals("e")||labyrinth[x][y + 1].equals("j")||labyrinth[x][y + 1].equals("x"))&& (clock != 1)){
						xStack.push(String.valueOf(x));
						yStack.push(String.valueOf(y));
						y++;//right
						clock = 3; //next move can't be left
						moved = true;
					}
					
					if(!moved){
						labyrinth[x][y] = "x"; //junction becomes dead end (Since movement was not possible)
						clock = 0; //next move can be anything
					}
					
				}
				
				//e found -> Exit found -> Program terminated
				if (labyrinth[x][y].equals("e")){
					System.out.println("The exit that was found is (" + x + ", " + y +") :D");
					break;
				}
				
				//e not found -> Exit not found -> Program terminated
				if(labyrinth[x + 1][y].equals("1") && labyrinth[x - 1][y].equals("1") && labyrinth[x][y + 1].equals("1") && labyrinth[x][y - 1].equals("1")){ 
					System.out.println("There is no exit in the labyrinth :(");
					break;
				}
				
				
		
			}//while
		
			//toString(labyrinth, n, m);
		
		}//try
		
		catch(IOException e){
			System.out.println("File not found!");
		}//catch
		
		
		
	}
}