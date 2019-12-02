import java.util.*; 

public class Player{
	private int victoryPoints;
	private int numRoads; 
	private int numSettlements;
	private int numCities; 
	
	//This array list contains all of the development cards that a particular user has 
	private ArrayList<DevCards>developmentCards = new ArrayList<DevCards>; 
	/*
	This HashMap contains the names and numbers of all of the resources that a given user possesses; the ArrayList with the same name was substituted in order for this class to be compatible 
	with the Robber class
	private HashMap<String, Integer>playerResources = new HashMap<>(); 
	*/
	private ArrayList<Integer>playerResources = new ArrayList<Integer>(5); 
	//NOTE: it may make more since for this to be an int array instead of an ArrayList because there are exactly 5 types of resources and this does not change; this would require modification of Robber
	
    public Player(){
		victoryPoints = 0; 
		
		//This for loop is used to populate the ArrayList containing the numbers of each resource that a player possesses
		for(int i = 0; i < 5; i++){
			playerResources.set(i, 0); 
		}
		/*
		This code was used to populate the HashMap
		playerResources.put("Brick", 0); 
		playerResources.put("Lumber", 0); 
		playerResources.put("Ore", 0); 
		playerResources.put("Grain", 0); 
		playerResources.put("Wool", 0); 
		*/
	}
	
	//TODO -- use tileRollNumber and tileResource from tile class to add resources to hash map
	//Figure out how to facilitate trading between players and maritime trade (would need information about a player's location)
	
	//this method should be called after each turn 
	public int calculateVictoryPoints(){
		victoryPoints = 0; 
		victoryPoints += (numSettlements); 
		victoryPoints += (2 * numCities); 
		// TODO -- if player has largest army or longest road, victory points += 2
		for (int i=0; i < developmentCards.size(); i++){
			if(developmentCards.get(i) == 'V'){
				victoryPoints += 1; 
			} 
		}
		return victoryPoints; 
	}
	
	public void buildOrBuyDevelopmentCard(String choice){
		if(choice.equalsIgnoreCase("Road") && playerResources.get("Brick") > 0 && playerResources.get("Lumber") > 0){
			playerResources.put("Brick", playerResources.get("Brick") -1);
			playerResources.put("Lumber", playerResources.get("Lumber") -1); 
			numRoads ++; 
		} else if (choice.equalsIgnoreCase("Settlement") && playerResources.get("Brick") > 0 && 
		playerResources.get("Lumber") > 0 && playerResources.get("Wool") > 0 && playerResources.get("Grain") > 0) {
			playerResources.put("Brick", playerResources.get("Brick") -1); 
			playerResources.put("Lumber", playerResources.get("Lumber") -1); 
			playerResources.put("Wool", playerResources.get("Wool") -1); 
			playerResources.put("Grain", playerResources.get("Grain") -1); 
			numSettlements ++; 
		} else if (choice.equalsIgnoreCase("City") && playerResources.get("Ore") >= 3 && playerResources.get("Grain") >= 2){
			playerResources.put("Ore", playerResources.get("Ore") -3); 
			playerResources.put("Grain", playerResources.get("Grain") -2);
			numCities ++; 
		} else if (choice.equalsIgnoreCase("Buy a Development Card") && playerResources.get("Ore") > 0 &&
		playerResources.get("Wool") > 0 && playerResources.get("Grain") > 0) {
			//the exact name of this choice is subject to change; "Buying a Development Card is just a place holder
			playerResources.put("Ore", playerResources.get("Ore") -1);
			playerResources.put("Wool", playerResources.get("Wool") -1);			
			playerResources.put("Grain", playerResources.get("Grain") -1);
			//add a random development card to the array list of player's development cards
		}
	}
	
	public ArrayList<Integer> getArr(){
		return playerResources; 
	}
	
}