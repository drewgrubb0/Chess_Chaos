package gamestates;

import java.awt.Graphics2D;

import d_utils.DButton;

/**
 * Interface for the creation of new GameStates
 * For the purpose of Chess Chaos should only be used for
 * MenuState, PlayState, and SpectateState
 * 
 * @author Drew Grubb
 */
public interface GameState
{
	
	/**
	 * Calls the state initiation method whenever the
	 * state is switched to the active state
	 */
	public void init();
	
	/**
	 * Calls the state update feature
	 */
	public void update();
	
	/**
	 * Calls the state render feature to draw
	 * any objects/text to the screen
	 */
	public void render(Graphics2D g);

	/**
	 * checks to see if a button is being clicked
	 * @param rect
	 * @return 
	 */
	public boolean isClickingButton(DButton button);
	
	/**
	 * Performs an action based on a button that is
	 * being clicked.
	 * @param buttonID
	 */
	public void performButtonAction(int buttonID);
}
