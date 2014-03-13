import java.util.ArrayList;
import java.util.List;

public class HelperClass 
{
	private static int hThree = 9, hTwo = 3, hOne = 1;
	private static int maxValue = 100000;
	
	private static int[][] singlePointGiven;

	public static int Eval(int[][] board, int playerID)
	{		
//		for(int i = board[0].length - 1; i >= 0; i--)
//		{
//			for(int j = 0; j < board.length; j++)
//			{
//				System.out.print(board[j][i]);
//			}
//			System.out.print("\n");
//		}
		
		singlePointGiven = new int[board.length][board[0].length];

		int eH = EvalHorizontal(board, playerID);
		if (eH == maxValue || eH == -maxValue) return eH;

		int eV = EvalVertical(board, playerID);
		if (eV == maxValue || eV == -maxValue) return eV;

		int eDL = EvalDiagonalLeft(board, playerID);
		if (eDL == maxValue || eDL == -maxValue) return eDL;

		int eDR = EvalDiagonalRight(board, playerID);
		if (eDR == maxValue || eDR == -maxValue) return eDR;

		return (eH + eV + eDL + eDR);
	}

	private static int EvalHorizontal(int[][] board, int playerID)
	{
		int result = 0;
		int nextPlayer = NextPlayer(playerID);

		for (int rows = 0; rows < board[0].length; rows++)
		{
			for (int cols = 0; cols < board.length; cols++)
			{
				int tempResult = 0;

				if (board[cols][rows] == playerID)
				{		
					if (InsideBoardBounds(cols + 3, rows, board))
					{
						if (PossibleLine(cols, rows, 1, 0, board, playerID))
						{
							if (board[cols + 3][rows] == playerID 
									&& board[cols + 2][rows] == playerID
									&& board[cols + 1][rows] == playerID)
							{
								return maxValue;
//								return Integer.MAX_VALUE - 1;
							}
							else if (board[cols + 2][rows] == playerID
									&& board[cols + 1][rows] == playerID)
							{
								tempResult += hThree;
								cols += 2;
							}
							else if (board[cols + 1][rows] == playerID)
							{
								tempResult += hTwo;
								cols += 1;
							}
							else
							{
								singlePointGiven[cols][rows] = 1;
								tempResult += hOne;
							}
						}
					}
					else if(InsideBoardBounds(cols - 3, rows, board))
					{
						if(PossibleLine(cols, rows, -1, 0, board, playerID))
						{
							singlePointGiven[cols][rows] = 1;
							tempResult += hOne;
						}
					}
				}
				else if (board[cols][rows] == nextPlayer)
				{
					if (InsideBoardBounds(cols + 3, rows, board))
					{
						if (PossibleLine(cols, rows, 1, 0, board, nextPlayer))
						{
							if (board[cols + 3][rows] == nextPlayer 
									&& board[cols + 2][rows] == nextPlayer
									&& board[cols + 1][rows] == nextPlayer)
							{
								return -maxValue;
//								return Integer.MIN_VALUE + 1;
							}
							else if (board[cols + 2][rows] == nextPlayer
									&& board[cols + 1][rows] == nextPlayer)
							{
								tempResult -= hThree;
								cols += 2;
							}
							else if (board[cols + 1][rows] == nextPlayer)
							{
								tempResult -= hTwo;
								cols += 1;
							}
							else
							{
								singlePointGiven[cols][rows] = 1;
								tempResult -= hOne;
							}
						}

					}
					else if(InsideBoardBounds(cols - 3, rows, board))
					{
						if(PossibleLine(cols, rows, -1, 0, board, nextPlayer))
						{
							singlePointGiven[cols][rows] = 1;
							tempResult -= hOne;
						}
					}
				}

				result += tempResult;
			}
		}

		return result;
	}

