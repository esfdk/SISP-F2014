import java.util.List;

public class MiniMax 
{	
	public static int MiniMaxDecision(int[][] board, int cutoff, int playerID)
	{
		int decision = 0;
		int decisionValue = Integer.MIN_VALUE;

		for(int i = 0; i < board.length; i++)
		{
			if(MinValue(HelperClass.Result(board, i, playerID), cutoff, playerID) >= decisionValue)
			{
				decision = i;
			}
		}

		return decision;
	}

	public static int MaxValue(int[][] board, int cutoff, int playerID)
	{
		if(cutoff == 0)
		{
			return HelperClass.Eval(board, playerID);
		}

		int v = Integer.MIN_VALUE;

		List<Integer> actions = HelperClass.Actions(board);

		int np = nextPlayer(playerID);
		
		for(int a : actions)
		{
			v = Math.max(v, MinValue(HelperClass.Result(board, a, playerID), cutoff - 1, np));
		}

		return v;
	}

	public static int MinValue(int[][] board, int cutoff, int playerID)
	{
		if(cutoff == 0)
		{
			return HelperClass.Eval(board, playerID);
		}

		int v = Integer.MAX_VALUE;

		List<Integer> actions = HelperClass.Actions(board);

		int np = nextPlayer(playerID);
		
		for(int a : actions)
		{
			v = Math.min(v, MaxValue(HelperClass.Result(board, a, np), cutoff - 1, np));
		}

		return v;
	}

	private static int nextPlayer(int playerID)
	{
		switch(playerID)
		{
			case 1: return 2;
			case 2: return 1;
			default: return 0;
		}	
	}
}
