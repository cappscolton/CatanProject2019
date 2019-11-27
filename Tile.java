public class Tile{
    public Vertex[] vertices;
    public Road[] roads;
    private int row;
    private int col;//TODO: for UI stuff
    private int tileRollNumber;
    private String tileRollResource;

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

    public void createRoads(){
        for(int i=0; i<6; i++){
            roads[i] = new Road(vertices[i], vertices[Math.floorMod(i+1,6)]); //floor mod to link 5 to 0
        }
    }

    public void setTileRollNumber(int n){
        tileRollNumber = n;
    }

    public void setTileRollResource(String n){
        tileRollResource = n;
    }

    public int getTileRollNumber(){
        return tileRollNumber;
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