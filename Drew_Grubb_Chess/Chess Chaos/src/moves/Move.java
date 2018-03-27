package moves;

import pieces.Piece;

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
	private Position prevPos;
	private Position newPos;
	private Piece capturedPiece;

	/**
	 * Creates a new Move based off Position settings
	 */
	public Move(Position prevPos, Position newPos)
	{
		this.prevPos = prevPos;
		this.newPos = newPos;
		this.capturedPiece = null;
	}
	
	/**
	 * Creates a new Move based off current position offsets
	 */
	public Move(Position prevPos, int xOff, int yOff)
	{
		this.prevPos = prevPos;
		this.newPos = new Position(prevPos.getPosX() + xOff, prevPos.getPosY() + yOff);
		this.capturedPiece = null;
	}
	
	/**
	 * Creates a special Move for En Passant that keeps track of the removed pawn location.
	 */
	public Move(Position prevPos, int xOff, int yOff, Piece capturedPiece)
	{
		this.prevPos = prevPos;
		this.newPos = new Position(prevPos.getPosX() + xOff, prevPos.getPosY() + yOff);
		this.capturedPiece = capturedPiece;
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
	
	@Override
	public String toString()
	{
		return prevPos + " -> " + newPos;
	}
	
	//Setters
	
	public void setCapturedPiece(Piece capturedPiece)
	{
		this.capturedPiece = capturedPiece;
	}
	
	//Getters
	
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
	 * @return capturedPiece
	 */
	public Piece getCapturedPiece()
	{
		return capturedPiece;
	}
}
