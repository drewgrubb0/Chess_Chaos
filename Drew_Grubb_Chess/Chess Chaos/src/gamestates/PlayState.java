package gamestates;

import java.awt.Graphics2D;
import java.util.Stack;

import boards.Board;
import boards.Move;
import boards.OctoChessBoard;
import boards.Position;
import d_utils.DButton;
import pieces.Piece;
import players.Player;

/**
 * PlayState handles the state of the board as well as any in game buttons.
 * Updates player turn system
 * @author Drew Grubb
 */
public class PlayState implements GameState
{
	GameStateManager manager;
	
	DButton button[];
	Board board;
	
	Player players[];
	int currentTurnColor;
	
	Stack<Move> moves;

	/**
	 * Creates a new PlayState that handles the state of the board
	 * @param gameStateManager
	 */
	public PlayState(GameStateManager gameStateManager)
	{
		this.manager = gameStateManager;
		
		players = new Player[2];
		board = new OctoChessBoard(this);
		
		moves = new Stack<Move>();
		currentTurnColor = Piece.WHITE;
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
		board.renderBoard(g);
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
	
	/**
	 * Makes a move from point A to point B and switches the turn
	 */
	public void makeMove(Piece piece, Position destination)
	{
		moves.add(new Move(piece.getPosition(), destination));
		board.performMove(piece, moves.peek());
		switchTurn();
	}

	/**
	 * Performs any action that happens on the switching of a turn.
	 * This includes:
	 * Updating King Position
	 * Updating isInCheck
	 * Updating isInCheckmate
	 * Updating isInStalemate
	 * Updating possibleMoves
	 * Changing player control
	 */
	public void switchTurn()
	{
		board.updateKingPosition(currentTurnColor);
		board.UpdateInCheck(currentTurnColor);
		
		if(board.isInCheckmate(currentTurnColor))
			return; //TODO
		if(board.isInStalemate(currentTurnColor))
			return; //TODO
		
		board.updatePossibleMoves();
		
		if(currentTurnColor < players.length)
			currentTurnColor = 0;
		else
			currentTurnColor++;
	}
}
