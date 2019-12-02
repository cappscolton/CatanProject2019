/* Robber.java
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
		


/* moveResources
 * Allows transfer of "robbed" resources
 * @param p1- The player being robbed
 * @param p2- the player getting resources
 */

	public static Player[] moveResources(Player p1,Player p2) { //brick, lumber, wool, grain, and ore
		Random r = new Random();
		
		ArrayList<Integer> player1 = p1.getArr(); //Getting the current player resources (this assumes a method 'getArr' in player class)
		ArrayList<Integer> player2 = p2.getArr();
		
		int card = r.nextInt(player1.size()); //Gets a random card index from player1
		int toAdd = player1.get(card); //Gets the resource at card index
		
		player1.remove(card);//removes resource from player 1
		player2.add(toAdd); //adds resource to player 2
		
	
		Player[] p = new Player[]{p1,p2}; //Array to hold both updated players
		
		return p;
	}//end moveResources
	

	
}//end class
