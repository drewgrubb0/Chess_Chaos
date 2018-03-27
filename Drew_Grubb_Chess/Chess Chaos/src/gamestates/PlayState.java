package gamestates;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.Stack;

import boards.Board;
import boards.ChessBoard;
import d_utils.DButton;
import d_utils.DTimer;
import moves.Move;
import pieces.Piece;
import players.Human;
import players.Player;

/**
 * PlayState handles the state of the board as well as any in game buttons.
 * Updates player turn system
 * @author Drew Grubb
 */
public class PlayState implements GameState
{
	GameStateManager manager;
	
	DButton buttons[];
	DTimer gameTimer;
	
	Board board;
	Player players[];
	int currentTurnColor;
	
	boolean isPaused;
	int losingColor = -2;

	/**
	 * Creates a new PlayState that handles the state of the board
	 * @param gameStateManager
	 */
	public PlayState(GameStateManager gameStateManager)
	{
		this.manager = gameStateManager;
		
		gameTimer = new DTimer();
		
		buttons = new DButton[2];
		buttons[0] = new DButton();
		buttons[0].setDimensions(650, 100, 100, 50);
		buttons[0].setText("Pause");
		
		buttons[1] = new DButton();
		buttons[1].setDimensions(650, 200, 100, 50);
		buttons[1].setText("Undo");
	}

	@Override
	public void init()
	{
		board = new ChessBoard();
		board.switchTurn();
		
		players = new Player[2];
		players[0] = new Human(board, manager.getInputManager(), Piece.WHITE);
		players[1] = new Human(board, manager.getInputManager(), Piece.BLACK);
		
		currentTurnColor = 0;
		
		gameTimer.startTimer();
		isPaused = false;
		losingColor = -2;
	}

	@Override
	public void update()
	{
		//Check for button clicks
		for(int x = 0 ; x < buttons.length ; x++)
			if(buttons[x].isClicking(manager.getInputManager().getMousePosition(), manager.getInputManager().isClicking()))
				performButtonAction(x);
		
		if(isPaused == false)
		{
			players[currentTurnColor].calculatePossibleMove();
		}
		
		//Player Input/Calculations block
		if(players[currentTurnColor].hasDecidedMove())
			makeMove(players[currentTurnColor].getDecidedMove());
		board.setSelectedPiece(players[currentTurnColor].getSelectedPiece());
	}

	@Override
	public void render(Graphics2D g)
	{
		board.renderBoard(g);
		
		g.setColor(Color.GREEN);
		
		for(int x = 0 ; x < buttons.length ; x++)
			if(buttons[x].isHovering(manager.getInputManager().getMousePosition()))
				buttons[x].renderHovered(g);
			else 
				buttons[x].render(g);
		
		g.drawString("" + gameTimer, 750, 15);
		
		//EndGame Rendering
		if(losingColor != -2)
		{
			if(losingColor == -1)
			{
				g.setFont(new Font("Calibri", Font.BOLD, 20));
				g.drawString("Stalemate! It's a draw!", 175, 20);
			}
			else
			{
				g.setFont(new Font("Calibri", Font.BOLD, 20));
				g.drawString("Player " + (losingColor+1) + " Loses! Checkmate!", 175, 20);
			}
		}
	}

	@Override
	public void performButtonAction(int buttonID)
	{
		if(buttonID == 0) //Pause/Resume Button
		{
			if(isPaused)
			{
				buttons[buttonID].setText("Pause");
				isPaused = false;
				gameTimer.resumeTimer();
			}
			else
			{
				gameTimer.pauseTimer();
				isPaused = true;
				buttons[buttonID].setText("Resume");
			}
		}
		if(buttonID == 1)
		{
			if(board.getMoves().size() > 0)
			{
				board.undoLastMove();
				switchTurn(true);
			}
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
		
		if(board.isInCheckmate(currentTurnColor))
			setLoser(currentTurnColor);
		if(board.isInStalemate(currentTurnColor))
			setLoser(-1);
		if(board.isInCheck(currentTurnColor))
			System.out.println("Yer boi in Check");
	}
	
	/**
	 * Sets losing color to whichever player lost
	 * Sets losing color to -1 if a stalemate occurs.
	 * 
	 * Also pauses in game timers and sets the board to
	 * paused so moves cannot be made.
	 * Potential bug: unpausing the game while losing and making moves
	 *
	 * @param color
	 */
	public void setLoser(int color)
	{
		losingColor = color;
		gameTimer.pauseTimer();
		isPaused = true;
	}
}
