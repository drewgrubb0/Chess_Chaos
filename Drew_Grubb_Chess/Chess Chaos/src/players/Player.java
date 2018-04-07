package players;

import boards.Board;
import moves.Move;
import pieces.Piece;

/**
 * An abstract class used to set the basis for adding a player/ai into the system.
 * Interacts with the board and any passed input to determine the best move to make when called.
 *
 * @author Drew Grubb
 */
public abstract class Player
{
	protected int myColor;
	protected Board board;
	protected Move decidedMove;
	
	protected Piece selectedPiece;
	
	protected Player(Board board, int myColor)
	{
		this.board = board;
		this.myColor = myColor;
		decidedMove = null;
	}
	
	public abstract void calculateMove();
	
	/**
	 * Performs any action that might need to be done on a turn switch.
	 */
	public void activateTurn(Board board)
	{
		selectedPiece = null;
		decidedMove = null;
		this.board = board;
	}
	
	/**
	 * @return if a move has been decided
	 */
	public boolean hasDecidedMove()
	{
		if(decidedMove != null)
			return true;
		else
			return false;
	}
	
	//Setters
	
	/**
	 * set decided move
	 * @param move
	 */
	protected void setDecidedMove(Move move)
	{
		decidedMove = move;
	}
	
	//Getters
	
	/**
	 * @return move that was decided either by calculation or manual choosing
	 */
	public Move getDecidedMove()
	{
		return decidedMove;
	}
	
	/**
	 * @return piece selected to be hovered
	 */
	public Piece getSelectedPiece()
	{
		return selectedPiece;
	}
}
