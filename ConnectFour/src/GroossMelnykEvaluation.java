public class GroossMelnykEvaluation 
{
	private static int hThree = 9, hTwo = 3, hOne = 1;
	private static int maxValue = Integer.MAX_VALUE - 1;
	
	private static int[][] singlePointGiven;

	/**
	 * Evaluates the state of the board.
	 * 
	 * @param board The board to evaluate.
	 * @param playerID The ID of the current player.
	 * @return The value.
	 */
	public static int Eval(int[][] board, int playerID)
	{
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

	/**
	 * Calculates the horizontal values of the board.
	 * 
	 * @param board The board.
	 * @param playerID The ID of the current player.
	 * @return The total of the horizontal values of the board.
	 */
	private static int EvalHorizontal(int[][] board, int playerID)
	{
		int result = 0;
		int nextPlayer = GroossMelnykHelperClass.NextPlayer(playerID);

		// Iterate over all spaces of the board.
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

	/**
	 * Calculates the vertical values of the board.
	 * 
	 * @param board The board.
	 * @param playerID The ID of the current player.
	 * @return The total of the horizontal values of the board.
	 */
	private static int EvalVertical(int[][] board, int playerID)
	{
		int result = 0;
		int nextPlayer = GroossMelnykHelperClass.NextPlayer(playerID);

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

	/**
	 * Calculates the diagonal values of the board that goes left.
	 * 
	 * @param board The board.
	 * @param playerID The ID of the current player.
	 * @return The total of the horizontal values of the board.
	 */
	private static int EvalDiagonalLeft(int[][] board, int playerID)
	{
		int result = 0;
		int nextPlayer = GroossMelnykHelperClass.NextPlayer(playerID);
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

	/**
	 * Calculates the diagonal values of the board that goes right.
	 * 
	 * @param board The board.
	 * @param playerID The ID of the current player.
	 * @return The total of the horizontal values of the board.
	 */
	private static int EvalDiagonalRight(int[][] board, int playerID)
	{
		int result = 0;
		int nextPlayer = GroossMelnykHelperClass.NextPlayer(playerID);
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
			if (board[currCol + (i * colChange)][currRow + (i * rowChange)] == GroossMelnykHelperClass.NextPlayer(playerID)) return false;
		}

		return true;
	}
}
