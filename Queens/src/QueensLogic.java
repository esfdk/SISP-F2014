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
    private BDDFactory fact;
    private BDD overallBDD;
    private BDD True;
    private BDD False;
    private BDD[][] BDDBoard;
    

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
        
        this.BDDBoard = new BDD[x][y];
        
        this.fact = JFactory.init(2000000, 200000);
        fact.setVarNum(size * size);
        True = fact.one();
        False = fact.zero();
        overallBDD = fact.one();
       
        for(int i = 0; i < x; i++)
        {
        	for(int j = 0; j < y; j++)
        	{
                BDDBoard[i][j] = fact.ithVar(j + i * size);                
        	}
        }
        
        
        for(int i = 0; i < x; i++)
        {
        	for(int j = 0; j < y; j++)
        	{
        		BuildBDD(i, j); 
        	}
        }
        
        overallBDD.restrictWith(fact.ithVar(0).and(fact.ithVar(7)));
     
        System.out.println(overallBDD.satOne().toString());
	}
    
    private void BuildBDD(int row, int col)
    {
    	BDD a = fact.one(), b = fact.one(), c = fact.one(), d = fact.one();
    	
    	// Vertical
    	for(int k = 0; k < y; k++)
    	{
    		if(k != col)
    		{
    			BDD n = BDDBoard[row][col].apply(BDDBoard[row][k], BDDFactory.nand);
    			a.andWith(n);
    		}
    	}
    	
    	// Horizontal
    	for(int k = 0; k < x; k++)
    	{
    		if(k != row)
    		{
    			BDD n = BDDBoard[row][col].apply(BDDBoard[k][col], BDDFactory.nand);
    			b.andWith(n);
    		}
    	}
    	
    	// Down-right
    	for(int k = 0; k < x; k++)
    	{
    		int tempC = (k - row) + col;
    		
    		if(tempC < 0 || tempC >= x) continue;
    		
    		if(k != row && tempC != col)
    		{
    			BDD n = BDDBoard[row][col].apply(BDDBoard[k][tempC], BDDFactory.nand);
    			c.andWith(n);
    		}
    	}
    	
    	// Up-right
    	for(int k = 0; k < x; k++)
    	{
    		int tempC = (row + col) - k;
    		
    		if(tempC < 0 || tempC >= x) continue;
    		
    		if(k != row && tempC != col)
    		{
    			BDD n = BDDBoard[row][col].apply(BDDBoard[k][tempC], BDDFactory.nand);
    			d.andWith(n);
    		}
    	}
    	
    	c.andWith(d);
    	b.andWith(c);
    	a.andWith(b);
    	
    	overallBDD.andWith(a);
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
