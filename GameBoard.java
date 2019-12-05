import javax.swing.*;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.*;
public class GameBoard extends JPanel{
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
	
	private Image redSettlement;
	private Image redCity;
	private Image redRoadVert;
	private Image redRoadDown;
	private Image redRoadUp;
	private Image greenSettlement;
	private Image greenCity;
	private Image greenRoadVert;
	private Image greenRoadDown;
	private Image greenRoadUp;
	private Image blueSettlement;
	private Image blueCity;
	private Image blueRoadVert;
	private Image blueRoadDown;
	private Image blueRoadUp;
	private Image orangeSettlement;
	private Image orangeCity;
	private Image orangeRoadVert;
	private Image orangeRoadDown;
	private Image orangeRoadUp;
	private Image[][] settlements;
	private Image[][] roads;
	
	private boolean[][] vertexNumbers;
	private boolean displayTileCount;
	
	private Image[] numbersSmall;
	
	private Image[] row1;
	private Image[] row2;
	private Image[] row3;
	private Image[] row4;
	private Image[] row5;
	private Image[][] images;
	private int[][] tileNumber;

	private Image robber;
	private int robberX;
	private int robberY;
	
	private int baseX;
	private int baseY;
	
	
	public GameBoard(Tile[][] tiles){
		//loadBackGround();
		setPreferredSize(new Dimension(1520, 1001));
		setVisible(true);
		initializeBooleanArrays();
		numbersSmall = new Image[10];
		loadImages();
		displayTileCount = true;
		//setTiles(tiles);
		createImageArrays(tiles);
		createMainArray();
	}
	
	protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
    }
	
	private void doDrawing(Graphics g) {   
        Graphics2D g2d = (Graphics2D) g;
		
		//get the size of the panel
		Dimension size = getSize();
		int sizeW = (int) size.getWidth();
        int sizeH = (int) size.getHeight();
		baseX = ((sizeW / 2) - 520);
		baseY = ((sizeH / 2) - 420);
		
		if (baseX < 0)
			baseX = 0;
		if (baseY < 0)
			baseY = 0;
		
		int count = 1;
		int countRow =  0;
		for (Image[] row : images){
			int xInit = (baseX + (Math.abs(countRow - 2)) * 105);
			int countCol = 0;
            for (Image i : row){
				int x = (xInit + (210 * countCol));
				int y = (baseY + (160 * (countRow)));
                g2d.drawImage(i, x, y, this);
				if (displayTileCount){
					if ((countRow == robberY) &&( countCol == robberX))
						g2d.drawImage(robber, x+75, y+49, this);
					else
						g2d.drawImage(getNumberImage(tileNumber[countRow][countCol]), (x + adjustX(tileNumber[countRow][countCol])), (y + 75), this);
				}
				else {
					if (count < 10){
						g2d.drawImage(getSmallNumberImage(getSecondDigit(count)), x+90, y+85, this);
					}
					else {
						g2d.drawImage(getSmallNumberImage(getFirstDigit(count)), x+80, y+85, this);
						g2d.drawImage(getSmallNumberImage(getSecondDigit(count)), x+100, y+85, this);
					}
				}
				count++;
				countCol++;
            }
			countRow++;
        }
		count = 1;
		for (int i = 0; i < 6; i++){
			int gapModifier = 0;
			for (int j = 0; j < 11; j++){
				int xInit = 0;
				int x = 0;
				int y = 0;
				if ((i - 2) <= 0){
					xInit = (baseX + (Math.abs(i - 2)) * 105);
					if (((j % 2) == 0) && (j != 0))
						gapModifier += 5;
					
					x = (xInit + (j * 100) + gapModifier);
					if (((j % 2) == 0) && (j != 0))
						gapModifier += 5;
					
					if ((j % 2) == 0)
						y = (baseY + (160 * i) + 50);
					else
						y = (baseY + (160 * i));
				}
				else {
					xInit = (baseX + (i - 3) * 105);
					if (((j % 2) == 0) && (j != 0))
						gapModifier += 5;
					
					x = (xInit + (j * 100) + gapModifier);
					if (((j % 2) == 0) && (j != 0))
						gapModifier += 5;
					
					if ((j % 2) == 0)
						y = (baseY + (160 * i));
					else
						y = (baseY + (160 * i) + 50);
				}//close if 
				
				if (vertexNumbers[i][j]){
					if (count < 10){
						g2d.drawImage(getSmallNumberImage(count), x-12, y-20, this);
					}
					else {
						g2d.drawImage(getSmallNumberImage(getFirstDigit(count)), x-20, y-20, this);
						g2d.drawImage(getSmallNumberImage(getSecondDigit(count)), x, y-20, this);
					}
					count++;
				}//end if of boolean array
				
			}//close for j
		}//close for i	
		
		for (int i = 0; i < 6; i++){
			int gapModifier = 0;
			for (int j = 0; j < 11; j++){
				int xInit = 0;
				int x = 0;
				int y = 0;
				if ((i - 2) <= 0){
					xInit = (baseX + (Math.abs(i - 2)) * 105);
					if (((j % 2) == 0) && (j != 0))
						gapModifier += 5;
					
					x = (xInit + (j * 100) + gapModifier);
					if (((j % 2) == 0) && (j != 0))
						gapModifier += 5;
					
					if ((j % 2) == 0)
						y = (baseY + (160 * i) + 50);
					else
						y = (baseY + (160 * i));
				}
				else {
					xInit = (baseX + (i - 3) * 105);
					if (((j % 2) == 0) && (j != 0))
						gapModifier += 5;
					
					x = (xInit + (j * 100) + gapModifier);
					if (((j % 2) == 0) && (j != 0))
						gapModifier += 5;
					
					if ((j % 2) == 0)
						y = (baseY + (160 * i));
					else
						y = (baseY + (160 * i) + 50);
				}//close if 
				g2d.drawImage(settlements[i][j], x-20, y-28, this);
				
			}//close for j
		}//close for i
    }
	
	public void setSettlementImage(int row, int col, int player){
		switch (player){
			case 0:
				if (settlements[row][col] == null)
					settlements[row][col] = redSettlement;
				else
					settlements[row][col] = redCity;
			case 1:
				if (settlements[row][col] == null)
					settlements[row][col] = greenSettlement;
				else
					settlements[row][col] = greenCity;
			case 2:
				if (settlements[row][col] == null)
					settlements[row][col] = blueSettlement;
				else
					settlements[row][col] = blueCity;
			case 3:
				if (settlements[row][col] == null)
					settlements[row][col] = orangeSettlement;
				else
					settlements[row][col] = orangeCity;
		}
	}
	
	public void setRobberLocation(int row, int col){
		robberY = row;
		robberX = col;
	}
	
	public void setVertexArray(boolean[][] bool){
		vertexNumbers = bool;
	}
	
	public void resetVertexArray(){
		for (int i = 0; i < 6; i++){
			for (int j = 0; j < 11; j++){
				vertexNumbers[i][j] = false;
			}
		}
	}
	
	public void turnTileCountOn(){
		displayTileCount = false;
	}
	
	public void turnTileCountOff(){
		displayTileCount = true;
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
		redSettlement = new ImageIcon("pieces/redSettlement.png").getImage();
		redCity = new ImageIcon("pieces/redCity.png").getImage();
		redRoadVert = new ImageIcon("pieces/redRoad_Vertical.png").getImage();
		redRoadDown = new ImageIcon("pieces/redRoad_Down.png").getImage();
		redRoadUp = new ImageIcon("pieces/redRoad_Up.png").getImage();
		greenSettlement = new ImageIcon("pieces/greenSettlement.png").getImage();
		greenCity = new ImageIcon("pieces/greenCity.png").getImage();
		greenRoadVert = new ImageIcon("pieces/greenRoad_Vertical.png").getImage();
		greenRoadDown = new ImageIcon("pieces/greenRoad_Down.png").getImage();
		greenRoadUp = new ImageIcon("pieces/greenRoad_Up.png").getImage();
		blueSettlement = new ImageIcon("pieces/blueSettlement.png").getImage();
		blueCity = new ImageIcon("pieces/blueCity.png").getImage();
		blueRoadVert = new ImageIcon("pieces/blueRoad_Vertical.png").getImage();
		blueRoadDown = new ImageIcon("pieces/blueRoad_Down.png").getImage();
		blueRoadUp = new ImageIcon("pieces/blueRoad_Up.png").getImage();
		orangeSettlement = new ImageIcon("pieces/orangeSettlement.png").getImage();
		orangeCity = new ImageIcon("pieces/orangeCity.png").getImage();
		orangeRoadVert = new ImageIcon("pieces/orangeRoad_Vertical.png").getImage();
		orangeRoadDown = new ImageIcon("pieces/orangeRoad_Down.png").getImage();
		orangeRoadUp = new ImageIcon("pieces/orangeRoad_Up.png").getImage();
		robber = new ImageIcon("pieces/robber.png").getImage();
		numbersSmall[0] = new ImageIcon("numbers/zero_small.png").getImage();
		numbersSmall[1] = new ImageIcon("numbers/one_small.png").getImage();
		numbersSmall[2] = new ImageIcon("numbers/two_small.png").getImage();
		numbersSmall[3] = new ImageIcon("numbers/three_small.png").getImage();
		numbersSmall[4] = new ImageIcon("numbers/four_small.png").getImage();
		numbersSmall[5] = new ImageIcon("numbers/five_small.png").getImage();
		numbersSmall[6] = new ImageIcon("numbers/six_small.png").getImage();
		numbersSmall[7] = new ImageIcon("numbers/seven_small.png").getImage();
		numbersSmall[8] = new ImageIcon("numbers/eight_small.png").getImage();
		numbersSmall[9] = new ImageIcon("numbers/nine_small.png").getImage();
	}
	
	private void createImageArrays(Tile[][] tiles){
		settlements = new Image[6][11];
		roads = new Image[11][10];
		tileNumber = new int[5][5];
		for (int i = 0; i < 5; i++){
			for (int j = 0; j < 5; j++){
				tileNumber[i][j] = 1;
			}
		}
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
					if (getTileImage(t) == desert){
						robberX = countCol;
						robberY = countRow;
					}
				}
				if (countRow == 1){
					row2[countCol] = getTileImage(t);
					if (getTileImage(t) == desert){
						robberX = countCol;
						robberY = countRow;
					}
				}
				if (countRow == 2){
					row3[countCol] = getTileImage(t);
					if (getTileImage(t) == desert){
						robberX = countCol;
						robberY = countRow;
					}
				}
				if (countRow == 3){
					row4[countCol] = getTileImage(t);
					if (getTileImage(t) == desert){
						robberX = countCol;
						robberY = countRow;
					}
				}
				if (countRow == 4){
					row5[countCol] = getTileImage(t);
					if (getTileImage(t) == desert){
						robberX = countCol;
						robberY = countRow;
					}
				}
				countCol++;
			}
			countRow++;
		}
	}

	//gets what image to use
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
	
	//decide which image to use
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
	
	//switch case for adjusting tile number positions
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
	
	//creates the main array of images
	private void createMainArray(){
		images = new Image[5][];
		images[0] = row1;
		images[1] = row2;
		images[2] = row3;
		images[3] = row4;
		images[4] = row5;
	}
	
	private int getFirstDigit(int i){
		return ((i - getSecondDigit(i)) / 10);
	}
	
	private int getSecondDigit(int i){
		return (i%10);
	}
	
	private void initializeBooleanArrays(){
		vertexNumbers = new boolean[6][11];
		for (int i = 0; i < 5; i++){
			for (int j = 0; j < 11; j++){
				vertexNumbers[i][j] = false;
			}
		}
	}
	
	private Image getSmallNumberImage(int i){
		return numbersSmall[i];
	}
}//end class