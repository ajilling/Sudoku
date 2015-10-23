/**
 * @author Adam Jilling
 * 
 * Puzzle object is the main grid on the screen that user will interact with.
 * It has either a easyGrid, mediumGrid, or hardGrid.
 */
import java.awt.Color;
import java.awt.Graphics;

public class Puzzle
{
	/**
	 * Will have a grid with blank cells
	 * int x, y: location
	 * Box[][] boxArray: array of Box objects to fill grid with
	 */
	Grid grid;
	Grid answerGrid;
	private int x, y;
	private final int WIDTH = 400;
	private final int BOX_WIDTH = 43;
	protected Box[][] boxArray = new Box[9][9];
	
	/**
	 * Puzzle constructor
	 * @param someX: x location
	 * @param someY: y location
	 * @param difficulty: easy, medium, or hard
	 */
	public Puzzle(int someX, int someY, int difficulty)
	{
		x = someX;
		y = someY;
		setup(difficulty);
	}
	
	/**
	 * Will set up puzzle using an int input to determine which level of
	 * difficulty to create. boxArray will be initialized here as well.
	 * @param difficulty
	 */
	private void setup(int difficulty)
	{
		if (difficulty == 0)
			grid = new EasyGrid();
		else if (difficulty == 1)
			grid = new MediumGrid();
		else grid = new HardGrid();
		int startX = x+6;
		int startY = y+6;
		
		// create our array of box objects
		for (int a = 0; a < boxArray.length; a++)
		{
			for (int b = 0; b < boxArray.length; b++)
			{
				// convert int value to a String
				String val = Integer.toString(grid.grid[a][b]);
				if (val.equals("0")) val = "";
				boxArray[a][b] = new Box(startX+(a*BOX_WIDTH), startY+(b*BOX_WIDTH), val, Color.BLACK);
			}
		}
	}
	
	/**
	 * Determine if a click is inside a particular box
	 * @param someX
	 * @param someY
	 * @return true if inside, false if not
	 */
	public boolean isInside(int someX, int someY)
	{
		return (someX >= x && someX <= (x+WIDTH) && someY >= y && someY <= (y+WIDTH));
	}
	
	/**
	 * Determines if a given box is able to be changed and updates
	 * its value if it is changeable
	 * @param a
	 * @param b
	 * @param value
	 */
	public void updateValue(int a, int b, int value)
	{
		if (boxArray[a][b].changeable == true)
		{
			String stringVal = Integer.toString(value);
			if (stringVal.equals("0")) stringVal = "";
			boxArray[a][b] = new Box(106+a*BOX_WIDTH, 146+b*BOX_WIDTH, stringVal, Color.BLUE);
			boxArray[a][b].changeable = true;
		}
	}
	
	/**
	 * Draws the outer border of the puzzle
	 * @param pane
	 */
	private void drawBorder(Graphics pane)
	{	
		pane.setColor(Color.BLACK);
		pane.fillRoundRect(x, y, WIDTH, WIDTH, 15, 15);
		
		pane.setColor(Color.ORANGE);
		pane.drawRoundRect(x, y, WIDTH, WIDTH, 15, 15);
	}
	
	/**
	 * Draws alternate 3x3 shades on puzzle to be able to tell each
	 * sub-square apart from each other
	 * @param pane
	 */
	private void drawShades(Graphics pane)
	{
		final int SIZE = 128;
		pane.setColor(new Color(208, 238, 242));
		pane.fillRect(x+8, y+8, SIZE, SIZE);
		pane.fillRect(x+SIZE+SIZE+8, y+SIZE+SIZE+8, SIZE, SIZE);
		pane.fillRect(x+SIZE+SIZE+8, y+8, SIZE, SIZE);
		pane.fillRect(x+8, y+SIZE+SIZE+8, SIZE, SIZE);
		pane.fillRect(x+8+SIZE, y+8+SIZE, SIZE, SIZE);
		
		pane.setColor(new Color(161, 207, 214));
		pane.fillRect(x+8+SIZE, y+8, SIZE, SIZE);
		pane.fillRect(x+8, y+SIZE+8, SIZE, SIZE);
		pane.fillRect(x+SIZE+SIZE+8, y+SIZE+8, SIZE, SIZE);
		pane.fillRect(x+SIZE+8, y+SIZE+SIZE+8, SIZE, SIZE);
	}
	
	/**
	 * Paint boxArray, border, and shades
	 * @param pane
	 */
	public void paint(Graphics pane)
	{
		drawBorder(pane);
		drawShades(pane);
		
		// draw each Box object
		for (int x = 0; x < boxArray.length; x++)
		{
			for (int y = 0; y < boxArray.length; y++)
			{
				boxArray[x][y].paint(pane);
			}
		}
	}
	
}
