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
		board.load();
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
	
	public void setResourcePanel(ArrayList<Integer> nums){
		resourcePanel.setArrayList(nums);
		loadBoard();
	}
	
	public int getIntegerInput(String text){
		String input = JOptionPane.showInputDialog(text);	
		int i = Integer.parseInt(input);
		return i;
	}
	
	public void errorMessage(String text){
		JOptionPane.showMessageDialog(null, text);
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