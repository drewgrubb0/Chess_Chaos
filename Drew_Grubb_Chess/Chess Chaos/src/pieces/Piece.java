package pieces;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;

import boards.Board;
import boards.Move;
import boards.Position;

/**
 * The abstract chess piece superclass.
 * Contains all necessary base functions of chess pieces with the
 * intent of making new pieces easy to add.
 * 
 * @author Drew Grubb
 */
public abstract class Piece
{	
	public static final int NEUTRAL = -1;
	public static final int WHITE = 0;
	public static final int BLACK = 1;
	public static final int GREEN = 2;
	public static final int BLUE = 3;
	
	protected int pieceColor;
	protected PieceType pieceType;
	protected Position currentPosition;
	
	protected BufferedImage image;
	protected boolean hasMoved;
	
	protected Board board;
	protected LinkedList<Move> moves;
	
	/**
	 * Creates a new piece in the given position
	 *
	 * @param pieceColor
	 * @param position
	 */
	public Piece(int pieceColor, Position position)
	{
		this.pieceColor = pieceColor;
		this.currentPosition = position;
		
		hasMoved = false;
		pieceType = PieceType.NULL;
		moves = new LinkedList<Move>();
	}
	
	public abstract void updatePossibleMoves();
	public abstract int getPieceValue();
	
	public void render(Graphics2D g)
	{
		if(image != null)
			g.drawImage(image, (currentPosition.getPosX() * Board.TILESIZE) + Board.BOARD_X_OFFSET, (currentPosition.getPosY()*Board.TILESIZE) + Board.BOARD_Y_OFFSET, Board.TILESIZE, Board.TILESIZE, null);
		else
		{
			//Draw Neutral "Solid Tile"
			g.setColor(Color.BLACK);
			g.fillRect((currentPosition.getPosX() *Board.TILESIZE) + Board.BOARD_X_OFFSET, (currentPosition.getPosY() * Board.TILESIZE) + Board.BOARD_Y_OFFSET, Board.TILESIZE, Board.TILESIZE);
		}
	}
	
	/**
	 * @return PieceType enum
	 */
	public PieceType getPieceType()
	{
		return pieceType;
	}

	/**
	 * @return pieceColor int 
	 */
	public int getPieceColor()
	{
		return pieceColor;
	}

	/**
	 * Checks to see if number of moves is > 0
	 * @return
	 */
	public boolean canMove()
	{
		return moves.size() > 0;
	}

	/**
	 * Utilizes overridden equals() method in moves to check if 
	 * piece can move to the given position.
	 * @param position
	 * @return
	 */
	public boolean canMoveTo(Position position)
	{
		for(Move move : moves)
		{
			if(move.getNewPosition().equals(position))
				return true;
		}
		
		return false;
	}

	/**
	 * @return currentPosition
	 */
	public Position getPosition()
	{
		return currentPosition;
	}
	
	/**
	 * Sets piece BufferedImage to image at path
	 * 
	 * @param path
	 */
	public void setImage(String path)
	{
		try
		{
			image = ImageIO.read(new File(path));
		} catch (IOException e)
		{
			image = null;
		}
	}

	/**
	 * Clears all possible moves
	 * Done in between turns
	 */
	public void clearMoves()
	{
		moves.clear();
	}

	/**
	 * Updates board
	 * @param board
	 */
	public void setBoard(Board board)
	{
		this.board = board;
	}
}
