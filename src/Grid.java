/**
 * @author Adam Jilling
 * 
 * Creates a new valid, randomized Sudoku grid
 * Since easyGrid, mediumGrid, and hardGrid will inherit from this,
 * Grid class itself is abstract
 */

public abstract class Grid
{
	/**
	 * two arrays: the grid we create and a copy for the answer key
	 */
	protected short grid[][] = new short[9][9];
	protected short answerGrid[][] = new short[9][9];
	
	/**
	 * Constructor will create a grid and a copy of grid
	 */
	public Grid()
	{
		fillDefault();
		shuffle();
		
		// copy filled out grid to answer grid before blanks are added
		for (int c = 0; c < 9; c++)
		{
			for (int j = 0; j < 9; j++)
			{
				answerGrid[j][c] = grid[j][c];
			}
		}
	}
	
	/**
	 * Accessor method to get access to answer key
	 * @return answerGrid
	 */
	public short[][] getAnswerGrid()
	{
		return answerGrid;
	}
	
	/**
	 * Fills an arbitrary, already valid Sudoku grid
	 */
	private void fillDefault()
	{
		grid[0][0]=3; grid[1][0]=9; grid[2][0]=1; grid[3][0]=2; grid[4][0]=8;
			grid[5][0]=6; grid[6][0]=5; grid[7][0]=7; grid[8][0]=4;
		grid[0][1]=4; grid[1][1]=8; grid[2][1]=7; grid[3][1]=3; grid[4][1]=5;
			grid[5][1]=9; grid[6][1]=1; grid[7][1]=2; grid[8][1]=6;
		grid[0][2]=6; grid[1][2]=5; grid[2][2]=2; grid[3][2]=7; grid[4][2]=1;
			grid[5][2]=4; grid[6][2]=8; grid[7][2]=3; grid[8][2]=9;
		grid[0][3]=8; grid[1][3]=7; grid[2][3]=5; grid[3][3]=4; grid[4][3]=3;
			grid[5][3]=1; grid[6][3]=6; grid[7][3]=9; grid[8][3]=2;
		grid[0][4]=2; grid[1][4]=1; grid[2][4]=3; grid[3][4]=9; grid[4][4]=6;
			grid[5][4]=7; grid[6][4]=4; grid[7][4]=8; grid[8][4]=5;
		grid[0][5]=9; grid[1][5]=6; grid[2][5]=4; grid[3][5]=5; grid[4][5]=2;
			grid[5][5]=8; grid[6][5]=7; grid[7][5]=1; grid[8][5]=3;
		grid[0][6]=1; grid[1][6]=4; grid[2][6]=9; grid[3][6]=6; grid[4][6]=7;
			grid[5][6]=3; grid[6][6]=2; grid[7][6]=5; grid[8][6]=8;
		grid[0][7]=5; grid[1][7]=3; grid[2][7]=8; grid[3][7]=1; grid[4][7]=4;
			grid[5][7]=2; grid[6][7]=9; grid[7][7]=6; grid[8][7]=7;
		grid[0][8]=7; grid[1][8]=2; grid[2][8]=6; grid[3][8]=8; grid[4][8]=9;
			grid[5][8]=5; grid[6][8]=3; grid[7][8]=4; grid[8][8]=1;
	}
	
	/**
	 * Find any two rows or columns that are within the same 3x3 parent row or column and swap
	 * them with each other. For example, row 1 and 2 could be swapped but not row 2 and 7.
	 * This will happen between 100,000 and 1,000,000 times for both
	 */
	private void shuffle()
	{
		// create a random number between 100000 and 1000000, number of shuffles to execute
		int num = (int)(Math.random() * 100000 + 1000000);
		
		// two variables for columns or rows to swap
		int n1, n2;
		
		// keep track if the rows or columns are eligible for a swap
		boolean valid;
		
		for (int i = 0; i < num; i++)
		{
			valid = false;
				
			// get 2 rows or columns to swap,
			// then check to see if they are both valid to be swapped
			do {
				n1 = (int)(Math.random() * 9);
				n2 = (int)(Math.random() * 9);
				valid = isValid(n1, n2);
			} while(!valid);
			
			// once we have valid values, swap that column and row
			swapColumns(n1, n2);
			swapRows(n1, n2);
		}
	}
	
	/**
	 * Swap one row with another, creates a temp variable to do this
	 * @param row1
	 * @param row2
	 */
	private void swapRows(int row1, int row2)
	{
		short temp;
		
		// loop through and swap values 0 through 8 with each other
		for (int i = 0; i < grid.length; i ++)
		{
			temp = grid[i][row1];
			grid[i][row1] = grid[i][row2];
			grid[i][row2] = temp;
		}
	}
	
	/**
	 * Swap one column with another, creates a temp variable to do this
	 * @param col1
	 * @param col2
	 */
	private void swapColumns(int col1, int col2)
	{
		short temp;
		
		// loop through and swap values 0 through 8 with each other
		for (int i = 0; i < grid.length; i ++){
			temp = grid[col1][i];
			grid[col1][i] = grid[col2][i];
			grid[col2][i] = temp;
		}
	}
	
	/**
	 * determines if any 2 rows or columns are valid to swap with each other
	 * i.e. if they are in the same 3x3 parent row or column
	 * @param num1
	 * @param num2
	 * @return if valid or not
	 */
	private boolean isValid(int num1, int num2)
	{
		if (num1 < 3)
		{
			if (num2 < 3 && num2 != num1) return true;
		}
		else if (num1 < 6)
		{
			if (num2 < 6 && num2 > 2 && num2 != num1) return true;
		}
		else if (num1 > 5 && num2 > 5 && num2 != num1) return true;
		
		// if none of these conditions are met, they are not valid
		return false;
	}
	
	/**
	 * After grid is initialized this will determine our blank values,
	 * the numbers to remove to create a playable Sudoku puzzle
	 * @param BLANKS
	 */
	protected void fillBlanks(int BLANKS)
	{
		for (int i = 0; i < BLANKS; i++)
		{
			int a, b;
			
			// find random spots in the puzzle that are not already blank
			do {
				a = (int)(Math.random() * 9);
				b = (int)(Math.random() * 9);
			} while(grid[a][b] == 0);
			
			// make them blank, or zero
			grid[a][b] = 0;
		}
	}
	
}