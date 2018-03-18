/**
 * 
 */
package input;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Listener to be used with Display to get clicked mouse events and mouse position
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
	public void mouseEntered(MouseEvent e){}
	
	@Override
	public void mouseExited(MouseEvent e){}

	@Override
	public void mouseDragged(MouseEvent e)
	{
		mousePosition = e.getPoint();
	}

	@Override
	public void mouseMoved(MouseEvent e)
	{
		mousePosition = e.getPoint();
	}

	public boolean isClicking()
	{
		return isClicking;
	}
	
	public void setClicking(boolean clicking)
	{
		isClicking = clicking;
	}
	
	public Point getMousePosition()
	{
		return mousePosition;
	}
}
