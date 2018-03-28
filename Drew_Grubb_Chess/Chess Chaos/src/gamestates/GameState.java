package gamestates;

import java.awt.Graphics2D;

/**
 * Interface for the creation of new GameStates to be used in conjunction with the GameStateManager.
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
	 * Performs an action based on a button that is
	 * being clicked.
	 * @param buttonID
	 */
	public void performButtonAction(int buttonID);
}
