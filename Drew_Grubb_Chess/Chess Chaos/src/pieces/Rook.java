package pieces;

import boards.Position;

/**
 * Standard Rook Piece
 * 
 * Can move up/down and left/right until it hits a wall or a piece
 * Captures up/down and left/right
 *
 * @author Drew Grubb
 */
public class Rook extends Piece
{

	/**
	 * Instantiates new Pawn
	 * @param pieceColor
	 * @param position
	 */
	public Rook(int pieceColor, Position position)
	{
		super(pieceColor, position);
		
		if(pieceColor == Piece.WHITE)
			setImage("res/Rook_WHITE.png");
		if(pieceColor == Piece.BLACK)
			setImage("res/Rook_BLACK.png");
		if(pieceColor == Piece.GREEN)
			setImage("res/Rook_GREEN.png");
		if(pieceColor == Piece.BLUE)
			setImage("res/Rook_BLUE.png");
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
