//package app;

/**
 * <Board.java>
 * <Colton Capps - CIS200 S>
 * Class to represent the Board which contains information about
 * most aspects of the game, including an array of Tiles making up
 * the board, an array of vertices that make up Tiles, and
 * methods to set up the board. See documentation images for visual
 * representation of Tile/Road/Vertex relationships.
*/ 

import java.util.Collections;
import java.util.ArrayList;
import java.util.Arrays;

public class Board {

    private Tile[][] tiles;
    private Vertex[][] vertices;
    private Robber robber;

    /** getTiles
     * getter for the array containing our Tiles
     * @return 2D jagged array of Tile objects
     */
    public Tile[][] getTiles(){
        return tiles;
    }

    /** getRobber
     * getter for boards Robber
     * @return Robber object - there should only be one on the board.
     */
    public Robber getRobber(){
        return robber;
    }



    
    /** Board
     * Constructor. Instantiates tile and vertex arrays then
     * creates necessary links between objects in the arrays
     * as well as initializing the robber on the desert tile.
     */  
    public Board(){
        tiles = createTileArray();
        vertices = createVertexArray();

        linkTilesToVertices(tiles, vertices);
        createRoads();
        setTileResourcesAndNumbers();
        initializeRobber();
    }

    /** createTileArray
     * Creates a two dimensional jagged array of Tile objects
     * with 5 rows of lengths 3, 4, 5, 4, 3
     * @return 2D jagged array of instatiated Tiles
     */ 
    private Tile[][] createTileArray(){
        Tile[][] tiles = new Tile[5][];
        int[] tileRowLengths = {3, 4, 5, 4, 3};
        for(int i=0; i<tileRowLengths.length; i++){
            Tile[] row = new Tile[tileRowLengths[i]];
            for(int j=0; j<row.length; j++){
                row[j] = new Tile(i, j);
            }
            tiles[i] = row;
        }
        return tiles;
    }

    /** createVertexArray
     * Creates a two dimensional jagged array of Vertex objects
     * with 5 rows of lengths 7,9,11,11,9,7
     * @return 2D jagged array of instatiated Vertices
     */  
    private Vertex[][] createVertexArray(){
        Vertex[][] vertices = new Vertex[6][];
        int[] vertexRowLengths = {7,9,11,11,9,7};
        for(int i=0; i<vertexRowLengths.length; i++){
            Vertex[] row = new Vertex[vertexRowLengths[i]];
            for(int j=0; j<row.length; j++){
                row[j] = new Vertex();
            }
            vertices[i] = row;
        }
        return vertices;
    }
	
	/**getVertexArray
	* getter for the 2d array of vertex objects 
	* @return 2d array of vertices
	*/
	public Vertex[][] getVertexArray(){
		return vertices; 
	}

    /** linkTileVerticesForRow
     * For a given row, connects elements of the board's Tile array to elements of the
     * board's Vertex array, such that neighboring Tiles point to the same Vertex objects.
     * Thus, modifying a Vertex of one Tile simultaneously modifies the connected ones 
     * reducing further positional calculations.
     *
     * @param 2D jagged array of Tile objects
     * @param 2D jagged array of Vertex objects
     * @param int row of tiles to modify. important because
     *        upper rows are increasing in length, while 
     *        lower rows are decreasing, which changes how we index.
     */
    private void linkTileVerticesForRow(Tile[][] tiles, Vertex[][] vertices, int row){
        int nextRowOffset;
        if (row==2)  nextRowOffset=0;
        else nextRowOffset = (row<2) ? 0 : 1; //determine if row is in upper half of board or lower
        // set tile vertices 0 1 2
        for(int col=0; col<tiles[row].length; col++){
            tiles[row][col].vertices[0] = vertices[row][nextRowOffset++];
            tiles[row][col].vertices[1] = vertices[row][nextRowOffset++];
            tiles[row][col].vertices[2] = vertices[row][nextRowOffset];
        }

        if (row==2)  nextRowOffset=0;
        else nextRowOffset = (row>2) ? 0 : 1; //determine if row is in upper half of board or lower
        // set tile vertices 5 4 3
        for(int col=0; col<tiles[row].length; col++){
            tiles[row][col].vertices[5] = vertices[row+1][nextRowOffset++];
            tiles[row][col].vertices[4] = vertices[row+1][nextRowOffset++];
            tiles[row][col].vertices[3] = vertices[row+1][nextRowOffset];
        }
    }


    /** linkTilesToVertices
     * Loops through each row calling the above method, and storing a
     * reference to the Tile within the Vertex for resource distribution.
     *
     * @param 2D jagged array of Tile objects
     * @param 2D jagged array of Vertex objects
     */
    private void linkTilesToVertices(Tile[][] tiles, Vertex[][] vertices){
        for(int i=0; i<tiles.length; i++){
            linkTileVerticesForRow(tiles, vertices, i);
        }
        for (Tile[] row : tiles){
            for (Tile t : row){
                for (Vertex v : t.vertices){
                    v.adjacentTiles.add(t);
                }
            }
        }
    }

    /** createRoads
     * Loop through all the tiles in the board and create road objects for them
     */
    private void createRoads(){
        for(Tile[] i : tiles){
            for (Tile t : i){
                t.createRoads();
            }
        }
    }

    /** setTileResourcesAndNumbers
     * There are fixed numbers of each resource and tile roll number on the map.
     * Given these hardcoded, randomize the board layout, assiging values to the attributes
     * of Tile objects in the board's array, yet assure that the desert always gets 0.
     */
    private void setTileResourcesAndNumbers(){
        String[] r = {  "wheat", "wheat", "wheat", "wheat",
                        "sheep", "sheep", "sheep", "sheep",
                        "wood", "wood", "wood", "wood", 
                        "brick", "brick", "brick",
                        "ore", "ore", "ore",
                        "desert"
                     };
        Integer[] n = { 2, 3, 3, 4, 4, 5, 5, 6, 6, 8, 8, 9, 9, 10, 10, 11, 11, 12 };
        ArrayList<String> resources = new ArrayList<String>(Arrays.asList(r));
        ArrayList<Integer> numbers = new ArrayList<Integer>(Arrays.asList(n));
        Collections.shuffle(resources);
        Collections.shuffle(numbers);
        numbers.add(0);

        for (Tile[] tRow : tiles){
            for (Tile t : tRow){
                String randomResource = resources.get(0);
                Integer randomNumber = (randomResource.equals("desert")) ? 0 : numbers.get(0);
                t.setTileRollNumber(randomNumber);
                t.setTileRollResource(randomResource);
                resources.remove(randomResource);
                numbers.remove(randomNumber);
            }
        }
    }

    /** initializeRobber
     * Loops thourgh Tile objects in the board to find desert and set the
     * robber's starting location by constructing a new robber with that Tile.
     */
    public void initializeRobber(){
        for(int i=0; i<tiles.length; i++){
            for (int j=0; j<tiles[0].length; j++){
                if (tiles[i][j].getTileRollResource().equals("desert")){
                    robber = new Robber(tiles[i][j]);
                }
            }
        }
    }
    

    
}