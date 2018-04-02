package players;

import boards.Board;
import input.InputManager;
import moves.Move;
import moves.Position;
import pieces.Piece;

/**
 * Chess Player that takes the role of the "Human" player, also known as the user.
 * Receives input through an input manager.
 *
 * @author Drew Grubb
 */
public class Human extends Player
{
	private InputManager input;

	/**
	 * Initializes Human Player
	 *
	 * @param board
	 * @param input
	 * @param color
	 */
	public Human(Board board, InputManager input, int color)
	{
		super(board, color);
		this.input = input;
	}

	@Override
	public void calculateMove()
	{
		int x = -1;
		int y = -1;
		
		if(input.isClicking())
		{
			x = ((input.getMousePosition().x - Board.BOARD_X_OFFSET) / Board.TILESIZE);
			y = ((input.getMousePosition().y - Board.BOARD_Y_OFFSET) / Board.TILESIZE);
		}
		else
			return;
		
		Piece piece = board.getPiece(new Position(x, y));
		if(piece != null)
		{
			if(piece.getPieceColor() == myColor)
			{
				if(selectedPiece == piece)
				{
					//Piece De-selection
					selectedPiece = null;
				}
				else
				{
					//Piece Selection
					selectedPiece = piece;
				}
			}
			else
			{
				if(selectedPiece != null)
				{
					if(selectedPiece.getPossibleMoves(true).containsDest(new Position(x, y)))
					{
						//Piece Capture
						setDecidedMove(new Move(selectedPiece.getPosition(), new Position(x, y)));
					}
				}
			}
		}
		else
		{
			if(selectedPiece != null)
			{
				if(selectedPiece.getPossibleMoves(true).containsDest(new Position(x, y)))
				{
					//Piece Movement
					setDecidedMove(new Move(selectedPiece.getPosition(), new Position(x, y)));
				}
				else
				{
					//Piece De-selection
					selectedPiece = null;
				}
			}
		}
	}
}
