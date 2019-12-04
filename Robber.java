/**Robber.java
 * Robber class with a variable to hold position and methods 'getPosition' to return the current position of the robber,
 * 'setPosition' to change the position of the robber, 'moveResources' to allow transfer of stolen resources, and 'blockResources;'
 * to keep resources from being given out if the robber is on a certain tile.
 * 
 */
import java.util.*;
public class Robber {

	private Tile position; //Current position of robber
	
	public Robber(Tile t) {
		position=t;		
	}
	
	public Tile getPosition() {
		return position;
	}
	
	public void setPosition(Tile newPosition) {
		position = newPosition;
	}
	
	public void moveRobber(Tile newPosition){
        position.setTileRollNumber(position.getTileRollNumberCache());

        newPosition.setTileRollNumberCache(newPosition.getTileRollNumber());
        newPosition.setTileRollNumber(0);

        setPosition(newPosition);
    }	


/** moveResources
 * Allows transfer of "robbed" resources
 * @param p1- The player being robbed
 * @param p2- the player getting resources
 */
	public static void moveResources(Player p1,Player p2) { //brick, lumber, ore, grain, and wool
		Random r = new Random();
		boolean moreThanZero = true; 
		int randomIndex; 
		
		int [] player1 = p1.getPlayerResources(); 
		int [] player2 = p2.getPlayerResources(); 
		
		do{
			randomIndex = r.nextInt(player1.length); //Gets a random card index from player1
			if(player1[randomIndex] <= 0){
				moreThanZero = false; 
			} else {
				moreThanZero = true; 
			}
		}while(moreThanZero == false); //Resource cannot be taken if player doesn't have any of that resource
		 
		
		player1[randomIndex]--;//removes resource from player 1
		player2[randomIndex]++; //adds resource to player 2
		
		p1.setPlayerResources(player1); 
		p2.setPlayerResources(player2); 
	}
	

	
	 /** cutHand
	 * Takes half of a player's hand if they have 8 or more resource cards when the robber is rolled
	 * @param p1- The player who's hand is being changed
	 */
	public static void cutHand(Player p1) {
		Random r = new Random();
			 
			
		int [] player = p1.getPlayerResources();
		int sum = 0; //sum of resource cards
		int take=0; //how many cards to take
			
		for (int i=0;i<player.length;i++) { //Adds up all resource cards
			sum+=player[i];
		}
			
		if(sum>7) {
			take=sum/2; //Will round down like rules require
				
			for(int i=0;i<take;i++) { //Takes out as many resources as necessary
				int randomResource = r.nextInt(4);
					
					while (player[randomResource]==0) { //loops until resource is one the player has
						randomResource = r.nextInt(4);
					}
					
				player[randomResource]--;
			}
		}//end if
			
		p1.setPlayerResources(player);
	}
}
