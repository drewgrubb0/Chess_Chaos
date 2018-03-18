package pieces;

import boards.Move;
import boards.Position;

/**
 * Standard Pawn Piece
 * 
 * Can move "forward" once
 * Can move forward twice if it has not moved yet
 * Captures diagonally one space
 *
 * @author Drew Grubb
 */
public class Pawn extends Piece
{

	/**
	 * Instantiates new Pawn
	 * @param pieceColor
	 * @param position
	 */
	public Pawn(int pieceColor, Position position)
	{
		super(pieceColor, position);
		
		if(pieceColor == Piece.WHITE)
			setImage("res/Pawn_WHITE.png");
		if(pieceColor == Piece.BLACK)
			setImage("res/Pawn_BLACK.png");
		if(pieceColor == Piece.GREEN)
			setImage("res/Pawn_GREEN.png");
		if(pieceColor == Piece.BLUE)
			setImage("res/Pawn_BLUE.png");
	}

	@Override
	public void updatePossibleMoves()
	{
		if(pieceColor == Piece.WHITE)
		{
			
		}
		if(pieceColor == Piece.BLACK)
		{
			
		}
		if(pieceColor == Piece.GREEN)
		{
			
		}
		if(pieceColor == Piece.BLUE)
		{
			
		}
	}

	@Override
	public int getPieceValue()
	{
		return 0;
	}

}
