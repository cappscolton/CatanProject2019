import javax.swing.*;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Graphics2D;
public class HomeScreen extends JPanel {
	private Image backGround;
	private Image back;
	private Image pic;
	
	public HomeScreen(){
		setPreferredSize(new Dimension(1200, 800));
		setVisible(true);
		loadImages();
	}
	
	private void loadImages(){
		backGround = new ImageIcon("images/home.png").getImage();
		back = new ImageIcon("images/home_resized.png").getImage();
	}

	protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
    }
	
	private void resize(){
		Dimension size = getSize();
		int sizeW = (int) size.getWidth();
        int sizeH = (int) size.getHeight();
		
		//System.out.println(sizeW + "    " + sizeH);
		
		pic = backGround.getScaledInstance(1200, 800, Image.SCALE_DEFAULT);
	}
	
	private void doDrawing(Graphics g) {   
        Graphics2D g2d = (Graphics2D) g;
		
		//resize();
		
		g2d.drawImage(back, 0, 0, this);
		//g2d.drawImage(pic, 0, 0, this);
	}
	
	public void load(){
		repaint();
	}
}