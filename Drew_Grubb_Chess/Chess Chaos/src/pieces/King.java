package pieces;

import moves.Move;
import moves.MoveSet;
import moves.Position;

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
	 */
	public King(int pieceColor)
	{
		super(pieceColor);
		
		if(pieceColor == Piece.WHITE)
			setImage("res/King_WHITE.png");
		if(pieceColor == Piece.BLACK)
			setImage("res/King_BLACK.png");
		if(pieceColor == Piece.GREEN)
			setImage("res/King_GREEN.png");
		if(pieceColor == Piece.BLUE)
			setImage("res/King_BLUE.png");
		
		pieceType = PieceType.KING;
	}

	@Override
	public MoveSet getPossibleMoves(boolean needsVerification)
	{
		MoveSet moves = new MoveSet(board);
		
		//Castling
		if(!hasMoved())
		{
			if(pieceColor == BLACK || pieceColor == WHITE)
			{
				
			}
			
			if(pieceColor == GREEN || pieceColor == BLUE)
			{
				
			}
		}
		
		moves.tryMove(new Move(currentPosition, -1 , -1), needsVerification);
		moves.tryMove(new Move(currentPosition, -1 , 0), needsVerification);
		moves.tryMove(new Move(currentPosition, -1 , 1), needsVerification);
		moves.tryMove(new Move(currentPosition, 0 , 1), needsVerification);
		moves.tryMove(new Move(currentPosition, 0 , -1), needsVerification);
		moves.tryMove(new Move(currentPosition, 1 , -1), needsVerification);
		moves.tryMove(new Move(currentPosition, 1 , 0), needsVerification);
		moves.tryMove(new Move(currentPosition, 1 , 1), needsVerification);
		
		return moves;
	}

	@Override
	public int getPieceValue()
	{
		return 0;
	}

}
