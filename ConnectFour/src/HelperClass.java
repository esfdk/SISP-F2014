import java.util.ArrayList;
import java.util.List;

public class HelperClass 
{
	public static int Eval(int[][] board, int player)
	{
		// TODO: Implement this
		return 0;
	}

	public static List<Integer> Actions(int[][] board)
	{
		// TODO: Implement this
		List<Integer> actions = new ArrayList<Integer>();
		return actions;
	}

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
