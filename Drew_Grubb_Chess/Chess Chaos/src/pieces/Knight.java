package pieces;

import boards.Board;
import moves.Move;
import moves.MoveSet;
import moves.Position;

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
	public Knight(int pieceColor)
	{
		super(pieceColor);
		
		if(pieceColor == Piece.WHITE)
			setImage("res/Knight_WHITE.png");
		if(pieceColor == Piece.BLACK)
			setImage("res/Knight_BLACK.png");
		if(pieceColor == Piece.GREEN)
			setImage("res/Knight_GREEN.png");
		if(pieceColor == Piece.BLUE)
			setImage("res/Knight_BLUE.png");
		
		pieceType = PieceType.KNIGHT;
	}

	@Override
	public MoveSet getPossibleMoves(boolean needsVerification)
	{
		MoveSet moves = new MoveSet(board);
		
		moves.tryMove(new Move(currentPosition, 2, 1), needsVerification);
		
		moves.tryMove(new Move(currentPosition, -2, 1), needsVerification);
		
		moves.tryMove(new Move(currentPosition, 2, -1), needsVerification);
		
		moves.tryMove(new Move(currentPosition, -2, -1), needsVerification);
		
		moves.tryMove(new Move(currentPosition, 1, 2), needsVerification);
		
		moves.tryMove(new Move(currentPosition, -1, 2), needsVerification);

		moves.tryMove(new Move(currentPosition, 1, -2), needsVerification);
		
		moves.tryMove(new Move(currentPosition, -1, -2), needsVerification);
		
//		System.out.println("Moveset: " + moves);
		
		return moves;
	}

	@Override
	public int getPieceValue()
	{
		return 0;
	}

}
