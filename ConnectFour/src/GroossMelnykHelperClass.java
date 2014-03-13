import java.util.ArrayList;
import java.util.List;

public class GroossMelnykHelperClass 
{
	/**
	 * The actions available on a given board.
	 * @param board The game board.
	 * @return A list of the actions available on a given board.
	 */
	public static List<Integer> Actions(int[][] board)
	{
		List<Integer> actions = new ArrayList<Integer>();

		for(int col = 0; col < board.length; col++)
		{
			int row = FindLowestEmptyRow(board, col);
			if (row > board[col].length)
				continue;
				
			if(board[col][row] == 0)
			{
				actions.add(col);
			}
		}

		return actions;
	}
	
	/**
	 * Finds the lowest empty row in the given column.
	 * @param board The game board.
	 * @param column The column to check.
	 * @return The index of the lowest row.
	 */
	private static int FindLowestEmptyRow(int[][] board, int column)
	{
		for (int row = 0; row < board[column].length; row++)
		{
			if (board[column][row] == 0)
				return row;
		}
		
		return Integer.MAX_VALUE - 1;
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

		for (int i = 0; i < result[column].length; i++) 
		{
			if(result[column][i] == 0)
			{
				result[column][i] = playerID;
				return result;
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
	public static int NextPlayer(int playerID)
	{
		switch(playerID)
		{
		case 1: return 2;
		case 2: return 1;
		default: return 0;
		}	
	}
}
