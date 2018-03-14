package pieces;

import boards.Position;

/**
 * Standard Knight Piece
 * 
 * Can move in an L shape (2 up 1 left, 2 left 1 up, 2 right 1 up, 2 up 1 right, etc...)
 * Can jump pieces
 * Captures directly on end of L
 *
 * @author Drew Grubb
 */
public class Knight extends Piece
{

	/**
	 * Instantiates new Knight
	 * @param pieceColor
	 * @param position
	 */
	public Knight(int pieceColor, Position position)
	{
		super(pieceColor, position);
		
		if(pieceColor == Piece.WHITE)
			setImage("res/Knight_WHITE.png");
		if(pieceColor == Piece.BLACK)
			setImage("res/Knight_BLACK.png");
		if(pieceColor == Piece.GREEN)
			setImage("res/Knight_GREEN.png");
		if(pieceColor == Piece.BLUE)
			setImage("res/Knight_BLUE.png");
	}

	@Override
	public void updatePossibleMoves()
	{
		
	}

	@Override
	public int getPieceValue()
	{
		return 0;
	}

}
