package pieces;

import boards.Position;

/**
 * Standard Bishop Piece
 * 
 * Can move diagonally until it hits a wall or piece
 * Captures diagonally
 *
 * @author Drew Grubb
 */
public class Bishop extends Piece
{

	/**
	 * Instantiates new Bishop
	 * @param pieceColor
	 * @param position
	 */
	public Bishop(int pieceColor, Position position)
	{
		super(pieceColor, position);
		
		if(pieceColor == Piece.WHITE)
			setImage("res/Bishop_WHITE.png");
		if(pieceColor == Piece.BLACK)
			setImage("res/Bishop_BLACK.png");
		if(pieceColor == Piece.GREEN)
			setImage("res/Bishop_GREEN.png");
		if(pieceColor == Piece.BLUE)
			setImage("res/Bishop_BLUE.png");
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
