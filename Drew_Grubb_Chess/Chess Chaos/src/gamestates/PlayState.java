package gamestates;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import core.Game;
import d_utils.DButton;
import d_utils.DTimer;

/**
 * PlayState handles the state of the game GUI buttons as well as calling and
 * keeping track of the Game class to update the state of the game.
 * @author Drew Grubb
 */
public class PlayState implements GameState
{
	private GameStateManager manager;
	
	private Game game;
	private DButton buttons[];
	private DTimer gameTimer;
	
	private boolean isPaused;

	/**
	 * Creates a new PlayState that handles the state of the board
	 * - Sets values
	 * - Initializes timer
	 * - Creates buttons
	 * @param gameStateManager
	 */
	public PlayState(GameStateManager gameStateManager)
	{
		this.manager = gameStateManager;
		
		gameTimer = new DTimer();
		
		buttons = new DButton[4];
		
		buttons[0] = new DButton();
		buttons[0].setDimensions(525, 35, 250, 100);
		buttons[0].setText("Pause");
		
		buttons[1] = new DButton();
		buttons[1].setDimensions(525, 160, 250, 100);
		buttons[1].setText("Undo");
		
		buttons[2] = new DButton();
		buttons[2].setDimensions(525, 285, 250, 100);
		buttons[2].setText("Reset");
		
		buttons[3] = new DButton();
		buttons[3].setDimensions(525, 410, 250, 100);
		buttons[3].setText("Quit to Menu");
	}

	@Override
	public void init()
	{
		if(game == null)
			manager.setCurrentState(GameStateManager.MENU_STATE);
		
		game.startGame();
		
		isPaused = false;
		gameTimer.startTimer();
	}

	@Override
	public void update()
	{
		//Check for button clicks
		for(int x = 0 ; x < buttons.length ; x++)
			if(buttons[x].isClicking(manager.getInputManager().getMousePosition(), manager.getInputManager().isClicking()))
				performButtonAction(x);
		
		
		if(isPaused == false && (game.getStateOfGame() != Game.STATE_DONE))
			game.updateGame();
		
		if(game.getStateOfGame() == Game.STATE_DONE)
			gameTimer.pauseTimer();
	}

	@Override
	public void render(Graphics2D g)
	{
		game.renderBoard(g);
		
		//Render buttons
		
		g.setColor(Color.GREEN);
		
		for(int x = 0 ; x < buttons.length ; x++)
			if(buttons[x].isHovering(manager.getInputManager().getMousePosition()))
				buttons[x].renderHovered(g);
			else 
				buttons[x].render(g);
		
		//Render timers
		g.drawString("" + gameTimer, 750, 15);
		
		//EndGame Rendering
		
		g.setFont(new Font("Calibri", Font.BOLD, 20));
		
		if(game.getStateOfGame() == Game.STATE_CHECK)
			g.drawString("Check.", 240, 20);
		
		if(game.getStateOfGame() == Game.STATE_DONE)
		{
			if(game.getWinner() != -2)
			{
				if(game.getWinner() == -1)
					g.drawString("Stalemate! It's a draw!", 175, 20);
				else
					g.drawString("Team " + ((game.getWinner() % 2) + 1) + " Wins! Checkmate!", 175, 20);
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
			game.undoLastMove();
		}
		
		if(buttonID == 2)
		{
			init();
		}
		
		if(buttonID == 3)
		{
			manager.setCurrentState(GameStateManager.MENU_STATE);
		}
	}
	
	/**
	 * Sets game to update and draw information from
	 * @param game
	 */
	public void setGame(Game game)
	{
		this.game = game;
	}
}
