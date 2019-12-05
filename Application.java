import javax.swing.*; 
import java.awt.*; 

public class Application{
    public static void main(String[] args){
        Board board = new Board();
        Tile[][] tiles = board.getTiles();// every other method in Board.java is internal
		IO io = new IO(tiles);
	int currentBiggestArmy=0; //Holds the player number who holds the biggest army
	}
		//-----------------------------------------------------------------------------------------------------------
		//BEGINNING OF ABBY'S CODE
		
		//Important -- if any attributes of player change, alert IO!!!
		
		public int getNumFromUser(){
			JFrame start = new JFrame(); 
			try{
				int numPlayers = Integer.parseInt(JOptionPane.showInputDialog(start, "Please enter the number of players (2-4):")); 
				if(numPlayers < 2 || numPlayers > 4){
					throw new NumberFormatException(); 
				}
			} catch (NumberFormatException e){
				JOptionPane.showMessageDialog(start, "You must enter an integer between 2-4."); 
				return -1;  
			}
			return numPlayers;
		}
		
		public int getNumPlayers(){
			int numPlayers = 0; 
			do{
				numPlayers = getNumFromUser(); 
			} while(numPlayers == -1);
			return numPlayers;
		}
			
		//have each player place 2 roads and 2 settlements on the board 
		
		//TURNS: 
		//roll for resource production (roll the 2 dice)
			//if player has settlement on intersection that borders producing terrain hex, receive 1 resource of hex's type
			//if you have 2-3 settlements bordering the hex, you receive 1 resource card for each settlement
			//2 resource cards for each city bordering a hex
			//if 7 is rolled, nobody receives resources, people with more than 7 cards must get rid of half of cards (returned to bank)
				//move robber, steal resource from opponent who has settlement or city adjascent to the target terrain hex
		
		//trade?? (low priority)
		
		//build roads, settlements, or cities and/or buy development cards
			//call build method in player class (fix/modify that method so it allows placement)
			//building settlements
				//find vertices where settlements (and city upgrades) can be placed! (pass boolean 2d array to IO)
					//will receive integer corresponding to the vertex that was chosen
			//MAX OF 5 SETTLEMENTS, 4 CITIES, 15 ROADS (INCORPORATE THIS IN PLAYER CLASS)
			
		//option to play one development card at any time during turn
			//knight -- must move robber (first player w/ 3 knights has largest army) 
			//ask group about progress card
			//vp card
			//monopoly (returns int, is passed a string) --> uses JOptionPane to display which resource you'd like to steal
		//when player is finished, pass the dice to next player, who goes through these steps
		
		
		
		
		
		//public int rollDice()
		
		//Calculates the biggest army, should be called after a knight card is drawn
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
	
		//public boolean hasLongestRoad()
    
}
