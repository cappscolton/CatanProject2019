import java.util.Collections;
import java.util.ArrayList;
import java.util.Arrays;

public class Board {

    private Tile[][] tiles;
    private Vertex[][] vertices;

    public Board(){
        tiles = createTileArray();
        vertices = createVertexArray();

        linkTilesToVertices(tiles, vertices);
        createRoads();
        setTileResourcesAndNumbers();
    }

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

    private void linkTileVerticesForRow(Tile[][] tiles, Vertex[][] vertices, int row){
    // Gives all Tiles in one row valid vertices.
        int nextRowOffset;
        if (row==2)  nextRowOffset=0;
        else nextRowOffset = (row<2) ? 0 : 1;
        // set tile vertices 0 1 2
        for(int col=0; col<tiles[row].length; col++){
            tiles[row][col].vertices[0] = vertices[row][nextRowOffset++];
            tiles[row][col].vertices[1] = vertices[row][nextRowOffset++];
            tiles[row][col].vertices[2] = vertices[row][nextRowOffset];
        }

        if (row==2)  nextRowOffset=0;
        else nextRowOffset = (row>2) ? 0 : 1;
        // set tile vertices 5 4 3
        for(int col=0; col<tiles[row].length; col++){
            tiles[row][col].vertices[5] = vertices[row+1][nextRowOffset++];
            tiles[row][col].vertices[4] = vertices[row+1][nextRowOffset++];
            tiles[row][col].vertices[3] = vertices[row+1][nextRowOffset];
        }
    }

    private void linkTilesToVertices(Tile[][] tiles, Vertex[][] vertices){
    // Loops through all the rows to store vertices in tiles with no duplicates
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

    private void createRoads(){
    // Creates roads for each tile
        for(Tile[] i : tiles){
            for (Tile t : i){
                t.createRoads();
            }
        }
    }

    public Tile[][] getBoardData(){
    // Getter
        return tiles;
    }

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
}