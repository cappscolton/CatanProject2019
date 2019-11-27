import java.util.*;

public class Vertex {
    private Player occupant = null;
    private int rollMultiplier = 0;
    private ArrayList<Vertex> adjacents = new ArrayList<Vertex>(2); // at least 2 adjacent vertices, most have 3
    public ArrayList<Tile> adjacentTiles = new ArrayList<Tile>(); // nearby tiles

    public Player getOccupant(){
        return occupant;
    }

    public boolean isOccupied(){
        return (this.getOccupant() != null);
    }
 
    public void setOccupant(Player p){
        occupant = p;
    }

    public boolean hasNeighboringSettlement(){
        for(Vertex v : adjacents){
            if (v.isOccupied()) return true;
        }
        return false;
    }

    public void setRollMultiplier(int n){
        rollMultiplier = n;
    }

    public void connect(Vertex v){
    // Update adjacents for this and v so that they know which vertices are nearby
        if (!adjacents.contains(v))
            adjacents.add(v);
        if (!v.adjacents.contains(this))
            v.adjacents.add(this);
    }

    public Vertex(){}
}