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
    private Road[][] roads;
    private Robber robber;

    /** getTiles
     * getter for the array containing our Tiles
     * @return 2D jagged array of Tile objects
     */
    public Tile[][] getTiles(){
        return tiles;
    }

    public Vertex[][] getVertexArray(){
        return vertices;
    }

    public Road[][] getRoadArray(){
        return roads;
    }

    public int findPlayersLongestRoad(Player p){
        Road startingRoad = null;
        ArrayList<Road> checkedRoads = new ArrayList<Road>();
        outerloop:
        for(Tile[] tRow : tiles){
            for (Tile t : tRow){
                for (Road r : t.getRoads()){
                    if (r.getOccupant()==p && !checkedRoads.contains(r)){
                        checkedRoads.add(r);
                        startingRoad = r;
                        break outerloop;
                    }
                }
            }
        }
        return lengthOfRoad(checkedRoads, startingRoad, 1);
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
        roads = createRoadArray();

        linkTilesToVertices(tiles, vertices);
        linkRoadsToVertices(roads, vertices);
        linkAdjacentRoads();
        linkTilesToRoads(tiles, roads);
        
        setTileResourcesAndNumbers();
        initializeRobber();
    }



    
    /* INTERNAL METHODS BELOW */



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

    /** createRoadArray
     * Creates a two dimensional jagged array of Road objects
     * with 5 rows of lengths 3, 4, 5, 4, 3
     * @return 2D jagged array of instatiated Roads
     */
    private Road[][] createRoadArray(){
        Road[][] roads = new Road[11][];
        int[] roadRowLengths = {6, 4, 8, 5, 10, 6, 10, 5, 8, 4, 6};
        for(int i=0; i<roadRowLengths.length; i++){
            Road[] row = new Road[roadRowLengths[i]];
            for(int j=0; j<row.length; j++){
                row[j] = new Road();
            }
            roads[i] = row;
        }
        return roads;
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

    private void linkAdjacentRoads(){
        for (Road[] rRow : roads){
            for (Road r : rRow){
                for (Road r1 : r.v1.adjacentRoads)
                    if (!r1.equals(r))
                        r.adjacents.add(r1);
                for (Road r1 : r.v2.adjacentRoads)
                    if (!r1.equals(r))
                        r.adjacents.add(r1);
            }
        }
    }

    private void linkTilesToRoads(Tile[][] tiles, Road[][] roads){
        for (int i=0; i<tiles.length; i++){
            linkTilesToRoadsForRow(roads, tiles, i);
        }
    }

    private void linkTilesToRoadsForRow(Road[][] roads, Tile[][] tiles, int row){
        for (int i=0; i<tiles[row].length; i++){
            Road r = roads[row*2][i*2];
            for (int j=0; j<2; j++){
                tiles[row][i].getRoads()[j] = r;
                r = r.adjacents.get(r.adjacents.size()-1);
            }
            tiles[row][i].getRoads()[2] = r;
            for (int j=3; j<6; j++){
                r = r.adjacents.get(0);
                tiles[row][i].getRoads()[j] = r;
                
            }
        }
    }

    private int lengthOfRoad(ArrayList<Road> checkedRoads, Road r, int length){
        int longestAdjacent = length;
        for (Road adj : r.adjacents){
            if (adj.getOccupant() != null){
                if(adj.getOccupant().equals(r.getOccupant()) && !checkedRoads.contains(adj)){
                    checkedRoads.add(adj);
                    int lengthAdj = lengthOfRoad(checkedRoads, adj, length+1);
                    if (lengthAdj > longestAdjacent)
                        longestAdjacent = lengthAdj;
                }
            }
        }
        return longestAdjacent;
    }

    /** linkTileVerticesForRow
     * For a given row, connects elements of the board's Tile array to elements of the
     * board's Vertex array, such that neighboring Tiles point to the same Vertex objects.
     * Thus, modifying a Vertex of one Tile simultaneously modifies the connected ones 
     * reducing further positional calculations.
     *
     * @param tiles jagged array of Tile objects
     * @param vertices jagged array of Vertex objects
     * @param row index of row of tiles to modify. important because
     *            upper rows are increasing in length, while 
     *            lower rows are decreasing, which changes how we index.
     */
    private void linkTileVerticesForRow(Tile[][] tiles, Vertex[][] vertices, int row){
        int nextRowOffset;
        if (row==2)  nextRowOffset=0;
        else nextRowOffset = (row<2) ? 0 : 1; //determine if row is in upper half of board or lower
        // set tile vertices 0 1 2
        for(int col=0; col<tiles[row].length; col++){
            tiles[row][col].getVertices()[0] = vertices[row][nextRowOffset++];
            tiles[row][col].getVertices()[1] = vertices[row][nextRowOffset++];
            tiles[row][col].getVertices()[2] = vertices[row][nextRowOffset];
        }

        if (row==2)  nextRowOffset=0;
        else nextRowOffset = (row>2) ? 0 : 1; //determine if row is in upper half of board or lower
        // set tile vertices 5 4 3
        for(int col=0; col<tiles[row].length; col++){
            tiles[row][col].getVertices()[5] = vertices[row+1][nextRowOffset++];
            tiles[row][col].getVertices()[4] = vertices[row+1][nextRowOffset++];
            tiles[row][col].getVertices()[3] = vertices[row+1][nextRowOffset];
        }
    }

    /** linkTilesToVertices
     * Loops through each row calling the above method, and storing a
     * reference to the Tile within the Vertex for resource distribution.
     *
     * @param tiles jagged array of Tile objects
     * @param vertices jagged array of Vertex objects
     */
    private void linkTilesToVertices(Tile[][] tiles, Vertex[][] vertices){
        for(int i=0; i<tiles.length; i++){
            linkTileVerticesForRow(tiles, vertices, i);
        }
        for (Tile[] row : tiles){
            for (Tile t : row){
                for (Vertex v : t.getVertices()){
                    v.adjacentTiles.add(t);
                }
            }
        }
    }

    /** linkRoadsToVertices
     * Loops through each row storing a
     * reference to 2 Vertex objects within the Roads in an array.
     *
     * @param roads jagged array of Road objects
     * @param vertices jagged array of Vertex objects
     * @param row index of row in 2d array
     */
    private void linkRoadsToVerticesForRow(Road[][] roads, Vertex[][] vertices, int row){
        boolean evenRow = (row % 2 == 0) ? true : false;
        int vertexRow = row/2;
        if (evenRow){
            int offset = 0;
            for (int col=0; col<roads[row].length; col++){
                roads[row][col].v1 = vertices[vertexRow][offset++];
                roads[row][col].v2 = vertices[vertexRow][offset];
                if (!roads[row][col].v1.adjacentRoads.contains(roads[row][col])){
                    roads[row][col].v1.adjacentRoads.add(roads[row][col]);
                }
                if (!roads[row][col].v2.adjacentRoads.contains(roads[row][col])){
                    roads[row][col].v2.adjacentRoads.add(roads[row][col]);       
                }         
            }
        }
        else {
            int nextRowOffset = (row>4) ? 0 : 1;
            int offset = 0;
            for (int col=0; col<roads[row].length; col++){
                roads[row][col].v1 = vertices[vertexRow+1][offset+nextRowOffset];
                roads[row][col].v2 = vertices[vertexRow][offset];
                if (!roads[row][col].v1.adjacentRoads.contains(roads[row][col])){
                    roads[row][col].v1.adjacentRoads.add(roads[row][col]);
                }
                if (!roads[row][col].v2.adjacentRoads.contains(roads[row][col])){
                    roads[row][col].v2.adjacentRoads.add(roads[row][col]);
                }
                offset += 2;
            }
        }

    }

    /** linkRoadsToVertices
     * Loop through all rows of roads calling linkRoadsToVerticesForRow()
     * to connect vertices to roads.
     */
    private void linkRoadsToVertices(Road[][] roads, Vertex[][] vertices){
        for (int i=0; i<roads.length; i++){
            linkRoadsToVerticesForRow(roads, vertices, i);
        }
        for (Road[] rRow : roads){
            for (Road r : rRow){
                r.v1.connect(r.v2);
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
    private void initializeRobber(){
        for(int i=0; i<tiles.length; i++){
            for (int j=0; j<tiles[i].length; j++){
                if (tiles[i][j].getTileRollResource().equals("desert")){
                    robber = new Robber(tiles[i][j]);
                }
            }
        }
    }

    
}