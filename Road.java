import java.util.ArrayList;    
import java.util.Objects;

public class Road {
    public Vertex v1;
    public Vertex v2;
    public int x;
    public int y;
    private Player occupant = null;
    public ArrayList<Road> adjacents = new ArrayList<Road>();

    /** Road
     *  Constructor. Dummy, everything is initialized once all the roads
     *  instantiated (in Board.java)
     */
	//public Road(){}; 
	
    public Road(int row, int col){
        x = row;
        y = col;
    }

    public Road(){}

    public void setOccupant(Player p){
        occupant = p;
    }

    public Player getOccupant(){
        return occupant;
    }

    public boolean playerIsConnected(Player p){
        for (Road r : adjacents){
            if (Objects.equals(p, r.getOccupant()) || Objects.equals(this.v1.getOccupant(), p) || Objects.equals(this.v2.getOccupant(), p))
                return true;
        }
        return false;
    }

    public boolean equals(Object o){
        Road r = (Road)(o);
        return ((this.v1==r.v1 && this.v2==r.v2) || (this.v2==r.v1 && this.v1==r.v2));
    }
        
}