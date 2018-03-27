package moves;

import java.util.ArrayList;

import boards.Board;
import pieces.Piece;

/**
 * Specialized List for the purpose of chess board additions
 * and checking to see if possible moves are allowed.
 * @author Drew Grubb
 */
public class MoveSet
{
	private Board board;
	private ArrayList<Move> moves;
	
	/**
	 * Initiates new Set of moves 
	 */
	public MoveSet(Board board)
	{
		this.board = board;
		moves = new ArrayList<Move>();
	}
	
	/**
	 * Checks to see if possibleMove is legal, and adds it to the Moveset if it is. 
	 * 
	 * There are several steps to a legal move:
	 * - Is the move on the board
	 * - Is the move on an empty space or onto an enemy piece
	 * - Does performing this move put a friendly King in check
	 * 
	 * Returns true if an unavailable piece was hit for loop breaking
	 * @param move
	 */
	public boolean tryMove(Move move, boolean needsVerification)
	{
		if(!board.isAvailableSpace(move.getNewPosition(), board.getPiece(move.getPreviousPosition()).getPieceColor()))
			return true;
		
		//If this move needs to be checked, check it
		if(needsVerification)
		{
			board.performMove(move);
			
			//If it does not put the player's king in check
			if(board.isInCheck(board.getPiece(move.getNewPosition()).getPieceColor()) == false)
				moves.add(move);
			
			board.undoLastMove();
		}
		else
		{
			moves.add(move);
		}
		
		//Is position enemy piece
		//If the piece is available it is either empty or an enemy piece.
		if(board.isEmptySpace(move.getNewPosition()) == false)
			return true;
		
		return false;
	}
	
	/**
	 * Checks to see if a move exists in the moveset.
	 * @param move
	 * @return
	 */
	public boolean containsMove(Move move)
	{
		return moves.contains(move);
	}
	
	/**
	 * Checks to see if an origin position exists in the moveset.
	 * @param pos
	 * @return
	 */
	public boolean containsPrev(Position pos)
	{
		for(Move move : moves)
			if(move.getNewPosition().equals(pos))
				return true;
		return false;
	}
	
	/**
	 * Checks to see if a destination position exists in the current moveset.
	 * @param pos
	 * @return
	 */
	public boolean containsDest(Position pos)
	{
		for(Move move : moves)
			if(move.getNewPosition().equals(pos))
				return true;
		return false;
	}

	/**
	 * @return
	 */
	public int size()
	{
		return moves.size();
	}
	
	@Override
	public String toString()
	{
		String string = "Moveset: ";
		for(Move move : moves)
			string += move + " : ";
		return string;
	}
}
