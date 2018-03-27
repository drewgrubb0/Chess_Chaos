package tests;

import java.util.LinkedList;

import org.junit.Assert;
import org.junit.Test;

import boards.Board;
import boards.ChessBoard;
import moves.Move;
import moves.Position;
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
		Bishop bishop  = new Bishop(Piece.WHITE);
		board.setPiece(new Position(3, 4), bishop);
		
		//Negative Test Case
		Assert.assertNotEquals(blankMoves, bishop.getPossibleMoves(true));
		
		LinkedList<Move> correctCase = new LinkedList<Move>();
		
		//TODO
		
		//Positive Test Case
		Assert.assertEquals(correctCase, bishop.getPossibleMoves(true));
	}
	
	@Test
	public void KingPossibleMoves()
	{
		Board board = new ChessBoard();
		King king  = new King(Piece.WHITE);
		board.setPiece(new Position(3, 4), king);
		
		//Negative Test Case
		Assert.assertNotEquals(blankMoves, king.getPossibleMoves(true));
		
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
		Assert.assertEquals(correctCase, king.getPossibleMoves(true));
	}
	
	@Test
	public void KnightPossibleMoves()
	{
		Board board = new ChessBoard();
		Knight knight  = new Knight(Piece.WHITE);
		board.setPiece(new Position(3, 4), knight);
		
		//Negative Test Case
		Assert.assertNotEquals(blankMoves, knight.getPossibleMoves(true));
		
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
		Assert.assertEquals(correctCase, knight.getPossibleMoves(true));
	}
	
	@Test
	public void PawnPossibleMoves()
	{	
		Board board = new ChessBoard();
		Pawn pawn  = new Pawn(Piece.WHITE);
		board.setPiece(new Position(3, 4), pawn);
		
		//Negative Test Case
		Assert.assertNotEquals(blankMoves, pawn.getPossibleMoves(true));
		
		LinkedList<Move> correctCase = new LinkedList<Move>();
		
		correctCase.add(new Move(pawn.getPosition(), new Position(pawn.getPosition().getPosX(), pawn.getPosition().getPosY() - 1)));
		correctCase.add(new Move(pawn.getPosition(), new Position(pawn.getPosition().getPosX(), pawn.getPosition().getPosY() - 2)));
		
		//Positive Test Case
		Assert.assertEquals(correctCase, pawn.getPossibleMoves(true));
	}
	
	@Test
	public void QueenPossibleMoves()
	{	
		Board board = new ChessBoard();
		Queen queen  = new Queen(Piece.WHITE);
		board.setPiece(new Position(3, 4), queen);
		
		//Negative Test Case
		Assert.assertNotEquals(blankMoves, queen.getPossibleMoves(true));
		
		LinkedList<Move> correctCase = new LinkedList<Move>();
		
		for(int x = 0 ; x < board.getLength() ; x++)
		{
			correctCase.add(new Move(queen.getPosition(), new Position(queen.getPosition().getPosX(), x)));
			correctCase.add(new Move(queen.getPosition(), new Position(x, queen.getPosition().getPosY())));
		}
		
		for(int x = 0 ; x < board.getLength() ; x++)
		{
			correctCase.add(new Move(queen.getPosition(), new Position(queen.getPosition().getPosX(), x)));
			correctCase.add(new Move(queen.getPosition(), new Position(x, queen.getPosition().getPosY())));
		}
		
		//Positive Test Case
		Assert.assertEquals(correctCase, queen.getPossibleMoves(true));
	}
	@Test
	public void RookPossibleMoves()
	{	
		Board board = new ChessBoard();
		Rook rook  = new Rook(Piece.WHITE);
		board.setPiece(new Position(3, 4), rook);
		
		//Negative Test Case
		Assert.assertNotEquals(blankMoves, rook.getPossibleMoves(true));
		
		LinkedList<Move> correctCase = new LinkedList<Move>();
		
		for(int x = 0 ; x < board.getLength() ; x++)
		{
			correctCase.add(new Move(rook.getPosition(), new Position(rook.getPosition().getPosX(), x)));
			correctCase.add(new Move(rook.getPosition(), new Position(x, rook.getPosition().getPosY())));
		}
		
		//Positive Test Case
		Assert.assertEquals(correctCase, rook.getPossibleMoves(true));
	}
}
