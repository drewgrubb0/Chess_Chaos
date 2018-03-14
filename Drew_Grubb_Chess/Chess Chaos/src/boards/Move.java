package boards;

/**
 * Class to represent moves on a Chess Board.
 * 
 * Created to keep track of moves for replaying and
 * for the sake of special moves like En Passant and Castling.
 *
 * @author Drew Grubb
 */
public class Move
{
	Position prevPos;
	Position newPos;

	/**
	 * Creates a new Move based off Position changes
	 */
	public Move(Position prevPos, Position newPos)
	{
		this.prevPos = prevPos;
		this.newPos = newPos;
	}
	
	/**
	 * @return prevPosition
	 */
	public Position getPreviousPosition()
	{
		return prevPos;
	}

	/**
	 * @return newPosition
	 */
	public Position getNewPosition()
	{
		return newPos;
	}
	
	/**
	 * Overrides Object equals method to make Move
	 * comparable in a list.
	 */
	public boolean equals(Object m)
	{
		if(!(((Move) m).getPreviousPosition().equals(this.getPreviousPosition())))
			return false;
		if(!(((Move) m).getNewPosition().equals(this.getNewPosition())))
			return false;
		
		return true;
	}
}
