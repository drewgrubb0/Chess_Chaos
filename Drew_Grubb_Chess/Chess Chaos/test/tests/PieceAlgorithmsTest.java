package tests;

import java.util.LinkedList;

import org.junit.Assert;
import org.junit.Test;

import boards.Board;
import boards.ChessBoard;
import boards.Move;
import boards.Position;
import pieces.Bishop;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Piece;
import pieces.Queen;
import pieces.Rook;

public class PieceAlgorithmsTest 
{
	LinkedList<Move> blankMoves = new LinkedList<Move>();
	
	@Test
	public void BishopPossibleMoves()
	{
		Board board = new ChessBoard();
		Bishop bishop  = new Bishop(Piece.WHITE, new Position(3, 4));
		board.setPiece(bishop);
		
		bishop.updatePossibleMoves();
		
		//Negative Test Case
		Assert.assertNotEquals(blankMoves, bishop.getPossibleMoves());
		
		LinkedList<Move> correctCase = new LinkedList<Move>();
		
		//TODO
		
		//Positive Test Case
		Assert.assertEquals(correctCase, bishop.getPossibleMoves());
	}
	
	@Test
	public void KingPossibleMoves()
	{
		Board board = new ChessBoard();
		King king  = new King(Piece.WHITE, new Position(3, 4));
		board.setPiece(king);
		
		king.updatePossibleMoves();
		
		//Negative Test Case
		Assert.assertNotEquals(blankMoves, king.getPossibleMoves());
		
		LinkedList<Move> correctCase = new LinkedList<Move>();
		
		correctCase.add(new Move(king.getPosition(), new Position(king.getPosition().getPosX() - 1, king.getPosition().getPosY() - 1)));
		correctCase.add(new Move(king.getPosition(), new Position(king.getPosition().getPosX() - 1, king.getPosition().getPosY())));
		correctCase.add(new Move(king.getPosition(), new Position(king.getPosition().getPosX() - 1, king.getPosition().getPosY() + 1)));
		correctCase.add(new Move(king.getPosition(), new Position(king.getPosition().getPosX() + 1, king.getPosition().getPosY() - 1)));
		correctCase.add(new Move(king.getPosition(), new Position(king.getPosition().getPosX() + 1, king.getPosition().getPosY())));
		correctCase.add(new Move(king.getPosition(), new Position(king.getPosition().getPosX() + 1, king.getPosition().getPosY() + 1)));
		correctCase.add(new Move(king.getPosition(), new Position(king.getPosition().getPosX(), king.getPosition().getPosY() - 1)));
		correctCase.add(new Move(king.getPosition(), new Position(king.getPosition().getPosX(), king.getPosition().getPosY() + 1)));
		
		//Positive Test Case
		Assert.assertEquals(correctCase, king.getPossibleMoves());
	}
	
	@Test
	public void KnightPossibleMoves()
	{
		Board board = new ChessBoard();
		Knight knight  = new Knight(Piece.WHITE, new Position(3, 4));
		board.setPiece(knight);
		
		knight.updatePossibleMoves();
		
		//Negative Test Case
		Assert.assertNotEquals(blankMoves, knight.getPossibleMoves());
		
		LinkedList<Move> correctCase = new LinkedList<Move>();
		
		correctCase.add(new Move(knight.getPosition(), new Position(knight.getPosition().getPosX() + 1, knight.getPosition().getPosY() - 1)));
		correctCase.add(new Move(knight.getPosition(), new Position(knight.getPosition().getPosX() + 1, knight.getPosition().getPosY() + 1)));
		correctCase.add(new Move(knight.getPosition(), new Position(knight.getPosition().getPosX() - 1, knight.getPosition().getPosY() - 1)));
		correctCase.add(new Move(knight.getPosition(), new Position(knight.getPosition().getPosX() - 1, knight.getPosition().getPosY() + 1)));
		correctCase.add(new Move(knight.getPosition(), new Position(knight.getPosition().getPosX() + 2, knight.getPosition().getPosY() - 2)));
		correctCase.add(new Move(knight.getPosition(), new Position(knight.getPosition().getPosX() + 2, knight.getPosition().getPosY() + 2)));
		correctCase.add(new Move(knight.getPosition(), new Position(knight.getPosition().getPosX() - 2, knight.getPosition().getPosY() - 2)));
		correctCase.add(new Move(knight.getPosition(), new Position(knight.getPosition().getPosX() - 2, knight.getPosition().getPosY() + 2)));
		
		//Positive Test Case
		Assert.assertEquals(correctCase, knight.getPossibleMoves());
	}
	
	@Test
	public void PawnPossibleMoves()
	{	
		Board board = new ChessBoard();
		Pawn pawn  = new Pawn(Piece.WHITE, new Position(3, 4));
		board.setPiece(pawn);
		
		pawn.updatePossibleMoves();
		
		//Negative Test Case
		Assert.assertNotEquals(blankMoves, pawn.getPossibleMoves());
		
		LinkedList<Move> correctCase = new LinkedList<Move>();
		
		correctCase.add(new Move(pawn.getPosition(), new Position(pawn.getPosition().getPosX(), pawn.getPosition().getPosY() - 1)));
		correctCase.add(new Move(pawn.getPosition(), new Position(pawn.getPosition().getPosX(), pawn.getPosition().getPosY() - 2)));
		
		//Positive Test Case
		Assert.assertEquals(correctCase, pawn.getPossibleMoves());
	}
	
	@Test
	public void QueenPossibleMoves()
	{	
		Board board = new ChessBoard();
		Queen queen  = new Queen(Piece.WHITE, new Position(3, 4));
		board.setPiece(queen);
		
		queen.updatePossibleMoves();
		
		//Negative Test Case
		Assert.assertNotEquals(blankMoves, queen.getPossibleMoves());
		
		LinkedList<Move> correctCase = new LinkedList<Move>();
		
		for(int x = 0 ; x < board.getWidth() ; x++)
		{
			correctCase.add(new Move(queen.getPosition(), new Position(queen.getPosition().getPosX(), x)));
			correctCase.add(new Move(queen.getPosition(), new Position(x, queen.getPosition().getPosY())));
		}
		
		for(int x = 0 ; x < board.getWidth() ; x++)
		{
			correctCase.add(new Move(queen.getPosition(), new Position(queen.getPosition().getPosX(), x)));
			correctCase.add(new Move(queen.getPosition(), new Position(x, queen.getPosition().getPosY())));
		}
		
		//Positive Test Case
		Assert.assertEquals(correctCase, queen.getPossibleMoves());
	}
	@Test
	public void RookPossibleMoves()
	{	
		Board board = new ChessBoard();
		Rook rook  = new Rook(Piece.WHITE, new Position(3, 4));
		board.setPiece(rook);
		
		rook.updatePossibleMoves();
		
		//Negative Test Case
		Assert.assertNotEquals(blankMoves, rook.getPossibleMoves());
		
		LinkedList<Move> correctCase = new LinkedList<Move>();
		
		for(int x = 0 ; x < board.getWidth() ; x++)
		{
			correctCase.add(new Move(rook.getPosition(), new Position(rook.getPosition().getPosX(), x)));
			correctCase.add(new Move(rook.getPosition(), new Position(x, rook.getPosition().getPosY())));
		}
		
		//Positive Test Case
		Assert.assertEquals(correctCase, rook.getPossibleMoves());
	}
}
