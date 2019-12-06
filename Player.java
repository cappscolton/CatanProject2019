import java.util.*; 

public class Player{
	public int number;
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
	
    public Player(int n){
		number = n;
		victoryPoints = 0; 
		//This for loop is used to populate the array containing the numbers of each resource that a player possesses
		//The elements in the array correspond to the number of bricks, lumber, ore, grain, and wool respectively 
		for(int i = 0; i < playerResources.length; i++){
			playerResources[i] = 100;  
		}
	} 
	
	//this method should be called after each turn 
	public int calculateVictoryPoints(){
		victoryPoints = 0; 
		victoryPoints += (numSettlements); 
		victoryPoints += (2 * numCities); 
		// TODO -- if player has largest army or longest road, victory points += 2
		for (int i=0; i < developmentCards.size(); i++){
			if(developmentCards.get(i).equals('V')){
				victoryPoints += 1; 
			} 
		}
		if (hasLargestArmy) victoryPoints+=2;
		if (hasLongestRoad) victoryPoints+=2;
		return victoryPoints; 
	}

	//resources in order of index: brick, lumber, ore, grain, wool
	public boolean buildOrBuyDevelopmentCard(String choice){
		//Roads cost 1 brick, 1 lumber
		if(choice.equalsIgnoreCase("Road")){
			if (playerResources[0] > 0 && playerResources[1] > 0){
				playerResources[0]--;
				playerResources[1]--; 
				numRoads ++; 
				distanceFromMaxRoads--;
			} 
			else return false;
		}
		//Settlements cost 1 brick, 1 lumber, 1 wool, 1 grain 
		else if (choice.equalsIgnoreCase("Settlement")){
			if(playerResources[0] > 0 && playerResources[1] > 0 && playerResources[4] > 0 && playerResources[3] > 0) {
				playerResources[0]--; 
				playerResources[1]--; 
				playerResources[4]--; 
				playerResources[3]--; 
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
				Character newDevCard = DevCards.getCard(); 
				if(newDevCard != 'N'){
					developmentCards.add(newDevCard); 
				}
			}
			else return false;		
		}
		return true;
	}
	
	public int getVictoryPoints() {
		return victoryPoints; 
	}
	
	public int[] getPlayerResources(){
		return playerResources; 
	}
	
	public void setPlayerResources(int[] updatedArray){
		playerResources = updatedArray; 
	}
	

	public void setHasLargestArmy (boolean b){
		hasLargestArmy = b; 
	}
	
	public void setHasLongestRoad (boolean b){
		hasLongestRoad = b; 
	}
	
	public ArrayList<Character> getDevelopmentCards(){
		return developmentCards; 
	}
	
	public int getMaxSettlements(){
		return distanceFromMaxSettlements;
	}
	
	public int getMaxRoads(){
		return distanceFromMaxRoads;
	}
	
	public int getMaxCities(){
		return distanceFromMaxCities;
	}
	
	public void addSettlementLocation (Vertex v){
		settlementLocations.add(v); 
	}
	
	public void addCityLocation (Vertex v){
		cityLocations.add(v);
		if(settlementLocations.contains(v)){
			settlementLocations.remove(v); 
		}
	}
	
	public void addRoad (Road r){
		roadLocations.add(r); 
	}
	
}
	
	
	
	
	
	

	
	
	
