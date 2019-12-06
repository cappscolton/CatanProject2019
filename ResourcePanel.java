import java.awt.Image;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Dimension;
import javax.swing.*;
import java.util.*;

public class ResourcePanel extends JPanel{
	private Image[] cards;
	private Image[] numbers;
	private Image times;
	private ArrayList<Integer> resources;
	
	
	public ResourcePanel(){
		setPreferredSize(new Dimension(250, 0));
		setVisible(true);
		loadImages();
		repaint();
		resources = new ArrayList<>(5);
		for (int i = 0; i < 5; i++){
			resources.add(i*4);
		}
	}
	
	protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
    }
	
	private void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
		//card image
		g2d.drawImage(cards[0], 0, 25, this);
		g2d.drawImage(cards[1], 0, 225, this);
		g2d.drawImage(cards[2], 0, 425, this);
		g2d.drawImage(cards[3], 0, 625, this);
		g2d.drawImage(cards[4], 0, 825, this);
		//x image
		for (int i = 0; i < 5; i++){
			g2d.drawImage(times, 110, (200 * i)+80, this);
		}
		//number image
		for (int i = 0; i < 5; i++){	
			if (resources.get(i) < 10){
				g2d.drawImage(getNumberImage(resources.get(i)), 175, (200 * i)+70, this);
			}
			else {
				g2d.drawImage(getNumberImage(getFirstDigit(resources.get(i))), 150, (200 * i)+70, this);
				g2d.drawImage(getNumberImage(getSecondDigit(resources.get(i))), 190, (200 * i)+70, this);
			}
		}
	}
	
	private void loadImages(){
		cards = new Image[5];
		numbers = new Image[10];
		cards[0] = new ImageIcon("cards/Brick.png").getImage(); 
		cards[1] = new ImageIcon("cards/Wood.png").getImage(); 
		cards[2] = new ImageIcon("cards/Rock.png").getImage(); 
		cards[3] = new ImageIcon("cards/Wheat.png").getImage(); 
		cards[4] = new ImageIcon("cards/Sheep.png").getImage(); 
		
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
	
	public void setArrayList(ArrayList<Integer> nums){
		resources = nums;
	}
	
	private int getFirstDigit(int i){
		return ((i - getSecondDigit(i)) / 10);
	}
	
	private int getSecondDigit(int i){
		return (i%10);
	}
	
	public void load(){
		revalidate();
	}
}