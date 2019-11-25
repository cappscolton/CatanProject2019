import java.util.*;
import javafx.util.Pair;

public class Tile{
    public Vertex[] vertices;
    public Road[] roads;
    private int row;
    private int col;//TODO
    private int tileRollNumber; //TODO
    private String tileResource; //TODO

    boolean isRobbed = false;

    public static class Road {
        Vertex v1;
        Vertex v2;
        public Road(Vertex i, Vertex j){
            v1 = i;
            v2 = j;
            v1.connect(v2);
        }
    }

    public void copyResourceToVertices(){
        for(Vertex v : vertices){
            v.adjacentResources.add(tileResource);
        }
    }

    public void createRoads(){
        for(int i=0; i<6; i++){
            roads[i] = new Road(vertices[i], vertices[Math.floorMod(i+1,6)]);
        }
    }

    private Pair<Integer, String> getResourcePair(){
    //TODO: see Board.giveVerticesResourcePairs()
        return new Pair<Integer, String>(tileRollNumber,tileResource);
    }

    public Tile(int y, int x){
        row = y;
        col = x;
        vertices = new Vertex[6];
        roads = new Road[6];
    }
    
}