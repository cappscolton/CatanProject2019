import java.util.*; 

public class Player{
	private int victoryPoints;
	private int numRoads; 
	private int numSettlements;
	private int numCities; 
	
	//This array list contains all of the development cards that a particular user has 
	private ArrayList<DevelopmentCards>developmentCards = null; //TODO -- will contain the development cards a player has 
	//This hash map contains the names and numbers of all of the resources that a given user possesses
	private HashMap<String, Integer>playerResources = new HashMap<>(); 
	
    public Player(){
		victoryPoints = 0; 
		playerResources.put("Brick", 0); 
		playerResources.put("Lumber", 0); 
		playerResources.put("Ore", 0); 
		playerResources.put("Grain", 0); 
		playerResources.put("Wool", 0); 
	}
	
	//TODO -- use tileRollNumber and tileResource from tile class to add resources to hash map
	//TODO -- figure out how to calculate largest army and longest road
	//Figure out how to facilitate trading between players and maritime trade (would need information about a player's location)
	
	//this method should be called after each turn 
	
	
	public int calculateVictoryPoints(){
		victoryPoints = 0; 
		victoryPoints += (numSettlements); 
		victoryPoints += (2 * numCities); 
		// TODO -- if player has largest army or longest road, victory points += 2
		for (int i=0; i < developmentCards.size(); i++){
			if(developmentCards.get(i).equalsIgnoreCase("Market")){
				victoryPoints += 1; 
			} else if (developmentCards.get(i).equalsIgnoreCase("University")){
				victoryPoints += 1; 
			} else if (developmentCards.get(i).equalsIgnoreCase("Great Hall")){
				victoryPoints += 1; 
			} else if (developmentCards.get(i).equalsIgnoreCase("Chapel")) {
				victoryPoints += 1; 
			} else if (developmentCards.get(i).equalsIgnoreCase("Library")){
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
	
	
}