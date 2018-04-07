package pieces;

import moves.Move;
import moves.MoveSet;
import moves.Position;

/**
 * Standard King Piece
 * 
 * Can move anywhere around itself once
 * Captures anywhere immediately around itself
 * Goal of the game is to checkmate this piece
 *
 * @author Drew Grubb
 */
public class King extends Piece
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates new King
	 * @param pieceColor
	 */
	public King(int pieceColor)
	{
		super(pieceColor);
		
		if(pieceColor == Piece.WHITE)
			setImage("res/King_WHITE.png");
		if(pieceColor == Piece.BLACK)
			setImage("res/King_BLACK.png");
		if(pieceColor == Piece.GREEN)
			setImage("res/King_GREEN.png");
		if(pieceColor == Piece.BLUE)
			setImage("res/King_BLUE.png");
		
		pieceType = PieceType.KING;
	}

	@Override
	public MoveSet getPossibleMoves(boolean needsVerification)
	{
		MoveSet moves = new MoveSet(board);
		
		checkCastle(moves, needsVerification);
		
		moves.tryMove(new Move(currentPosition, -1 , -1), needsVerification);
		moves.tryMove(new Move(currentPosition, -1 , 0), needsVerification);
		moves.tryMove(new Move(currentPosition, -1 , 1), needsVerification);
		moves.tryMove(new Move(currentPosition, 0 , 1), needsVerification);
		moves.tryMove(new Move(currentPosition, 0 , -1), needsVerification);
		moves.tryMove(new Move(currentPosition, 1 , -1), needsVerification);
		moves.tryMove(new Move(currentPosition, 1 , 0), needsVerification);
		moves.tryMove(new Move(currentPosition, 1 , 1), needsVerification);
		
		return moves;
	}
	
	/**
	 * Checks all castle parameters to see if a castle can be performed, and adds
	 * it to the possible move set if it can. Several steps to this:
	 * 
	 * - King cannot currently be in check
	 * - Both rook and King cannot have moved
	 * - King cannot pass through a space that would put him in check
	 * 
	 * @param moves
	 * @param needsVerification
	 */
	public void checkCastle(MoveSet moves, boolean needsVerification)
	{
		//Castling
		if(!hasMoved())
		{
			if(needsVerification == true && board.isInCheck(getPieceColor()))
				return;
			
			//Castling to the left
			for(int x = 1 ; currentPosition.getPosX() - x >= 0 ; x++)
			{
				Piece possibleRook = board.getPiece(new Position(currentPosition.getPosX() - x, currentPosition.getPosY()));
				
				if(possibleRook == null)
				{
					moves.tryMove(new Move(currentPosition, -x, 0), needsVerification);
					
					if(moves.getMove(moves.size() - 1) != null)
					if(moves.getMove(moves.size() - 1).equals(new Move(currentPosition, -x, 0)))
					{
						moves.removeLastMove();
					}
					else
						break;
						
				}
				else
				{
					if(board.isFriendlyPiece(possibleRook.getPosition(), pieceColor))
					{
						if(possibleRook.getPieceType() == PieceType.ROOK)
						{
							if(currentPosition.getPosX() - x != 0)
								break;
							
							if(possibleRook.hasMoved() == false)
							{
								Move castle = new Move(currentPosition,(int) -Math.ceil((x/2)), 0);
								castle.setCapturedPiece(possibleRook);
								castle.setCastle(-1);
								moves.tryMove(castle, needsVerification);
							}
							else
								break;
						}
						else
							break;
					}
					else
						break;
				}
			}
			
			//Castling to the right
			for(int x = 1 ; x + currentPosition.getPosX() < board.getLength() ; x++)
			{
				Piece possibleRook = board.getPiece(new Position(currentPosition.getPosX() + x, currentPosition.getPosY()));
				
				if(possibleRook == null)
				{
					moves.tryMove(new Move(currentPosition, x, 0), needsVerification);
				
					if(moves.size() > 0)
						if(moves.getMove(moves.size() - 1).equals(new Move(currentPosition, x, 0)))
						{
							moves.removeLastMove();
						}
						else
							break;
						
				}
				else
				{
					if(board.isFriendlyPiece(possibleRook.getPosition(), pieceColor))
					{
						if(possibleRook.getPieceType() == PieceType.ROOK)
						{
							if(currentPosition.getPosX() + x != board.getLength() - 1)
								break;
							
							if(possibleRook.hasMoved() == false)
							{
								Move castle = new Move(currentPosition, (x/2 + x%2), 0);
								castle.setCapturedPiece(possibleRook);
								castle.setCastle(1);
								moves.tryMove(castle, needsVerification);
							}
							else
								break;
						}
						else
							break;
					}
					else
						break;
				}
			}
			
		}
	}

	@Override
	public int getPieceValue()
	{
		return 900;
	}
}
