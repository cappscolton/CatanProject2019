/* DevCards2.java
 * Sarah Diener - created 11/5/19
 * Allows user to create a devcards object that will hold a "deck" (arraylist) of development cards. getCard() method
 * returns a random card (a character) for the user, and the updateCards updates the deck by removing the appropriate card
 * from the arraylist.
 * 
 */
import java.util.*;
public class DevCards2 {
	private ArrayList<Character> remaining;
	
	
	
	
	//Constructor
	public DevCards2() {
		remaining = new ArrayList <Character>();
		
		for(int i=0;i<14;i++) { //Adds all knight cards to the array list
			remaining.add('K');
		}
		
		for(int i=0;i<2;i++) { //Adds all road building cards to the array list
			remaining.add('R');
		}
		
		for(int i=0;i<2;i++) { //Adds all year of plenty cards to the array list
			remaining.add('P');
		}
		
		for(int i=0;i<2;i++) { //Adds all monopoly cards to the array list
			remaining.add('M');
		}
		
		for(int i=0;i<5;i++) { //Adds all victory cards to the array list
			remaining.add('V');
		}
	}
	
	
	
	
/*Get card method
 * Generates a random card
 * 
 * Takes no parameters
 * @return- the char representation of a card (or 'N' if there are no cards left)
 */
	public char getCard() {
		Random r = new Random();
		
		if(remaining.size()>0) {
			return remaining.get(r.nextInt(remaining.size())); //Returns a random card from the deck
		}
		else {
			return 'N';
		}
	}//End getCard
	
	
	
	
	
	
/* updateCards method
 * Updates the amount of cards left to change the probability accordingly
 * @param- the character representing the card to be removed from the "deck"
 * Returns no values
 */
	public void updateCards(char c) {
		for (int i=0;i<remaining.size();i++) { //Goes through arraylist of cards
			if(remaining.get(i)==c) { //If it finds a matching card, remove it from the deck
				//System.out.println("Found card to remove ("+remaining.get(i)+")");
				remaining.remove(i);
				break;
			}
			//System.out.println("Searching...");
		}//end for
	}//end updateCards
	
	
	
}//End class
