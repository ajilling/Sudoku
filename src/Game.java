/**
 * @author Adam Jilling
 * 
 * Instantiates all objects, draws them in the
 * window, and implements all of our mouse click methods
 */

import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane; // pop-up dialog box

public class Game extends Frame implements MouseListener
{
	/**
	 * creates a window object, a puzzle object that inherits a grid,
	 * easy, medium, hard buttons
	 * number buttons: 1 through 9, with a clear button
	 * hint and reset buttons
	 * a background image object
	 * int x and y values to use for our mouse coordinates
	 * long start and elapsed to be able to time a game
	 * int hintCounter to count the number of hints used
	 * a mouseVal which will communicate which button on the number bar is selected
	 */
	private Window myWindow;
	private Puzzle myPuzzle;
	private short[][] userGrid = new short[9][9];
	private short[][] answerGrid = new short[9][9];
	private Chooser easy, medium, hard;
	private Chooser one, two, three, four, five, six, seven, eight, nine, clear;
	private Chooser hint, reset;
	private final int WIDTH = 34;
	private Image img;
	private int x, y;
	private short mouseVal;
	private long start, elapsed;
	private int hintCounter = 0;
	
	/**
	 * Game constructor instantiates window, all objects and selects a
	 * medium level puzzle by default
	 */
	public Game()
	{
		setTitle("Sudoku");
		setLocation(100, 100);
		setSize(600, 730);
		setBackground(Color.GRAY);

		// allow window to close
		myWindow = new Window();
		addWindowListener(myWindow);
		
		myPuzzle = new Puzzle(100, 140, 1);
		easy = new Chooser(100, 70, 50, 120, "Easy", 18);
		medium = new Chooser(240, 70, 50, 120, "Medium", 18);
		hard = new Chooser(380, 70, 50, 120, "Hard", 18);
		
		// medium button selected by default
		medium.select();
		
		// get arrays for userGrid and answerGrid
		answerGrid = myPuzzle.grid.answerGrid;
		userGrid = myPuzzle.grid.grid;
		
		// start our timer by getting current system time
		start = System.currentTimeMillis();
		
		// create number bar buttons
		one = new Chooser (100, 560, WIDTH, WIDTH, "1", 16);
		two = new Chooser (140, 560, WIDTH, WIDTH, "2", 16);
		three = new Chooser (180, 560, WIDTH, WIDTH, "3", 16);
		four = new Chooser (220, 560, WIDTH, WIDTH, "4", 16);
		five = new Chooser (260, 560, WIDTH, WIDTH, "5", 16);
		six = new Chooser (300, 560, WIDTH, WIDTH, "6", 16);
		seven = new Chooser (340, 560, WIDTH, WIDTH, "7", 16);
		eight = new Chooser (380, 560, WIDTH, WIDTH, "8", 16);
		nine = new Chooser (420, 560, WIDTH, WIDTH, "9", 16);
		clear = new Chooser (460, 560, WIDTH, 40, "Clear", 10);
		
		hint = new Chooser(170, 620, 50, 120, "Hint!", 20);
		reset = new Chooser(310, 620, 50, 120, "Reset", 20);
		
		// create background image
		img = Toolkit.getDefaultToolkit().createImage("background.jpg");
		
		addMouseListener(this);
		setVisible(true);
	}
	
	/**
	 * Paint method will draw all objects
	 */
	public void paint(Graphics g)
	{
		// draw our background image
		g.drawImage(img, 0, 0, null);
		
		myPuzzle.paint(g);
		
		easy.paint(g);
		medium.paint(g);
		hard.paint(g);
		
		one.paint(g);
		two.paint(g);
		three.paint(g);
		four.paint(g);
		five.paint(g);
		six.paint(g);
		seven.paint(g);
		eight.paint(g);
		nine.paint(g);
		clear.paint(g);
		
		hint.paint(g);
		reset.paint(g);
	}
	
	/**
	 * Clear all selected numbers to ensure only one number is
	 * selected at a time
	 */
	private void clearNumSelect()
	{
		one.unselect();
		two.unselect();
		three.unselect();
		four.unselect();
		five.unselect();
		six.unselect();
	    seven.unselect();
		eight.unselect();
		nine.unselect();
		clear.unselect();
	}
	
	/**
	 * Clears all user-entered values from a puzzle grid
	 * The called updateValue method will ensure the default values remain
	 */
	private void resetGame()
	{
		for (int j = 0; j < 9; j++)
		{
			for (int k = 0; k < 9; k++)
			{
				myPuzzle.updateValue(j, k, 0);
			}
		}
	}
	
	/**
	 * Receives mouse-clicked coordinates and determines which box
	 * within the puzzle was clicked
	 * @param a
	 * @param b
	 */
	private void getBox(int a, int b)
	{
		int boxX, boxY;
		a = a - 110;
		b = b - 150;
		boxX = a / 43;
		boxY = b / 43;
		
		// updates value within myPuzzle and within userGrid
		myPuzzle.updateValue(boxX, boxY, mouseVal);
		userGrid[boxX][boxY] = mouseVal;
	}
	
	/**
	 * Finds a random box on the grid and fills in it's correct value,
	 * then updates userGrid with that value
	 */
	private void fillHint()
	{
		int a, b;
		
		// find a box that has not already been filled
		do {
			a = (int)(Math.random() * 9);
			b = (int)(Math.random() * 9);
		} while(userGrid[a][b] != 0);
		
		myPuzzle.updateValue(a, b, answerGrid[a][b]);
		userGrid[a][b] = answerGrid[a][b];
		repaint();
		checkWinner();
	}
	
