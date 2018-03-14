package boards;

import gamestates.GameState;
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
	public ChessBoard(GameState state)
	{
		super(8, 8);
		this.state = state;
		
		setPiece(new Rook(Piece.BLACK, new Position(0, 0)));
		setPiece(new Rook(Piece.WHITE, new Position(0, 7)));
		setPiece(new Knight(Piece.BLACK, new Position(1, 0)));
		setPiece(new Knight(Piece.WHITE, new Position(1, 7)));
		setPiece(new Bishop(Piece.BLACK, new Position(2, 0)));
		setPiece(new Bishop(Piece.WHITE, new Position(2, 7)));
		setPiece(new Queen(Piece.BLACK, new Position(3, 0)));
		setPiece(new Queen(Piece.WHITE, new Position(3, 7)));
		setPiece(new King(Piece.BLACK, new Position(4, 0)));
		setPiece(new King(Piece.WHITE, new Position(4, 7)));
		setPiece(new Bishop(Piece.BLACK, new Position(5, 0)));
		setPiece(new Bishop(Piece.WHITE, new Position(5, 7)));
		setPiece(new Knight(Piece.BLACK, new Position(6, 0)));
		setPiece(new Knight(Piece.WHITE, new Position(6, 7)));
		setPiece(new Rook(Piece.BLACK, new Position(7, 0)));
		setPiece(new Rook(Piece.WHITE, new Position(7, 7)));
		
		//Pawns
		for(int x = 0 ; x < 8 ; x++)
		{
			setPiece(new Pawn(Piece.BLACK, new Position(x, 1)));
			setPiece(new Pawn(Piece.WHITE, new Position(x, 6)));
		}
			
	}

}
