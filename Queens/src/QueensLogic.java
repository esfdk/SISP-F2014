/**
 * This class implements the logic behind the BDD for the n-queens problem
 * You should implement all the missing methods
 * 
 * @author Stavros Amanatidis
 *
 */
import java.util.*;

import net.sf.javabdd.*;

public class QueensLogic 
{
    private int x = 0;
    private int y = 0;
    private int[][] board;

    /**
     * The constructor.
     */
    public QueensLogic() 
    {
       //constructor
    }

    /**
     * ???
     * 
     * @param size ???
     */
    public void initializeGame(int size) 
    {
        this.x = size;
        this.y = size;
        this.board = new int[x][y];
    }
    
    /**
     * ???
     * 
     * @return ???
     */
    public int[][] getGameBoard() 
    {
        return board;
    }

    /**
     * ???
     * 
     * @param column ???
     * @param row ???
     * @return ???
     */
    public boolean insertQueen(int column, int row) 
    {

        if (board[column][row] == -1 || board[column][row] == 1) 
        {
            return true;
        }
        
        board[column][row] = 1;
        
        // put some logic here..
      
        return true;
    }
}
