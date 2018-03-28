package pieces;

import moves.Move;
import moves.MoveSet;

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
	 */
	public Rook(int pieceColor)
	{
		super(pieceColor);
		
		if(pieceColor == Piece.WHITE)
			setImage("res/Rook_WHITE.png");
		if(pieceColor == Piece.BLACK)
			setImage("res/Rook_BLACK.png");
		if(pieceColor == Piece.GREEN)
			setImage("res/Rook_GREEN.png");
		if(pieceColor == Piece.BLUE)
			setImage("res/Rook_BLUE.png");
		
		pieceType = PieceType.ROOK;
	}

	@Override
	public MoveSet getPossibleMoves(boolean needsVerification)
	{	
		MoveSet moves = new MoveSet(board);
		
		for(int x = 1 ; x + currentPosition.getPosX() < board.getLength(); x++)
		{
			if(moves.tryMove(new Move(currentPosition, x, 0), needsVerification))
				break;
		}
		
		for(int x = 1 ; currentPosition.getPosX() - x >= 0 ; x++)
		{
			if(moves.tryMove(new Move(currentPosition, -x, 0), needsVerification))
				break;
		}
		
		for(int y = 1 ; y + currentPosition.getPosY() < board.getHeight() ; y++)
		{
			if(moves.tryMove(new Move(currentPosition, 0, y), needsVerification))
				break;
		}
		
		for(int y = 1 ; currentPosition.getPosY() - y >= 0 ; y++)
		{
			if(moves.tryMove(new Move(currentPosition, 0, -y), needsVerification))
				break;
		}
		
		return moves;
	}

	@Override
	public int getPieceValue()
	{
		return 0;
	}

}
