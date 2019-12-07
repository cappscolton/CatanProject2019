/* <Tile.java>
 * Colton Capps - CIS 200 S
 * An object to represent at tile on a board for Settlers of Catan.
 * The tile has 6 vertices (is hexagonal) and 6 roads.
 * It is also associated with a dice roll number to distribute resources
 * when that number is rolled and a resource value to specify what resource.
 */
import java.util.ArrayList;

public class Tile{
    private Vertex[] vertices;
    private Road[] roads;
    private int row;
    private int col; //TODO: for UI stuff
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

    /** getTileRollResource
     *  setter. Sets the resource of the tile. Used for resource distribution.
     */
    public void setTileRollResource(String s){
        tileRollResource = s;
    }

    /** getRoads
     *  getter. gets the roads that border this tile.
     *  @return array of 6 roads.
     */
    public Road[] getRoads(){
        return roads;
    }

    /** getVertices
     *  getter. gets the vertices that border this tile.
     *  @return array of 6 vertices.
     */
    public Vertex[] getVertices(){
        return vertices;
    }

    /** createRoads
     *  Creates 6 roads for a tile and stores them in this tile.
     */
    public void createRoads(){
        for(int i=0; i<6; i++){
            roads[i] = new Road(); //floor mod to link 5 to 0
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