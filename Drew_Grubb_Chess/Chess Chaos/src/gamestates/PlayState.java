package gamestates;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.Stack;

import boards.Board;
import boards.ChessBoard;
import boards.Move;
import d_utils.DButton;
import d_utils.DTimer;
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
	
	Stack<Move> moves;

	/**
	 * Creates a new PlayState that handles the state of the board
	 * @param gameStateManager
	 */
	public PlayState(GameStateManager gameStateManager)
	{
		this.manager = gameStateManager;
		
		gameTimer = new DTimer();
		
		buttons = new DButton[1];
		buttons[0] = new DButton();
		buttons[0].setDimensions(700, 100, 50, 50);
		buttons[0].setText("Pause");
	}

	@Override
	public void init()
	{
		board = new ChessBoard();
		
		players = new Player[2];
		players[0] = new Human(board, manager.getInputManager(), Piece.WHITE);
		players[1] = new Human(board, manager.getInputManager(), Piece.BLACK);
		
		moves = new Stack<Move>();
		
		currentTurnColor = Piece.WHITE;
		
		board.updatePossibleMoves();
		
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
		
		if(players[currentTurnColor].hasDecidedMove())
			makeMove(players[currentTurnColor].getDecidedMove());
		
		board.setSelectedPiece(players[currentTurnColor].getSelectedPiece());
		
		if(manager.getInputManager().isClicking())
			manager.getInputManager().setClicking(false);
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
				//TODO
			}
		}
		
		g.drawString("" + gameTimer, 750, 15);
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
	}
	
	/**
	 * Makes a move from point A to point B and switches the turn
	 * Adds move to the playing stack for save-ability and replaying.
	 */
	public void makeMove(Move move)
	{
		moves.add(move);
		board.performMove(move);
		switchTurn();
	}

	/**
	 * Performs any action that happens on the switching of a turn.
	 * This includes:
	 * Updating King Position
	 * Updating isInCheck
	 * Updating isInCheckmate
	 * Updating isInStalemate
	 * Updating possibleMoves
	 * Changing player control
	 * Updating player boards/selected pieces/moves
	 */
	public void switchTurn()
	{
		board.updatePossibleMoves();
		board.updateKingPosition(currentTurnColor);
		board.UpdateInCheck(currentTurnColor);
		
		if(board.isInCheckmate(currentTurnColor))
			setLoser(currentTurnColor);
		if(board.isInStalemate(currentTurnColor))
			setLoser(-1);
		if(board.isInCheck())
			System.out.println("Yer boi in Check");
		
		//Actual Changing of Player control
		currentTurnColor++;
		if(currentTurnColor >= players.length)
			currentTurnColor = 0;
		
		players[currentTurnColor].activateTurn(board);
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
