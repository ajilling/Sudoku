/**
 * @author Adam Jilling
 * 
 * Inherits from grid and creates a hard grid
 *
 */

public class HardGrid extends Grid
{
	/**
	 * Number of blank tiles to create
	 */
	private final int BLANKS = 50;
	
	/**
	 * Call the super constructor and alter blank values here
	 */
	public HardGrid()
	{
		super();
		fillBlanks(BLANKS);
	}

}