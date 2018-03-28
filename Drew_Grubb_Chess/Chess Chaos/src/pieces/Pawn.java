package pieces;

import moves.Move;
import moves.MoveSet;
import moves.Position;

/**
 * Standard Pawn Piece
 * 
 * Can move "forward" once
 * Can move forward twice if it has not moved yet
 * Captures diagonally one space
 *
 * @author Drew Grubb
 */
public class Pawn extends Piece
{

	/**
	 * Instantiates new Pawn
	 * @param pieceColor
	 * @param position
	 */
	public Pawn(int pieceColor)
	{
		super(pieceColor);
		
		if(pieceColor == Piece.WHITE)
			setImage("res/Pawn_WHITE.png");
		if(pieceColor == Piece.BLACK)
			setImage("res/Pawn_BLACK.png");
		if(pieceColor == Piece.GREEN)
			setImage("res/Pawn_GREEN.png");
		if(pieceColor == Piece.BLUE)
			setImage("res/Pawn_BLUE.png");
		
		pieceType = PieceType.PAWN;
	}

	@Override
	public MoveSet getPossibleMoves(boolean needsVerification)
	{
		//Pawn requires special checks because it does not capture in front of it
		//and cannot move normally diagonally but can capture.
		
		MoveSet moveSet = new MoveSet(board);
		int xVal = 0;
		int yVal = 0;
		
		//Determines direction specific piece colors move
		//So the below algorithms dont have to be written out four times
		if(pieceColor == WHITE)
			yVal = -1;
		if(pieceColor == BLACK)
			yVal = 1;
		if(pieceColor == GREEN)
			xVal = 1;
		if(pieceColor == BLUE)
			xVal = -1;
		
		//Normal Moves
		if(board.isEmptySpace(new Position(currentPosition.getPosX() + xVal, currentPosition.getPosY() + yVal)))
		{
			moveSet.tryMove(new Move(currentPosition, xVal, yVal), needsVerification);
			
			if(!hasMoved())
				if(board.isEmptySpace(new Position(currentPosition.getPosX() + (2*xVal), currentPosition.getPosY() + (2*yVal))))
					moveSet.tryMove(new Move(currentPosition, xVal*2, yVal*2), needsVerification);
		}
		
		//Capture Moves
		//Requires Color specific algorithms because green/blue capture to the right and left instead of up or down
		
		if(pieceColor == WHITE || pieceColor == BLACK)
		{
			if(board.isEnemyPiece(new Position(currentPosition.getPosX() - 1, currentPosition.getPosY() + yVal), pieceColor))
				moveSet.tryMove(new Move(currentPosition, new Position(currentPosition.getPosX() - 1, currentPosition.getPosY() + yVal)), needsVerification);
			
			if(board.isEnemyPiece(new Position(currentPosition.getPosX() + 1, currentPosition.getPosY() + yVal), pieceColor))
				moveSet.tryMove(new Move(currentPosition, new Position(currentPosition.getPosX() + 1, currentPosition.getPosY() + yVal)), needsVerification);
		}
		
		if(pieceColor == GREEN || pieceColor == BLUE)
		{
			if(board.isEnemyPiece(new Position(currentPosition.getPosX() + xVal, currentPosition.getPosY() - 1), pieceColor))
				moveSet.tryMove(new Move(currentPosition, new Position(currentPosition.getPosX() + xVal, currentPosition.getPosY() + 1)), needsVerification);
			
			if(board.isEnemyPiece(new Position(currentPosition.getPosX() + xVal, currentPosition.getPosY() + 1), pieceColor))
				moveSet.tryMove(new Move(currentPosition, new Position(currentPosition.getPosX() + xVal, currentPosition.getPosY() - 1)), needsVerification);
		}
		
		//En Passant
		//xVal and yVal are swapped in this case because W&B are checking left to right, and G&B are checking up and down
		tryPassant(new Position(currentPosition.getPosX() + yVal, currentPosition.getPosY() + xVal), moveSet, needsVerification);
		tryPassant(new Position(currentPosition.getPosX() - yVal, currentPosition.getPosY() - xVal), moveSet, needsVerification);
		
		
		return moveSet;
	}
	
	/**
	 * Attempts to add an En Passant to the moveset.
	 * First checks to make sure that the move attempted is an en passant.
	 * 
	 * En Passant Rules:
	 * - Previous move must have been a Pawn double move.
	 * - Pawn must be "next to" vulnerable pawn.
	 * @param posX
	 * @param posY
	 */
	private void tryPassant(Position pos, MoveSet moveSet, boolean needsVerification)
	{
		//Checks to see if adjacent piece is a Pawn
		Piece target = board.getPiece(pos);
		
		if(target == null || board.isEnemyPiece(pos, getPieceColor()) == false)
			return;
		if(target.getPieceType() != PieceType.PAWN)
			return;
		
		Move moveInQuestion = board.getMoves().peek();
		Position correctOrigin;
		
		int xVal = 0;
		int yVal = 0;
		
		//Determines direction specific piece colors move
		//So the below algorithms dont have to be written out four times
		if(pieceColor == WHITE)
			yVal = -1;
		if(pieceColor == BLACK)
			yVal = 1;
		if(pieceColor == GREEN)
			xVal = 1;
		if(pieceColor == BLUE)
			xVal = -1;
		
		correctOrigin = new Position(pos.getPosX() + xVal*2, pos.getPosY() + yVal*2);
		
		//Checks to see if last move was a pawn double move to the location in question
		if(moveInQuestion.equals(new Move(correctOrigin, pos)))
		{
			Move passantMove = new Move(currentPosition, new Position(pos.getPosX() + xVal, pos.getPosY() + yVal));
			passantMove.setCapturedPiece(target);
			moveSet.tryMove(passantMove, needsVerification);
		}
	}

	@Override
	public int getPieceValue()
	{
		return 0;
	}

}