	private static int EvalVertical(int[][] board, int playerID)
	{
		int result = 0;
		int nextPlayer = NextPlayer(playerID);

		for (int cols = 0; cols < board.length; cols++)
		{
			for (int rows = 0; rows < board[0].length; rows++)
			{
				int tempResult = 0;

				if (board[cols][rows] == playerID)
				{
					if (InsideBoardBounds(cols, rows + 3, board))
					{
						if (PossibleLine(cols, rows, 0, 1, board, playerID))
						{
							if (board[cols][rows + 3] == playerID 
									&& board[cols][rows + 2] == playerID
									&& board[cols][rows + 1] == playerID)
							{
								return maxValue;
//								return Integer.MAX_VALUE - 1;
							}
							else if (board[cols][rows + 2] == playerID
									&& board[cols][rows + 1] == playerID)
							{
								tempResult += hThree;
								rows += 2;
							}
							else if (board[cols][rows + 1] == playerID)
							{
								tempResult += hTwo;
								rows += 1;
							}
							else
							{
								if(singlePointGiven[cols][rows] == 0)
								{
									tempResult += hOne;
									singlePointGiven[cols][rows] = 1;
								}
							}
						}
					}
				}
				else if (board[cols][rows] == nextPlayer)
				{
					if (InsideBoardBounds(cols, rows + 3, board))
					{
						if (PossibleLine(cols, rows, 0, 1, board, nextPlayer))
						{
							if (board[cols][rows + 3] == nextPlayer
									&& board[cols][rows + 2] == nextPlayer
									&& board[cols][rows + 1] == nextPlayer)
							{
								return -maxValue;
//								return Integer.MIN_VALUE + 1;
							}
							else if (board[cols][rows + 2] == nextPlayer
									&& board[cols][rows + 1] == nextPlayer)
							{
								tempResult -= hThree;
								rows += 2;
							}
							else if (board[cols][rows + 1] == nextPlayer)
							{
								tempResult -= hTwo;
								rows += 1;
							}
							else
							{
								if(singlePointGiven[cols][rows] == 0)
								{
									tempResult -= hOne;
									singlePointGiven[cols][rows] = 1;
								}
							}
						}
					}
				}

				result += tempResult;
			}
		}

		return result;
	}

	private static int EvalDiagonalLeft(int[][] board, int playerID)
	{
		int result = 0;
		int nextPlayer = NextPlayer(playerID);
		int[][] visitedPoints = new int[board.length][board[0].length];

		for (int rows = 0; rows < board[0].length; rows++)
		{
			for (int cols = 0; cols < board.length; cols++)
			{
				int tempResult = 0;

				if (visitedPoints[cols][rows] == 1)
				{
					continue;
				}

				visitedPoints[cols][rows] = 1;

				if (board[cols][rows] == playerID)
				{
					if (InsideBoardBounds(cols - 3, rows + 3, board))
					{
						if (PossibleLine(cols, rows, -1, 1, board, playerID))
						{
							if (board[cols - 3][rows + 3] == playerID 
									&& board[cols - 2][rows + 2] == playerID
									&& board[cols - 1][rows + 1] == playerID)
							{
								return maxValue;
//								return Integer.MAX_VALUE - 1;
							}
							else if (board[cols - 2][rows + 2] == playerID
									&& board[cols - 1][rows + 1] == playerID)
							{
								tempResult += hThree;
								visitedPoints[cols - 2][rows + 2] = 1;
								visitedPoints[cols - 1][rows + 1] = 1;
							}
							else if (board[cols - 1][rows + 1] == playerID)
							{
								tempResult += hTwo;
								visitedPoints[cols - 1][rows + 1] = 1;
							}
							else
							{
								if(singlePointGiven[cols][rows] == 0)
								{
									tempResult += hOne;
									singlePointGiven[cols][rows] = 1;
								}
							}
						}
					}
				}
				else if (board[cols][rows] == nextPlayer)
				{
					if (InsideBoardBounds(cols - 3, rows + 3, board))
					{
						if (PossibleLine(cols, rows, -1, 1, board, nextPlayer))
						{
							if (board[cols - 3][rows + 3] == nextPlayer
									&& board[cols - 2][rows + 2] == nextPlayer
									&& board[cols - 1][rows + 1] == nextPlayer)
							{
								return -maxValue;
//								return Integer.MIN_VALUE + 1;
							}
							else if (board[cols - 2][rows + 2] == nextPlayer
									&& board[cols - 1][rows + 1] == nextPlayer)
							{
								tempResult -= hThree;
								visitedPoints[cols - 2][rows + 2] = 1;
								visitedPoints[cols - 1][rows + 1] = 1;
							}
							else if (board[cols - 1][rows + 1] == nextPlayer)
							{
								tempResult -= hTwo;
								visitedPoints[cols - 1][rows + 1] = 1;
							}
							else
							{
								if(singlePointGiven[cols][rows] == 0)
								{
									tempResult -= hOne;
									singlePointGiven[cols][rows] = 1;
								}
							}
						}
					}
				}

				result += tempResult;
			}
		}

		return result;
	}

