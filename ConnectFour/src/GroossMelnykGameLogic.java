public class GroossMelnykGameLogic implements IGameLogic 
{
	private static int cutoff;
	private int sizeX = 0;
	private int sizeY = 0;
	private int playerID;

	private int emptySlots;
	private int[][] board;

	public GroossMelnykGameLogic() 
	{
	}

	public void initializeGame(int x, int y, int playerID) 
	{
		cutoff = 3;
		this.sizeX = x;
		this.sizeY = y;
		this.playerID = playerID;
		board = new int[sizeX][sizeY];
		emptySlots = sizeX * sizeY;
	}

	public Winner gameFinished() 
	{
		for (int i = 0; i < sizeX; i++) 
		{
			for (int j = 0; j < sizeY; j++) 
			{
				if (board[i][j] != 0) 
				{
					int p = board[i][j];

					if (i + 3 < sizeX) 
					{
						if (board[i + 1][j] == p && board[i + 2][j] == p
								&& board[i + 3][j] == p)
							return whoWon(p);
					}

					if (j + 3 < sizeY) 
					{
						if (board[i][j + 1] == p && board[i][j + 2] == p
								&& board[i][j + 3] == p)
							return whoWon(p);
					}

					if (i + 3 < sizeX && j + 3 < sizeY) 
					{
						if (board[i + 1][j + 1] == p
								&& board[i + 2][j + 2] == p
								&& board[i + 3][j + 3] == p)
							return whoWon(p);
					}

					if (i - 3 >= 0 && j + 3 < sizeY) 
					{
						if (board[i - 1][j + 1] == p
								&& board[i - 2][j + 2] == p
								&& board[i - 3][j + 3] == p)
							return whoWon(p);
					}
				}
			}
		}

		if (emptySlots == 0)
			return Winner.TIE;

		return Winner.NOT_FINISHED;
	}

	private Winner whoWon(int p) 
	{
		if (p == 1)
			return Winner.PLAYER1;

		return Winner.PLAYER2;
	}

	public void insertCoin(int column, int playerID) 
	{
		for (int i = 0; i < sizeY; i++) 
		{
			if (board[column][i] == 0) 
			{
				board[column][i] = playerID;
				emptySlots--;
				return;
			}
		}
	}

	public int decideNextMove() 
	{
		return GroossMelnykMiniMax.MiniMaxDecision(board, cutoff, playerID);
	}
}