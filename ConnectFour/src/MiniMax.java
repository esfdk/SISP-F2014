import java.util.List;

public class MiniMax 
{	
	private static int player;
	
	/**
	 * Evaluates the game board and estimates the best move to make with the current board state.
	 * 
	 * @param board The game board.
	 * @param cutoff How many steps the algorithm should look ahead.
	 * @param playerID The ID of the player making the decision.
	 * @return The MiniMax decision.
	 */
	public static int MiniMaxDecision(int[][] board, int cutoff, int playerID)
	{
		player = playerID;
		
		int decision = 0;
		int decisionValue = Integer.MIN_VALUE;
		
		List<Integer> actions = HelperClass.Actions(board);
		
		for(int a : actions)
		{
			int tempValue = MinValue(HelperClass.Result(board, a, playerID), cutoff, HelperClass.NextPlayer(playerID));
			if (tempValue >= decisionValue)
			{
				decision = a;
				decisionValue = tempValue;
			}
		}

		return decision;
	}

	/**
	 * Calculates the maximum value decision for the MiniMax algorithm.
	 * 
	 * @param board The board to consider.
	 * @param cutoff How many steps the function should look ahead.
	 * @param playerID The ID of the player taking the decision.
	 * @return The value for this step.
	 */
	private static int MaxValue(int[][] board, int cutoff, int playerID)
	{
		if(cutoff == 0)
		{
			return HelperClass.Eval(board, player);
		}

		int v = Integer.MIN_VALUE;
		int np = HelperClass.NextPlayer(playerID);
		
		List<Integer> actions = HelperClass.Actions(board);
		
		for(int a : actions)
		{
			v = Math.max(v, MinValue(HelperClass.Result(board, a, playerID), cutoff - 1, np));
		}

		return v;
	}

	/**
	 * Calculates the minimum value decision for the MiniMax algorithm.
	 * 
	 * @param board The board to consider.
	 * @param cutoff How many steps the function should look ahead.
	 * @param playerID The ID of the player taking the decision.
	 * @return The value for this step.
	 */
	private static int MinValue(int[][] board, int cutoff, int playerID)
	{
		if(cutoff == 0)
		{
			return HelperClass.Eval(board, player);
		}

		int v = Integer.MAX_VALUE;
		int np = HelperClass.NextPlayer(playerID);
		
		List<Integer> actions = HelperClass.Actions(board);
		
		for(int a : actions)
		{
			v = Math.min(v, MaxValue(HelperClass.Result(board, a, playerID), cutoff - 1, np));
		}

		return v;
	}
	
	private static int CountFilledPlaces(int[][] board)
	{
		int result = 0;

		for(int i = board[0].length - 1; i >= 0; i--)
		{
			for(int j = 0; j < board.length; j++)
			{
				if (board[j][i] != 0)
					result++;
			}
		}
		
		return result;
	}
}
