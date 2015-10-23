/**
 * @author Adam Jilling
 * 
 * Inherits from grid and creates a medium grid
 *
 */

public class MediumGrid extends Grid
{
	/**
	 * Number of blank tiles to create
	 */
	private final int BLANKS = 40;
	
	/**
	 * Call the super constructor and alter blank values here
	 */
	public MediumGrid()
	{
		super();
		fillBlanks(BLANKS);
	}

}