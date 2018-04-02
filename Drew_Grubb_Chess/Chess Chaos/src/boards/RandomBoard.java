package boards;

import java.util.Random;

import moves.Position;
import pieces.Bishop;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Piece;
import pieces.Queen;
import pieces.Rook;

/**
 * Octagonal Chess Board setup
 * Randomly designed by myself to be used for testing of different environments
 * and neutral pieces IE solid "border" pieces.
 *
 * @author Drew Grubb
 */
public class RandomBoard extends Board
{
	Random random;

	/**
	 * Sets up octagonal chess board with solid neutral pieces as the border
	 * @param playState 
	 */
	public RandomBoard()
	{	
		super(8, 8);
		random = new Random();
		int type = 0;
			
		for(int x = 0 ; x < 8 ; x++)
		{
			for(int y = 0 ; y < 8 ; y++)
			{
				if(y == 2)
					y = 6;
				
				type = random.nextInt(5);
				
				if(y < 2)
				{
					switch(type)
					{
						case 0:
							setPiece(new Position(x, y), new Pawn(Piece.BLACK));
						break;
						case 1:
							setPiece(new Position(x, y), new Rook(Piece.BLACK));
						break;
						case 2:
							setPiece(new Position(x, y), new Bishop(Piece.BLACK));
						break;
						case 3:
							setPiece(new Position(x, y), new Knight(Piece.BLACK));
						break;
						case 4:
							setPiece(new Position(x, y), new Queen(Piece.BLACK));
						break;
					}
				}
				
				if(y >= 6)
				{
					switch(type)
					{
						case 0:
							setPiece(new Position(x, y), new Pawn(Piece.WHITE));
						break;
						case 1:
							setPiece(new Position(x, y), new Rook(Piece.WHITE));
						break;
						case 2:
							setPiece(new Position(x, y), new Bishop(Piece.WHITE));
						break;
						case 3:
							setPiece(new Position(x, y), new Knight(Piece.WHITE));
						break;
						case 4:
							setPiece(new Position(x, y), new Queen(Piece.WHITE));
						break;
					}
				}
				
			}
		}
		
		setPiece(new Position(random.nextInt(8), random.nextInt(2)), new King(Piece.BLACK));
		setPiece(new Position(random.nextInt(8), random.nextInt(2) + 6), new King(Piece.WHITE));
	}

}
