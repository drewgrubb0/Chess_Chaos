package gamestates;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import boards.ChessBoard;
import boards.ChessBoard4P;
import boards.OctoChessBoard;
import boards.RandomBoard;
import core.Game;
import d_utils.DButton;

/**
 * GameState in charge of Menu traversal.
 * 
 * Is in charge of:
 * - Rendering menu options.
 * - Updating menu options based off user responses.
 * - Checking for/responding to clicks.
 * - Setting game values and switching to PlayState or SpectateState
 * 
 * Is NOT in charge of:
 * - Rendering Boards/Pieces
 * - Starting the game
 * 
 * @author Drew Grubb
 */
public class MenuState implements GameState
{
	public static final int MENU_INITIAL = 0;
	public static final int MENU_BOARD = 1;
	public static final int MENU_PLAYERS = 2;
	public static final int MENU_SETTINGS = 3;
	
	public static final int TYPE_CASUAL = 0;
	public static final int TYPE_TIMED = 1;
	
	private GameStateManager manager;
	private DButton buttons[];
	private Game game;
	
	private int currentMenuState;
	private int currentPlayerSetting;
	
	private String menuText;
	private String subText;
	
	/**
	 * Creates a new PlayState that handles menu traversal
	 * - Sets default values
	 * - Creates buttons
	 *
	 * @param gameStateManager
	 */
	public MenuState(GameStateManager gameStateManager)
	{
		this.manager = gameStateManager;
	}

	@Override
	public void init()
	{
		currentMenuState = MENU_INITIAL;
		currentPlayerSetting = 0;
		
		buttons = new DButton[4];
		
		buttons[0] = new DButton();
		buttons[0].setDimensions(70, 320, 250, 100);
		buttons[0].setText("Play Chess");
		
		buttons[1] = new DButton();
		
		buttons[2] = new DButton();
		buttons[2].setDimensions(470, 320, 250, 100);
		buttons[2].setText("Watch Replay");
		
		buttons[3] = new DButton();
		buttons[3].setDimensions(270, 450, 250, 100);
		buttons[3].setText("Quick Play");
		
		menuText = "Welcome to Chess Chaos!";
		subText = "Please pick your game type";
	}

	@Override
	public void update()
	{
		//Check for button clicks
		for(int x = 0 ; x < buttons.length ; x++)
			if(buttons[x].isClicking(manager.getInputManager().getMousePosition(), manager.getInputManager().isClicking()))
				performButtonAction(x);
	}

	@Override
	public void render(Graphics2D g)
	{
		//Render buttons
		g.setColor(Color.GREEN);
		
		g.setFont(new Font("Calibri", Font.BOLD, 30));
		for(int x = 0 ; x < buttons.length ; x++)
			if(buttons[x].isHovering(manager.getInputManager().getMousePosition()))
				buttons[x].renderHovered(g);
			else 
				buttons[x].render(g);
		
		//Center & Render text
		
		g.setFont(new Font("Calibri",Font.BOLD, 50));
		centerAndRenderText(12, menuText, g);
		
		g.setFont(new Font("Calibri", Font.BOLD, 30));
		centerAndRenderText(5, subText, g);
	}
	
	private void centerAndRenderText(int yfrac, String text, Graphics2D g)
	{
		FontMetrics metrics = g.getFontMetrics(g.getFont());
		
		int textX = (800 - metrics.stringWidth(text)) / 2;
		int textY = ((600 - metrics.getHeight()) / yfrac) + metrics.getAscent();
				
		g.drawString(text, textX, textY);
	}

	@Override
	public void performButtonAction(int buttonID)
	{
		if(currentMenuState == MENU_INITIAL)
		{
			if(buttonID == 0)
			{
				currentMenuState = MENU_BOARD;
				buttons[0].setText("Standard Chess");
				buttons[1].setText("Random Chess");
				buttons[1].setDimensions(270, 190, 250, 100);
				buttons[2].setText("Octogonal Chess");
				buttons[3].setText("Chess Chaos");
				subText = "Please choose the board you want to play on";
			}
			
			if(buttonID == 1)
			{
				
			}
			
			if(buttonID == 2)
			{
				manager.setCurrentState(GameStateManager.REPLAY_STATE);
			}
			
			if(buttonID == 3)
			{
				game = new Game(manager.getInputManager());
				((PlayState) manager.getState(GameStateManager.PLAY_STATE)).setGame(game);
				manager.setCurrentState(GameStateManager.PLAY_STATE);
			}
		}
		else
		if(currentMenuState == MENU_BOARD)
		{
			if(buttonID == 0)
			{
				game = new Game(2);
				game.setBoardType(ChessBoard.BOARD_NAME);
			}
			
			if(buttonID == 1)
			{
				game = new Game(2);
				game.setBoardType(RandomBoard.BOARD_NAME);
			}
			
			if(buttonID == 2)
			{
				game = new Game(2);
				game.setBoardType(OctoChessBoard.BOARD_NAME);
			}
			
			if(buttonID == 3)
			{
				game = new Game(4);
				game.setBoardType(ChessBoard4P.BOARD_NAME);
			}
			
			currentMenuState = MENU_PLAYERS;
			buttons[0].setText("Random AI");
			buttons[1].setText("Human Player");
			buttons[2].setText("Aggressive AI");
			buttons[3].setText("Quit to Main Menu");
			subText = "Please choose Player 1";
		}
		else
		if(currentMenuState == MENU_PLAYERS)
		{
			
			if(buttonID == 0)
			{
				game.setUpPlayer(currentPlayerSetting, "Random", null);
			}
			
			if(buttonID == 1)
			{
				game.setUpPlayer(currentPlayerSetting, "Human", manager.getInputManager());
			}
			
			if(buttonID == 2)
			{
				game.setUpPlayer(currentPlayerSetting, "Aggressive", null);
			}
			
			if(buttonID == 3)
			{
				init();
				return;
			}
			
			currentPlayerSetting++;
			subText = "Please choose Player " + (currentPlayerSetting + 1);
			
			if(currentPlayerSetting >= game.getNumPlayers())
			{
				currentMenuState = MENU_SETTINGS;
				subText = "Please choose your game mode";
				buttons[0].setText("Casual Mode");
				buttons[1].setText("");
				buttons[1].setDimensions(0, 0, 0, 0);
				buttons[2].setText("Speed Chess");
			}
		}
		else
		if(currentMenuState == MENU_SETTINGS)
		{
			if(buttonID == 0)
			{
				game.setGameType(Game.TYPE_CASUAL);
				((PlayState) manager.getState(GameStateManager.PLAY_STATE)).setGame(game);
				manager.setCurrentState(GameStateManager.PLAY_STATE);
			}
			
			if(buttonID == 1)
			{
				
			}
			
			if(buttonID == 2)
			{
				game.setGameType(Game.TYPE_SPEED);
				((PlayState) manager.getState(GameStateManager.PLAY_STATE)).setGame(game);
				manager.setCurrentState(GameStateManager.PLAY_STATE);
			}
			
			if(buttonID == 3)
			{
				init();
				return;
			}
		}
	}

}
