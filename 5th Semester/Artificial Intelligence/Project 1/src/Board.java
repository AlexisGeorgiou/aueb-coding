
import java.util.ArrayList;
import java.lang.Math;

public class Board
{
    //Variables for the Boards values
	public static final int X = 1;
	public static final int O = -1;
	public static final int EMPTY = 0;
    
    //Immediate move that lead to this board
    private Move lastMove;

    /* Variable containing who played last; whose turn resulted in this board
     * Even a new Board has lastLetterPlayed value; it denotes which player will play first
     */
	private int lastLetterPlayed;

	private int [][] gameBoard;
	
	public Board()
	{
		lastMove = new Move();
		lastLetterPlayed = O; //This way, X will always move first
		gameBoard = new int[8][8]; //Dimensions are 8x8
		for(int i=0; i<8; i++)
		{
			for(int j=0; j<8; j++)
			{
				gameBoard[i][j] = EMPTY;
			}
		}
		
		//Initial game board
		gameBoard[3][3] = X;
		gameBoard[3][4] = O;
		gameBoard[4][4] = X;
		gameBoard[4][3] = O;
	}
	
	public Board(Board board)
	{
		lastMove = board.lastMove;
		lastLetterPlayed = board.lastLetterPlayed;
		gameBoard = new int[8][8];
		for(int i=0; i<8; i++)
		{
			for(int j=0; j<8; j++)
			{
				gameBoard[i][j] = board.gameBoard[i][j];
			}
		}
	}
		
	public Move getLastMove()
	{
		return lastMove;
	}
	
	public int getLastLetterPlayed()
	{
		return lastLetterPlayed;
	}
	
	public int[][] getgameBoard()
	{
		return gameBoard;
	}

	public void setLastMove(Move lastMove)
	{
		this.lastMove.setRow(lastMove.getRow());
		this.lastMove.setCol(lastMove.getCol());
		this.lastMove.setValue(lastMove.getValue());
	}
	
	public void setLastLetterPlayed(int lastLetterPlayed)
	{
		this.lastLetterPlayed = lastLetterPlayed;
	}
	
	public void setgameBoard(int[][] gameBoard)
	{
		for(int i=0; i<8; i++)
		{
			for(int j=0; j<8; j++)
			{
				this.gameBoard[i][j] = gameBoard[i][j];
			}
		}
	}

    //Make a move; it places a letter in the board
	public void makeMove(int row, int col, int letter)
	{
		gameBoard[row][col] = letter;
		capture(row, col, letter);
		lastMove = new Move(row, col);
		lastLetterPlayed = letter;
	}

