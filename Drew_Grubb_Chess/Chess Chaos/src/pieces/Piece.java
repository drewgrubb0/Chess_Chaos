package pieces;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;

import boards.Board;
import moves.MoveSet;
import moves.Position;

/**
 * The abstract chess piece superclass.
 * Contains all necessary base functions of chess pieces with the
 * intent of making new pieces easy to add.
 * 
 * @author Drew Grubb
 */
public abstract class Piece implements Serializable
{	
	private static final long serialVersionUID = 1L;
	
	public static final int NEUTRAL = -1;
	public static final int WHITE = 0;
	public static final int BLACK = 1;
	public static final int GREEN = 2;
	public static final int BLUE = 3;
	
	protected int pieceColor;
	protected PieceType pieceType;
	protected Position currentPosition;
	
	//Transient because BufferedImages are not Serializable
	transient protected BufferedImage image;
	protected int numMovesMade;
	
	protected Board board;
	//Transient because MoveSets are not required for Replays
	transient protected MoveSet moveSet;
	
	/**
	 * Creates a new piece in the given position
	 *
	 * @param pieceColor
	 * @param position
	 */
	public Piece(int pieceColor)
	{
		this.pieceColor = pieceColor;
		
		numMovesMade = 0;
		pieceType = PieceType.NULL;
		moveSet = new MoveSet(board);
	}
	
	/**
	 * Updates the possible moves based on the specific piece type.
	 * In general, updatePossibleMoves algorithm will work as follows:
	 * Create fake board
	 * perform specific move on the board
	 * if move causes the current player to be in check, it is skipped
	 * if not, move is added to the move set.
	 * 
	 * @param needsVerification used to avoid infinite looping,
	 * IE "Does this move need to check to see if it puts the player in check"
	 * @return moveset
	 */
	public abstract MoveSet getPossibleMoves(boolean needsVerification);
	
	/**
	 * Returns piece value of specific piece for the sake of minimax AI
	 * @return
	 */
	public abstract int getPieceValue();
	
	/////Rendering
	
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
	
	/////Setters
	
	/**
	 * Updates verified moveset
	 */
	public void updateMoveset()
	{
		moveSet = getPossibleMoves(true);
	}
	
	/**
	 * Increases number of moves made
	 */
	public void increaseNumMoves()
	{
		numMovesMade++;
	}
	
	/**
	 * Decreases number of moves made
	 */
	public void decreaseNumMoves()
	{
		if(numMovesMade > 0)
			numMovesMade--;
	}
	
	/**
	 * Updates board
	 * @param board
	 */
	public void setBoard(Board board)
	{
		this.board = board;
	}

	/**
	 * Updates position
	 * @param position
	 */
	public void setPosition(Position position)
	{
		currentPosition = position;
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
	
	/////Getters
	
	/**
	 * Checks to see if a piece has moved by seeing how many moves it has made.
	 * @return
	 */
	public boolean hasMoved()
	{
		return numMovesMade > 0;
	}
	
	/**
	 * @return bool value: can piece move
	 */
	public boolean canMove()
	{
		return moveSet.size() > 0;
	}
	
	/**
	 * @return Current verified moveset
	 */
	public MoveSet getVerifiedMoves()
	{
		return moveSet;
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
	 * @return currentPosition
	 */
	public Position getPosition()
	{
		return currentPosition;
	}
	
	/**
	 * @return numMovesMade
	 */
	public int getNumMovesMade()
	{
		return numMovesMade;
	}
	
	@Override
	public String toString()
	{
		return pieceType + " at " + currentPosition;
	}
}
