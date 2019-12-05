import java.util.*;

public class ApplicationOutline{
	public static void main(String[] args){
		//initialize array/arraylist of players
		ArrayList<Player>playerList = new ArrayList<Player>(4); 
		
		//initialize the board
		Board board = new Board();
		Tile[][] tiles = board.getTiles();
		
		//load the inital launch and home
		IO io = new IO();
		io.loadHome();
		
		//important variables
		int numPlayers = 0;
		
		//set up booleans for loops
		boolean homeScreen = true;
		boolean newGame = false;
		boolean inGame = false;
		boolean endGame = false;
		while (true){
			//loop for the homeScreen
			while (homeScreen){
				int action = io.getHomeAction();
				//gets if the user wants to start a new game
				if (action == 1){
					io.newGame(tiles);
					io.loadGame();
					homeScreen = false;
					newGame = true;
					System.out.println("newGame");
				}
			}//end while homeScreen
			
			//loop for generating a new game
			while (newGame){
				//gets the number of players
				if (numPlayers == 0){
					numPlayers = getNumberOfPlayers(io);
				}
				
				//adding player objects to the array list
				Player player1 = new Player(); 
				playerList.add(player1); 
				if (numPlayers >= 2) {
					Player player2 = new Player(); 
					playerList.add(player2); 
				}
				if (numPlayers >= 3) {
					Player player3 = new Player(); 
					playerList.add(player3);
				}
				if (numPlayers == 4) {
					Player player4 = new Player(); 
					playerList.add(player4); 
				}
				
				//gets the action of the player
				int action = io.getNewGameAction();
				
				//player wants to make a new board
				if (action == 1){
					board = new Board();
					tiles = board.getTiles();
					io.newBoard(tiles);
				}
				//wants to start the game
				else if (action == 2){
					newGame = false;
					inGame = true;
					System.out.println("starting game");
				}
			}//end while newGame
			
			//set up variables for turn logic
			int playerTurn = 0;
			boolean turn = true;
			//first time set
			boolean first = true;
			//loop for inGame
			while (inGame){
				//set the button to game buttons
				System.out.println(playerTurn);
				while (turn){
					if (first){
						io.startGame();
						io.loadGame();
						first = false;
					}
					
					//check if a player has won
					for (int i = 0; i < numPlayers; i++){
						if(playerList.get(i).getVictoryPoints() == 10) {
							inGame = false;
							endGame = true;
						}//end if 
					}//end for if
					
					//gets the action of the player
					int action = io.getInGameAction();
					
					//build settlement
					if (action == 1){
					/*
						boolean of what vertices that a settlement can be placed on 
						io.showAvailableVertices(boolean array variable)
					*/
						int vertex = getVertexLocation(io);
						//-1 from vertex location means they canceled their selection
						//for loop of the boolean array to get which specific vertex was chosen
						
					}
					
					//build road
					if (action == 2){
					/*
						boolean array of where roads can be placed
						io.showAvailableRoads(boolean array variable)
					*/
						int vertex = getRoadLocation(io);
						//for loop of the boolean array to get which specific vertex was chosen
						
					}
					
					//upgrade Settlement
					if (action == 3){
					/*
						boolean array of where current settlements are
						io.showAvailableVertices(boolean array variable)
					*/
						int vertex = getVertexLocation(io);
						//for loop of the boolean array to get which specific vertex was chosen
					}
					
					//development cards 
					if (action == 4){
						//Coby need's to work on this 
					}
					
					//end turn 
					if (action == 5){
						turn = false;
					}
				}//end while turn
				playerTurn++;
				if (playerTurn == numPlayers)
					playerTurn = 0;
				turn = true;
			}//end inGame
			
			while (endGame){
				int action = io.getEndGameAction();
				
				//back to homeScreen
				if (action == 1){
					io.backToHome();
					endGame = false;
					homeScreen = true;
				}
			}
		}//end main while true
	}//end main method
	
	//gets the number of players via JOptionPane
	public static int getNumberOfPlayers(IO io){
		while (true){
			try{
				int i = io.getIntegerInput("enter the number of players (2-4)");
				if (i >= 2 && i <= 4)
					return i;
				else
					io.errorMessage("ERROR: Number of players must be 2-4");
			}
			catch (NumberFormatException e){
				io.errorMessage("ERROR: Please enter a valid number of players");
			}
		}//end while true
	}//end getNumberOfPlayers
	
	public static int getVertexLocation(IO io){
		while (true){
			try{
				int i = io.getIntegerInput("Enter the number of the vertex");
				if (i > -1)
					return i;
				else
					io.errorMessage("ERROR: Number of players must Positive");
				//should this be vertex number should be positive?
			}
			catch (NumberFormatException e){
				io.errorMessage("ERROR: Please enter a valid integer");
			}
		}
	}
	
	public static int getRoadLocation(IO io){
		while (true){
			int i = io.getIntegerInput("Enter the number of the Road location");
			if (i > 0)
				return i;
		}
	}
	
}//end class