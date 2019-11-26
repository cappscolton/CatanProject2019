import javax.swing.*;
import java.awt.*;
public class IO extends JFrame{
	private GameBoard board;
	private Menu menu;
	
	public IO(Tile[][] tiles){
		super("Catan");
		setUp(tiles);
		load();
	}
	
	public void setUp(Tile[][] tiles){
		//setResizeable(false);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		//create what is meant to be seen
		board = new GameBoard(tiles);
		//add that to the frame
		add(board);
	}
	
	public void load(){
		board.load();
		//fit everthing around what is needed
		validate();
		pack();
		//calls to repaint the JPanels
		board.load();
	}
	
	
}