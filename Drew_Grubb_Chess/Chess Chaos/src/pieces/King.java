package pieces;

import boards.Position;

/**
 * Standard King Piece
 * 
 * Can move anywhere around itself once
 * Captures anywhere immediately around itself
 * Goal of the game is to checkmate this piece
 *
 * @author Drew Grubb
 */
public class King extends Piece
{

	/**
	 * Instantiates new King
	 * @param pieceColor
	 * @param position
	 */
	public King(int pieceColor, Position position)
	{
		super(pieceColor, position);
		
		if(pieceColor == Piece.WHITE)
			setImage("res/King_WHITE.png");
		if(pieceColor == Piece.BLACK)
			setImage("res/King_BLACK.png");
		if(pieceColor == Piece.GREEN)
			setImage("res/King_GREEN.png");
		if(pieceColor == Piece.BLUE)
			setImage("res/King_BLUE.png");
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
