package core;

import java.awt.Color;
import java.awt.Font;
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
import d_utils.DCountdownTimer;
import d_utils.DTimer;
import input.InputManager;
import moves.Move;
import players.AggressiveAI;
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
	public static final int STATE_PLAYING = 0;
	public static final int STATE_CHECK = 1;
	public static final int STATE_DONE = 2;
	
	public static final int TYPE_CASUAL = 0;
	public static final int TYPE_SPEED = 1;
	
	private Board board;
	private String boardType;
	private Player players[];
	
	private int currentTurnColor = 0;
	private int gameWinner = -2;
	private int stateOfGame;
	
	private int gameType = 0;
	private DCountdownTimer[] timers;
	private DTimer aiTimer;
	
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
		this.boardType = ChessBoard.BOARD_NAME;
		
		players = new Player[2];
		setUpPlayer(0, "Human", manager);
		setUpPlayer(1, "Human", manager);
		
		gameType = TYPE_CASUAL;
	}
	
	/**
	 * Performs all of the required actions when starting the game
	 * such as setting up the board and starting any timers.
	 */
	public void startGame()
	{
		//Sets up board based on given type
		switch(boardType)
		{
			case ChessBoard.BOARD_NAME:
				board = new ChessBoard();
			break;
			case OctoChessBoard.BOARD_NAME:
				board = new OctoChessBoard();
			break;
			case ChessBoard4P.BOARD_NAME:
				board = new ChessBoard4P();
			break;
			case RandomBoard.BOARD_NAME:
				board = new RandomBoard();
			break;
			default:
				board = new ChessBoard();
			break;
		}
		
		if(gameType == TYPE_SPEED)
		{
			timers = new DCountdownTimer[players.length];
			
			for(int x = 0 ; x < timers.length ; x++)
				timers[x] = new DCountdownTimer(300000);
		}
		
		gameWinner = -2;
		stateOfGame = STATE_PLAYING;
		currentTurnColor = -1;
		switchTurn(false);
		
		aiTimer = new DTimer();
		aiTimer.startTimer();
	}
	
	/**
	 * The actual "playing" of the game, where all fields and states of the game
	 * are handled, updated, and kept track of. This includes updating the board,
	 * dealing with Player input/calculations, and determining winners.
	 */
	public void updateGame()
	{		
		if(gameType == TYPE_SPEED)
		{
			if(timers[currentTurnColor].getTime() <= 0)
			{
				stateOfGame = STATE_DONE;
				
				if(currentTurnColor % 2 == 0)
					gameWinner = 1;
				else
					gameWinner = 2;
			}
		}
		
		players[currentTurnColor].calculateMove();
		
		if(!(players[currentTurnColor] instanceof Human))
		{
			if(aiTimer.getTime() > 500)
			{
				if(players[currentTurnColor].hasDecidedMove())
					makeMove(players[currentTurnColor].getDecidedMove());
				aiTimer.resetTimer();
			}
		}
		else
		{
			if(players[currentTurnColor].hasDecidedMove())
				makeMove(players[currentTurnColor].getDecidedMove());
		}
		
		board.setSelectedPiece(players[currentTurnColor].getSelectedPiece());
	}
	
	/**
	 * Calls the board render method to display the board and all of the pieces on it.
	 */
	public void renderBoard(Graphics2D g)
	{
		board.renderBoard(g);
		
		g.setColor(Color.GREEN);
		g.setFont(new Font(Font.DIALOG, Font.PLAIN, 13));
		
		if(gameType == TYPE_SPEED)
			for(int x = 0 ; x < players.length ; x++)
			{
				g.drawString("Player " + x + " timer", 50 + (120 * x), 530);
				g.drawString(timers[x] + "", 70 + (120*x), 560);
			}
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
			if(gameType == TYPE_SPEED && currentTurnColor >= 0)
				timers[currentTurnColor].pauseTimer();
			
			currentTurnColor++;
			if(currentTurnColor >= players.length)
				currentTurnColor = 0;
			
			if(gameType == TYPE_SPEED)
			{
				if(timers[currentTurnColor].getTime() == timers[currentTurnColor].getStartingTime())
					timers[currentTurnColor].startTimer();
				else
					timers[currentTurnColor].resumeTimer();
			}
		}
		
		players[currentTurnColor].activateTurn(board);
		
		if(board.isInCheck(currentTurnColor))
			stateOfGame = STATE_CHECK;
		else
			stateOfGame = STATE_PLAYING;
		
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
	 * Pauses the current timer
	 */
	public void pauseCurrentTimer()
	{
		if(gameType == TYPE_SPEED)
			timers[currentTurnColor].pauseTimer();
	}
	
	/**
	 * Resumes the current timer
	 */
	public void resumeCurrentTimer()
	{
		if(gameType == TYPE_SPEED)
			timers[currentTurnColor].resumeTimer();
	}
	
	/**
	 * Sets up type of board that this game will be played on.
	 * @param boardChess
	 */
	public void setBoardType(String boardChess)
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
			case "Aggressive":
				players[color] = new AggressiveAI(board, color);
			break;
			default:
				players[color] = new Human(board, input, color);
			break;
		}
	}
	
	/**
	 * Method used to set game type where
	 * casual = 0
	 * timed = 1
	 */
	public void setGameType(int gameType)
	{
		this.gameType = gameType;
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
	 * @return gets the current game type
	 */
	public int getType()
	{
		return gameType;
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
