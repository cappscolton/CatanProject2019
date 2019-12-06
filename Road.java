import java.util.ArrayList;    

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
    public Road(int row, int col){
        x = row;
        y = col;
    }

    public void setOccupant(Player p){
        occupant = p;
    }

    public Player getOccupant(){
        return occupant;
    }

    public boolean equals(Object o){
        Road r = (Road)(o);
        return ((this.v1==r.v1 && this.v2==r.v2) || (this.v2==r.v1 && this.v1==r.v2));
    }
        
}