	/**
	 * Compares each value of userGrid with answerGrid to determine
	 * if the puzzle is completed, show dialog if winner
	 */
	private void checkWinner()
	{
		// default value of winner is true
		boolean winner = true;
		
		// if any value is not correct, winner is false
		for (int x = 0; x < 9; x++)
		{
			for (int y = 0; y < 9; y++)
			{
				if (userGrid[y][x] != answerGrid[y][x]) winner = false;
			}
		}
		
		// show dialog if it is a winner
		if (winner)
		{
			// calculate time it took
			long end = System.currentTimeMillis();
			elapsed = (end - start) / 1000;
			
			// call method to format the time
			String time = formatTime(elapsed);
			hint.unselect();
			repaint();
			
			// show winner dialog box
			JOptionPane.showMessageDialog(null,
					"You finished the puzzle in " + time + " and used "
					+ hintCounter + " hints!");
		}
	}
	
	/**
	 * Receives a long value of total seconds of time
	 * @param time
	 * @return a String in mm:ss format or just number of seconds
	 * if less than 60
	 */
	private String formatTime(long time)
	{
		String timeString = "";
		int mins, secs;
		
		// no need to format if time is less than 60 seconds
		if (time < 60)
		{
			timeString = String.valueOf(time) + " seconds";
			return timeString;
		}
		
		// determine number of minutes and seconds
		mins = (int)time / 60;
		secs = (int)time % 60;
		
		// create a String in mm:ss format
		timeString = String.valueOf(mins) + ":" + String.valueOf(secs);
		
		return timeString;

	}
	
	public void mouseClicked(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	
	/**
	 * Determine where mouse was clicked and call appropriate methods
	 */
	public void mousePressed(MouseEvent e)
	{
		x = e.getX();
		y = e.getY();
		
		if (myPuzzle.isInside(x, y))
		{
			getBox(x, y);
			repaint();
			checkWinner();
		}
		
		if (one.isInside(x, y))
		{
			clearNumSelect();
			one.select();
			repaint();
			mouseVal = 1;
		}
		
		if (two.isInside(x, y))
		{
			clearNumSelect();
			two.select();
			repaint();
			mouseVal = 2;
		}
		
		if (three.isInside(x, y))
		{
			clearNumSelect();
			three.select();
			repaint();
			mouseVal = 3;
		}
		
		if (four.isInside(x, y))
		{
			clearNumSelect();
			four.select();
			repaint();
			mouseVal = 4;
		}
		
		if (five.isInside(x, y))
		{
			clearNumSelect();
			five.select();
			repaint();
			mouseVal = 5;
		}
		
		if (six.isInside(x, y))
		{
			clearNumSelect();
			six.select();
			repaint();
			mouseVal = 6;
		}
		
		if (seven.isInside(x, y))
		{
			clearNumSelect();
			seven.select();
			repaint();
			mouseVal = 7;
		}
		
		if (eight.isInside(x, y))
		{
			clearNumSelect();
			eight.select();
			repaint();
			mouseVal = 8;
		}
		
		if (nine.isInside(x, y))
		{
			clearNumSelect();
			nine.select();
			repaint();
			mouseVal = 9;
		}
		
		if (easy.isInside(x, y))
		{
			// if easy is already selected, do nothing
			if (!easy.isSelected())
			{
				myPuzzle = new Puzzle(100, 140, 0);
				answerGrid = myPuzzle.grid.answerGrid;
				userGrid = myPuzzle.grid.grid;
				start = System.currentTimeMillis();
				hintCounter = 0;
				easy.select();
				medium.unselect();
				hard.unselect();
				repaint();
			}
		}
		
		if (medium.isInside(x, y))
		{
			// if medium is already selected, do nothing
			if (!medium.isSelected())
			{
				myPuzzle = new Puzzle(100, 140, 1);
				answerGrid = myPuzzle.grid.answerGrid;
				userGrid = myPuzzle.grid.grid;
				start = System.currentTimeMillis();
				hintCounter = 0;
				easy.unselect();
				medium.select();
				hard.unselect();
				repaint();
			}
		}
		
		if (hard.isInside(x, y))
		{
			// if hard is already selected, do nothing
			if (!hard.isSelected())
			{
				myPuzzle = new Puzzle(100, 140, 2);
				answerGrid = myPuzzle.grid.answerGrid;
				userGrid = myPuzzle.grid.grid;
				start = System.currentTimeMillis();
				hintCounter = 0;
				easy.unselect();
				medium.unselect();
				hard.select();
				repaint();
			}
		}
		
		if (clear.isInside(x, y))
		{
			clearNumSelect();
			clear.select();
			repaint();
			mouseVal = 0;
		}
		
		if (reset.isInside(x, y))
		{
			resetGame();
			start = System.currentTimeMillis();
			hintCounter = 0;
			reset.select();
			repaint();
		}
		
		if (hint.isInside(x, y))
		{
			hint.select();
			hintCounter++;
			fillHint();
			repaint();
		}
	}
	
	/**
	 * Determine a mouse release and call appropriate methods
	 */
	public void mouseReleased(MouseEvent e)
	{
		if (reset.isInside(x, y))
		{
			reset.unselect();
			repaint();
		}
		
		if (hint.isInside(x, y))
		{
			hint.unselect();
			repaint();
		}
	}
	
}
