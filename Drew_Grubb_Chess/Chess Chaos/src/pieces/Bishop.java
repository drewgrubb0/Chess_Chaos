package pieces;

import moves.Move;
import moves.MoveSet;

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
	public Bishop(int pieceColor)
	{
		super(pieceColor);
		
		if(pieceColor == Piece.WHITE)
			setImage("res/Bishop_WHITE.png");
		if(pieceColor == Piece.BLACK)
			setImage("res/Bishop_BLACK.png");
		if(pieceColor == Piece.GREEN)
			setImage("res/Bishop_GREEN.png");
		if(pieceColor == Piece.BLUE)
			setImage("res/Bishop_BLUE.png");
		
		pieceType = PieceType.BISHOP;
	}

	@Override
	public MoveSet getPossibleMoves(boolean needsVerification)
	{
		MoveSet moves = new MoveSet(board);
		
		//Down-Right
		for(int x = 1, y = 1 ; x < (board.getLength() - currentPosition.getPosX()) && y < (board.getHeight() - currentPosition.getPosY()); x++, y++)
		{
			if(moves.tryMove(new Move(currentPosition, x, y), needsVerification))
				break;
		}
		
		//Up-Right
		for(int x = 1, y = -1 ; x < (board.getLength() - currentPosition.getPosX()) && y + currentPosition.getPosY() >= 0; x++, y--)
		{
			if(moves.tryMove(new Move(currentPosition, x, y), needsVerification))
				break;
		}
		
		//Up-Left
		for(int x = -1, y = -1 ; x + currentPosition.getPosX() >= 0 && y + currentPosition.getPosY() >= 0; x--, y--)
		{
			if(moves.tryMove(new Move(currentPosition, x, y), needsVerification))
				break;
		}
		
		//Down-Left
		for(int x = -1, y = 1 ; x + currentPosition.getPosX() >= 0 && y < (board.getHeight() - currentPosition.getPosY()); x--, y++)
		{
			if(moves.tryMove(new Move(currentPosition, x, y), needsVerification))
				break;
		}
		
		return moves;
	}

	@Override
	public int getPieceValue()
	{
		return 30;
	}

}
