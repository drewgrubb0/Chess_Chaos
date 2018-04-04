package core;

import java.awt.Graphics2D;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import boards.Board;
import boards.ChessBoard;
import boards.ChessBoard4P;
import boards.OctoChessBoard;
import boards.RandomBoard;
import input.InputManager;
import moves.Move;
import players.Human;
import players.Player;
import players.RandomAI;

/**
 * The physical Game that will handle and keep track of all of the states of the game.
 * Utilized by the PlayState to update GUI and keep the game moving.
 * 
 * @author Drew Grubb
 */
public class Game
{
	public static final int BOARD_CHESS = 0;
	public static final int BOARD_OCTOGANAL = 1;
	public static final int BOARD_4PLAYER = 2;
	public static final int BOARD_RANDOM = 3;
	
	public static final int STATE_PLAYING = 0;
	public static final int STATE_CHECK = 1;
	public static final int STATE_DONE = 2;
	
	public static final int TYPE_CASUAL = 0;
	public static final int TYPE_SPEED = 1;
	
	private Board board;
	private int boardType;
	private Player players[];
	
	private int currentTurnColor = 0;
	private int gameWinner = -2;
	private int stateOfGame;
	
	/**
	 * Constructor that sets up all default game values
	 */
	public Game(int numPlayers)
	{
		players = new Player[numPlayers];
	}
	
	/**
	 * Constructor for QuickPlay,
	 * Sets up normal 1v1 Player vs. Player Chess
	 */
	public Game(InputManager manager)
	{
		this.boardType = BOARD_4PLAYER;
		
		players = new Player[4];
		setUpPlayer(0, "Human", manager);
		setUpPlayer(1, "Human", manager);
		
		setUpPlayer(2, "Human", manager);
		setUpPlayer(3, "Human", manager);
	}
	
	public void startGame()
	{
		//Sets up board based on given type
		switch(boardType)
		{
			case BOARD_CHESS:
				board = new ChessBoard();
			break;
			case BOARD_OCTOGANAL:
				board = new OctoChessBoard();
			break;
			case BOARD_4PLAYER:
				board = new ChessBoard4P();
			break;
			case BOARD_RANDOM:
				board = new RandomBoard();
			break;
			default:
				board = new ChessBoard();
			break;
		}
		
		gameWinner = -2;
		stateOfGame = STATE_PLAYING;
		currentTurnColor = -1;
		switchTurn(false);
	}
	
	/**
	 * The actual "playing" of the game, where all fields and states of the game
	 * are handled, updated, and kept track of. This includes updating the board,
	 * dealing with Player input/calculations, and determining winners.
	 */
	public void updateGame()
	{
		//Overridden by CHECK or DONE if they are in those respective states
		stateOfGame = STATE_PLAYING;
		
		players[currentTurnColor].calculateMove();
		
		//Has to check if the player has decided their move due to human players not calculating instantly.
		if(players[currentTurnColor].hasDecidedMove())
			makeMove(players[currentTurnColor].getDecidedMove());
		
		board.setSelectedPiece(players[currentTurnColor].getSelectedPiece());
	}
	
	/**
	 * Calls the board render method to display the board and all of the pieces on it.
	 */
	public void renderBoard(Graphics2D g)
	{
		board.renderBoard(g);
	}
	
	/**
	 * Undoes the last move on the board and 
	 * changes the current player turn if applicable
	 */
	public void undoLastMove()
	{
		if(board.getMoves().size() > 0)
		{
			board.undoLastMove();
			switchTurn(true);
		}
	}
	
	/**
	 * Makes a move from point A to point B and switches the turn
	 * Adds move to the playing stack for save-ability and replaying.
	 */
	public void makeMove(Move move)
	{
		board.performMove(move);
		switchTurn(false);
	}
	
	/**
	 * Performs any action that happens on the switching of a turn.
	 * This includes:
	 * Updating King Position
	 * Updating isInCheck
	 * Updating isInCheckmate
	 * Updating isInStalemate
	 * Changing player control
	 * Updating player boards/selected pieces/moves
	 */
	public void switchTurn(boolean isUndoing)
	{		
		board.switchTurn();
		
		//Actual Changing of Player control
		
		if(isUndoing)
		{
			currentTurnColor--;
			if(currentTurnColor < 0)
				currentTurnColor = 1;
		}
		else
		{
			currentTurnColor++;
			if(currentTurnColor >= players.length)
				currentTurnColor = 0;
		}
		
		players[currentTurnColor].activateTurn(board);
		
		if(board.isInCheck(currentTurnColor))
		{
			//CHECK overidden by DONE if board is in a state of Checkmate
			stateOfGame = STATE_CHECK;
		}
		
		if(board.isInCheckmate(currentTurnColor))
		{
			if(currentTurnColor % 2 == 0)
				gameWinner = 1;
			else
				gameWinner = 2;
			
			stateOfGame = STATE_DONE;
		}
		
		if(board.isInStalemate(currentTurnColor))
		{
			gameWinner = -1;
			stateOfGame = STATE_DONE;
		}
	}
	
	/**
	 * Saves the replay of this file (Basically just the Move stack) to
	 * the given directory in a .Chaos file (glorified text file).
	 * @param selectedFile
	 */
	public void saveReplay(File selectedFile, String fileName)
	{
		try
		{
			FileOutputStream fileOutputStream = new FileOutputStream(selectedFile + "/" + fileName + ".Chaos");
			ObjectOutputStream out = new ObjectOutputStream(fileOutputStream);
			
			out.writeUTF(board.toString());
			
			if(board.toString().equals("RandomBoard"))
			{
				out.writeLong(((RandomBoard) board).getSeed());
			}
			
			out.writeObject(board.getMoves());
			
			out.close();
			
		}catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	///////////SETTERS
	
	/**
	 * Sets up type of board that this game will be played on.
	 * @param boardChess
	 */
	public void setBoardType(int boardChess)
	{
		this.boardType = boardChess;
	}
	
	/**
	 * Sets up a specific Player to be played within the game
	 * @param color the color representing the player you are creating.
	 * @param playerType String type to determine difficulty/type of player
	 * @param input if the player is Human, the InputManager used to generate movement.
	 */
	public void setUpPlayer(int color, String playerType, InputManager input)
	{
		if(input == null)
			playerType = "Random";
		
		switch(playerType)
		{
			case "Human":
				players[color] = new Human(board, input, color);
			break;
			case "Random":
				players[color] = new RandomAI(board, color);
			break;
			default:
				players[color] = new Human(board, input, color);
			break;
		}
	}
	
	//////////GETTERS
	
	/**
	 * Method used to retrieve winner when the game ends.
	 * @return winner
	 */
	public int getWinner()
	{
		return gameWinner;
	}
	
	/**
	 * Returns current state of the game
	 * @return
	 */
	public int getStateOfGame()
	{
		return stateOfGame;
	}
	
	/**
	 * Returns current number of players in the game
	 * @return
	 */
	public int getNumPlayers()
	{
		if(players == null)
			return 0;
		
		return players.length;
	}
}
