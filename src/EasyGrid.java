/**
 * @author Adam Jilling
 * 
 * Inherits from grid and creates an easy grid
 *
 */

public class EasyGrid extends Grid
{
	/**
	 * Number of blank tiles to create
	 */
	private final int BLANKS = 25;
	
	/**
	 * Call the super constructor and alter blank values here
	 */
	public EasyGrid()
	{
		super();
		fillBlanks(BLANKS);
	}

}