    //Checks whether a move is valid; whether a square is empty, whether it is in the dimensions, and whether there is an "ally" in one of the lines
	public boolean isValidMove(int row, int col, int letter)
	{
		if ((row < 0) || (col < 0 ) || (row > 8) || (col > 8))
		{
			return false;
		}
		if(gameBoard[row][col] != EMPTY)
		{
			return false;
		}
		//Checking south block
		if(row != 7 && gameBoard[row + 1][col] == letter*(-1))
			if(checkSouthLine(row, col, letter) != null)
				return true;
		//Checking north block
		if(row != 0 && gameBoard[row - 1][col] == letter*(-1))
			if(checkNorthLine(row, col, letter) != null)
				return true;
		//Checking east block
		if(col != 7 && gameBoard[row][col + 1] == letter*(-1))
			if(checkEastLine(row, col, letter) != null)
				return true;
		//Checking West block
		if(col != 0 && gameBoard[row][col - 1] == letter*(-1))
			if(checkWestLine(row, col, letter) != null)
				return true;
		//Checking Northeast block
		if(row != 0  && col != 7 && gameBoard[row - 1][col + 1] == letter*(-1)  )
			if(checkNorthEastLine(row, col, letter) != null)
				return true;
		//Checking Southeast block
		if(row != 7 && col != 7 && gameBoard[row + 1][col + 1] == letter*(-1)  )
			if(checkSouthEastLine(row, col, letter) != null)
				return true;
		//Checking Southwest block
		if(row != 7 && col != 0  && gameBoard[row + 1][col - 1] == letter*(-1) )
			if(checkSouthWestLine(row, col, letter) != null)
				return true;
		//Checking Northwest
		if(row != 0 && col != 0 && gameBoard[row - 1][col - 1] == letter*(-1))
			if(checkNorthWestLine(row, col, letter) != null)
				return true;						
		return false;
	}
	//Helper functions to validate moves
	//Check N
	private int[] checkNorthLine(int row, int col, int letter){
		int[] pos = new int[2];
		for(int i = row - 2; i >= 0; i--){
        	if (gameBoard[i][col] == EMPTY)
            	break;
        	if (gameBoard[i][col] == letter){ // If its ours
				pos[0] = i;
				pos[1] = col;
				return pos;
			}
		}
		return null;
	}
	//Check NE
	private int[] checkNorthEastLine(int row, int col, int letter){
		row -= 2;
		col += 2;
		int[] pos = new int[2];
		while(row >= 0 && col <= 7){
            if(gameBoard[row][col] == EMPTY)
				break;
            if(gameBoard[row--][col++] == letter){
				pos[0] = row;
				pos[1] = col;
				return pos;
			}	
		}
		return null;
	}
	//Check E
	private int[] checkEastLine(int row, int col, int letter){
		int[] pos = new int[2];
		for(int j = col + 2; j <= 7; j++){
        	if (gameBoard[row][j] == EMPTY)
            	break;
			 if (gameBoard[row][j] == letter){ // If its ours
				pos[0] = col;
				pos[1] = j;
				return pos;
			 }
		}
		return null;
	}
	//Check SE
	private int[] checkSouthEastLine(int row, int col, int letter){
		row += 2;
		col += 2;
		int[] pos = new int[2];
		while(row <= 7 && col <= 7){
            if(gameBoard[row][col] == EMPTY)
                break;
            if(gameBoard[row++][col++] == letter){
				pos[0] = row;
				pos[1] = col;
				return pos;
			}
		}
		return null;
	}
	//Check S
	private int[] checkSouthLine(int row, int col, int letter){
		int[] pos = new int[2];
		for(int i = row + 2; i <= 7; i++){
        	if (gameBoard[i] [col] == EMPTY)
				break;
        	if (gameBoard[i][col] == letter){ // If its ours
				pos[0] = i;
				pos[1] = col;
				return pos;
			}
		}
		return null;
	}
	//Check SW
	private int[] checkSouthWestLine(int row, int col, int letter){
		row += 2;
		col -= 2;
		int[] pos = new int[2];
		while(row <= 7 && col >= 0){
            if(gameBoard[row][col] == EMPTY)
                break;
            if(gameBoard[row++][col--] == letter){
				pos[0] = row;
				pos[1] = col;
				return pos;
			}
		}
		return null;
	}
	//Check W
	private int[] checkWestLine(int row, int col, int letter){
		int[] pos = new int[2];
        for(int j = col - 2; j >= 0; j--){
        	if (gameBoard[row][j] == EMPTY)
				break;
        	if (gameBoard[row][j] == letter){ // If its ours
				pos[0] = row;
				pos[1] = j;
				return pos;
			}
		}
		return null;
	}
	//Check NW
	private int[] checkNorthWestLine(int row, int col, int letter){
		row -= 2;
		col -= 2;
		int[] pos = new int[2];
		while(row >= 0 && col >= 0){
            if(gameBoard[row][col] == EMPTY)
                break;
            if(gameBoard[row--][col--] == letter){
				pos[0] = row;
				pos[1] = col;
				return pos;
			}
		}
		return null;
	}
    /* Generates the children of the state
     * Any square in the board that is empty results to a child
     */
	public ArrayList<Board> getChildren(int letter)
	{
		boolean hasMove = false;
		ArrayList<Board> children = new ArrayList<Board>();
		for(int row=0; row<8; row++)
		{
			for(int col=0; col<8; col++)
			{
				if(isValidMove(row, col, letter))
				{
					Board child = new Board(this);
					child.makeMove(row, col, letter);
					children.add(child);
					hasMove = true;
				}
			}
		}
		if(!hasMove){ //If the AI can't move, it will just duplicate the current state of the board as the child
			Board child = new Board(this);
			child.setLastLetterPlayed(letter);
			children.add(child);
		}
		return children;
	}

