package boards;

import gamestates.GameState;
import moves.Position;
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
		setPiece(new Position(0, 0), new Pawn(Piece.NEUTRAL));
		setPiece(new Position(1, 0), new Pawn(Piece.NEUTRAL));
		setPiece(new Position(6, 0), new Pawn(Piece.NEUTRAL));
		setPiece(new Position(7, 0), new Pawn(Piece.NEUTRAL));
		setPiece(new Position(0, 1), new Pawn(Piece.NEUTRAL));
		setPiece(new Position(7, 1), new Pawn(Piece.NEUTRAL));
		
		setPiece(new Position(0, 7), new Pawn(Piece.NEUTRAL));
		setPiece(new Position(1, 7), new Pawn(Piece.NEUTRAL));
		setPiece(new Position(6, 7), new Pawn(Piece.NEUTRAL));
		setPiece(new Position(7, 7), new Pawn(Piece.NEUTRAL));
		setPiece(new Position(0, 6), new Pawn(Piece.NEUTRAL));
		setPiece(new Position(7, 6), new Pawn(Piece.NEUTRAL));
		                            
		setPiece(new Position(2, 2), new Pawn(Piece.NEUTRAL));
		setPiece(new Position(5, 2), new Pawn(Piece.NEUTRAL));
		                            
		setPiece(new Position(2, 5), new Pawn(Piece.NEUTRAL));
		setPiece(new Position(5, 5), new Pawn(Piece.NEUTRAL));
		                            
		setPiece(new Position(2, 0), new Bishop(Piece.WHITE));
		setPiece(new Position(2, 7), new Bishop(Piece.BLACK));
		setPiece(new Position(5, 0), new Bishop(Piece.WHITE));
		setPiece(new Position(5, 7), new Bishop(Piece.BLACK));
		                            
		setPiece(new Position(2, 1), new Knight(Piece.WHITE));
		setPiece(new Position(2, 6), new Knight(Piece.BLACK));
		setPiece(new Position(5, 1), new Knight(Piece.WHITE));
		setPiece(new Position(5, 6), new Knight(Piece.BLACK));
		
		setPiece(new Position(3, 0), new Queen(Piece.WHITE));
		setPiece(new Position(3, 7), new Queen(Piece.BLACK));
		setPiece(new Position(4, 0), new King(Piece.WHITE));
		setPiece(new Position(4, 7), new King(Piece.BLACK));
		                            
		setPiece(new Position(3, 2), new Pawn(Piece.WHITE));
		setPiece(new Position(3, 5), new Pawn(Piece.BLACK));
		setPiece(new Position(4, 2), new Pawn(Piece.WHITE));
		setPiece(new Position(4, 5), new Pawn(Piece.BLACK));
	}

}
