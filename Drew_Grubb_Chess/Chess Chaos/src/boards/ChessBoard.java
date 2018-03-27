package boards;

import moves.Position;
import pieces.Bishop;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Piece;
import pieces.Queen;
import pieces.Rook;

/**
 * Standard Chess Board 8x8 Setup
 *
 * @author Drew Grubb
 */
public class ChessBoard extends Board
{
	/**
	 * Instantiates a Standard 8x8 ChessBoard
	 */
	public ChessBoard()
	{
		super(8, 8);
		
		setPiece(new Position(0, 0), new Rook(Piece.BLACK));
		setPiece(new Position(0, 7), new Rook(Piece.WHITE));
		setPiece(new Position(1, 0), new Knight(Piece.BLACK));
		setPiece(new Position(1, 7), new Knight(Piece.WHITE));
		setPiece(new Position(2, 0), new Bishop(Piece.BLACK));
		setPiece(new Position(2, 7), new Bishop(Piece.WHITE));
		setPiece(new Position(3, 0), new Queen(Piece.BLACK));
		setPiece(new Position(3, 7), new Queen(Piece.WHITE));
		setPiece(new Position(4, 0), new King(Piece.BLACK));
		setPiece(new Position(4, 7), new King(Piece.WHITE));
		setPiece(new Position(5, 0), new Bishop(Piece.BLACK));
		setPiece(new Position(5, 7), new Bishop(Piece.WHITE));
		setPiece(new Position(6, 0), new Knight(Piece.BLACK));
		setPiece(new Position(6, 7), new Knight(Piece.WHITE));
		setPiece(new Position(7, 0), new Rook(Piece.BLACK));
		setPiece(new Position(7, 7), new Rook(Piece.WHITE));
		
		//Pawns
		for(int x = 0 ; x < 8 ; x++)
		{
			setPiece(new Position(x, 1), new Pawn(Piece.BLACK));
			setPiece(new Position(x, 6), new Pawn(Piece.WHITE));
		}	
	}

}
