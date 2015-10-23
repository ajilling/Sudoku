/**
 * @author Adam Jilling
 * 
 * Allow us to close the window using the button in title bar
 */

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Window extends WindowAdapter
{
	public void windowClosing(WindowEvent event)
	{
		System.exit(0);
	}
}
