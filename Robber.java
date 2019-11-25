/* Robber.java
 * Robber class with a variable to hold position and methods 'getPosition' to return the current position of the robber,
 * 'setPosition' to change the position of the robber, 'moveResources' to allow transfer of stolen resources, and 'blockResources;'
 * to keep resources from being given out if the robber is on a certain tile.
 * 
 */
import java.util.*;
public class Robber {

	private int[] position; //Current position of robber

	
	
	//constructor
	public Robber() {
		position=new int[2]; 
		//Sets initial position of the robber at (3,3), the desert
		position[0]=3;
		position[1]=3;
		
	}
	
	
/* getPosition
 * gets the current position of the robber
 * No parameters
 * @return- the position of the robber (in an array- position[0] is row and position[1] is column)
 */
	public int[] getPosition() {
		return position;
	}//end getPosition
	
	
/* setPosition
 * Sets the new position of the robber
 * @param a- the new row of the robber
 * @param b- the new column of the robber
 * Returns no values
 */
	public void setPosition(int a,int b) {
		position[0]=a;
		position[1]=b;
	}//end setPosition
	
	
	
	
/* moveResources
 * Allows transfer of "robbed" resources
 * @param p1- The player being robbed
 * @param p2- the player getting resources
 */
	public Player[] moveResources(Player p1,Player p2) { //brick, lumber, wool, grain, and ore
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
	
	
	
/* blockResources
 * Blocks resources from being given out while robber is present on the tile
 */
	public void blockResources() {
		int[] rowLengths = {3, 4, 5, 4, 3};
		if((position[0]!=3)&&(position[1]!=3)) { //if the robber is not in the desert
			for(int i=0;i<5;i++) { //rows
				for(int j=0;j<rowLengths[i];j++) { //columns, only goes through how many there are in current row
					
					if((i==position[0])&&(j==position[1])) { //if the index is where the robber is
						//TODO- Code to block resources from being given out
					}
				}//end inner for
			}//end outer for
		}//end if
		
	}//end blockResources
	
}//end class
