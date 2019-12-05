package app;


import java.util.ArrayList;

public class Tile{
    public Vertex[] vertices;
    public Road[] roads;
    private int row;
    private int col;//TODO: for UI stuff
    private int tileRollNumber;
    private int tileRollNumberCache = 0; //for robbing
    private String tileRollResource;

    /** setTileRollNumber
     *  setter. Sets this tile to give resources when the number is rolled.
     */
    public void setTileRollNumber(int n){
        tileRollNumber = n;
    }

    /** setTileRollNumberCache
     *  setter. Sets the cache. Used for updating robber.
     */
    public void setTileRollNumberCache(int n){
        tileRollNumberCache = n;
    }

    /** getTileRollNumber
     *  getter. Gets the number, used for resource distribution.
     */
    public int getTileRollNumber(){
        return tileRollNumber;
    }


    /** getTileRollNumberCache
     *  getter. Gets the cache roll, used for updating robber
     */
    public int getTileRollNumberCache(){
        return tileRollNumberCache;
    }

    /** getTileRollResource
     *  getter. Gets the resource of the tile. Used for resource distribution.
     */
    public String getTileRollResource(){
        return tileRollResource;
    }


    

    /** Road
     *  A class that represents a connection between two Vertex objects
	 */
    public static class Road {
        Vertex v1;
        Vertex v2;
        Player occupant;
        ArrayList<Road> adjacents = new ArrayList<Road>();
        /** Road
         *  Constructor. Takes two Vertex objects and makes them
         *  reference each other, but also generates a Road object containing both.
         */
        public Road(Vertex i, Vertex j){
            v1 = i;
            v2 = j;
            v1.connect(v2);
            if (v1.adjacentRoads.size() < 3){
                v1.adjacentRoads.add(this);
            }
            if (v2.adjacentRoads.size() < 3){
                v2.adjacentRoads.add(this);
            }
        }
    }

    /** createRoads
     *  Creates 6 roads for a tile and stores them in this tile.
     */
    public void createRoads(){
        for(int i=0; i<6; i++){
            roads[i] = new Road(vertices[i], vertices[Math.floorMod(i+1,6)]); //floor mod to link 5 to 0
        }
    }




    /** Tile
     *  constructor. Creates a new Tile object
     *  by instantiating vertex and road arrays.
     */
    public Tile(int y, int x){
    
        row = y;
        col = x;
        vertices = new Vertex[6];
        roads = new Road[6];
    }
    
}