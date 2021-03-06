import javax.swing.*;
import java.awt.Dimension;
import java.awt.Toolkit;
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
	private boolean checkVertexNumbers;
	private boolean[][] roadNumbers;
	private boolean checkRoadNumbers;
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
	
	private int player;
	private int VP;
	private int roll;
	private Image playerInfo;
	private Image[] numbers;
	
	private Image buildCosts;
	
	public GameBoard(Tile[][] tiles){
		//loadBackGround();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setPreferredSize(new Dimension(1500, 980));
		setVisible(true);
		initializeBooleanArrays();
		numbersSmall = new Image[10];
		numbers = new Image[10];
		loadImages();
		displayTileCount = true;
		//setTiles(tiles);
		createImageArrays(tiles);
		createMainArray();
		player = 0; 
		VP = 0; 
		roll = 0;
	}
	
	//overrides paintComponent
	protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
    }
	
	//contains the method for drawing sprites on the board
	//all of the components are placed by pixel counting
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
		//prints the players info 
		g2d.drawImage(playerInfo, 10, 10, this);
		g2d.drawImage(getBigNumberImage(player+1), 260, 20, this);
		
		if (VP < 10){
			g2d.drawImage(getBigNumberImage(VP), 140, 130, this);
		}
		else {
			g2d.drawImage(getBigNumberImage(getFirstDigit(VP)), 140, 130, this);
			g2d.drawImage(getBigNumberImage(getSecondDigit(VP)), 180, 130, this);
		}
		if (roll < 10){
			g2d.drawImage(getBigNumberImage(roll), 180, 240, this);
		}
		else {
			g2d.drawImage(getBigNumberImage(getFirstDigit(roll)), 180, 240, this);
			g2d.drawImage(getBigNumberImage(getSecondDigit(roll)), 220, 240, this);
		}
		
		//paint build cost card
		g2d.drawImage(buildCosts, 10, 650, this);
		
		
		//prints the tiles and their numbers
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
		
		//prints the settlements to the board
		for (int i = 0; i < 6; i++){
			int gapModifier = 0;
			int[] rowLengths  = {7,9,11,11,9,7};
			for (int j = 0; j < rowLengths[i]; j++){
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
		
		//prints integers for vertices
		if (checkVertexNumbers){
			count = 1;
			for (int i = 0; i < 6; i++){
				int gapModifier = 0;
				int[] rowLengths  = {7,9,11,11,9,7};
				for (int j = 0; j < rowLengths[i]; j++){
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
					}//end if of vertexNumbers
				}//close for j
			}//close for i
		}//end if checkVertexNumbers
		
		//printing the roads
		for (int i = 0; i < 11; i++){
			int xModifier = 110;
			int xInit = 0;
			int x = 0;
			int y = 0;
			int gapModifier = 0;
			int[] rowLengths = {6, 4, 8, 5, 10, 6, 10, 5, 8, 4, 6};
			for (int j = 0; j < rowLengths[i]; j++){
				//check if the roads are vertical or slanted
				//roads are on a slanted row
				if ((i % 2) == 0){
					if (i < 5){
						xInit = (baseX + (Math.abs((i - 4) / 2)) * 105) + 10;
						y = (baseY + ((i / 2) * 160));
					}
					else {
						xInit = (baseX + (Math.abs((i - 6) / 2)) * 105) + 10;
						y = (baseY + ((i / 2) * 160)-2);
					}
					if ((j % 2) == 0){
						if (j != 0)
							gapModifier +=10;
						x = (xInit + ((j/2) * 200) + gapModifier);
					}//end if ((j % 2) == 0)
					else {
						x = (xInit + ((j/2) * 200) + xModifier + gapModifier);
					}//end else ((j % 2) == 0)
				}
				else {
					y = (baseY + ((i / 2) * 160) + 65);
					xInit = (baseX + ((Math.abs((i/2) - 2)) * 105) - 9);
					x = xInit + (210 * j) ;
				}
				g2d.drawImage(roads[i][j], x, y, this);
					
			}//close for java
		}//close for i
		
		//printing numbers for the roads
		if (checkRoadNumbers){
			int xModifier = 105;
			//printing the roads
			count = 1;
			for (int i = 0; i < 11; i++){
				int xInit = 0;
				int x = 0;
				int y = 0;
				int gapModifier = 0;
				int[] rowLengths = {6, 4, 8, 5, 10, 6, 10, 5, 8, 4, 6};
				for (int j = 0; j < rowLengths[i]; j++){
					//check if the roads are vertical or slanted
					//roads are on a slanted row
					if ((i % 2) == 0){
						if (i < 5)
							xInit = (baseX + (Math.abs((i - 4) / 2)) * 105) + 45;
						else 
							xInit = (baseX + (Math.abs((i - 6) / 2)) * 105) + 45;
						y = (baseY + ((i / 2) * 160) + 25);
						if ((j % 2) == 0){
							if (j != 0)
								gapModifier +=10;
							x = (xInit + ((j/2) * 200) + gapModifier);
						}//end if ((j % 2) == 0)
						else {
							x = (xInit + ((j/2) * 200) + xModifier + gapModifier);
						}//end else ((j % 2) == 0)
					}
					else {
						y = (baseY + ((i / 2) * 160) + 100);
						xInit = (baseX + ((Math.abs((i/2) - 2)) * 105) - 9);
						x = xInit + (210 * j) + 3;
					}
					if (roadNumbers[i][j]){
						if (count < 10){
							g2d.drawImage(getSmallNumberImage(count), x-12, y-20, this);
						}
						else {
							g2d.drawImage(getSmallNumberImage(getFirstDigit(count)), x-20, y-20, this);
							g2d.drawImage(getSmallNumberImage(getSecondDigit(count)), x, y-20, this);
						}
						count++;
					}//end if of roadNumbers
				}//close for java
			}//close for i
		}//end if checkRoadNumbers
    }//end doDrawing
	
	//sets player to display who's turn it is
	public void setTurnInfo(int p, int vp, int r){
		player = p;
		VP = vp;
		roll = r;
	}
	
	//sets settlement array an image and if upgrading it will turn the settlement to a city
	public void setSettlementImage(int row, int col, int player){
		switch (player){
			case 0:
				if (settlements[row][col] == null)
					settlements[row][col] = redSettlement;
				else
					settlements[row][col] = redCity;
				break;
			case 1:
				if (settlements[row][col] == null)
					settlements[row][col] = greenSettlement;
				else
					settlements[row][col] = greenCity;
				break;
			case 2:
				if (settlements[row][col] == null)
					settlements[row][col] = blueSettlement;
				else
					settlements[row][col] = blueCity;
				break;
			case 3:
				if (settlements[row][col] == null)
					settlements[row][col] = orangeSettlement;
				else
					settlements[row][col] = orangeCity;
				break;
		}
	}
		
	public void setRoadImage(int row, int col, int player){
		switch (player){
			case 0:
				if ((row % 2) == 0){
					if (row < 5){
						if ((col % 2) == 0)
							roads[row][col] = redRoadUp;
						else 
							roads[row][col] = redRoadDown;
					}
					else{
						if ((col % 2) == 0)
							roads[row][col] = redRoadDown;
						else 
							roads[row][col] = redRoadUp;
					}
				}
				else {
					roads[row][col] = redRoadVert;
				}
				break;
			case 1:
				if ((row % 2) == 0){
					if (row < 5){
						if ((col % 2) == 0)
							roads[row][col] = greenRoadUp;
						else 
							roads[row][col] = greenRoadDown;
					}
					else{
						if ((col % 2) == 0)
							roads[row][col] = greenRoadDown;
						else 
							roads[row][col] = greenRoadUp;
					}
				}
				else {
					roads[row][col] = greenRoadVert;
				}
				break;
			case 2:
				if ((row % 2) == 0){
					if (row < 5){
						if ((col % 2) == 0)
							roads[row][col] = blueRoadUp;
						else 
							roads[row][col] = blueRoadDown;
					}
					else{
						if ((col % 2) == 0)
							roads[row][col] = blueRoadDown;
						else 
							roads[row][col] = blueRoadUp;
					}
				}
				else {
					roads[row][col] = blueRoadVert;
				}
				break;
			case 3:
				if ((row % 2) == 0){
					if (row < 5){
						if ((col % 2) == 0)
							roads[row][col] = orangeRoadUp;
						else 
							roads[row][col] = orangeRoadDown;
					}
					else{
						if ((col % 2) == 0)
							roads[row][col] = orangeRoadDown;
						else 
							roads[row][col] = orangeRoadUp;
					}
				}
				else {
					roads[row][col] = orangeRoadVert;
				}
				break;
		}
	}
	
	//sets the location of the robber
	public void setRobberLocation(int row, int col){
		robberY = row;
		robberX = col;
	}
	
	//get where vertexes are able to be placed so ints can be put for choosing
	public void setVertexArray(boolean[][] bool){
		vertexNumbers = bool;
		checkVertexNumbers = true;
	}
	//way to reset the boolean array
	public void resetVertexArray(){
		for (int i = 0; i < 6; i++){
			int[] rowLengths = {7,9,11,11,9,7};
			for (int j = 0; j < rowLengths[i]; j++){
				vertexNumbers[i][j] = false;
			}
		}
		checkVertexNumbers = false;
	}
	
	//set roadNumbers to true where a number is desired
	public void setRoadArray(boolean[][] bool){
		roadNumbers = bool;
		checkRoadNumbers = true;
	}
	//reset the array to all false
	public void resetRoadArray(){
		for (int i = 0; i < 11; i++){
			int[] rowLengths = {6, 4, 8, 5, 10, 6, 10, 5, 8, 4, 6};
			for (int j = 0; j < rowLengths[i]; j++){
				roadNumbers[i][j] = false;
			}
		}
		checkRoadNumbers = false;
	}
	
	//manipulate if the tiles should be numbered for placing the robber
	public void turnTileCountOn(){
		displayTileCount = false;
	}
	
	public void turnTileCountOff(){
		displayTileCount = true;
	}
	
	//load all the sprites used by the board class
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
		playerInfo = new ImageIcon("images/pInfo.png").getImage();
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
		buildCosts = new ImageIcon("images/Building_Costs.png").getImage();
	}
	
	//uses tiles to create an image array based on the tiles
	//get the initial robber X and Y
	private void createImageArrays(Tile[][] tiles){
		settlements = new Image[6][];
		int[] vertexRowLengths = {7,9,11,11,9,7};
		for (int i = 0; i < 6; i++){
			settlements[i] = new Image[vertexRowLengths[i]];
		}
		roads = new Image[11][];
		int[] roadRowLengths = {6, 4, 8, 5, 10, 6, 10, 5, 8, 4, 6};
		for (int i = 0; i < 11; i++){
			roads[i] = new Image[roadRowLengths[i]];
		}
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

	//gets what tile image to use
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
	
	//decide which white tile number image to use
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
	
	//used to convert int to two digits 
	private int getFirstDigit(int i){
		return ((i - getSecondDigit(i)) / 10);
	}
	
	private int getSecondDigit(int i){
		return (i%10);
	}
	
	//gets the int for the digit
	private Image getSmallNumberImage(int i){
		return numbersSmall[i];
	}
	
	//get the int of the digit
	private Image getBigNumberImage(int i){
		return numbers[i];
	}
	
	//initializes the array to all false
	private void initializeBooleanArrays(){
		vertexNumbers = new boolean[6][];
		int[] rowLengths = {7,9,11,11,9,7};
		for (int i = 0; i < 6; i++){
			boolean[] row = new boolean[rowLengths[i]];
			for (int j = 0; j < rowLengths[i]; j++){
				row[j] = false;
			}
			vertexNumbers[i] = row;
		}
		roadNumbers = new boolean[11][];
		int[] roadRowLengths = {6, 4, 8, 5, 10, 6, 10, 5, 8, 4, 6};
		for (int i = 0; i < 11; i++){
			boolean[] row = new boolean[roadRowLengths[i]];
			for (int j = 0; j < roadRowLengths[i]; j++){
				row[j] = false;
			}
			roadNumbers[i] = row;
		}
	}
}//end class