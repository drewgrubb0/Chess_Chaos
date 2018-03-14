package pieces;

import boards.Position;

/**
 * Standard Queen Piece
 * 
 * Combined move set of rook and bishop
 * Can move up/down left/right or diagonally
 * Moves until it hits a wall or a piece
 * Captures up/down left/right or diagonally
 *
 * @author Drew Grubb
 */
public class Queen extends Piece
{

	/**
	 * Instantiates new Queen
	 * @param pieceColor
	 * @param position
	 */
	public Queen(int pieceColor, Position position)
	{
		super(pieceColor, position);
		
		if(pieceColor == Piece.WHITE)
			setImage("res/Queen_WHITE.png");
		if(pieceColor == Piece.BLACK)
			setImage("res/Queen_BLACK.png");
		if(pieceColor == Piece.GREEN)
			setImage("res/Queen_GREEN.png");
		if(pieceColor == Piece.BLUE)
			setImage("res/Queen_BLUE.png");
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
