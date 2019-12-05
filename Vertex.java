package app;


import java.util.*;

public class Vertex {
    private Player occupant = null;
    private int rollMultiplier = 0;
    private ArrayList<Vertex> adjacents = new ArrayList<Vertex>(2); // at least 2 adjacent vertices, most have 3
    public ArrayList<Tile> adjacentTiles = new ArrayList<Tile>(); // nearby tiles
    public ArrayList<Tile.Road> adjacentRoads = new ArrayList<Tile.Road>();


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

    public Vertex(){}
}