package boards;

import gamestates.GameState;
import pieces.Bishop;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Piece;
import pieces.Queen;

/**
 * Octagonal Chess Board setup
 *
 * @author Drew Grubb
 */
public class OctoChessBoard extends Board
{

	/**
	 * Sets up octagonal chess board with solid neutral pieces as the border
	 * @param playState 
	 */
	public OctoChessBoard()
	{	
		super(8, 8);
		
		//Border Pieces
		setPiece(new Pawn(Piece.NEUTRAL, new Position(0, 0)));
		setPiece(new Pawn(Piece.NEUTRAL, new Position(1, 0)));
		setPiece(new Pawn(Piece.NEUTRAL, new Position(6, 0)));
		setPiece(new Pawn(Piece.NEUTRAL, new Position(7, 0)));
		setPiece(new Pawn(Piece.NEUTRAL, new Position(0, 1)));
		setPiece(new Pawn(Piece.NEUTRAL, new Position(7, 1)));
		
		setPiece(new Pawn(Piece.NEUTRAL, new Position(0, 7)));
		setPiece(new Pawn(Piece.NEUTRAL, new Position(1, 7)));
		setPiece(new Pawn(Piece.NEUTRAL, new Position(6, 7)));
		setPiece(new Pawn(Piece.NEUTRAL, new Position(7, 7)));
		setPiece(new Pawn(Piece.NEUTRAL, new Position(0, 6)));
		setPiece(new Pawn(Piece.NEUTRAL, new Position(7, 6)));
		
		setPiece(new Pawn(Piece.NEUTRAL, new Position(2, 2)));
		setPiece(new Pawn(Piece.NEUTRAL, new Position(5, 2)));
		
		setPiece(new Pawn(Piece.NEUTRAL, new Position(2, 5)));
		setPiece(new Pawn(Piece.NEUTRAL, new Position(5, 5)));
		
		setPiece(new Bishop(Piece.WHITE, new Position(2, 0)));
		setPiece(new Bishop(Piece.BLACK, new Position(2, 7)));
		setPiece(new Bishop(Piece.WHITE, new Position(5, 0)));
		setPiece(new Bishop(Piece.BLACK, new Position(5, 7)));
		
		setPiece(new Knight(Piece.WHITE, new Position(2, 1)));
		setPiece(new Knight(Piece.BLACK, new Position(2, 6)));
		setPiece(new Knight(Piece.WHITE, new Position(5, 1)));
		setPiece(new Knight(Piece.BLACK, new Position(5, 6)));
		
		setPiece(new Queen(Piece.WHITE, new Position(3, 0)));
		setPiece(new Queen(Piece.BLACK, new Position(3, 7)));
		setPiece(new King(Piece.WHITE, new Position(4, 0)));
		setPiece(new King(Piece.BLACK, new Position(4, 7)));
		
		setPiece(new Pawn(Piece.WHITE, new Position(3, 2)));
		setPiece(new Pawn(Piece.BLACK, new Position(3, 5)));
		setPiece(new Pawn(Piece.WHITE, new Position(4, 2)));
		setPiece(new Pawn(Piece.BLACK, new Position(4, 5)));
	}

}
