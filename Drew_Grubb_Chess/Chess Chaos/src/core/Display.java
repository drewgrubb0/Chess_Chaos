package core;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import gamestates.GameStateManager;
import input.InputManager;

/**
 * Class in charge of instantiating all display related fields in a GameLoop setting:
 * JFrame
 * BufferStrategy
 * Canvas - BorderLayout
 * InputManager
 * 
 * @author Drew Grubb
 */
public class Display extends Canvas
{
	private static final long serialVersionUID = 1L;
	
	private JFrame frame;
	private int width, height;
	
	private GameStateManager gameStateManager;
	private InputManager listener;
	
	/**
	 * Initializes GameStateManager and an input manager
	 * before creating the window display.
	 */
	public Display()
	{	
		listener = new InputManager();
		this.addMouseListener(listener);
		this.addMouseMotionListener(listener);
		
		gameStateManager = new GameStateManager(this);
		
		width = 800;
		height = 600;
		createWindow();
	}
	
	/**
	 * Initializes the JFrame and BorderLayout that the
	 * graphics will be displayed on
	 */
	public void createWindow()
	{
		frame = new JFrame("Chess Chaos by Drew Grubb");
		
		frame.setSize(width, height);
		frame.setResizable(false);
		frame.setFocusable(false);
		
		//Centers on Screen
		frame.setLocationRelativeTo(null);
		
		//x ends the program
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setVisible(true);
		frame.add(this, BorderLayout.CENTER);
	}
	
	/**
	 * Calls the update method within the GameStateManager
	 */
	public void update()
	{
		gameStateManager.updateCurrentState();
	}
	
	/**
	 * Creates the BufferStrategy and graphics library that will
	 * be used to render to the JFrame.
	 * 
	 * Draws from the graphics library into the JFrame,
	 * calls the render method within the GameStateManager.
	 */
	public void render()
	{
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){this.createBufferStrategy(2); return;}
		Graphics2D g = (Graphics2D) bs.getDrawGraphics();

		//Anti-Aliases fonts to prevent chopped edges
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		//Set Background to Black
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
		
		//Set default Font
		g.setFont(new Font(Font.DIALOG, Font.PLAIN, 13));
		
		gameStateManager.renderCurrentState(g);
		listener.clearInputCallbacks();
		
		g.dispose();
		bs.show();
	}

	/**
	 * @return the InputManager
	 */
	public InputManager getInputManager()
	{
		return listener;
	}
}
