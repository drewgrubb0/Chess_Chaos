package boards;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.Serializable;
import java.util.Stack;

import moves.Move;
import moves.Position;
import pieces.Piece;
import pieces.PieceType;

/**
 * Board class to be used for a variety of Chess game checking and maniupulating.
 * 
 * Is in charge of:
 * - Rendering the board and pieces.
 * - Render's possible moves of a selected piece
 * - Calculating the state of the board when directed.
 * - Performing/Undoing moves when directed.
 * - Keeping track of the current moves made within the game.
 * 
 * Is NOT in charge of:
 * - Updating piece possiblemoves every turn unless called to do so.
 * - Physically deciding which moves to perform
 * - Deciding which piece to "select"
 * - Ending/Starting the game
 *
 * @author Drew Grubb
 */
public class Board implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	public static final int TILESIZE = 60;
	public static final int BOARD_X_OFFSET = 30;
	public static final int BOARD_Y_OFFSET = 30;
	
	private Piece[][] board;
	private Piece selectedPiece;
	
	private int boardLength;
	private int boardHeight;
	
	private Stack<Move> moves;
	
	/**
	 * Instantiates new Board and all board related objects
	 * - Creates the physical 2D Piece matrix
	 * - Creates the (empty) Stack of Moves made.
	 * 
	 * @param length
	 * @param height
	 */
	public Board(int length, int height)
	{
		boardLength = length;
		boardHeight = height;
		board = new Piece[boardLength][boardHeight];
		moves = new Stack<Move>();
	}
	
	/**
	 * Performs actions required on a switched turn
	 * - Update piece possible moves.
	 */
	public void switchTurn()
	{
		for (int x = 0; x < getLength(); x++) 
        	for (int y = 0; y < getHeight(); y++) 
        		if(getPiece(new Position(x, y)) != null)
            		getPiece(new Position(x, y)).updateMoveset();
	}
	
	//EndGame Checks
	
	/**
	 * Checks to see if the king of the given color is in check by
	 * checking through each piece's move set to see if it can attack the king.
	 * @param currentTurnColor
	 * @return whether or not the board is in a state of check
	 */
	public boolean isInCheck(int currentTurnColor)
	{
		Position kingPos = getKingPosition(currentTurnColor);
		
		for (int x = 0; x < getLength(); x++) 
        	for (int y = 0; y < getHeight(); y++) 
        		if(getPiece(new Position(x, y)) != null)
        		{
            		if(!isFriendlyPiece(new Position(x, y), currentTurnColor))
            		{
            			if(getPiece(new Position(x, y)).getPossibleMoves(false).containsDest(kingPos) == true)
            			{
            				return true;
            			}
            		}
        		}
        
        return false;
	}
	
	/**
	 * Checks to see if the King of the given color is in checkmate.
	 * @param currentTurnColor
	 * @return whether or not the board is in a state of checkmate
	 */
	public boolean isInCheckmate(int currentTurnColor)
	{
		return isInCheck(currentTurnColor) && !canMove(currentTurnColor);
	}
	
	/**
	 * Checks to see if the King of the given color is in stalemate.
	 * @param currentTurnColor
	 * @return whether or not the board is in a state of stalemate
	 */
	public boolean isInStalemate(int currentTurnColor)
	{
		return !isInCheck(currentTurnColor) && !canMove(currentTurnColor);
	}
	
	/**
	 * @param currentTurnColor
	 * @return The colors ability to make a move on the board.
	 */
	public boolean canMove(int currentTurnColor)
	{
		for (int x = 0; x < getLength(); x++) 
		{
			for (int y = 0; y < getHeight(); y++) 
			{
				if(getPiece(new Position(x, y)) != null)
				{
					if(getPiece(new Position(x, y)).getPieceColor() == currentTurnColor)
					{
						if(getPiece(new Position(x, y)).canMove())
							return true;
					}
				}
			}
		}
		return false;
	}
	
	//Moves Modifying Actions
	
	/**
	 * Performs necessary actions to move the piece on the board and updates the Move Stack.
	 * @param move
	 */
	public void performMove(Move move)
	{
		Piece tempPiece = move.getCapturedPiece();
		
		if(tempPiece == null)
			move.setCapturedPiece(getPiece(move.getNewPosition()));
		else
			setPiece(tempPiece.getPosition(), null);
		
		setPiece(move.getNewPosition(), getPiece(move.getPreviousPosition()));
		setPiece(move.getPreviousPosition(), null);
		
		if(getPiece(move.getNewPosition()) != null)
			getPiece(move.getNewPosition()).increaseNumMoves();
		
		moves.add(move);
	}
	
	/**
	 * Undoes the last move performed on the board and updates the Move Stack.
	 */
	public void undoLastMove()
	{
		if(moves.size() == 0)
			return;
		
		Move move = moves.pop();
		Piece tempPiece = move.getCapturedPiece();
		
		setPiece(move.getPreviousPosition(), getPiece(move.getNewPosition()));
		setPiece(move.getNewPosition(), null);
		
		getPiece(move.getPreviousPosition()).decreaseNumMoves();
		
		if(tempPiece != null)
			setPiece(tempPiece.getPosition(), tempPiece);
	}
	
	//Various Value Checks
	
	/**
	 * @param pos
	 * @return is the given position on the board.
	 */
	public boolean isOnBoard(Position pos)
	{
		return pos.getPosX() >= 0 && pos.getPosX() < boardLength && pos.getPosY() >= 0 && pos.getPosY() < boardHeight;
	}
	
	/**
	 * @param pos
	 * @return is the given position not occupied.
	 */
	public boolean isEmptySpace(Position pos)
	{
		return getPiece(pos) == null;
	}
	
	/**
	 * @param pos
	 * @param pieceColor
	 * @return is the piece at the given position part of pieceColor's team.
	 */
	public boolean isFriendlyPiece(Position pos, int pieceColor)
	{
		if(!isEmptySpace(pos))
		{
			//Is piece a solid "Wall" piece
			if(getPiece(pos).getPieceColor() == Piece.NEUTRAL)
				return true;
			
			//Is piece on the same team
			if(pieceColor % 2 == getPiece(pos).getPieceColor() % 2)
				return true;
		}
		return false;
	}
	
	/**
	 * @param pos
	 * @param pieceColor
	 * @return is the piece at the given position not a part of pieceColor's team.
	 */
	public boolean isEnemyPiece(Position pos, int pieceColor)
	{
		return !isFriendlyPiece(pos, pieceColor) && !isEmptySpace(pos);
	}
	
	/**
	 * @param pos
	 * @param pieceColor
	 * @return is the position able to be moved to by the current color
	 */
	public boolean isAvailableSpace(Position pos, int pieceColor)
	{
		return isOnBoard(pos) && !isFriendlyPiece(pos, pieceColor);
	}
	
	//Rendering
	
	/**
	 * Draws the Board to the screen.
	 * Draws the Pieces to the screen.
	 * If a piece is currently selected by a Player, 
	 * highlights all possible moves that can be made by that piece.
	 * @param g graphics2d object
	 */
	public void renderBoard(Graphics2D g)
	{
		for(int x = 0 ; x < board.length ; x++)
			for(int y = 0 ; y < board[0].length ; y++)
			{	
				//Draw board squares
				if((x + y) % 2 == 1)
					g.setColor(new Color(102, 51, 0)); //Brown Squares
				else
					g.setColor(new Color(255, 255, 204)); //Tan Squares
				
				g.fillRect(x*TILESIZE + BOARD_X_OFFSET, y*TILESIZE  + BOARD_Y_OFFSET, TILESIZE, TILESIZE);
				
				//Highlight piece possibleMove squares
				if(selectedPiece != null)
				{
					if(selectedPiece.getVerifiedMoves().containsDest(new Position(x, y)))
						g.setColor(new Color(200, 200, 0, 200));
					
					g.fillRect(x*TILESIZE + BOARD_X_OFFSET, y*TILESIZE  + BOARD_Y_OFFSET, TILESIZE, TILESIZE);
				}
				
				//Draw Pieces
				if(board[x][y] != null)
					board[x][y].render(g);
			}
	}
	
	//Various Setters
	
	/**
	 * Sets piece on board at position given
	 * Performs necessary actions on piece
	 * @param pos
	 * @param piece
	 */
	public void setPiece(Position pos, Piece piece)
	{
		board[pos.getPosX()][pos.getPosY()] = piece;
		
		if(piece != null)
		{	
			piece.setPosition(pos);
			piece.setBoard(this);
		}
	}
	
	/**
	 * Sets selected piece for move highlighting
	 * @param selectedPiece
	 */
	public void setSelectedPiece(Piece selectedPiece)
	{
		this.selectedPiece = selectedPiece;
	}
	
	//Various Getters
	
	/**
	 * Returns piece at a specific position
	 */
	public Piece getPiece(Position pos)
	{
		if(isOnBoard(pos) == false)
			return null;
		
		return board[pos.getPosX()][pos.getPosY()];
	}
	
	/**
	 * Returns King position provided that there is a king of the specific pieceColor on the board.
	 * @param pieceColor
	 */
	private Position getKingPosition(int pieceColor)
	{
		for(int x = 0 ; x < boardLength ; x++)
		{
			for(int y = 0 ; y < boardHeight ; y++)
			{
				if(getPiece(new Position(x, y)) != null)
				{
					if(getPiece(new Position(x, y)).getPieceType() == PieceType.KING)
					{
						if(getPiece(new Position(x, y)).getPieceColor() == pieceColor)
							return new Position(x, y);
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * @return the length of the board
	 */
	public int getLength() 
	{
		return boardLength;
	}
	
	/**
	 * @return the height of the board
	 */
	public int getHeight() 
	{
		return boardHeight;
	}
	
	/**
	 * @return the internal 2D Piece matrix
	 */
	public Piece[][] getBoard()
	{
		return board;
	}
	
	/**
	 * @return the Stack of Moves the game has gone through
	 */
	public Stack<Move> getMoves()
	{
		return moves;
	}
}
