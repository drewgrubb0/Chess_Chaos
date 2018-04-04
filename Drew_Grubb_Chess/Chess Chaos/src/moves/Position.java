package moves;

import java.io.Serializable;

/**
 * Provides readability and comparability for Piece location on the board
 * Mostly used for keeping track of moves
 * 
 * @author Drew Grubb
 */
public class Position implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private int posX;
	private int posY;

	/**
	 * Creates a new Position based off x,y location
	 */
	public Position(int x, int y)
	{
		this.posX = x;
		this.posY = y;
	}

	/**
	 * @return X Position
	 */
	public int getPosX()
	{
		return posX;
	}
	
	/**
	 * @return Y Position
	 */
	public int getPosY()
	{
		return posY;
	}
	
	/**
	 * Overrides Object equals method to allow
	 * positions to be comparable in a list.
	 *
	 * @param p
	 * @return
	 */
	public boolean equals(Object p)
	{
		if(((Position) p).getPosX() != this.posX)
			return false;
		if(((Position) p).getPosY() != this.posY)
			return false;
		
		return true;
	}
	
	public String toString()
	{
		return "(" + posX + " : " + posY + ")";
	}
}
