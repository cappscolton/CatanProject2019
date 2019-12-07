import javax.swing.*;
import java.util.*;
import java.awt.*;

public class DevelopmentCards extends JFrame{
	private CardsPanel panel;
	private Menu menu;
	
	public DevelopmentCards(ArrayList<Character> cards){
		super("Development Cards");
		setUp();
		panel = new CardsPanel(cards);
		add(panel, BorderLayout.CENTER);
		menu = new Menu();
		menu.cardMenu();
		add(menu, BorderLayout.WEST);
		load();
	}
	
	public void updateCardsPanel(ArrayList<Character> cards){
		panel.totalCards(cards);
		load();
	}

	private void setUp(){
		setVisible(true);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setLayout(new BorderLayout());
	}
	
	public void load(){
		panel.repaint();
		menu.repaint();
		validate();
		pack();
	}
	
	public int getAction(){
		return menu.getCardMenuAction();
	}
}