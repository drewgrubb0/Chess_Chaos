package boards;

import java.awt.Color;
import java.awt.Graphics2D;

import gamestates.GameState;
import pieces.Piece;
import pieces.PieceType;

/**
 * Abstract class board used for the creation of new Chess Boards and
 * the checking for check, checkmate, and stalemate.
 *
 * @author Drew Grubb
 */
public abstract class Board
{
	public static final int TILESIZE = 60;
	public static final int BOARD_X_OFFSET = 30;
	public static final int BOARD_Y_OFFSET = 30;
	
	private Piece[][] board;
	private Piece selectedPiece;
	private int boardWidth;
	private int boardHeight;
	
	private Position kingPosition;
	private boolean isInCheck;
	
	protected GameState state;
	
	/**
	 * Initializes new Board
	 *
	 * @param boardWidth
	 * @param boardHeight
	 */
	public Board(int boardWidth, int boardHeight)
	{
		this.boardWidth = boardWidth;
		this.boardHeight = boardHeight;
		clearBoard();
	}
	
	public void update()
	{
		
	}
	
	/**
	 * Checks to see if the King from given side is in check
	 *
	 * @param color
	 * @return
	 */
	public boolean UpdateInCheck(int color)
	{
		if(kingPosition == null)
			return false;
		
		for(int x = 0 ; x < boardWidth ; x++)
			for(int y = 0 ; y < boardHeight ; y++)
			{
				if(board[x][y] != null && board[x][y].getPieceColor() != color)
				{
					if(board[x][y].canMoveTo(kingPosition))
					{
						isInCheck = true;
						return true;
					}
				}
			}
		
		isInCheck = false;
		return false;
	}
	
	/**
	 * Checks to see if the King from given side is in checkmate
	 * Checkmate also means in check but cannot move
	 * 
	 * Moveset changes by being in check are in the hands of the pieces.
	 *
	 * @param color
	 * @return
	 */
	public boolean isInCheckmate(int color)
	{
		return isInCheck() && !canMove(color);
	}
	
	/**
	 * Checks to see if the King from given side is in stalemate
	 * Stalemate also means not in check but can't move
	 * 
	 * Move set changes by being in check are in the hands of the pieces.
	 *
	 * @param color
	 * @return
	 */
	public boolean isInStalemate(int color)
	{	
		return !isInCheck() && !canMove(color);
	}
	
	/**
	 * Checks to see if the number of moves available to PlayerColor color
	 * Used in Stalemate Checking
	 *
	 * @param color
	 * @return
	 */
	public boolean canMove(int color)
	{
		for(int x = 0 ; x < boardWidth ; x++)
			for(int y = 0 ; y < boardHeight ; y++)
			{
				if(board[x][y] != null && board[x][y].getPieceColor() != color)
				{
					if(board[x][y].canMove())
						return true;
				}
			}
		
		return false;
	}
	
	/**
	 * Searches for an existing King of PieceColor color
	 *
	 * @param color
	 * @return position of Piece
	 */
	public Position updateKingPosition(int color)
	{
		for(int x = 0 ; x < boardWidth ; x++)
			for(int y = 0 ; y < boardHeight ; y++)
			{
				if(board[x][y] != null && board[x][y].getPieceType() == PieceType.KING)
				{
					if(board[x][y].getPieceColor() == color)
					{
						return new Position(x, y);
					}
				}
			}
		
		return null;
	}
	
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
					if(selectedPiece.canMoveTo(new Position(x, y)))
						g.setColor(new Color(200, 200, 0, 200));
					
					g.fillRect(x*TILESIZE + BOARD_X_OFFSET, y*TILESIZE  + BOARD_Y_OFFSET, TILESIZE, TILESIZE);
				}
				
				//Draw Pieces
				if(board[x][y] != null)
					board[x][y].render(g);
			}
	}
	
	/**
	 * Places new Piece
	 *
	 * @param p
	 */
	public void setPiece(Piece p)
	{
		board[p.getPosition().getPosX()][p.getPosition().getPosY()] = p;
		p.setBoard(this);
	}
	
	/**
	 * Clears board / creates new board allocation
	 */
	public void clearBoard()
	{
		board = new Piece[boardWidth][boardHeight];
		selectedPiece = null;
		kingPosition = null;
	}
	
	/**
	 * Returns current king position
	 * @return
	 */
	public Position getKingPosition()
	{
		return kingPosition;
	}
	
	/**
	 * @return isInCheck
	 */
	public boolean isInCheck()
	{
		return isInCheck;
	}

	/**
	 * Loops through the board pieces and updates their possible moves
	 */
	public void updatePossibleMoves()
	{
		for(int x = 0 ; x < board.length ; x++)
			for(int y = 0 ; y < board[0].length ; y++)
			{
				board[x][y].clearMoves();
				board[x][y].setBoard(this);
				board[x][y].updatePossibleMoves();
			}
	}

	/**
	 *
	 * @param peek
	 */
	public void performMove(Piece piece, Move move)
	{
		board[move.getPreviousPosition().getPosX()][move.getPreviousPosition().getPosY()] = null;
		board[move.getNewPosition().getPosX()][move.getNewPosition().getPosY()] = piece;
	}
}
