package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;

import boards.BlankBoard;
import boards.Board;
import boards.ChessBoard;
import moves.Move;
import moves.Position;
import pieces.King;
import pieces.Piece;
import pieces.Rook;

/**
 * 
 * @author Drew Grubb
 */
public class EndGameTests
{
	@Test
	public void standardBoardCheckmateTest()
	{
		Board board = new ChessBoard();
		
		board.performMove(new Move(new Position(5, 6), new Position(5, 4)));
		board.performMove(new Move(new Position(6, 6), new Position(6, 4)));
		
		assertFalse(board.isInCheckmate(Piece.WHITE));
		
		board.performMove(new Move(new Position(3, 0), new Position(7, 4)));
		
		assertTrue(board.isInCheckmate(Piece.WHITE));
	}
	
	@Test
	public void standardBoardStalemateTest()
	{
		Board board = new BlankBoard();
		
		board.setPiece(new Position(0, 0), new King(Piece.BLACK));
		board.setPiece(new Position(7, 7), new King(Piece.WHITE));
		
		board.setPiece(new Position(1, 4), new Rook(Piece.WHITE));
		board.setPiece(new Position(4, 2), new Rook(Piece.WHITE));
		
		board.performMove(new Move(new Position(4, 2), new Position(4, 1)));
		
		assertTrue(board.isInStalemate(Piece.BLACK));
	}
}
