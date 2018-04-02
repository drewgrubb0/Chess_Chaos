package players;

import java.util.Random;

import boards.Board;
import moves.Position;
import pieces.Piece;

/**
 * An Artificial Intelligence player to be used with the PlayState
 * Selects moves completely randomly, created to be the easiest AI for
 * those just trying to teach themselves game mechanics.
 *
 * @author Drew Grubb
 */
public class RandomAI extends Player
{
	Random random;
	
	/**
	 * Creates a new RandomAI with the proper initializers
	 */
	public RandomAI(Board board, int myColor)
	{
		super(board, myColor);
		random = new Random();
	}

	@Override
	public void calculateMove()
	{
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
	}

}
