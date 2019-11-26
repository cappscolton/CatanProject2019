import javax.swing.*;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Graphics2D;
public class GameBoard extends JPanel{
	private Dimension size;
	private int bgW;
	private int bgH;
	private Image bg;
	private Image desert;
	private Image field;
	private Image forest;
	private Image mountain;
	private Image plain;
	private Image plateau;
	private Image number2;
	private Image number3;
	private Image number4;
	private Image number5;
	private Image number6;
	private Image number8;
	private Image number9;
	private Image number10;
	private Image number11;
	private Image number12;
	private Image[] row1;
	private Image[] row2;
	private Image[] row3;
	private Image[] row4;
	private Image[] row5;
	private Image[][] images;
	private int[][] tileNumber;
	
	public GameBoard(Tile[][] tiles){
		//loadBackGround();
		setPreferredSize(new Dimension(1200, 800));
		setVisible(true);
		loadImages();
		//setTiles(tiles);
		createImageArrays(tiles);
		createMainArray();
		
	}
	
	public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
    }
	
	private void doDrawing(Graphics g) {
        
        Graphics2D g2d = (Graphics2D) g;

        //g2d.drawImage(bg, 0, 0, this);
		
		//get the size of the panel
		size = getSize();
		int sizeW = (int) size.getWidth();
        int sizeH = (int) size.getHeight();
		int baseX = ((sizeW / 2) - 520);
		int baseY = ((sizeH / 2) - 420);
		
		if (baseX < 0)
			baseX = 0;
		if (baseY < 0)
			baseY = 0;
		
		int countRow =  0;
		for (Image[] row : images){
			int xInit = (baseX + (Math.abs(countRow - 2)) * 105);
			int countCol = 0;
            for (Image i : row){
				int x = (xInit + (210 * countCol));
				int y = (baseY + (160 * (countRow)));
                g2d.drawImage(i, x, y, this);
				g2d.drawImage(getNumberImage(tileNumber[countRow][countCol]), (x + adjustX(tileNumber[countRow][countCol])), (y + 75), this);
				System.out.print(tileNumber[countRow][countCol] + "\t");
				countCol++;
            }
			System.out.println();
			countRow++;
        }
		
		
		/*
		g2d.drawImage(desert, 0, 0, this);
		System.out.println("desert");
		g2d.drawImage(field, 200, 0, this);
		System.out.println("field");
		g2d.drawImage(forest, 0, 200, this);
		System.out.println("forest");
		g2d.drawImage(mountain, 200, 200, this);
		System.out.println("mountain");
		g2d.drawImage(plain, 400, 0, this);
		System.out.println("plain");
		g2d.drawImage(plateau, 400, 200, this);
		System.out.println("plateau");
		*/
    }
	
	private void loadImages(){
        desert = new ImageIcon("tiles/Desert.png").getImage(); 
        field = new ImageIcon("tiles/Field.png").getImage();
        forest = new ImageIcon("tiles/Forest.png").getImage();
        mountain = new ImageIcon("tiles/Mountain.png").getImage();
        plain = new ImageIcon("tiles/Plain.png").getImage();
        plateau = new ImageIcon("tiles/Plateau.png").getImage();
		number2 = new ImageIcon("numbers/2.png").getImage();
		number3 = new ImageIcon("numbers/3.png").getImage();
		number4 = new ImageIcon("numbers/4.png").getImage();
		number5 = new ImageIcon("numbers/5.png").getImage();
		number6 = new ImageIcon("numbers/6.png").getImage();
		number8 = new ImageIcon("numbers/8.png").getImage();
		number9 = new ImageIcon("numbers/9.png").getImage();
		number10 = new ImageIcon("numbers/10.png").getImage();
		number11 = new ImageIcon("numbers/11.png").getImage();
		number12 = new ImageIcon("numbers/12.png").getImage();
	}
	
	private void createImageArrays(Tile[][] tiles){
		tileNumber = new int[5][5];
		row1 = new Image[3];
		row2 = new Image[4];
		row3 = new Image[5];
		row4 = new Image[4];
		row5 = new Image[3];
		int countRow = 0;
		for (Tile[] row : tiles){
			int countCol = 0;
			for (Tile t : row){
				tileNumber[countRow][countCol] = t.getTileRollNumber();
				if (countRow == 0){
					row1[countCol] = getTileImage(t);
				}
				if (countRow == 1){
					row2[countCol] = getTileImage(t);
				}
				if (countRow == 2){
					row3[countCol] = getTileImage(t);
				}
				if (countRow == 3){
					row4[countCol] = getTileImage(t);
				}
				if (countRow == 4){
					row5[countCol] = getTileImage(t);
				}
				countCol++;
			}
			countRow++;
		}
	}
	
	private void loadBackGround(){
        //creates an image icon and an image of the background
        ImageIcon back = new ImageIcon("C:/Users/cwals/OneDrive/Desktop/catan/tiles/Desert.png");
        bg = back.getImage(); 
        
		//sets width and height of the background
        bgW = bg.getWidth(null);
        bgH = bg.getHeight(null);
    }
	
	public void load(){
		repaint();
	}
	
	private Image getTileImage(Tile t){
		if (t.getTileRollResource().equals("desert"))
			return desert;
		if (t.getTileRollResource().equals("wheat"))
			return field;
		if (t.getTileRollResource().equals("wood"))
			return forest;
		if (t.getTileRollResource().equals("ore"))
			return mountain;
		if (t.getTileRollResource().equals("sheep"))
			return plain;
		if (t.getTileRollResource().equals("brick"))
			return plateau;
		return null;
	}
	
	private Image getNumberImage(int i){
		switch (i){
			case 2:
				return number2;
			case 3:
				return number3;
			case 4:
				return number4;
			case 5:
				return number5;
			case 6: 
				return number6;
			case 8:
				return number8;
			case 9:
				return number9;
			case 10:
				return number10;
			case 11:
				return number11;
			case 12:
				return number12;
		}
		return null;
	}
	
	private int adjustX(int i){
		switch (i){
			case 2:
				return 78;
			case 3:
				return 80;
			case 4:
				return 78;
			case 5:
				return 82;
			case 6: 
				return 78;
			case 8:
				return 82;
			case 9:
				return 79;
			case 10:
				return 72;
			case 11:
				return 71;
			case 12:
				return 70;
		}
		return 0;
	}
	
	private void createMainArray(){
		images = new Image[5][];
		images[0] = row1;
		images[1] = row2;
		images[2] = row3;
		images[3] = row4;
		images[4] = row5;
	}
}