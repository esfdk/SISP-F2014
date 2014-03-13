import java.util.ArrayList;
import java.util.List;

public class HelperClass 
{
	public static int Eval(int[][] board, int player)
	{
		int result = 0;
		
		result += EvalHorizontal(board, player);
		result += EvalVertical(board, player);
		result += EvalDiagonalLeft(board, player);
		result += EvalDiagonalRight(board, player);
		
		// TODO: Implement this
		return result;
	}
	
	private static int EvalHorizontal(int[][] board, int playerID)
	{
		for (int cols = 0; cols < board.length; cols++)
		{
			for (int rows = 0; rows < board[0].length; rows++)
			{
				if (board[cols][rows] == playerID)
				{
					
				}
				else if (board[cols][rows] == nextPlayer(playerID))
				{
					
				}
			}
		}
		
		return 0;
	}
	
	private static int EvalVertical(int[][] board, int player)
	{
		
		return 0;
	}
	
	private static int EvalDiagonalLeft(int[][] board, int player)
	{
		
		return 0;
	}
	
	private static int EvalDiagonalRight(int[][] board, int player)
	{
		
		return 0;
	}

	/**
	 * The actions available on a given board.
	 * @param board The game board.
	 * @return A list of the actions available on a given board.
	 */
	public static List<Integer> Actions(int[][] board)
	{
		List<Integer> actions = new ArrayList<Integer>();
		
		for(int i = 0; i < board.length; i++)
		{
			if(board[i][0] == 0)
			{
				actions.add(i);
			}
		}
		
		return actions;
	}

	/**
	 * Calculates the resulting board state of an action performed on a specific board state.
	 * @param board The game board.
	 * @param column The action being made.
	 * @param playerID The player taking the action.
	 * @return The resulting board.
	 */
	public static int[][] Result(int[][] board, int column, int playerID)
	{
		int[][] result = copyBoard(board);

		for (int i = 0; i < result.length; i++) 
		{
			if(result[column][i] == 0)
			{
				result[column][i] = playerID;	
			}
		}

		return result;
	}

	/**
	 * Copies a board into a new board (used in the result function).
	 * @param board The board to copy.
	 * @return The newly copied board.
	 */
	private static int[][] copyBoard(int[][] board)
	{
		// Array copy code courtesy of http://stackoverflow.com/questions/1686425/copy-a-2d-array-in-java
		int [][] boardClone = new int[board.length][];
		for(int i = 0; i < board.length; i++)
		{
			boardClone[i] = board[i].clone();
		}
		return boardClone;
	}
	
	/**
	 * The playerID of the next player.
	 * 
	 * @param playerID The ID of the current player.
	 * @return The ID of the next player.
	 */
	public static int nextPlayer(int playerID)
	{
		switch(playerID)
		{
			case 1: return 2;
			case 2: return 1;
			default: return 0;
		}	
	}
}
