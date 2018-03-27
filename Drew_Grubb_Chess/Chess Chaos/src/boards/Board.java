package boards;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Stack;

import moves.Move;
import moves.Position;
import pieces.Piece;
import pieces.PieceType;

/**
 * Abstract class board used for the creation of new Chess Boards and
 * the checking for check, checkmate, and stalemate.
 * 
 * Renders board and pieces.
 *
 * @author Drew Grubb
 */
public class Board
{
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
	 */
	public void switchTurn()
	{
		for (int x = 0; x < getLength(); x++) 
        {
        	for (int y = 0; y < getHeight(); y++) 
        	{
        		if(getPiece(new Position(x, y)) != null)
        		{
 //           		getPiece(new Position(x, y)).setBoard(this);
            		getPiece(new Position(x, y)).updateMoveset();
        		}
        	}
        }
	}
	
	//EndGame Checks
	
	/**
	 * Checks to see if the King of the given color is in Check by
	 * checking through each piece's moveset to see if it can attack the king.
	 * @param currentTurnColor
	 */
	public boolean isInCheck(int currentTurnColor)
	{
		Position kingPos = getKingPosition(currentTurnColor);
		
		for (int x = 0; x < getLength(); x++) 
        {
        	for (int y = 0; y < getHeight(); y++) 
        	{
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
        	}
        }
        
        return false;
	}
	
	/**
	 * Checks to see if the King of the given color is in checkmate.
	 * @param currentTurnColor
	 */
	public boolean isInCheckmate(int currentTurnColor)
	{
		return isInCheck(currentTurnColor) && !canMove(currentTurnColor);
	}
	
	/**
	 * Checks to see if the King of the given color is in stalemate.
	 * @param currentTurnColor
	 */
	public boolean isInStalemate(int currentTurnColor)
	{
		return !isInCheck(currentTurnColor) && !canMove(currentTurnColor);
	}
	
	/**
	 * Checks to see if there are any pieces that can move of a given side.
	 * @return
	 */
	private boolean canMove(int currentTurnColor)
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
	 * Performs necessary actions to move the piece on the board
	 * @param move
	 */
	public void performMove(Move move)
	{
		Piece tempPiece = move.getCapturedPiece();
		
		if(tempPiece == null)
			move.setCapturedPiece(getPiece(move.getNewPosition()));
		else
			setPiece(tempPiece.getPosition(), null); //En Passant
		
		setPiece(move.getNewPosition(), getPiece(move.getPreviousPosition()));
		setPiece(move.getPreviousPosition(), null);
		
		getPiece(move.getNewPosition()).increaseNumMoves();
		
		moves.add(move);
	}
	
	/**
	 * Undoes the last move performed on the board
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
	 * Checks to see if the given position is on the board
	 * @param pos
	 */
	public boolean isOnBoard(Position pos)
	{
		return pos.getPosX() >= 0 && pos.getPosX() < boardLength && pos.getPosY() >= 0 && pos.getPosY() < boardHeight;
	}
	
	/**
	 * Checks to see if the given position is occupied
	 * @param pos
	 */
	public boolean isEmptySpace(Position pos)
	{
		return getPiece(pos) == null;
	}
	
	/**
	 * Checks to see if the given position is a part of pieceColor's team.
	 * @param pos
	 * @param pieceColor
	 */
	public boolean isFriendlyPiece(Position pos, int pieceColor)
	{
		if(!isEmptySpace(pos))
		{
			//Is piece a solid "Wall" piece
			if(getPiece(pos).getPieceColor() == Piece.NEUTRAL)
				return true;
			
			//TODO
			//Is piece on the same team
			if(pieceColor == getPiece(pos).getPieceColor())
			{
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Checks to see if the given position is not a part of pieceColor's team.
	 * @param pos
	 * @param pieceColor
	 */
	public boolean isEnemyPiece(Position pos, int pieceColor)
	{
		return !isFriendlyPiece(pos, pieceColor) && !isEmptySpace(pos);
	}
	
	/**
	 * Checks to see if the given position can be moved to. This must clear several checks:
	 * - Position is either empty or an enemy piece
	 * - Position is on the board
	 * @param pos
	 * @param pieceColor
	 * @return
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
	 * highlights all possible moves that can be made.
	 *
	 * @param g
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
	
	public int getLength() 
	{
		return boardLength;
	}
	
	public int getHeight() 
	{
		return boardHeight;
	}
	
	public Piece[][] getBoard()
	{
		return board;
	}
	
	public Stack<Move> getMoves()
	{
		return moves;
	}
}
