import net.sf.javabdd.*;

/**
 * This class implements the logic behind the BDD for the n-queens problem
 * You should implement all the missing methods
 * 
 * @author Stavros Amanatidis (with edits by Jacob Grooss (jcgr) & Jakob Melnyk (jmel))
 *
 */
public class QueensLogic 
{
	private int size = 0;
	
	private int[][] gameBoard;

	private BDD[][] BDDBoard;
	private BDDFactory fact;
	private BDD overallBDD;

	/**
	 * The constructor.
	 */
	public QueensLogic() 
	{
		//constructor
	}

	/**
	 * Initializes the game with a board of the given size.
	 * 
	 * @param size The size of the board.
	 */
	public void initializeGame(int size) 
	{
		this.size = size;
		this.gameBoard = new int[size][size];

		this.BDDBoard = new BDD[size][size];

		this.fact = JFactory.init(2000000, 200000);
		fact.setVarNum(size * size);

		buildBDD();
	}

	/**
	 * Gets the game board.
	 * 
	 * @return The game board.
	 */
	public int[][] getGameBoard() 
	{
		return gameBoard;
	}

	/**
	 * Inserts a queen on the given position.
	 * 
	 * @param column The column.
	 * @param row The row.
	 * @return true if the queen was placed, false if it was an illegal move.
	 */
	public boolean insertQueen(int column, int row) 
	{
		if (gameBoard[column][row] == -1 || gameBoard[column][row] == 1) 
		{
			return false;
		}

		gameBoard[column][row] = 1;
		
		restrictBDD();
		gameBoard = updateBoard();

		return true;
	}

	/**
	 * Rebuilds the entire BDD.
	 */
	private void buildBDD()
	{
		// Reset the BDD.
		overallBDD = fact.one();

		// Initialize each position in the BDDBoard.
		for(int col = 0; col < size; col++)
			for(int row = 0; row < size; row++)
				BDDBoard[col][row] = fact.ithVar(row + col * size);

		// Build rules for every single position on the board.
		for(int col = 0; col < size; col++)
		{
			BDD columnBDD = fact.zero();

			for(int row = 0; row < size; row++)
			{
				buildBDDRules(col, row);

				// Ensure that there at least a queen in each column in a satisfiable solution.
				BDD n = columnBDD.apply(BDDBoard[col][row], BDDFactory.or);
				columnBDD.orWith(n);
			}

			overallBDD.andWith(columnBDD);
		}
	}

	/**
	 * Builds the BDD rules for the col/row position
	 * 
	 * @param col The column index.
	 * @param row The row index.
	 */
	 private void buildBDDRules(int col, int row)
	 {
		 BDD vertical = fact.one(), horizontal = fact.one(), downRight = fact.one(), upRight = fact.one();

		 // Vertical
		 for(int tempRow = 0; tempRow < size; tempRow++)
		 {
			 if (tempRow == row) continue;

			 BDD n = BDDBoard[col][row].apply(BDDBoard[col][tempRow], BDDFactory.nand);
			 vertical.andWith(n);
		 }

		 // Horizontal
		 for(int tempRow = 0; tempRow < size; tempRow++)
		 {
			 if (tempRow == col) continue;

			 BDD n = BDDBoard[col][row].apply(BDDBoard[tempRow][row], BDDFactory.nand);
			 horizontal.andWith(n);
		 }

		 // Down-right
		 for(int tempRow = 0; tempRow < size; tempRow++)
		 {
			 int tempCol = (tempRow - col) + row;

			 if(tempCol < 0 || tempCol >= size) continue;
			 if (tempRow == col && tempCol == row) continue;

			 BDD n = BDDBoard[col][row].apply(BDDBoard[tempRow][tempCol], BDDFactory.nand);
			 downRight.andWith(n);
		 }

		 // Up-right
		 for(int tempRow = 0; tempRow < size; tempRow++)
		 {
			 int tempCol = (col + row) - tempRow;

			 if(tempCol < 0 || tempCol >= size) continue;
			 if (tempRow == col && tempCol == row) continue;

			 BDD n = BDDBoard[col][row].apply(BDDBoard[tempRow][tempCol], BDDFactory.nand);
			 upRight.andWith(n);
		 }

		 downRight.andWith(upRight);
		 horizontal.andWith(downRight);
		 vertical.andWith(horizontal);

		 overallBDD.andWith(vertical);
	 }

	 /**
	  * Restricts the BDD with the queen's position from the game board.
	  */
	 private void restrictBDD()
	 {
		 for (int col = 0; col < size; col++)
		 {
			 for (int row = 0; row < size; row++)
			 {
				 if (gameBoard[col][row] == 1)
				 {
					 overallBDD.restrictWith(fact.ithVar(row + col * size));
				 }
			 }
		 }
	 }

	 /**
	  * Updates the game board.
	  * 
	  * @return The updated board.
	  */
	 private int[][] updateBoard()
	 {
		 int[][] domains = getValidDomains();
		 int[][] tempBoard = gameBoard;

		 // "Closes" positions based on the valid domains.
		 for (int col = 0; col < size; col++)
		 {
			 for (int row = 0; row < size; row++)
			 {
				 if (domains[col][row] == 0)
				 {
					 tempBoard[col][row] = -1;
				 }
			 }
		 }
		 
		 return tempBoard;
	 }

	 /**
	  * Gets the domains that are still "open" according to the BDD.
	  * 
	  * @return an int[][] where 1 means the position is still open.
	  */
	 private int[][] getValidDomains() 
	 {
		 int validDomains[][] = new int[size][size];

		 // Checks each position.
		 for (int col = 0; col < size; col++)
		 {
			 for (int row = 0; row < size; row++)
			 {
				 // If a the BDD can be restricted and is not unsatisfiable,
				 // we can place a queen on that position.
				 BDD restricted = overallBDD.restrict(fact.ithVar(row + col * size));
				 if (!restricted.isZero())
					 validDomains[col][row] = 1;
			 }
		 }

		 return validDomains;
	 }
}
