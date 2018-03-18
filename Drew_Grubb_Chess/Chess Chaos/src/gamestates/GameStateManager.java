package gamestates;

import java.awt.Graphics2D;

import core.Display;
import input.InputManager;

/**
 * Class to manage and maintain the updating, rendering, and switching of GameStates
 * Creates a convenience for accessing and updating active GameStates.
 * 
 * @author Drew Grubb
 */
public class GameStateManager
{	
	public static final int MENU_STATE = 0;
	public static final int PLAY_STATE = 0;
	public static final int SPECTATE_STATE = 0;
	
	private Display display;
	
	private GameState states[];
	private int currentState;

	/**
	 * Initializes new GameStateManager for Chess Chaos
	 *
	 * @param display
	 */
	public GameStateManager(Display display)
	{
		this.display = display;
		
		states = new GameState[3];
		
		states[0] = new MenuState(this);
		states[1] = new PlayState(this);
		states[2] = new SpectateState(this);
		
		setCurrentState(1);
	}
	
	/**
	 * Calls the update method of the current active GameState
	 */
	public void updateCurrentState()
	{
		states[currentState].update();
	}
	
	/**
	 * Calls the render method of the current active GameState
	 * @param g
	 */
	public void renderCurrentState(Graphics2D g)
	{
		states[currentState].render(g);
	}

	/**
	 * Changes current state so that the GSM will now update and render
	 * that state. Calls init() of new state.
	 * @param currentState the currentState to set
	 */
	public void setCurrentState(int currentState)
	{
		this.currentState = currentState;
		states[currentState].init();
	}

	/**
	 * For convenience within GameStates
	 * @return the InputManager from display
	 */
	public InputManager getInputManager()
	{
		return display.getInputManager();
	}
}
