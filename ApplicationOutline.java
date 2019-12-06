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
					Player player1 = new Player(1); 
					playerList.add(player1); 
					if (numPlayers >= 2) {
						Player player2 = new Player(2); 
						playerList.add(player2); 
					}
					if (numPlayers >= 3) {
						Player player3 = new Player(3); 
						playerList.add(player3);
					}
					if (numPlayers == 4) {
						Player player4 = new Player(4); 
						playerList.add(player4); 
					}
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
			gameloop:
			while (inGame){
				//set the button to game buttons
				System.out.println(playerTurn);
				while (turn){
					
					if (first){
						io.startGame();
						io.loadGame();
						first = false;
					}
					
					updateLongestRoad(playerList, board);

					//check if a player has won
					for (int i = 0; i < numPlayers; i++){
						if(playerList.get(i).calculateVictoryPoints() >= 10) {
							inGame = false;
							endGame = true;
							break gameloop;
						}
					}
					
					//gets the action of the player
					int action = io.getInGameAction();
					
					//build settlement
					if (action == 1){
						if(playerList.get(playerTurn).getMaxSettlements() > 0){
							buildSettlement(playerList.get(playerTurn), io, board, false);	
						} else {
							System.out.println("Player " + (playerTurn + 1) + " is out of settlements!"); 
						}	
					}
					
					//build road
					if (action == 2){
						if(playerList.get(playerTurn).getMaxRoads() >0){
							buildRoad(playerList.get(playerTurn), io, board, true);		
						} else {
							System.out.println("Player " + (playerTurn + 1) + " is out of roads!"); 
						}
					}
					
					//upgrade Settlement
					if (action == 3){
						if(playerList.get(playerTurn).getMaxCities() > 0){
							upgradeSettlementToCity(playerList.get(playerTurn), io, board);
						} else {
							System.out.println("Player " + (playerTurn + 1) + " is out of cities!"); 
						}
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
	

	public static void updateLongestRoad(ArrayList<Player> playerList, Board board){
		for (int i = 0; i < playerList.size(); i++){
			playerList.get(i).setHasLongestRoad(false);
			playerList.get(i).setHasLargestArmy(false);
		}
		Player p = findPlayerWithLongestRoad(board, playerList);
		if (p!=null) p.setHasLargestArmy(true);
	}

	public static void buildSettlement(Player p, IO io, Board board, boolean mustConnect){
		if (p.buildOrBuyDevelopmentCard("Settlement"))
		{
			io.setVertexLocations(getVertexAvailabilityArray(board, p, mustConnect));
			io.loadBoard();
			int vertex = getVertexLocation(io);
			if (vertex==-1) return;
			//-1 from vertex location means they canceled their selection
			//for loop of the boolean array to get which specific vertex was chosen
			io.resetVertexArray();
			io.loadBoard();
			int count = 1;
			outerloop:
			for(Vertex[] vRow : board.getVertexArray()){
				for(Vertex v : vRow){
					if (v.getOccupant()==null && !v.hasNeighboringSettlement()){
						if (mustConnect){
							if (v.playerIsConnected(p)){
								if (count==vertex){
									v.setOccupant(p);
									v.setRollMultiplier(1);
									io.setSettlement(v.x, v.y, p.number);
									break outerloop;
								}
								else{
									count++;
									continue;
								}
							}
						}
						else{
							if (count==vertex){
								v.setOccupant(p);
								io.setSettlement(v.x, v.y, p.number);
								v.setRollMultiplier(1);
								break outerloop;
							}
							else{
								count++;
							}
						}
					}
				}
			}
		}	
	}

	public static void upgradeSettlementToCity(Player p, IO io, Board board){
		if (p.buildOrBuyDevelopmentCard("City"))
		{
			io.setVertexLocations(getCityAvailabilityArray(board, p));
			io.loadBoard();
			int vertex = getVertexLocation(io);
			if (vertex==-1) return;
			//-1 from vertex location means they canceled their selection
			//for loop of the boolean array to get which specific vertex was chosen
			io.resetVertexArray();
			io.loadBoard();
			int count = 1;
			outerloop:
			for(Vertex[] vRow : board.getVertexArray()){
				for(Vertex v : vRow){
					if (v.getRollMultiplier()==1 && v.getOccupant().equals(p)){
						if (count==vertex){
							v.setRollMultiplier(2);
							io.setSettlement(v.x, v.y, p.number);
							break outerloop;
						}
						else count++;
					}
				}
			}
		}
	}

	public static void buildRoad(Player p, IO io, Board board, boolean mustConnect){
		if (p.buildOrBuyDevelopmentCard("Road"))
		{
			io.setRoadLocations(getRoadAvailabilityArray(board, p, mustConnect));
			io.loadBoard();
			int road = getRoadLocation(io);
			if (road==-1) return;		
			//-1 from road location means they canceled their selection
			//for loop of the boolean array to get which specific vertex was chosen
			io.resetRoadArray();
			io.loadBoard();
			int count = 1;
			outerloop:
			for(Road[] rRow : board.getRoadArray()){
				for(Road r : rRow){
					if (r.getOccupant()==null){
						if (mustConnect){
							if (r.playerIsConnected(p)){
								if (count==road){
									r.setOccupant(p);
									io.setRoad(r.x, r.y, p.number);
									break outerloop;
								}
								else{
									count++;
									continue;
								}
							}
						}
						else{
							if (count==road){
								r.setOccupant(p);
								io.setRoad(r.x, r.y, p.number);
								break outerloop;
							}
							else{
								count++;
							}
						}
					}
				}
			}
		}	
	}

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
				if (i > 0 || i== -1)
					return i;
				else
					io.errorMessage("ERROR: Vertex number must positive");
			}
			catch (NumberFormatException e){
				io.errorMessage("ERROR: Please enter a valid integer");
			}
		}
	}
	
	public static int getRoadLocation(IO io){
		while (true){
			try{
				int i = io.getIntegerInput("Enter the number of the road location");
				if (i > 0 || i== -1)
					return i;
				else
					io.errorMessage("ERROR: Vertex number must positive");
			}
			catch (NumberFormatException e){
				io.errorMessage("ERROR: Please enter a valid integer");
			}
		}
	}
	
	public static boolean [][] getVertexAvailabilityArray (Board board, Player p, boolean mustConnect){
		Vertex[][] vertices = board.getVertexArray();
		boolean [][] availabilityArray = new boolean[6][];
        int[] availabilityArrayRowLengths = {7,9,11,11,9,7};
        for(int i=0; i<availabilityArray.length; i++){
            boolean[] row = new boolean[availabilityArrayRowLengths[i]];
            for(int j=0; j<availabilityArrayRowLengths[i]; j++){
				row[j] = (vertices[i][j].getOccupant() == null && !vertices[i][j].hasNeighboringSettlement());
				if (mustConnect)
					row[j] = (row[j] && vertices[i][j].playerIsConnected(p));
			}
			availabilityArray[i] = row;
        }
		return availabilityArray; 
	}

	public static boolean [][] getRoadAvailabilityArray (Board board, Player p, boolean mustConnect){
		Road[][] roads = board.getRoadArray();
		boolean [][] availabilityArray = new boolean[11][];
        int[] availabilityArrayRowLengths = {6, 4, 8, 5, 10, 6, 10, 5, 8, 4, 6};
        for(int i=0; i<availabilityArray.length; i++){
            boolean[] row = new boolean[availabilityArrayRowLengths[i]];
            for(int j=0; j<availabilityArrayRowLengths[i]; j++){
				row[j] = (roads[i][j].getOccupant() == null);
				if (mustConnect)
					row[j] = (row[j] && roads[i][j].playerIsConnected(p));					
			}
			availabilityArray[i] = row;
        }
		return availabilityArray; 
	}
	
	public static boolean [][] getCityAvailabilityArray (Board board, Player p){
		Vertex[][] vertices = board.getVertexArray();
		boolean [][] availabilityArray = new boolean[6][];
        int[] availabilityArrayRowLengths = {7,9,11,11,9,7};
        for(int i=0; i<availabilityArray.length; i++){
            boolean[] row = new boolean[availabilityArrayRowLengths[i]];
            for(int j=0; j<availabilityArrayRowLengths[i]; j++){
				row[j] = (vertices[i][j].getRollMultiplier() == 1);
			}
			availabilityArray[i] = row;
		}
		return availabilityArray;
	}
	
	
	public static int biggestArmy(Player[] p,int f) {
		int currentBiggest=0; //The player with the biggest army
		int toBeat = 0;//the number of knight cards needed for a player to get the biggest army
			if(f!=0) { //If someone already has the largest army
				ArrayList <Character> beatDevCards = p[f-1].getDevelopmentCards(); 
				currentBiggest=f;
					for(int j=0;j<beatDevCards.size();j++) { //Finds all knights in player's devcards
						if(beatDevCards.get(j)=='K') {
							toBeat++; 
						}
					}
			}//end if
			
			int[] sum = new int[p.length]; //array to hold the sum of knight cards for all players
			for(int i=0;i<p.length;i++) {//goes through all players
				ArrayList <Character> devCards = p[i].getDevelopmentCards(); //Gets the development cards of current player
				
				for(int j=0;j<devCards.size();j++) { //Finds all knights in player's devcards
					if(devCards.get(j)=='K') {
						sum[i]++;
					}
				}//end inner for
			}//end for
			for(int k=0;k<p.length;k++) { 
				if((sum[k]>toBeat)&&(sum[k]>=3)) { //Changes current biggest if the player has more knight cars than toBeat and more than 3
					currentBiggest=k+1;
				}
			}
			return currentBiggest;
		}//end biggestArmy
	
		public static Player findPlayerWithLongestRoad(Board board, ArrayList<Player> players){
			Player plrWithLongestRoad=null;
			int winningLength = 0;
			for (Player p : players){
				int length = board.findPlayersLongestRoad(p);
				if (length > winningLength){
					plrWithLongestRoad = p;
					p.setHasLongestRoad(true);
					winningLength = length;
				} else {
					p.setHasLongestRoad(false); 
				}
			}
			return plrWithLongestRoad;
		}

}//end class


