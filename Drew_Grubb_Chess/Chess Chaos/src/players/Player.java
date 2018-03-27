package players;

import boards.Board;
import moves.Move;
import pieces.Piece;

/**
 * 
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
	
	public abstract void calculatePossibleMove();
	
	public boolean hasDecidedMove()
	{
		if(decidedMove != null)
			return true;
		else
			return false;
	}
	
	public Move getDecidedMove()
	{
		return decidedMove;
	}
	
	protected void setDecidedMove(Move move)
	{
		decidedMove = move;
	}
	
	public Piece getSelectedPiece()
	{
		return selectedPiece;
	}

	/**
	 *
	 */
	public void activateTurn(Board board)
	{
		selectedPiece = null;
		decidedMove = null;
		this.board = board;
	}
}
