package boards;

import moves.Position;
import pieces.Bishop;
import pieces.Knight;
import pieces.Rook;
import pieces.Piece;
import pieces.Queen;

/**
 * A four player chess board.
 * 
 * Overrides Check/Stalemate/Checkmate 
 * so the game is over when a player has been eliminated.
 * 
 * @author Drew Grubb
 */
public class ChessBoard4P extends Board
{
	private static final long serialVersionUID = 1L;
	
	public static final String BOARD_NAME = "4PlayerBoard";

	/**
	 * Creates a new 4 Player Board
	 */
	public ChessBoard4P()
	{
		super(8, 8);
		
		setPiece(new Position(0, 0), new Rook(Piece.NEUTRAL));
		setPiece(new Position(1, 1), new Rook(Piece.NEUTRAL));
		
		setPiece(new Position(6, 6), new Rook(Piece.NEUTRAL));
		setPiece(new Position(7, 7), new Rook(Piece.NEUTRAL));
		
		setPiece(new Position(7, 0), new Rook(Piece.NEUTRAL));
		setPiece(new Position(6, 1), new Rook(Piece.NEUTRAL));
		
		setPiece(new Position(0, 7), new Rook(Piece.NEUTRAL));
		setPiece(new Position(1, 6), new Rook(Piece.NEUTRAL));
		
		setPiece(new Position(1, 7), new Bishop(Piece.WHITE));
		setPiece(new Position(2, 7), new Rook(Piece.WHITE));
		setPiece(new Position(3, 7), new Queen(Piece.WHITE));
		setPiece(new Position(4, 7), new Queen(Piece.WHITE));
		setPiece(new Position(5, 7), new Rook(Piece.WHITE));
		setPiece(new Position(6, 7), new Bishop(Piece.WHITE));
		setPiece(new Position(2, 6), new Rook(Piece.WHITE));
		setPiece(new Position(3, 6), new Knight(Piece.WHITE));
		setPiece(new Position(4, 6), new Knight(Piece.WHITE));
		setPiece(new Position(5, 6), new Rook(Piece.WHITE));
		
		setPiece(new Position(1, 0), new Bishop(Piece.BLACK));
		setPiece(new Position(2, 0), new Rook(Piece.BLACK));
		setPiece(new Position(3, 0), new Queen(Piece.BLACK));
		setPiece(new Position(4, 0), new Queen(Piece.BLACK));
		setPiece(new Position(5, 0), new Rook(Piece.BLACK));
		setPiece(new Position(6, 0), new Bishop(Piece.BLACK));
		setPiece(new Position(2, 1), new Rook(Piece.BLACK));
		setPiece(new Position(3, 1), new Knight(Piece.BLACK));
		setPiece(new Position(4, 1), new Knight(Piece.BLACK));
		setPiece(new Position(5, 1), new Rook(Piece.BLACK));
		
		setPiece(new Position(0, 1), new Bishop(Piece.GREEN));
		setPiece(new Position(0, 2), new Rook(Piece.GREEN));
		setPiece(new Position(0, 3), new Queen(Piece.GREEN));
		setPiece(new Position(0, 4), new Queen(Piece.GREEN));
		setPiece(new Position(0, 5), new Rook(Piece.GREEN));
		setPiece(new Position(0, 6), new Bishop(Piece.GREEN));
		setPiece(new Position(1, 2), new Rook(Piece.GREEN));
		setPiece(new Position(1, 3), new Knight(Piece.GREEN));
		setPiece(new Position(1, 4), new Knight(Piece.GREEN));
		setPiece(new Position(1, 5), new Rook(Piece.GREEN));
		
		setPiece(new Position(7, 1), new Bishop(Piece.BLUE));
		setPiece(new Position(7, 2), new Rook(Piece.BLUE));
		setPiece(new Position(7, 3), new Queen(Piece.BLUE));
		setPiece(new Position(7, 4), new Queen(Piece.BLUE));
		setPiece(new Position(7, 5), new Rook(Piece.BLUE));
		setPiece(new Position(7, 6), new Bishop(Piece.BLUE));
		setPiece(new Position(6, 2), new Rook(Piece.BLUE));
		setPiece(new Position(6, 3), new Knight(Piece.BLUE));
		setPiece(new Position(6, 4), new Knight(Piece.BLUE));
		setPiece(new Position(6, 5), new Rook(Piece.BLUE));
		
	}
	
	@Override
	public boolean isInCheck(int currentTurnColor)
	{
		return false;
	}
	
	@Override
	public boolean isInStalemate(int currentTurnColor)
	{
		return false;
	}
	
	@Override
	public boolean isInCheckmate(int currentTurnColor)
	{
		for(int x = 0 ; x < getLength() ; x++)
		{
			for(int y = 0 ; y < getHeight() ; y++)
			{
				if(getPiece(new Position(x, y)) != null)
				{
					if(getPiece(new Position(x, y)).getPieceColor() == currentTurnColor)
						return false;
				}
			}
		}
		return true;
	}
	
	@Override
	public String toString()
	{
		return "4PlayerBoard";
	}

}
