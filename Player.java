import java.util.*; 

/**Player
 * This class contains the data properties and methods that are used by players.
 *
 * @author Abby 
 *
 */
public class Player{
	private int number;
	private int victoryPoints;
	private int numRoads; 
	private int numSettlements;
	private int numCities; 
	//This array list contains all of the development cards that a particular user has 
	private ArrayList<Character>developmentCards = new ArrayList<Character>(); 
	private boolean hasLargestArmy = false; 
	private boolean hasLongestRoad = false; 
	
	//These variables to be decremented each time a user places a settlement, city, or road so that user cannot place more than the number alotted to them 
	private int distanceFromMaxSettlements = 5; 
	private int distanceFromMaxCities = 4; 
	private int distanceFromMaxRoads = 15;
	
	//include locations of settlements and cities (array of vertices!!!!)
	//create array list of road locations 
	private ArrayList<Vertex>settlementLocations = new ArrayList<Vertex>(); 
	private ArrayList<Vertex>cityLocations = new ArrayList<Vertex>(); 
	private ArrayList<Road>roadLocations = new ArrayList<Road>(); 
	
	private int [] playerResources = new int [5]; 
	
	
	/**Player 
	 * Constructor for player object.
	 *
	 * @param n is the player's number
	 */
    public Player(int n){
		number = n-1;
		victoryPoints = 0; 
		//This for loop is used to populate the array containing the numbers of each resource that a player possesses
		//The elements in the array correspond to the number of bricks, lumber, ore, grain, and wool respectively 
		for(int i = 0; i < playerResources.length; i++){
			playerResources[i] = 0;  
		}
	} 
	
	
	/**calculateVitoryPoints
	 * This method calculates a player's victory points and should be called after each turn
	 *
	 * @return number of victory points
	 */
	public int calculateVictoryPoints(){
		victoryPoints = 0; 
		victoryPoints += (numSettlements); 
		victoryPoints += (2 * numCities); 
		for (int i=0; i < developmentCards.size(); i++){
			if(developmentCards.get(i).equals('V')){
				victoryPoints += 1; 
			} 
		}
		if (hasLargestArmy) 
			victoryPoints+=2;
		if (hasLongestRoad) 
			victoryPoints+=2;
		return victoryPoints; 
	}