	private static int EvalDiagonalRight(int[][] board, int playerID)
	{
		int result = 0;
		int nextPlayer = NextPlayer(playerID);
		int[][] visitedPoints = new int[board.length][board[0].length];

		for (int rows = 0; rows < board[0].length; rows++)
		{
			for (int cols = 0; cols < board.length; cols++)
			{
				int tempResult = 0;

				if (visitedPoints[cols][rows] == 1)
				{
					continue;
				}

				visitedPoints[cols][rows] = 1;

				if (board[cols][rows] == playerID)
				{
					if (InsideBoardBounds(cols + 3, rows + 3, board))
					{
						if (PossibleLine(cols, rows, 1, 1, board, playerID))
						{
							if (board[cols + 3][rows + 3] == playerID 
									&& board[cols + 2][rows + 2] == playerID
									&& board[cols + 1][rows + 1] == playerID)
							{
								return maxValue;
//								return Integer.MAX_VALUE - 1;
							}
							else if (board[cols + 2][rows + 2] == playerID
									&& board[cols + 1][rows + 1] == playerID)
							{
								tempResult += hThree;
								visitedPoints[cols + 2][rows + 2] = 1;
								visitedPoints[cols + 1][rows + 1] = 1;
							}
							else if (board[cols + 1][rows + 1] == playerID)
							{
								tempResult += hTwo;
								visitedPoints[cols + 1][rows + 1] = 1;
							}
							else
							{
								if(singlePointGiven[cols][rows] == 0)
								{
									tempResult += hOne;
									singlePointGiven[cols][rows] = 1;
								}
							}
						}
					}
				}
				else if (board[cols][rows] == nextPlayer)
				{
					if (InsideBoardBounds(cols + 3, rows + 3, board))
					{
						if (PossibleLine(cols, rows, 1, 1, board, nextPlayer))
						{
							if (board[cols + 3][rows + 3] == nextPlayer
									&& board[cols + 2][rows + 2] == nextPlayer
									&& board[cols + 1][rows + 1] == nextPlayer)
							{
								return -maxValue;
//								return Integer.MIN_VALUE + 1;
							}
							else if (board[cols + 2][rows + 2] == nextPlayer
									&& board[cols + 1][rows + 1] == nextPlayer)
							{
								tempResult -= hThree;
								visitedPoints[cols + 2][rows + 2] = 1;
								visitedPoints[cols + 1][rows + 1] = 1;
							}
							else if (board[cols + 1][rows + 1] == nextPlayer)
							{
								tempResult -= hTwo;
								visitedPoints[cols + 1][rows + 1] = 1;
							}
							else
							{
								if(singlePointGiven[cols][rows] == 0)
								{
									tempResult -= hOne;
									singlePointGiven[cols][rows] = 1;
								}
							}
						}
					}
				}

				result += tempResult;
			}
		}

		return result;
	}

	/**
	 * Checks if the given coordinates are inside the board bounds.
	 * 
	 * @param col The column to check.
	 * @param row The row to check.
	 * @param board The board to check for.
	 * @return True if the place is inside the board. False otherwise.
	 */
	private static boolean InsideBoardBounds(int col, int row, int[][] board)
	{
		if (col >= board.length || col < 0) return false;
		if (row >= board[0].length || row < 0) return false;

		return true;
	}

	/**
	 * Checks if the given line can result in 4-in-a-row.
	 * 
	 * @param currCol The column to start at.
	 * @param currRow The row to start at.
	 * @param colChange How much the column changes per step.
	 * @param rowChange How much the row changes per step.
	 * @param board The board to check on.
	 * @param playerID The ID of the current player.
	 * @return True if it is possible to get 4-in-a-row for the given player. False otherwise.
	 */
	private static boolean PossibleLine(int currCol, int currRow, int colChange, int rowChange, int[][] board, int playerID)
	{
		for (int i = 0; i < 4; i++)
		{
			if (board[currCol + (i * colChange)][currRow + (i * rowChange)] == NextPlayer(playerID)) return false;
		}

		return true;
	}

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
	
	private static int FindLowestEmptyRow(int[][] board, int column)
	{
		for (int row = 0; row < board[column].length; row++)
		{
			if (board[column][row] == 0)
				return row;
		}
		
		return maxValue;
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
	
	
	public static void PrintBoard(int[][] board)
	{
		for(int i = board[0].length - 1; i >= 0; i--)
		{
			for(int j = 0; j < board.length; j++)
			{
				System.out.print(board[j][i]);
			}
			System.out.print("\n");
		}
		System.out.println();
	}
}
