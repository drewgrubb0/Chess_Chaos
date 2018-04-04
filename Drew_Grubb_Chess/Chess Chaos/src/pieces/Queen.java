package pieces;

import moves.Move;
import moves.MoveSet;

/**
 * Standard Queen Piece
 * 
 * Combined move set of rook and bishop
 * Can move up/down left/right or diagonally
 * Moves until it hits a wall or a piece
 * Captures up/down left/right or diagonally
 *
 * @author Drew Grubb
 */
public class Queen extends Piece
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates new Queen
	 * @param pieceColor
	 */
	public Queen(int pieceColor)
	{
		super(pieceColor);
		
		if(pieceColor == Piece.WHITE)
			setImage("res/Queen_WHITE.png");
		if(pieceColor == Piece.BLACK)
			setImage("res/Queen_BLACK.png");
		if(pieceColor == Piece.GREEN)
			setImage("res/Queen_GREEN.png");
		if(pieceColor == Piece.BLUE)
			setImage("res/Queen_BLUE.png");
		
		pieceType = PieceType.QUEEN;
	}

	@Override
	public MoveSet getPossibleMoves(boolean needsVerification)
	{
		MoveSet moves = new MoveSet(board);
		
		//Rook Move set
		for(int x = 1 ; x + currentPosition.getPosX() < board.getLength(); x++)
		{
			if(moves.tryMove(new Move(currentPosition, x, 0), needsVerification))
				break;
		}
		
		for(int x = 1 ; currentPosition.getPosX() - x >= 0 ; x++)
		{
			if(moves.tryMove(new Move(currentPosition, -x, 0), needsVerification))
				break;
		}
		
		for(int y = 1 ; y + currentPosition.getPosY() < board.getHeight() ; y++)
		{
			if(moves.tryMove(new Move(currentPosition, 0, y), needsVerification))
				break;
		}
		
		for(int y = 1 ; currentPosition.getPosY() - y >= 0 ; y++)
		{
			if(moves.tryMove(new Move(currentPosition, 0, -y), needsVerification))
				break;
		}
		
		//Bishop move set
		
		//Down-Right
		for(int x = 1, y = 1 ; x < (board.getLength() - currentPosition.getPosX()) && y < (board.getHeight() - currentPosition.getPosY()); x++, y++)
		{
			if(moves.tryMove(new Move(currentPosition, x, y), needsVerification))
				break;
		}
		
		//Up-Right
		for(int x = 1, y = -1 ; x < (board.getLength() - currentPosition.getPosX()) && y + currentPosition.getPosY() >= 0; x++, y--)
		{
			if(moves.tryMove(new Move(currentPosition, x, y), needsVerification))
				break;
		}
		
		//Up-Left
		for(int x = -1, y = -1 ; x + currentPosition.getPosX() >= 0 && y + currentPosition.getPosY() >= 0; x--, y--)
		{
			if(moves.tryMove(new Move(currentPosition, x, y), needsVerification))
				break;
		}
		
		//Down-Left
		for(int x = -1, y = 1 ; x + currentPosition.getPosX() >= 0 && y < (board.getHeight() - currentPosition.getPosY()); x--, y++)
		{
			if(moves.tryMove(new Move(currentPosition, x, y), needsVerification))
				break;
		}
		
		return moves;
	}

	@Override
	public int getPieceValue()
	{
		return 90;
	}

}