	/*Captures the pawns in between of the current pawn 
	*and the allied pawns on different directions. 
	*/
	private void capture(int row, int col, int letter){
		int[] ally = new int[2];
		//Capturing south block
		if(row != 7 && gameBoard[row + 1][col] == letter*(-1)){
			ally = checkSouthLine(row, col, letter);
			if(ally != null){
				for(int i = row; i < ally[0]; i++){
					gameBoard[i][col] = letter;
				}
			}
		}
		//Capturing north block
		if(row != 0 && gameBoard[row - 1][col] == letter*(-1)){
			ally = checkNorthLine(row, col, letter);
			if(ally != null){
				for(int i = row; i > ally[0]; i--){
					gameBoard[i][col] = letter;
				}				
			}
		}	
		//Capturing east block
		if(col != 7 && gameBoard[row][col + 1] == letter*(-1)){
			ally = checkEastLine(row, col, letter);
			if(ally != null){
				for(int j = col; j < ally[1]; j++){
					gameBoard[row][j] = letter;
				}				
			}
		}
		//Capturing West block
		if(col != 0 && gameBoard[row][col - 1] == letter*(-1)){
			ally = checkWestLine(row, col, letter);
			if(ally != null){
				for(int j = col; j > ally[1]; j--){
					gameBoard[row][j] = letter;
				}				
			}	
		}
		//Capturing Northeast block
		if(row != 0  && col != 7 && gameBoard[row - 1][col + 1] == letter*(-1)  ){
			ally = checkNorthEastLine(row, col, letter);
			if(ally != null){
				int i = row;
				int j = col;
				i--;
				j++;
				while(i > ally[0]){
					gameBoard[i][j] = letter;
					i--;
					j++;
				}				
			}
		}
		//Capturing Southeast block
		if(row != 7 && col != 7 && gameBoard[row + 1][col + 1] == letter*(-1)  ){
			ally = checkSouthEastLine(row, col, letter);
			if(ally != null){
				int i = row;
				int j = col;
				i++;
				j++;
				while(i < ally[0]){
					gameBoard[i][j] = letter;
					i++;
					j++;
				}			
			}
		}
		//Capturing Southwest block
		if(row != 7 && col != 0  && gameBoard[row + 1][col - 1] == letter*(-1) ){
			ally = checkSouthWestLine(row, col, letter);
			if(ally != null){
				int i = row;
				int j = col;
				i++;
				j--;
				while(i < ally[0]){
					gameBoard[i][j] = letter;
					i++;
					j--;
				}					
			}
		}
		//Capturing Northwest
		if(row != 0 && col != 0 && gameBoard[row - 1][col - 1] == letter*(-1)){
			ally = checkNorthWestLine(row, col, letter);
			if(ally != null){
				int i = row;
				int j = col;
				i--;
				j--;
				while(i > ally[0]){
					gameBoard[i][j] = letter;
					i--;
					j--;
				}				
			}
			
		}				
	}
	/*These evaluate functions are used to calculate the heuristic value of the nodes we find
	when traversing the minimax tree. The criteria we use are:
	1. The amount of  each player's pawns
	2. The amount of  each player's pawns that's on one of the 4 sides
    3. The amount of  each player's pawns that's on one of the 4 corners
     */
	public int evaluate(){
		int evaluation = evaluateSum() + 2*evaluateSides() + 3*evaluateCorners();
		if(isTerminal()){
			if(evaluation == 0)
				return evaluation;
			return evaluation + 1000*(evaluation/Math.abs(evaluation));
		}
		return evaluation;
	}
	//Helper functions for calculating heuristic
	//evaluate 1st criterion
	private int evaluateSum(){
		int totalXs = 0;
		int totalOs = 0;
		for(int row = 0; row < 8; row++)
			for(int col = 0; col < 8; col++){
				if(gameBoard[row][col] == X)
					totalXs++;
				else if(gameBoard[row][col] == O){
					totalOs++;
				}
			}
		return totalXs - totalOs;
	}
	//evaluate 2nd criterion
	private int evaluateCorners(){
		int totalXcorners = 0;
		int totalOcorners = 0;

		if (gameBoard[0][0] == X) 
			totalXcorners++;
		else if (gameBoard[0][0] == O)
			totalOcorners++;

		if (gameBoard[7][0] == X) 
			totalXcorners++;
		else if (gameBoard[7][0] == O)
			totalOcorners++;

		if (gameBoard[0][7] == X) 
			totalXcorners++;
		else if (gameBoard[0][7] == O)
			totalOcorners++;

		if (gameBoard[7][7] == X) 
			totalXcorners++;
		else if (gameBoard[7][7] == O)
			totalOcorners++;

		return totalXcorners - totalOcorners;
	}
	//evaluate 3rd criterion
	private int evaluateSides(){
		int totalXsides = 0;
		int totalOsides = 0;
		for (int col = 1; col < 7; col++)
			if (gameBoard[0][col] == X)
				totalXsides++;
			else if (gameBoard[0][col] == O)
				totalOsides++;
		for (int col = 1; col < 7; col++)
			if (gameBoard[7][col] == X)
				totalXsides++;
			else if (gameBoard[7][col] == O)
				totalOsides++;
		for (int row = 1; row < 7; row++)
			if (gameBoard[row][0] == X)
				totalXsides++;
			else if (gameBoard[row][0] == O)
				totalOsides++;
		for (int row = 1; row < 7; row++)
			if (gameBoard[row][7] == X)
				totalXsides++;
			else if (gameBoard[row][7] == O)
				totalOsides++;
		return totalXsides - totalOsides;
	}
        

    /*
     *A state is terminal when neither player can make a valid move
     */
    public boolean isTerminal()
    {
	   	
		if(!canPlay(O) && !canPlay(X))
			return true;
		/*if(isFull(gameBoard))
			return true;*/

        return false;
	}
	
	
	//Helper function for checking if Terminal
	public boolean canPlay( int letter){
		//Checking if player can play
		for(int row=0; row<8; row++)
		{
			for(int col=0; col < 8; col++)
			{
				if(isValidMove(row, col, letter))
					return true;
			}
		}
		return false;
	}

    //Prints the board
	public void print()
	{
		System.out.println("   0 1 2 3 4 5 6 7 ");
		System.out.println(" * * * * * * * * * *");
		for(int row=0; row<8; row++)
		{
			System.out.print(row + "* ");
			for(int col=0; col<8; col++)
			{
				switch (gameBoard[row][col])
				{
					case X:
						System.out.print("X ");
						break;
					case O:
						System.out.print("O ");
						break;
					case EMPTY:
						System.out.print("- ");
						break;
					default:
						break;
				}
			}
			System.out.println("*");
		}
		System.out.println(" * * * * * * * * * *");
	}
}
