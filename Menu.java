import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
public class Menu extends JPanel {
	//holds buttons
	private JButton[] buttons;
	//home buttons
	private ToggleButton newGame;
	//new game buttons
	private ToggleButton newBoard;
	private ToggleButton startGame;
	//inGame buttons
	private ToggleButton buildSettlement;
	private ToggleButton buildRoad;
	private ToggleButton upgradeSettlement;
	private ToggleButton developmentCards;
	private ToggleButton endTurn;
	//endGame buttons
	private ToggleButton homeScreen;
	//dev card buttons
	private ToggleButton useKnight;
	private ToggleButton useMonopoly;
	private ToggleButton useYearPlenty;
	private ToggleButton useBuildRoad;
	private ToggleButton closeWindow;
	
	public Menu(){
		setLayout(new GridLayout(0, 1));
		setPreferredSize(new Dimension(150, 0));
		homeMenu();
		//buttons = new ArrayList<JButton>(0);
		buttons = new JButton[4];
	}
	
	//adds and removes the menu buttons based on what the application is going to
	public void homeMenu(){
		newGame = new ToggleButton("New Game");
		add(newGame);
		
	}
	
	public void newGameMenu(){
		remove(newGame);
		
		newBoard = new ToggleButton("New Board");
		add(newBoard);
		startGame = new ToggleButton("Start Game");
		add(startGame);
	}
	
	public void inGameMenu(){
		remove(newBoard);
		remove(startGame);
		
		buildSettlement = new ToggleButton("Build Settlement");
		add(buildSettlement);
		buildRoad = new ToggleButton("Build Road");
		add(buildRoad);
		upgradeSettlement = new ToggleButton("Upgrade Settlement");
		add(upgradeSettlement);
		developmentCards = new ToggleButton("See Development Cards");
		add(developmentCards);
		endTurn = new ToggleButton("End Turn");
		add(endTurn);
	}
	
	public void endGameMenu(){
		remove(buildSettlement);
		remove(buildRoad);
		remove(upgradeSettlement);
		remove(developmentCards);
		remove(endTurn);
		
		homeScreen = new ToggleButton("Go Back To Home Screen");
		add(homeScreen);
	}
	
	public void cardMenu(){
		remove(newGame);
		
		useKnight = new ToggleButton("Use Knight");
		add(useKnight);
		useMonopoly = new ToggleButton("Use Monopoly");
		add(useMonopoly);
		useYearPlenty = new ToggleButton("Use Year of Plenty");
		add(useYearPlenty);
		useBuildRoad = new ToggleButton("Use Road Building");
		add(useBuildRoad);
		closeWindow = new ToggleButton("Close Window");
		add(closeWindow);
	}
	
	//gets if any of the buttons have been pressed
	//if a button is pressed reverts it back and passes 
	//which button was hit up through io to application
	public int getHomeAction(){
		if (newGame.getNewAction()){
			//System.out.println("1");
			newGame.resetActionPerformed();
			return 1;
		}
		else {
			System.out.print("");
			return 0;
		}
	}
	
	public int getNewGameAction(){
		if (newBoard.getNewAction()){
			newBoard.resetActionPerformed();
			return 1;
		}
		else if (startGame.getNewAction()){
			startGame.resetActionPerformed();
			return 2;
		}
		else {
			System.out.print("");
			return 0;
		}
	}
	
	public int getInGameAction(){
		if (buildSettlement.getNewAction()){
			buildSettlement.resetActionPerformed();
			return 1;
		}
		else if (buildRoad.getNewAction()){
			buildRoad.resetActionPerformed();
			return 2;
		}
		else if (upgradeSettlement.getNewAction()){
			upgradeSettlement.resetActionPerformed();
			return 3;
		}
		else if (developmentCards.getNewAction()){
			developmentCards.resetActionPerformed();
			return 4;
		}
		else if (endTurn.getNewAction()){
			endTurn.resetActionPerformed();
			return 5;
		}
		else {
			System.out.print("");
			return 0;
		}
	}
	
	public int getEndGameAction(){
		if (homeScreen.getNewAction()){
			homeScreen.resetActionPerformed();
			return 1;
		}
		else {
			System.out.print("");
			return 0;
		}
	}
	
	public int getCardMenuAction(){
		if (useKnight.getNewAction()){
			useKnight.resetActionPerformed();
			return 1;
		}
		else if (useMonopoly.getNewAction()){
			useMonopoly.resetActionPerformed();
			return 2;
		}
		else if (useYearPlenty.getNewAction()){
			useYearPlenty.resetActionPerformed();
			return 3;
		}
		else if (useBuildRoad.getNewAction()){
			useBuildRoad.resetActionPerformed();
			return 4;
		}
		else if (closeWindow.getNewAction()){
			closeWindow.resetActionPerformed();
			return 5;
		}
		else {
			System.out.print("");
			return 0;
		}
	}
}