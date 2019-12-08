/* Application.java
 * Contains the main function which loops
 * with a UI and many game logic features. Manipulates arrays
 * of Players, Tiles, Vertices, and more while communicating 
 * through an IO class to a UI, to implement turn logic featured
 * in the popular board game Settlers of Catan. 
 */

import java.util.*;

public class ApplicationOutline{
	
	public static void main(String[] args){
		
		Board board = new Board(); // initialize the board
		Tile[][] tiles = board.getTiles();
		
		IO io = new IO(); // initialize IO
		io.loadHome();
		
		//important variables
		int numPlayers = 0;
		ArrayList<Player>playerList = new ArrayList<Player>(4); //initialize array/arraylist of players
		//set up booleans for loops
		boolean homeScreen = true;
		boolean newGame = false;
		boolean inGame = false;
		boolean endGame = false;


		/* Game Logic Loop */

		while (true){
			while (homeScreen){ //loop for the homeScreen
				int action = io.getHomeAction(); //UI waits for user to start a game
				if (action == 1){
					io.newGame(tiles);
					io.loadGame();
					homeScreen = false;
					newGame = true;
				}
			}//end while homeScreen
			
			
			while (newGame){ //loop for generating a new game
				int action = io.getNewGameAction(); //gets the action of the player from UI

				if (action == 1){ //player wants to make a new board
					board = new Board();
					tiles = board.getTiles();
					io.newBoard(tiles);
				}
				
				else if (action == 2){ //wants to start the game
					newGame = false;
					inGame = true;
					if (numPlayers == 0)
						numPlayers = initializePlayers(playerList, io, board);
				}
			}//end pre-game UI loop
			
			//set up variables for turn logic
			int playerTurn = 0;
			boolean turn = true;
			boolean first = true;

			gameloop:
			while (inGame){ // game logic loop
			
			
				System.out.println("inGame");
			
				/* Phase 1: Roll and distribute resources */
				int roll = rollDie() + rollDie();
				System.out.println("3");
				if (roll==7){ // rob players with more than 7 resources on dice roll 7
					for (Player p : playerList){
						Robber.cutHand(p);
					}
				}
				System.out.println("1");
				distributeResources(roll, board);
				System.out.println("2");				
				io.loadBoard();

				/* Phase 2: Allow user to spend resources */
				while (turn){
					
					System.out.println("turn");
					
					if (first){
						io.startGame();
						io.loadGame();
						first = false;
					}

					updateResources(playerList.get(playerTurn), board, io); // keep resource panel up to date.
					updateLongestRoad(playerList, board); // recalculate longest road.
					updateLargestArmy(playerList);
					io.setTurnInfo(playerTurn, playerList.get(playerTurn).calculateVictoryPoints(), roll); //display victory points, current player, and dice
					

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
						buildSettlement(playerList.get(playerTurn), io, board, true, false);	
					}
					
					//build road
					if (action == 2){
						buildRoad(playerList.get(playerTurn), io, board, true, false);		
					}
					
					//upgrade Settlement
					if (action == 3){
						upgradeSettlementToCity(playerList.get(playerTurn), io, board);
					}
					
					//development cards 
					if (action == 4){

						ArrayList<Character> cards = playerList.get(playerTurn).getDevelopmentCards();						
						
						DevelopmentCards dv = new DevelopmentCards(cards);
						boolean looking = true;
						while (looking){
							Player p = playerList.get(playerTurn);
							int cardAction = dv.getAction();
							
							//use Knight
							if (cardAction == 1){
								//close window and exit loop
								for (Character C : cards){
									if (C=='K') {
										cards.remove(C);
										break;
									}
								}
								looking = false;
								dv.dispose();
							}
							//use monopoly card
							else if (cardAction == 2){
								//close window and exit loop
								looking = false;
								for (Character C : cards){
									if (C=='M') {
										cards.remove(C);
										break;
									}
								}
								dv.dispose();
							}
							//use Year of Plenty
							else if (cardAction == 3){
								int resource1 = io.getIntegerInput("Select a free resource:\n1)Brick\n2)Lumber\n3)Ore\n4)Grain\n5)Wool");
								p.getPlayerResources()[resource1-1]++;
								updateResources(p, board, io);
								int resource2 = io.getIntegerInput("Select a free resource:\n1)Brick\n2)Lumber\n3)Ore\n4)Grain\n5)Wool");
								p.getPlayerResources()[resource2-1]++;
								updateResources(p, board, io);
								for (Character C : cards){ // remove the card from the players inventory
									if (C=='P') {
										cards.remove(C);
										break;
									}
								}
								looking = false;
								dv.dispose();
							}
							//road building
							else if (cardAction == 4){
								dv.dispose();
								buildRoad(p, io, board, true, true); // 2 free roads
								buildRoad(p, io, board, true, true);
								for (Character C : cards){ // remove the card from the players inventory
									if (C=='R') {
										cards.remove(C);
										break;
									}
								}
								looking = false;								
							}
							//buy dev Card
							else if (cardAction == 6){
								if (!p.buildOrBuyDevelopmentCard("Buy a Development Card", false, board.getDeck())){
									io.errorMessage("You don't have the resources to buy a card.");
								};
								updateResources(p,board,io);
								dv.updateCardsPanel(p.getDevelopmentCards()); // display the players cards up to date
								
							}
							//demo give cards
							else if (cardAction == 7){
								//give the cards
								cards.add('R'); // for demonstration - free dev cards
								cards.add('V');
								cards.add('P');
								cards.add('K');
								cards.add('K');
								cards.add('K');
								dv.updateCardsPanel(p.getDevelopmentCards());
							}
							//close window
							else if (cardAction == 5){
								//close window and exit loop
								looking = false;
								dv.dispose();
							}
						} 
					}
					
					//action == end turn
					if (action == 5){
						turn = false;
					}
					
					System.out.println("turn");

					
				} //end of turn loop

				
				playerTurn++; //cycle through players
				if (playerTurn == numPlayers)
					playerTurn = 0;
				turn = true; //restart turn loop
				
				
				System.out.println("inGame");
			}//end inGame
			
			while (endGame){ // someone has reached 10 VP
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








	/* Game Logic Methods Below */

	/** rollDie
	 * Roll a 6 sided die and return the random value.
	 * @return random integer 1-6
	 */
	public static int rollDie(){
		Random r = new Random();
		return r.nextInt(6)+1;
	}

	/** initializePlayers
	 * Prompt the user for the number of players.
	 * Create an ArrayList of Player objects accordingly
	 * then allow the Players to place their initial settlements/roads.
	 * @param playerList ArrayList of Player objects to be returned to main loop for further use.
	 * @param io IO object that allows communication to UI.
	 * @param board Board object storing the data necessary for game logic.
	 * @return int number of players in the newly generated list.
	 */
	public static int initializePlayers(ArrayList<Player> playerList, IO io, Board board){
		int numPlayers = getNumberOfPlayers(io);
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
		placeInitialBuildings(playerList, io, board);
		return numPlayers;
	}

	/** distributeResources
	 * Loop through all the vertices in the board and call the method
	 * that distributes resources based on the dice roll.
	 * @param roll integer dice roll value
	 * @param board Board object storing the data necessary for game logic.
	 */
	public static void distributeResources(int roll, Board board){
		Vertex[][] vertices = board.getVertexArray();
		for (Vertex[] vRow : vertices){
			for(Vertex v : vRow){
				v.distributeResources(roll);
			}
		}		
	}

	/** updateResources
	 * Update the panel of the UI that displays the resources of the current player.
	 * @param p current player - display their resources
	 * @param board Board object storing the data necessary for game logic.
	 * @param io IO object that allows communication to UI.
	 */
	public static void updateResources(Player p, Board board, IO io){
		ArrayList<Integer> resources = new ArrayList<Integer>(5);
		for(int i : p.getPlayerResources()){
			resources.add(i);
		}
		io.setResourcePanel(resources);
		io.loadBoard();
	}

	/** placeInitialBuildings
	 * Place 2 settlements and 2 roads for each player for free.
	 * Loop through players forward once, then backwards.
	 * @param playerList ArrayList of Player objects to be returned to main loop for further use.
	 * @param io IO object that allows communication to UI.
	 * @param board Board object storing the data necessary for game logic.
	 */
	public static void placeInitialBuildings(ArrayList<Player> playerList, IO io, Board board){
		for (int i=0; i<playerList.size(); i++){
			io.setTurnInfo(i, playerList.get(i).calculateVictoryPoints(), 0);
			buildSettlement(playerList.get(i), io, board, false, true);
			buildRoad(playerList.get(i), io, board, true, true);
		}
		for (int i=playerList.size()-1; i>=0; i--){
			io.setTurnInfo(i, playerList.get(i).calculateVictoryPoints(), 0);
			buildSettlement(playerList.get(i), io, board, false, true);
			buildRoad(playerList.get(i), io, board, true, true);
		}
		io.setTurnInfo(0, playerList.get(0).calculateVictoryPoints(), 0);
		distributeResources(-1, board);

	}

	/** updateLongestRoad
	 * Check for a new longest road by looping thorugh players. Update the boolean in the
	 * Player with the longest road to "true", and the others to "false".
	 * @param playerList ArrayList of Player objects to be returned to main loop for further use.
	 * @param board Board object storing the data necessary for game logic.
	 */
	public static void updateLongestRoad(ArrayList<Player> playerList, Board board){
		for (int i = 0; i < playerList.size(); i++){
			playerList.get(i).setHasLongestRoad(false);
		}
		Player p = findPlayerWithLongestRoad(board, playerList);
		if (p!=null) p.setHasLongestRoad(true);
	}

	/** updateLargestArmy
	 * Check for a new largest army by looping thorugh players. Update the boolean in the
	 * Player with the largest army to "true", and the others to "false".
	 * @param playerList ArrayList of Player objects to be returned to main loop for further use.
	 * @param board Board object storing the data necessary for game logic.
	 */
	public static void updateLargestArmy(ArrayList<Player> playerList){
		for (int i = 0; i < playerList.size(); i++){
			playerList.get(i).setHasLargestArmy(false);
		}
		Player p = findPlayerWithLargestArmy(playerList);
		if (p!=null) p.setHasLargestArmy(true);
	}

	/** buildSettlement
	 * Build a settlement by displaying available veritces with numbers on the UI
	 * then prompting the user to select one and checking if the build is valid.
	 * @param p Player who is trying to build
	 * @param io IO object that allows communication to UI.
	 * @param board Board object storing the data necessary for game logic.
	 * @param mustConnect determines whether the Vertex must be connected to previous buildings - only case when it mustn't is initial placements.
	 * @param isFree removes resource cost - this is for initial placements when the user has no resources
	 */
	public static void buildSettlement(Player p, IO io, Board board, boolean mustConnect, boolean isFree){
		if (p.getMaxSettlements()<=0){
			io.errorMessage("Player " + (p.getNumber()+1) + " is out of settlements!"); 
			return;
		}
		if (p.buildOrBuyDevelopmentCard("Settlement", isFree, board.getDeck())){
			io.setVertexLocations(getVertexAvailabilityArray(board, p, mustConnect));
			io.loadBoard();
			int vertex=-1;
			do{
				vertex = getVertexLocation(io);
			} while (vertex==-1);
			io.resetVertexArray();
			io.loadBoard();
			//-1 from vertex location means they canceled their selection
			//for loop of the boolean array to get which specific vertex was chosen
			
			int count = 1;
			outerloop:
			for(Vertex[] vRow : board.getVertexArray()){
				for(Vertex v : vRow){
					if (v.getOccupant()==null && !v.hasNeighboringSettlement()){
						if (mustConnect){
							if (v.playerIsConnected(p)){
								if (count==vertex){
									v.setOccupant(p);
									p.addSettlementLocation(v); 
									v.setRollMultiplier(1);
									io.setSettlement(v.x, v.y, p.getNumber());
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
								io.setSettlement(v.x, v.y, p.getNumber());
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
		else io.errorMessage("You cannot afford to build a settlement. This building costs 1 lumber, 1 brick, 1 wool, and 1 grain.");
			
	}

	/** upgradeSettlementToCity
	 * Upgrade a settlement to a city by displaying available veritces with numbers on the UI
	 * then prompting the user to select one and checking if the build is valid.
	 * @param p Player who is trying to build
	 * @param io IO object that allows communication to UI.
	 * @param board Board object storing the data necessary for game logic.
	 */
	public static void upgradeSettlementToCity(Player p, IO io, Board board){
		if(p.getMaxCities() <= 0){
			io.errorMessage("Player " + (p.getNumber()+1) + " is out of cities!");
			return;
		}
		if (p.buildOrBuyDevelopmentCard("City", false, board.getDeck()))
		{
			io.setVertexLocations(getCityAvailabilityArray(board, p));
			io.loadBoard();
			int vertex = -1;
			do{
				vertex = getVertexLocation(io);
			}while (vertex==-1);
			io.resetVertexArray();
			io.loadBoard();
			//-1 from vertex location means they canceled their selection
			//for loop of the boolean array to get which specific vertex was chosen
			
			int count = 1;
			outerloop:
			for(Vertex[] vRow : board.getVertexArray()){
				for(Vertex v : vRow){
					if (v.getRollMultiplier()==1 && v.getOccupant().equals(p)){
						if (count==vertex){
							v.setRollMultiplier(2);
							p.addCityLocation(v); 
							io.setSettlement(v.x, v.y, p.getNumber());
							break outerloop;
						}
						else count++;
					}
				}
			}
		}
		else io.errorMessage("You cannot afford to build a city. This building costs 2 grain and 3 ore.");
	}

	/** buildRoad
	 * Build a road by displaying available veritces with numbers on the UI
	 * then prompting the user to select one and checking if the build is valid.
	 * @param p Player who is trying to build
	 * @param io IO object that allows communication to UI.
	 * @param board Board object storing the data necessary for game logic.
	 * @param mustConnect determines whether the Vertex must be connected to previous buildings - only case when it mustn't is initial placements.
	 * @param isFree removes resource cost - this is for initial placements and Road Building dev card
	 */
	public static void buildRoad(Player p, IO io, Board board, boolean mustConnect, boolean isFree){
		if (p.getMaxRoads()<=0){
			io.errorMessage("Player " + (p.getNumber()+1) + " is out of roads!"); 
			return;
		}
		if (p.buildOrBuyDevelopmentCard("Road", isFree, board.getDeck()))
		{
			io.setRoadLocations(getRoadAvailabilityArray(board, p, mustConnect));
			io.loadBoard();
			int road = -1;
			do{
				road = getRoadLocation(io);
			} while (road==-1);
			io.resetRoadArray();
			io.loadBoard();
			//-1 from road location means they canceled their selection
			//for loop of the boolean array to get which specific vertex was chosen
			
			int count = 1;
			outerloop:
			for(Road[] rRow : board.getRoadArray()){
				for(Road r : rRow){
					if (r.getOccupant()==null){
						if (mustConnect){
							if (r.playerIsConnected(p)){
								if (count==road){
									r.setOccupant(p);
									p.addRoad(r); 
									io.setRoad(r.x, r.y, p.getNumber());
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
								p.addRoad(r); 
								io.setRoad(r.x, r.y, p.getNumber());
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
		else io.errorMessage("You cannot afford to build a road. This upgrade costs 1 lumber and 1 brick.");	
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
	
	// gets the users integer selection from UI prompt
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
	
	// gets the users integer selection from UI prompt
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
	
	/** getVertexAvailabilityArray
	 * Loops through Vertex 2D array of board and creates a boolean array of the same shape
	 * Each boolean tells us whether Player p is able to build there. This will tell us
	 * where we can indicate availability on the UI.
	 * @param board Board object storing the data necessary for game logic.
	 * @param p Player who is trying to build
	 * @param mustConnect determines whether the Vertex must be connected to previous buildings - only case when it mustn't is initial placements.
	 * @return 2D array of booleans corresponding to 2D array in board
	 */
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

	/** getRoadAvailabilityArray
	 * Loops through Road 2D array of board and creates a boolean array of the same shape
	 * Each boolean tells us whether Player p is able to build there. This will tell us
	 * where we can indicate availability on the UI.
	 * @param board Board object storing the data necessary for game logic.
	 * @param p Player who is trying to build
	 * @param mustConnect determines whether the Road must be connected to previous buildings - only case when it mustn't is initial placements.
	 * @return 2D array of booleans corresponding to 2D array in board
	 */
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
	
	/** getRoadAvailabilityArray
	 * Loops through Vertex 2D array of board and creates a boolean array of the same shape
	 * Each boolean tells us whether Player p is able to upgrade the settlement at the corresponding vertex in the Vertex[][]. 
	 * This will tell us where we can indicate availability on the UI.
	 * @param board Board object storing the data necessary for game logic.
	 * @param p Player who is trying to build
	 * @return 2D array of booleans corresponding to 2D array in board
	 */
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
	
	/** findPlayerWithLongestRoad
		 * Loops through players calling methods to determine the size of their army. 
		 * Returns the Player object with the largest army.
		 * @param board Board object storing the data necessary for game logic.
		 * @param players List of our players
		 * @return Player with the largest army.
		 */
	public static Player findPlayerWithLargestArmy(ArrayList<Player> playerList){
		Player winningPlayer = null;
		int winningArmySize = 0;
		for(Player p : playerList){
			ArrayList<Character> cards = p.getDevelopmentCards();
			int armySize = 0;
			for (Character c : cards){
				if (c=='K') armySize++;
			}
			if (armySize>winningArmySize && armySize >= 3){
				winningPlayer = p;
				winningArmySize = armySize;
			}
		}
		return winningPlayer;
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
	
		/** findPlayerWithLongestRoad
		 * Loops through players calling methods to determine the length of their longest road. 
		 * Returns the Player object with the longest segment.
		 * @param board Board object storing the data necessary for game logic.
		 * @param players List of our players
		 * @return Player with the longest segment
		 */
		public static Player findPlayerWithLongestRoad(Board board, ArrayList<Player> players){
			Player plrWithLongestRoad=null;
			int winningLength = 0;
			for (Player p : players){
				int length = board.findPlayersLongestRoad(p);
				if (length > winningLength && length >=5){
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


