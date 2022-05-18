import java.util.*;


public class Main
{
	public static void main(String[] args)
	{	
		//
		Scanner in = new Scanner(System.in);
        //We create the players and the board
        //MaxDepth for the MiniMax algorithm is set to 2; feel free to change the values
		GamePlayer AI = new GamePlayer();
		Board board = new Board();

		// Set depth (Selecting difficutly)
		System.out.print("Select the AI's difficulty (max depth, try not to exceed level 7): ");
		AI.setDepth(in.nextInt());
		in.nextLine();

		board.print(); //print starting board
		
		String answer;
		System.out.println("Do you want to go first or not?(Yes/No)");
		answer = in.nextLine();
		String wentFirst = answer;
		while( !answer.equalsIgnoreCase("Yes") && !answer.equalsIgnoreCase("No")){
			System.out.println("Please select one of the valid answers, 'Yes' or 'No'. ");
			answer = in.nextLine();
		}
		if(answer.equalsIgnoreCase("Yes")){
			AI.setLetter(Board.O);
			System.out.println("You have chosen to play as X while your opponent has O.");
		}
		else{
			AI.setLetter(Board.X);
			System.out.println("You have chosen to play as O while your opponent has X.");
		}
		int AILetter = AI.getPlayerletter(); //Letter the AI plays with. Can be either X or O (You get the other one)
		//While the game has not finished
		while(!board.isTerminal())
		{
			System.out.println();
			//If You played last, then AI plays now
			if(board.getLastLetterPlayed() == AILetter * (-1)){
				System.out.println("AI's turn");
				if(!board.canPlay(AILetter)){
					System.out.println("AI can't play, it's your turn again.");
					board.setLastLetterPlayed(AILetter);
					break;
				}
				Move AImove = AI.MiniMax(board); 
				board.makeMove(AImove.getRow(), AImove.getCol(), AILetter); //AI makes the move
				System.out.println("AI placed at (" + AImove.getRow() + ", " + AImove.getCol() + ")"); 
			}
			//If AI played last, then You play now
			else if( board.getLastLetterPlayed() == AILetter){
				System.out.println("Player's turn");
				if(!board.canPlay(AILetter)){
					System.out.println("You can't play, it's AI's turn again.");
					board.setLastLetterPlayed(AILetter * (-1)); //You make the move
					break;
				}
				System.out.print("Your Move. Insert it like ?,?: ");
				answer = in.nextLine();
				Move playerMove = new Move(Integer.parseInt(answer.trim().substring(0, 1)), Integer.parseInt(answer.trim().substring(2, 3)));
				while(answer.length()  != 3 || !board.isValidMove(playerMove.getRow(), playerMove.getCol(), AILetter * (-1)) ){
					System.out.print("Your move is invalid, try again: ");
					answer = in.nextLine();
					playerMove = new Move(Integer.parseInt(answer.trim().substring(0, 1)), Integer.parseInt(answer.trim().substring(2, 3)));
				}
				board.makeMove(playerMove.getRow(), playerMove.getCol(), AILetter * (-1)); //You make the move
				System.out.println("You placed at (" + playerMove.getRow() + ", " + playerMove.getCol() + ")");
			}
			board.print(); //print current board
			System.out.print("Press enter to continue.");
			in.nextLine();//Prompt
		}	
		//Match Result handling
		int result = board.evaluate();
		if (result < 0 && wentFirst.equalsIgnoreCase("Yes")){
			System.out.println("AI Wins!!!");
		}
		else if(result > 0 && wentFirst.equalsIgnoreCase("Yes")){
			System.out.println("You Win!!!");
		}
		else if(result == 0 && wentFirst.equalsIgnoreCase("Yes")){
			System.out.println("It's a Tie!!!");
		}
		else if(result < 0 && wentFirst.equalsIgnoreCase("No")){
			System.out.println("You Win!!!");
		}
		else if(result > 0 && wentFirst.equalsIgnoreCase("No")){
			System.out.println("AI Wins!!!");
		}
		else if(result == 0 && wentFirst.equalsIgnoreCase("No")){
			System.out.println("It's a Tie!!!");
		}
	}
}
