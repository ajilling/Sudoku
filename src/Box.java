/**
 * @author Adam Jilling
 * 
 * A box is an individual number element that makes up the Sudoku grid.
 * It will pull a single int value from the grid array and dress it up to
 * make it visually presentable in the puzzle on screen. An array of box
 * objects will then ultimately form the puzzle.
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Box
{
	/**
	 * Data members:
	 * int x, y: determine where on screen to be placed
	 * int centerX, centerY: will determine center of box to line up number
	 * String value: the int value will be converted to a string
	 *   to allow use of String's sizing methods to make sure the numbers
	 *   are centered
	 * Color fontColor: the color of the font to use
	 * boolean changeable: will ensure the user can only change non-default values
	 * int SIZE: the size of each box element
	 * 
	 */
	private int x, y, centerX, centerY;
	String value;
	Color fontColor = Color.BLACK;
	protected boolean changeable = false;
	private final int SIZE = 43;
	
	/**
	 * Constructor
	 * @param someX: x coordinate
	 * @param someY: y coordinate
	 * @param someValue: value to display
	 * @param color: color of font to use
	 * If value is blank, sets changeable to true
	 * Will also determine the centerX and centerY values here based on
	 * already known x, y, and size values
	 */
	public Box(int someX, int someY, String someValue, Color color)
	{
		x = someX;
		y = someY;
		value = someValue;
		fontColor = color;
		centerX = x + (SIZE / 2);
		centerY = y + (SIZE / 2);
		if (someValue.equals("")) changeable = true;
	}
	
	/**
	 * Determines if a user click is inside a box object
	 * @param someX
	 * @param someY
	 * @return true if inside, false if not
	 */
	public boolean isInside(int someX, int someY)
	{
		return (someX >= x && someX <= (x+SIZE) && someY >= y && someY <= (y+SIZE));
	}
	
	/**
	 * Paints each box object
	 * @param pane
	 */
	public void paint(Graphics pane)
	{
		// draw the border of the box
		pane.setColor(Color.BLACK);
		pane.drawRect(x, y, SIZE, SIZE);

		// set the font, font size, and font color
		pane.setColor(fontColor);
		pane.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
		
		// get size overall size of font to make sure it is centered within box
		int labelWidth = pane.getFontMetrics().stringWidth(value);
		int labelHeight = pane.getFontMetrics().getMaxAscent();
		
		// draw the string within the box
		pane.drawString(value, centerX - labelWidth / 2, centerY + labelHeight / 2);
	}
}
