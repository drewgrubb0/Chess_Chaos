package moves;

import java.io.Serializable;

import pieces.Piece;

/**
 * Class to represent moves on a Chess Board.
 * 
 * Created to keep track of moves for replaying and
 * for the sake of special moves like En Passant and Castling.
 *
 * @author Drew Grubb
 */
public class Move implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private Position prevPos;
	private Position newPos;
	private Piece capturedPiece;
	private int score;
	private int castle = 0;

	/**
	 * Creates a new Move based off Position settings
	 */
	public Move(Position prevPos, Position newPos)
	{
		this.prevPos = prevPos;
		this.newPos = newPos;
		this.capturedPiece = null;
		score = 1;
	}
	
	/**
	 * Creates a new Move based off current position offsets
	 */
	public Move(Position prevPos, int xOff, int yOff)
	{
		this.prevPos = prevPos;
		this.newPos = new Position(prevPos.getPosX() + xOff, prevPos.getPosY() + yOff);
		this.capturedPiece = null;
		score = 0;
	}
	
	/**
	 * Creates a special Move for En Passant that keeps track of the removed pawn location.
	 */
	public Move(Position prevPos, int xOff, int yOff, Piece capturedPiece)
	{
		this.prevPos = prevPos;
		this.newPos = new Position(prevPos.getPosX() + xOff, prevPos.getPosY() + yOff);
		this.capturedPiece = capturedPiece;
		score = 0;
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
	
	/**
	 * sets the score of the move for AI evaluation
	 * @param score
	 */
	public void setScore(int score)
	{
		this.score = score;
	}
	
	/**
	 * sets piece that is being captured
	 * @param capturedPiece
	 */
	public void setCapturedPiece(Piece capturedPiece)
	{
		this.capturedPiece = capturedPiece;
	}
	
	/**
	 * @param i the direction of which to castle, -1 for Left 1 for Right
	 */
	public void setCastle(int i)
	{
		this.castle = i;
	}
	
	//Getters
	
	/**
	 * @return score of the move
	 */
	public int getScore()
	{
		return score;
	}
	
	/**
	 * @return if move is a castle, the direction at which to place the rook
	 */
	public int getCastle()
	{
		return castle;
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
	 * @return capturedPiece
	 */
	public Piece getCapturedPiece()
	{
		return capturedPiece;
	}
}