	/**buildOrBuyDevelopmentCard
	 * This method deducts players resources in exchange for roads, settlements, cities, or development cards.
	 *
	 * @param choice is what the player wants to buy
	 * @return true if the player successfully purchases item 
	 */
	//resources in order of index: brick, lumber, ore, grain, wool
	public boolean buildOrBuyDevelopmentCard(String choice, boolean free, DevCards deck){
		//Roads cost 1 brick, 1 lumber
		if(choice.equalsIgnoreCase("Road")){
			if ((playerResources[0] > 0 && playerResources[1] > 0) || free){
				if(!free){
					playerResources[0]--;
					playerResources[1]--; 
				}
				numRoads ++; 
				distanceFromMaxRoads--;
			} 
			else return false;
		}
		//Settlements cost 1 brick, 1 lumber, 1 wool, 1 grain 
		else if (choice.equalsIgnoreCase("Settlement")){
			if((playerResources[0] > 0 && playerResources[1] > 0 && playerResources[4] > 0 && playerResources[3] > 0) || free) {
				if (!free){
					playerResources[0]--; 
					playerResources[1]--; 
					playerResources[4]--; 
					playerResources[3]--; 
				}
				numSettlements ++; 
				distanceFromMaxSettlements--;
			}
			else return false;
		}
		//Cities cost three ore and 2 grain 
		else if (choice.equalsIgnoreCase("City")){
			if (playerResources[2] >= 3 && playerResources[3] >= 2){
				playerResources[2]-=3; 
				playerResources[3]-=2;
				numCities ++; 
				distanceFromMaxCities--;
				distanceFromMaxSettlements++; 
				numSettlements--;
			}
			else return false;
		} 
		//Development cards cost 1 ore, 1 wool, 1 grain 
		else if (choice.equalsIgnoreCase("Buy a Development Card")) {
			if (playerResources[2] > 0 && playerResources[4] > 0 && playerResources[3] > 0) {
				//the exact name of this choice is subject to change; "Buying a Development Card" is just a place holder
				playerResources[2]--;
				playerResources[4]--;			
				playerResources[3]--;
				//add a random development card to the array list of player's development cards
				Character newDevCard = deck.getCard();
				if(newDevCard != 'N'){
					developmentCards.add(newDevCard); 
				}
			}
			else return false;		
		}
		return true;
	}
	
	
	/**getVictoryPoints
	 * Getter method for victory points
	 *
	 * @return victory points
	 */
	public int getVictoryPoints() {
		return victoryPoints; 
	}
	
	
	/**getPlayerResources
	 * Getter method resources
	 *
	 * @return integer array of resources that a player has
	 */
	public int[] getPlayerResources(){
		return playerResources; 
	}
	
	
	/**setPlayerResources
	 * Setter method for player resources
	 * 
	 * @param updatedArray is an integer array of resources that a player has
	 */
	public void setPlayerResources(int[] updatedArray){
		playerResources = updatedArray; 
	}
	
	
	/**setHasLargestArmy
	 * Setter that assigns a boolean value to the data property hasLargestArmy
	 *
	 * @param b is a boolean corresponding to whether or not a player has the largest army
	*/ 
	public void setHasLargestArmy (boolean b){
		hasLargestArmy = b; 
	}
	
	
	/**setHasLongestRoad
	 * Setter that assigns a boolean value to the data property hasLongestRoad
	 *
	 * @param b is a boolean corresponding to whether or not a player has the longest road
	 */
	public void setHasLongestRoad (boolean b){
		hasLongestRoad = b; 
	}
	
	
	/**getDevelopmentCards
	 * Getter for the development cards that a player possesses
	 *
	 * @return a character array list corresponding to development cards
	 */
	public ArrayList<Character> getDevelopmentCards(){
		return developmentCards; 
	}
	
	
	/**getMaxSettlements
	 * Getter for integer variable that shows how many settlements a user has left
	 *
	 * @return the number of settlements that a user has left to place
	 */
	public int getMaxSettlements(){
		return distanceFromMaxSettlements;
	}
	
	
	/**getMaxRoads
	 * Getter for integer variable that shows how many roads a user has left
	 *
	 * @return the number of roads that a user has left to place
	 */
	public int getMaxRoads(){
		return distanceFromMaxRoads;
	}
	
	
	/**getMaxCities
	 * Getter for integer variable that shows how many cities a user has left
	 *
	 * @return the number of cities that a user has left to place
	 */
	public int getMaxCities(){
		return distanceFromMaxCities;
	}
	
	
	/**addSettlementLocation
	 * Method that allows a vertex object to be added to the private array list containing the locations of all of a player's settlements
	 *
	 * @param v is a vertex on which a player has a settlement
	 */
	public void addSettlementLocation (Vertex v){
		settlementLocations.add(v); 
	}
	
	/**addCityLocation
	 * Method that allows a vertex object to be added to the private array list containing the locations of all of a player's cities
	 * 
	 * @param v is a vertex on which a player has a city
	 */
	public void addCityLocation (Vertex v){
		cityLocations.add(v);
		if(settlementLocations.contains(v)){
			settlementLocations.remove(v); 
		}
	}
	
	/**addRoad
	 * Method that allows a road object to be added to the private array list containing the locations of all a player's roads
	 *
	 * @param r is a road object that the user has placed
	 */
	public void addRoad (Road r){
		roadLocations.add(r); 
	}
	
	/**getNumber
	 * Getter method for player number
	 *
	 * @return a player's number (int)
	 */
	public int getNumber(){
		return number; 
	}
}
	
	
	
	
	
	

	
	
	
