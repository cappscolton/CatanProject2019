public class Application{
    public static void main(String[] args){
        Board board = new Board();

        //example manipulating board
        Tile[][] tiles = board.getBoardData();
		
		IO io = new IO(tiles);
		/*
        for (Tile[] row : tiles){
            for (Tile t : row){
                System.out.println(t.resource);
                System.out.println(t.vertices[0].occupant); // assuming Player has toString()
            }
        }
		*/
        // every other method in Board.java is internal


        // iteration over tiles/vertices
        // all the info you need for gameplay should be stored in Tile and Vertex objects
        // robber designated by 0 for resource number, "desert" for resource string


        //BoardUI bUI = new BoardUI(board);
		
    }
}