package gamestates;

import java.awt.Graphics2D;

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
	GameStateManager manager;
	
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
	}

	@Override
	public void update()
	{
	}

	@Override
	public void render(Graphics2D g)
	{
	}

	@Override
	public void performButtonAction(int buttonID)
	{
	}

}
