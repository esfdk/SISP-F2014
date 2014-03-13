import java.util.ArrayList;
import java.util.List;

public class HelperClass 
{
	public static int Eval(int[][] board, int player)
	{
		// TODO: Implement this
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
}
