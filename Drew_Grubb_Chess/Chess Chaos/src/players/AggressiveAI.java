package players;

import java.util.Random;

import boards.Board;
import moves.Move;
import moves.MoveSet;
import moves.Position;
import pieces.Piece;

/**
 * An Artificial Intelligence player to be used with the PlayState
 * Selects moves using the Minimax algorithm with depth 0
 *
 * @author Drew Grubb
 */
public class AggressiveAI extends Player
{
	
	/**
	 * Creates a new RandomAI with the proper initializers
	 */
	public AggressiveAI(Board board, int myColor)
	{
		super(board, myColor);
	}

	@Override
	public void calculateMove()
	{
		int maxScore = -9999;
		Move highestMove = new Move(new Position(0, 0), 0, 0);
		
		for(int x = 0 ; x < board.getLength() ; x++)
		{
			for(int y = 0 ; y < board.getHeight() ; y++)
			{
				if(board.isFriendlyPiece(new Position(x, y), myColor))
				{
					Move moveToCheck = getHighestMove(board.getPiece(new Position(x, y)));
					
					if(moveToCheck != null && moveToCheck.getScore() > maxScore)
					{
						highestMove = moveToCheck;
						maxScore = moveToCheck.getScore();
					}
				}
			}
		}
		
		//If no "best" moves are available, move randomly
		if(maxScore == 0)
		{
			Random random = new Random();
			Piece selectedPiece = null;
			
			while(selectedPiece == null)
			{
				selectedPiece = board.getPiece(new Position(random.nextInt(board.getLength()), random.nextInt(board.getHeight())));
				
				if(selectedPiece != null)
				{
					if(selectedPiece.canMove() == false || selectedPiece.getPieceColor() != myColor)
						selectedPiece = null;
				}
			}
			
			decidedMove = selectedPiece.getVerifiedMoves().getMove(random.nextInt(selectedPiece.getVerifiedMoves().size()));
			return;
		}
		
		decidedMove = highestMove;
	}
	
	/**
	 * Gets the highest scored move for the given piece
	 * @param piece the piece to check
	 * @return the highest scored piece from that piece's move set.
	 */
	private Move getHighestMove(Piece piece)
	{
		int highestMoveScore = 0;
		int highestMoveIndex = 0;
		MoveSet set = piece.getVerifiedMoves();
		
		for(int x = 0 ; x < set.size() ; x++)
		{
			if(set.getMove(x).getScore() > highestMoveScore)
			{
				highestMoveScore = set.getMove(x).getScore();
				highestMoveIndex = x;
			}
		}
		
		return set.getMove(highestMoveIndex);
	}
}
