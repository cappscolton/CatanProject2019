import javax.swing.*;
import java.util.*;
import java.awt.*;
public class IO extends JFrame{
	private GameBoard board;
	private Menu menu;
	private ResourcePanel resourcePanel;
	private HomeScreen home;
	
	public IO(){
		super("Catan");
		setUp();
	}
	
	private void setUp(){
		//setResizeable(false);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		menu = new Menu();
		add(menu, BorderLayout.WEST);
		resourcePanel = new ResourcePanel();
		home = new HomeScreen();
		homeScreen();
	}
	
	private void homeScreen(){
		add(home, BorderLayout.CENTER);
	}
	
	private void removeHomeScreen(){
		remove(home);
	}
	
	//row is the y, col is the x, player is the player 0-3
	//works for upgrading and placing
	public void setSettlement(int row, int col, int player){
		board.setSettlementImage(row, col, player);
	}
	
	//will set the robber location and move the number for the tile
	public void setRobberLocation(int row, int col){
		board.setRobberLocation(row, col);
	}
	
	//sets the resource panel to the array list of resources
	//will need to be changed every turn
	public void setResourcePanel(ArrayList<Integer> nums){
		resourcePanel.setArrayList(nums);
		loadBoard();
	}
	
	//if a point in the array is true it will issue an integer that counts every time its been placed
	public void setVertexLocations(boolean[][] bool){
		board.setVertexArray(bool);
	}
	
	//resets the vertex locations numbers will stay til this is called
	public void resetVertexLocations(){
		resetVertexArray();
	}
	
	//will get an integer from a pop up
	//String text will be displayed in the pop up as well
	public int getIntegerInput(String text){
		String input = JOptionPane.showInputDialog(text);	
		int i = Integer.parseInt(input);
		return i;
	}
	
	//displays a string good for errors or general info
	public void errorMessage(String text){
		JOptionPane.showMessageDialog(null, text);
	}

	public void backToHome(){
		remove(board);
		remove(resourcePanel);
		add(home);
		menu.homeMenu();
	}
	
	public void newGame(Tile[][] tiles){
		removeHomeScreen();
		board = new GameBoard(tiles);
		add(board, BorderLayout.CENTER);
		board.repaint();
		add(resourcePanel, BorderLayout.EAST);
		menu.newGameMenu();
	}
	
	public void newBoard(Tile[][] tiles){
		remove(board);
		board = new GameBoard(tiles);
		add(board, BorderLayout.CENTER);
		loadBoard();
	}
	
	public void startGame(){
		menu.inGameMenu();
	}
	
	public void endGame(){
		removeBoard();
	}
	
	public void removeBoard(){
		remove(board);
		remove(resourcePanel);
	}
	
	public void loadHome(){
		validate();
		pack();
		home.repaint();
	}
	
	public void loadBoard(){
		validate();
		pack();
		board.repaint();
	}
	
	public void loadGame(){
		//fit everthing around what is needed
		validate();
		pack();
		//calls to repaint the JPanel
		board.repaint();
		resourcePanel.repaint();
		menu.repaint();
	}

	public int getHomeAction(){
		return menu.getHomeAction();
	}
	
	public int getNewGameAction(){
		return menu.getNewGameAction();
	}
	
	public int getInGameAction(){
		return menu.getInGameAction();
	}
	
	public int getEndGameAction(){
		return menu.getEndGameAction();
	}
}