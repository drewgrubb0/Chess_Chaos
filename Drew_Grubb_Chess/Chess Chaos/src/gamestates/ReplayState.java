package gamestates;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Stack;

import boards.Board;
import boards.ChessBoard;
import d_utils.DButton;
import d_utils.DTimer;
import moves.Move;

/**
 * GameState in charge of replaying a game in a spectator view using a specific
 * .chao file. Player can watch the game move by move or automatically.
 * 
 * Is in charge of:
 * - Replaying the given game move by move
 * - Changing the speed of the replay on player request
 * 
 * Is NOT in charge of:
 * - Performing any move actions
 * - Handling user input beyond button clicking
 * - Handling a game of chess
 * 
 * @author Drew Grubb
 */
public class ReplayState implements GameState
{
	GameStateManager manager;
	DButton buttons[];
	
	Board board;
	
	String replayFile;
	Stack<Move> movesMade;
	Stack<Move> movesLeft;
	
	boolean isPaused;
	int moveDelayMS;
	DTimer moveTimer;

	/**
	 * Initializes the Replay State that handles the replaying of a game
	 * - Creates buttons
	 * - Initializes Stacks
	 * - Creates timer
	 */
	public ReplayState(GameStateManager manager)
	{
		this.manager = manager;
		isPaused = false;
		
		replayFile = "";
		movesLeft = new Stack<Move>();
		movesMade = new Stack<Move>();
		moveDelayMS = 1000;
		
		moveTimer = new DTimer();
		buttons = new DButton[6];
		
		buttons[0] = new DButton();
		buttons[0].setDimensions(525, 35, 250, 100);
		buttons[0].setText("Pause");
		
		buttons[1] = new DButton();
		buttons[1].setDimensions(525, 160, 120, 100);
		buttons[1].setText("Speed up");
		
		buttons[2] = new DButton();
		buttons[2].setDimensions(655, 160, 120, 100);
		buttons[2].setText("Slow down");
		
		buttons[3] = new DButton();
		buttons[3].setDimensions(525, 285, 120, 100);
		buttons[3].setText("Do Move");
		
		buttons[4] = new DButton();
		buttons[4].setDimensions(655, 285, 120, 100);
		buttons[4].setText("Undo Move");
		
		buttons[5] = new DButton();
		buttons[5].setDimensions(525, 410, 250, 100);
		buttons[5].setText("Quit to Menu");
	}

	@Override
	public void init()
	{
		//TODO set up board based on file name
		board = new ChessBoard();
		
//		if(loadReplayFile(replayFile) == false)
//			manager.setCurrentState(GameStateManager.MENU_STATE);
		
		moveDelayMS = 1000;
		isPaused = false;
		
		moveTimer.startTimer();
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
			if(moveTimer.getTime() >= moveDelayMS)
			{
				if(movesLeft.size() > 0)
				{
					board.performMove(movesLeft.peek());
					movesMade.push(movesLeft.pop());
				}
				
				moveTimer.resetTimer();
			}
		}
	}

	@Override
	public void render(Graphics2D g)
	{
		board.renderBoard(g);
		
		g.setColor(Color.GREEN);
		
		//Render buttons
		for(int x = 0 ; x < buttons.length ; x++)
			if(buttons[x].isHovering(manager.getInputManager().getMousePosition()))
				buttons[x].renderHovered(g);
			else 
				buttons[x].render(g);
		
		g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
		g.drawString("Replaying game from " + replayFile, 30, 545);
	}

	@Override
	public void performButtonAction(int buttonID)
	{
		if(buttonID == 0)
		{
			if(isPaused)
			{
				buttons[buttonID].setText("Pause");
				isPaused = false;
				moveTimer.resumeTimer();
			}
			else
			{
				moveTimer.pauseTimer();
				isPaused = true;
				buttons[buttonID].setText("Resume");
			}
		}
		
		if(buttonID == 1)
		{
			if(moveDelayMS >= 0)
				moveDelayMS -= 200;
		}
		
		if(buttonID == 2)
		{
			moveDelayMS += 200;
		}
		
		if(buttonID == 3)
		{
			if(movesLeft.size() > 0)
			{
				movesMade.push(movesLeft.pop());
				board.undoLastMove();
			}
		}
		
		if(buttonID == 4)
		{
			if(movesMade.size() > 0)
			{
				movesLeft.push(movesMade.pop());
				board.performMove(movesMade.peek());
			}
		}
		
		if(buttonID == 5)
		{
			manager.setCurrentState(GameStateManager.MENU_STATE);
		}
	}
	
	/**
	 * Uses ObjectInputStream to read from the .chao file to perform the following:
	 * Loads the Move Stack from the file and reverses it into the ReplayState Move Stack.
	 * @param path
	 * @return successfulness of the loading
	 */
	public boolean loadReplayFile(String path)
	{
		try{
			ObjectInputStream reader = new ObjectInputStream(new FileInputStream(new File(path)));
			
			@SuppressWarnings("unchecked")
			Stack<Move> moveSet = (Stack<Move>) reader.readObject();
			
			//Places moves in backwards because Move Stack is saved with the last move on the top of the stack
			while(moveSet.size() > 0)
				movesLeft.push(moveSet.pop());
			
			reader.close();
			
		} catch(IOException | ClassNotFoundException e){
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	/**
	 * Sets the game file to be loaded up upon ReplayState initialization
	 */
	public void setGameFile(String replayFile)
	{
		this.replayFile = replayFile;
	}

}
