package gamestates;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

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
	public static final int MENU_PLAYERS = 1;
	public static final int MENU_SETTINGS = 2;
	public static final int MENU_BOARD = 3;
	
	public static final int TYPE_CASUAL = 0;
	public static final int TYPE_TIMED = 1;
	
	private GameStateManager manager;
	private DButton buttons[];
	private Game game;
	
	private int currentMenuState;
	private int currentPlayerSetting;
	private int gameType;
	
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
		
		buttons = new DButton[3];
		
		buttons[0] = new DButton();
		buttons[0].setDimensions(275, 175, 250, 100);
		buttons[0].setText("Quick Play");
		
		buttons[1] = new DButton();
		buttons[1].setDimensions(275, 300, 250, 100);
		buttons[1].setText("Play Chess");
		
		buttons[2] = new DButton();
		buttons[2].setDimensions(275, 425, 250, 100);
		buttons[2].setText("Play Chess Chaos");
		
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
				//Quickplay constructor
				game = new Game(manager.getInputManager());
				
				((PlayState) manager.getState(GameStateManager.PLAY_STATE)).setGame(game);
				manager.setCurrentState(GameStateManager.PLAY_STATE);
			}
			
			if(buttonID == 1)
			{	
				currentMenuState = MENU_PLAYERS;
				currentPlayerSetting = 0;
				menuText = "Standard Chess";
				subText = "Please choose player 1";
				
				game = new Game(2);
			}
			
			if(buttonID == 2)
			{
				currentMenuState = MENU_PLAYERS;
				currentPlayerSetting = 0;
				menuText = "Chess Chaos";
				subText = "Please choose player 1";
				
				game = new Game(4);
			}
			
			buttons[0].setText("Human");
			buttons[1].setText("Random");
			buttons[2].setText("Minimax");
		}
		else
		if(currentMenuState == MENU_PLAYERS)
		{
			if(buttonID == 0)
			{
				game.setUpPlayer(currentPlayerSetting, "Human", manager.getInputManager());
				currentPlayerSetting++;
				subText = "Please choose player " + (currentPlayerSetting + 1);
			}
			
			if(buttonID == 1)
			{
				game.setUpPlayer(currentPlayerSetting, "Random", manager.getInputManager());
				currentPlayerSetting++;
				subText = "Please choose player " + (currentPlayerSetting + 1);
			}
			
			if(buttonID == 2)
			{
				game.setUpPlayer(currentPlayerSetting, "Minimax", manager.getInputManager());
				currentPlayerSetting++;
				subText = "Please choose player " + (currentPlayerSetting + 1);
			}
			
			if(currentPlayerSetting >= game.getNumPlayers())
			{
				currentMenuState = MENU_SETTINGS;
				
				buttons[0].setText("Casual Chess");
				buttons[1].setText("Speed Chess");
				buttons[2].setText("Return to Menu");
				
				subText = "Please choose your game mode";
			}
		}
		else
		if(currentMenuState == MENU_SETTINGS)
		{
			if(buttonID == 0)
			{
				gameType = TYPE_CASUAL;
				game.setBoardType(Game.BOARD_CHESS);
				
				((PlayState) manager.getState(GameStateManager.PLAY_STATE)).setGame(game);
				manager.setCurrentState(GameStateManager.PLAY_STATE);
			}
			
			if(buttonID == 1)
			{
				gameType = TYPE_TIMED;
				game.setBoardType(Game.BOARD_RANDOM);
				
				((PlayState) manager.getState(GameStateManager.PLAY_STATE)).setGame(game);
				manager.setCurrentState(GameStateManager.PLAY_STATE);
			}
			
			if(buttonID == 2)
			{
				init();
			}
		}
	}

}
