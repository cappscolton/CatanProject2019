import javax.swing.*;
import java.util.*;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Dimension;
public class CardsPanel extends JPanel{
	private int knightCount;
	private int roadCount;
	private int yearCount;
	private int monopolyCount;
	private int victoryCount;
	
	private Image knightCard;
	private Image roadCard;
	private Image yearCard;
	private Image monopolyCard;
	private Image victoryCard;
	
	private Image[] numbers;
	private Image times;
	
	public CardsPanel(ArrayList<Character> cards){
		setVisible(true);
		setPreferredSize(new Dimension(1300, 950));
		totalCards(cards);
		numbers = new Image[10];
		loadImages();
	}//end constructor
	
	protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
    }
	
	private void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
		
		//knight
		g2d.drawImage(knightCard, 25, 25, this);
		g2d.drawImage(times, 335, 250, this);
		if (knightCount < 10){
			g2d.drawImage(getNumberImage(knightCount), 385, 240, this);
		}
		else {
			g2d.drawImage(getNumberImage(getFirstDigit(knightCount)), 375, 240, this);
			g2d.drawImage(getNumberImage(getSecondDigit(knightCount)), 415, 240, this);
		}
		
		//monopoly
		g2d.drawImage(monopolyCard, 25, 490, this);
		g2d.drawImage(times, 335, 670, this);
		if (monopolyCount < 10){
			g2d.drawImage(getNumberImage(monopolyCount), 385, 660, this);
		}
		else {
			g2d.drawImage(getNumberImage(getFirstDigit(monopolyCount)), 375, 660, this);
			g2d.drawImage(getNumberImage(getSecondDigit(monopolyCount)), 415, 660, this);
		}
		
		//year of plenty
		g2d.drawImage(yearCard, 450, 25, this);
		g2d.drawImage(times, 760, 250, this);
		if (yearCount < 10){
			g2d.drawImage(getNumberImage(yearCount), 820, 240, this);
		}
		else {
			g2d.drawImage(getNumberImage(getFirstDigit(yearCount)), 810, 240, this);
			g2d.drawImage(getNumberImage(getSecondDigit(yearCount)), 850, 240, this);
		}
		
		//RoadBuilding
		g2d.drawImage(roadCard, 450, 490, this);
		g2d.drawImage(times, 760, 670, this);
		if (roadCount < 10){
			g2d.drawImage(getNumberImage(roadCount), 820, 660, this);
		}
		else {
			g2d.drawImage(getNumberImage(getFirstDigit(roadCount)), 810, 660, this);
			g2d.drawImage(getNumberImage(getSecondDigit(roadCount)), 850, 660, this);
		}
		
		//Victory points
		g2d.drawImage(victoryCard, 875, 25, this);
		g2d.drawImage(times, 1185, 250, this);
		if (victoryCount < 10){
			g2d.drawImage(getNumberImage(victoryCount), 1245, 240, this);
		}
		else {
			g2d.drawImage(getNumberImage(getFirstDigit(victoryCount)), 1245, 240, this);
			g2d.drawImage(getNumberImage(getSecondDigit(victoryCount)), 1245, 240, this);
		}		
	}
	
	public void totalCards(ArrayList<Character> cards){
		knightCount=0;
		roadCount=0;
		yearCount=0;
		yearCount=0;
		monopolyCount=0;
		victoryCount=0;
		for (int i = 0; i < cards.size(); i++){
			switch (cards.get(i)){
				case 'K':
					knightCount++;
					break;
				case 'R':
					roadCount++;
					break;
				case 'P':
					yearCount++;
					break;
				case 'M':
					monopolyCount++;
					break;
				case 'V':
					victoryCount++;
					break;
			}//end switch
		}//end for i
	}
	
	private void loadImages(){
		knightCard = new ImageIcon("cards/knight.png").getImage();
		roadCard = new ImageIcon("cards/roadBuilding.png").getImage();
		yearCard = new ImageIcon("cards/yearOfPlenty.png").getImage();
		monopolyCard = new ImageIcon("cards/monopoly.png").getImage();
		victoryCard = new ImageIcon("cards/victoryPoint.png").getImage();
		numbers[0] = new ImageIcon("numbers/zero.png").getImage();
		numbers[1] = new ImageIcon("numbers/one.png").getImage();
		numbers[2] = new ImageIcon("numbers/two.png").getImage();
		numbers[3] = new ImageIcon("numbers/three.png").getImage();
		numbers[4] = new ImageIcon("numbers/four.png").getImage();
		numbers[5] = new ImageIcon("numbers/five.png").getImage();
		numbers[6] = new ImageIcon("numbers/six.png").getImage();
		numbers[7] = new ImageIcon("numbers/seven.png").getImage();
		numbers[8] = new ImageIcon("numbers/eight.png").getImage();
		numbers[9] = new ImageIcon("numbers/nine.png").getImage();
		times = new ImageIcon("numbers/x.png").getImage();
	}
	
	private int getFirstDigit(int i){
		return ((i - getSecondDigit(i)) / 10);
	}
	
	private int getSecondDigit(int i){
		return (i%10);
	}
	
	private Image getNumberImage(int i){
		switch (i){
			case 0:
				return numbers[0];
			case 1:
				return numbers[1];
			case 2:
				return numbers[2];
			case 3:
				return numbers[3];
			case 4:
				return numbers[4];
			case 5:
				return numbers[5];
			case 6:
				return numbers[6];
			case 7:
				return numbers[7];
			case 8:
				return numbers[8];
			case 9:
				return numbers[9];
		}
		return null;
	}
}