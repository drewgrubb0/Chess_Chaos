package gamestates;

import java.awt.Graphics2D;

import d_utils.DButton;

/**
 * 
 * @author Drew Grubb
 */
public class MenuState implements GameState
{
	GameStateManager manager;

	/**
	 * 
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
	public boolean isClickingButton(DButton button)
	{
		return false;
	}

	@Override
	public void performButtonAction(int buttonID)
	{
	}

}
