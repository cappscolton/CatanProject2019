public class Board {

    private Tile[][] tiles;
    private Vertex[][] vertices;

    public Board(){
        tiles = createTileArray();
        vertices = createVertexArray();

        linkTilesToVertices(tiles, vertices);
        createRoads();
    }

    private Tile[][] createTileArray(){
        Tile[][] tiles = new Tile[7][];
        int[] tileRowLengths = {3, 4, 5, 6, 5, 4, 3};
        for(int i=0; i<7; i++){
            Tile[] row = new Tile[tileRowLengths[i]];
            for(int j=0; j<row.length; j++){
                row[j] = new Tile(i, j);
            }
            tiles[i] = row;
        }
        return tiles;
    }

    private Vertex[][] createVertexArray(){
        Vertex[][] vertices = new Vertex[8][];
        int[] vertexRowLengths = {7,9,11,13,13,11,9,7};
        for(int i=0; i<8; i++){
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
        if (row==3)  nextRowOffset=0;
        else nextRowOffset = (row<3) ? 0 : 1;
        // set tile vertices 0 1 2
        for(int col=0; col<tiles[row].length; col++){
            tiles[row][col].vertices[0] = vertices[row][nextRowOffset++];
            tiles[row][col].vertices[1] = vertices[row][nextRowOffset++];
            tiles[row][col].vertices[2] = vertices[row][nextRowOffset];
        }

        if (row==3)  nextRowOffset=0;
        else nextRowOffset = (row>3) ? 0 : 1;
        // set tile vertices 5 4 3
        for(int col=0; col<tiles[row].length; col++){
            tiles[row][col].vertices[5] = vertices[row+1][nextRowOffset++];
            tiles[row][col].vertices[4] = vertices[row+1][nextRowOffset++];
            tiles[row][col].vertices[3] = vertices[row+1][nextRowOffset];
        }
    }

    private void linkTilesToVertices(Tile[][] tiles, Vertex[][] vertices){
    // Loops through all the rows to store vertices in tiles with no duplicates
        for(int i=0; i<7; i++){
            linkTileVerticesForRow(tiles, vertices, i);
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

    
    /* COULD BE MODIFED AND MOVED TO PLAYER.JAVA? */
    public void buildSettlement(Player p, Vertex v){
    // Check if a vertex\neighboring vertex is occupied, then occupy
        if (!v.isOccupied()){
            if (!v.hasNeighboringSettlement()){
                v.setOccupant(p);
            }
        }
    }

    private void assignResourceToTiles(){
        //TODO: preset number of each resource tile, assign randomly
    }

    private void assignNumberToTiles(){
        //TODO: preset number of each

    }

    private void giveVerticesResourcePairs(){
    //To determine whether a settlement/player gets a resource from a roll,
    //the vertex must store a pair (number, resource) for each adjacent tile.
    // Give the pair corresonding to each tile.
    // TODO: not sure if this is best done in Board.java
    // Another option would be storing references to the 3 tiles within each Vertex
    // which would allow us to loop through every vertex, checking its settlement status
    // then use the tile reference to reward.
    }
}