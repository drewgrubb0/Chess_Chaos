package d_utils;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

/**
 * Button class that works to simplify a lot of the menu displaying, text
 * positioning, and input checking with AWT throughout this program.
 * 
 * Is in charge of:
 * - Rendering button in correct location when called to.
 * - When called, can see if a point is in it or if a point is clicking in it.
 * - Centering text in the middle of the button.
 * 
 * Is NOT in charge of and should be handled further up:
 * - Setting font sizes
 * - Checking if it is being hovered within or clicked in by some sort of manager
 * - Setting the correct render method to use
 * - Establishing default dimensions and position
 * 
 * @author Drew Grubb
 */
public class DButton
{
	private int x, y;
	private int width, height;
	private String text; 
	
	/**
	 * Creates a new DButton object with base values.
	 */
	public DButton()
	{
		this.x = 0;
		this.y = 0;
		
		this.width = 0;
		this.height = 0;
		
		this.text = "";
	}
	
	/**
	 * Creates a new DButton object with Rectangular dimensions
	 * @param Rect
	 */
	public DButton(Rectangle rect)
	{
		this.x = rect.x;
		this.y = rect.y;
		
		this.width = rect.width;
		this.height = rect.height;
		
		this.text = "";
	}
	
	/**
	 * Creates a new DButton object with Rectangular dimensions
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public DButton(int x, int y, int width, int height)
	{
		this.x = x;
		this.y = y;
		
		this.width = width;
		this.height = height;
		
		this.text = "";
	}
	
	/**
	 * Sets the text to be displayed in this button
	 */
	public void setText(String string)
	{
		this.text = string;
	}
	
	/**
	 * Clears button text so button is blank.
	 */
	public void clearText()
	{
		this.text = "";
	}
	
	/**
	 * Changes the dimensions of this button.
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public void setDimensions(int x, int y, int width, int height)
	{
		this.x = x;
		this.y = y;
		
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Checks to see if point p is inside this Button. 
	 * Useful for checking if a mouse is hovering over this button so an action can be performed.
	 * 
	 * @param point
	 * @return is point p within the button dimensions
	 */
	public boolean isHovering(Point point)
	{
		if(point.x < this.x)
			return false;
		if(point.x > this.x + this.width)
			return false;
		if(point.y < this.y)
			return false;
		if(point.y > this.y + this.height)
			return false;
		
		return true;
	}
	
	/**
	 * Checks to see if point p is inside this Button, provided that parameter isClicking is true. 
	 * Useful for checking if a mouse is clicking inside this button so an action can be performed.
	 * @param point
	 * @param isClicking
	 * @return is point p within the button dimensions while isClicking is true
	 */
	public boolean isClicking(Point point, boolean isClicking)
	{
		if(!isClicking)
			return false;
		
		if(point.x < this.x)
			return false;
		if(point.x > this.x + this.width)
			return false;
		if(point.y < this.y)
			return false;
		if(point.y > this.y + this.height)
			return false;
		
		return true;
	}
	
	/**
	 * Draws this button to the screen in its normal state
	 * given a particular graphics object.
	 * @param g
	 */
	public void render(Graphics2D g)
	{
		g.drawRect(x, y, width, height);
		renderText(g);
	}
	
	/**
	 * Draws this button to the screen in its hovered state
	 * given a particular graphics object.
	 * @param g
	 */
	public void renderHovered(Graphics2D g)
	{
		Color color = g.getColor();
		
		g.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 180));
		g.fillRect(x, y, width, height);
		
		g.setColor(color);
		
		g.drawRect(x, y, width, height);
		renderText(g);
	}
	
	/**
	 * Draws and centers the button text based off font,
	 * text length, and text height.
	 * @param g
	 */
	private void renderText(Graphics2D g)
	{
		FontMetrics metrics = g.getFontMetrics(g.getFont());
		
		int textX = x + (width - metrics.stringWidth(text)) / 2;
		int textY = y + ((height - metrics.getHeight()) / 2) + metrics.getAscent();
				
		g.drawString(text, textX, textY);
	}
}
