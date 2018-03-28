/**
 * 
 */
package input;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Class in charge of receiving and keeping track of input listener callbacks.
 * 
 * Is in charge of:
 * - Keeping track of mouse point at all times.
 * - Keeping track of mouse clicking at all times.
 * 
 * Is NOT in charge of:
 * - Doing anything with this information.
 * - Clearing this information unless called to do so.
 * 
 * @author Drew Grubb
 */
public class InputManager implements MouseListener, MouseMotionListener
{
	private boolean isClicking;
	private Point mousePosition;
	
	/**
	 * Instantiates new InputManager 
	 * to allow for Canvas integration
	 */
	public InputManager()
	{
		isClicking = false;
		mousePosition = new Point();
	}
	
	/**
	 * Clears any unused inputs remaining from last frame
	 * Important because it prevents "ghost clicking", 
	 * where holding down the mouse button and hovering over something clicks it.
	 */
	public void clearInputCallbacks()
	{
		isClicking = false;
	}
	
	@Override
	public void mouseClicked(MouseEvent e)
	{
		mousePosition = e.getPoint();
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		isClicking = true;
		mousePosition = e.getPoint();
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		isClicking = false;
		mousePosition = e.getPoint();
	}
	
	@Override
	public void mouseMoved(MouseEvent e)
	{
		mousePosition = e.getPoint();
	}
	
	@Override
	public void mouseDragged(MouseEvent e)
	{
		mousePosition = e.getPoint();
	}

	@Override
	public void mouseEntered(MouseEvent e){}
	
	@Override
	public void mouseExited(MouseEvent e){}

	/**
	 * @return is mouse being clicked
	 */
	public boolean isClicking()
	{
		return isClicking;
	}
	
	/**
	 * @return current known mouse position
	 */
	public Point getMousePosition()
	{
		return mousePosition;
	}
}
