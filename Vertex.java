/* Vertex.java
 * Colton Capps
 * A Vertex object represents a point at which Tile objects meet
 * on the Board for the game Catan. Each vertex contains a Player
 * occupant, multiplier for game functionality and 
 * arrays of nearby Vertices, Tiles, and Roads.
 * Contains methods to use/modify these adjacents/occupant/multipliers
 * in Application.java
 */

import java.util.*;

public class Vertex {
    private Player occupant = null;
    private int rollMultiplier = 0;
    public int x;
    public int y;
    private ArrayList<Vertex> adjacents = new ArrayList<Vertex>(2); // at least 2 adjacent vertices, most have 3
    public ArrayList<Tile> adjacentTiles = new ArrayList<Tile>(); // nearby tiles
    public ArrayList<Road> adjacentRoads = new ArrayList<Road>();


    /** getOccupant
     *  getter. returns the player occupying Vertex/Settlement
     */
    public Player getOccupant(){
        return occupant;
    }

    /** isOccupied
     *  checks whether vertex is occupied by city/settlement
     */
    public boolean isOccupied(){
        return (this.getOccupant() != null);
    }

    /** setOccupant
     *  setter. Sets the occupant of the object. Used for resource
     *  distribution and victory point calculation.
     */
    public void setOccupant(Player p){
        occupant = p;
    }

    /** hasNeighboringSettlement
     *  checks adjacent verticies to verify valid new settlements.
     */
    public boolean hasNeighboringSettlement(){
        for(Vertex v : adjacents){
            if (v.isOccupied()) return true;
        }
        return false;
    }

    /** setRollMultiplier
     *  setter. Sets the resource distrubtion multiplier for the vertex.
     *  1 for settlements, 2 for cities, 0 unoccupied.
     */
    public void setRollMultiplier(int n){
        rollMultiplier = n;
    }

    /** getRollMultiplier
     *  getter. gets the resource distrubtion multiplier for the vertex.
     *  1 for settlements, 2 for cities, 0 unoccupied.
     */
    public int getRollMultiplier(){
        return rollMultiplier;
    }    

    /** connect
     *  connects this Vertex to Vertex v, such that each Vertex is stored in the other's
     *  "adjacents" array.
     */
    public void connect(Vertex v){
        if (!adjacents.contains(v))
            adjacents.add(v);
        if (!v.adjacents.contains(this))
            v.adjacents.add(this);
    }

    /** playerIsConnected
     *  looks at nearby roads to see if they are occupied by the same player
     *  making this vertex a valid placement location.
     *  @return true if the player is connected to this vertex, false otherwise
     */
    public boolean playerIsConnected(Player p){
        for (Vertex v : adjacents){
            if (v.getOccupant().equals(p)) return true;
        }
        return false;
    }

    /** distributeResources
     *  Loops through adjacent tiles to determine if the dice roll
     *  warants resource distribution. If so, give resources to the occupant of this
     *  Vertex (if there is one).
     */
    public void distributeResources(int roll){
        if (occupant!=null){
            for (Tile t : adjacentTiles){
                if (t.getTileRollNumber()==roll || roll==-1){
                    String resource = t.getTileRollResource();
                    if (resource.equals("wheat"))
                        occupant.getPlayerResources()[3]++;
                    else if (resource.equals("wood"))
                        occupant.getPlayerResources()[1]++;
                    else if (resource.equals("sheep"))
                        occupant.getPlayerResources()[4]++;
                    else if (resource.equals("ore"))
                        occupant.getPlayerResources()[2]++;
                    else if (resource.equals("brick"))
                        occupant.getPlayerResources()[0]++;
                }
                
            }
        }
    }

    /** Vertex
     * Constructor. Row and column / x and y in the 2D vertex array of the board.
     */
    public Vertex(int row, int col){
        x=row;
        y=col;
    }
}