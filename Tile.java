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

    public boolean isRobbed = false;

    public static class Road {
        Vertex v1;
        Vertex v2;
        Player occupant;
        ArrayList<Road> adjacents = new ArrayList<Road>();
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

    public void createRoads(){
        for(int i=0; i<6; i++){
            roads[i] = new Road(vertices[i], vertices[Math.floorMod(i+1,6)]); //floor mod to link 5 to 0
        }
    }

    public void setTileRollNumber(int n){
        tileRollNumber = n;
    }

    public void setTileRollNumberCache(int n){
        tileRollNumberCache = n;
    }

    public void setTileRollResource(String n){
        tileRollResource = n;
    }

    public int getTileRollNumber(){
        return tileRollNumber;
    }

    public int getTileRollNumberCache(){
        return tileRollNumberCache;
    }

    public String getTileRollResource(){
        return tileRollResource;
    }

    public Tile(int y, int x){
        row = y;
        col = x;
        vertices = new Vertex[6];
        roads = new Road[6];
    }
    
}