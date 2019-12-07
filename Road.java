/* Road.java
 * Colton Capps
 * Represents a road that borders a Tile object residing in the Board
 * of a game of Catan. Each road contains two vertices and an occupant.
 * This file contains methods to use/modify these values in the Board class
 * and in the Application class where our main game loop runs. 
 */

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
    public Road(){}
    
    /** Road
     *  Constructor. row and column location in the board 2D array as parameters.
     */
    public Road(int row, int col){
        x = row;
        y = col;
    }

    /** setOccupant
     *  Setter. Sets occupant to Player object of this road
     */
    public void setOccupant(Player p){
        occupant = p;
    }

    /** setOccupant
     *  getter. gets occupant Player object of this road
     */
    public Player getOccupant(){
        return occupant;
    }

    /** playerIsConnected
     *  check nearby roads and vertices to see if an adjacent settlement
     *  or road is occupied by the same player (which tells us if this road is connected)
     *  for checking validity of placement at this road's location.
     * @return true if valid/connected, false otherwise
     * @param p the player who is trying to occupy this road.
     */
    public boolean playerIsConnected(Player p){
        for (Road r : adjacents){
            boolean case1 = r.getOccupant()!=null && Objects.equals(p, r.getOccupant());
            boolean case2 = (this.v1.getOccupant()!=null && this.v1.getOccupant()==p) || (this.v2.getOccupant()!=null && this.v2.getOccupant()==p);
            if ( case1 || case2 )
                return true;
        }
        return false;
    }

    /** equals
     * two roads are equal if they possess the 2 same vertices
     * @return true if equal, false otherwise.
     */
    public boolean equals(Object o){
        Road r = (Road)(o);
        return ((this.v1==r.v1 && this.v2==r.v2) || (this.v2==r.v1 && this.v1==r.v2));
    }
        
}