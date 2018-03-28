package gamestates;

import java.awt.Graphics2D;

import core.Display;
import input.InputManager;

/**
 * Class to manage and maintain the updating, rendering, and switching of GameStates
 * Creates a convenience for accessing and updating active GameStates.
 * This is where GameStates would have to manually be added to the code.
 * 
 * Is in charge of:
 * - Calling update/render methods of active states.
 * - Calling init methods of set states.
 * 
 * Is NOT in charge of:
 * - Deciding to change a state
 * - Performing any actual updating/rendering, merely a router.
 * 
 * @author Drew Grubb
 */
public class GameStateManager
{	
	public static final int MENU_STATE = 0;
	public static final int PLAY_STATE = 1;
	public static final int REPLAY_STATE = 2;
	
	private Display display;
	
	private GameState states[];
	private int currentState;

	/**
	 * Initializes new GameStateManager for Chess Chaos
	 * Sets default active state.
	 *
	 * @param display
	 */
	public GameStateManager(Display display)
	{
		this.display = display;
		
		states = new GameState[3];
		
		states[0] = new MenuState(this);
		states[1] = new PlayState(this);
		states[2] = new ReplayState(this);
		
		setCurrentState(2);
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
