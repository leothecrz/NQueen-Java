package crz.NQueenHill;

import java.util.Arrays;
import java.util.Random;


public class Climber
{
    
    private int N;
    private int stepsLimit;
    private int indexes[];
    private int board[][];

    public Climber(int N)
    {
        this(N, Integer.MAX_VALUE);
    }

    public Climber(int N, int stepLimit)
    {
        this.N = N;
        this.stepsLimit = stepLimit;
        
        clearBoard();
        printBoard(false);
        System.out.println();

        randomizeBoard();
        printBoard(false);
        System.out.println();
    }
 
    public void clearBoard()
    {
        indexes = new int[N];
        board = new int[N][N];
        for(int i=0; i<N; i++)
        {
            indexes[i] = i;
            Arrays.fill(board[i],0);
        }
    }

    public void printBoard(boolean R)
    {
        System.out.println("Board: ");
        for(int i=0; i<N; i++)
        {
            for(int j=0; j<N; j++)    
                if(R)
                    System.out.print(board[i][j] + ", " );
                else
                    System.out.print(board[j][i] + ", " );
            System.out.println();
    
        }

        System.out.println("Indexes: ");
        for(int j=0; j<N; j++)
            System.out.print(indexes[j] + ", ");
    
        System.out.println();
    }

    public void randomizeBoard()
    {
        randomizeIndexes();
        for(int i=0; i<N;i++)
            board[indexes[i]][i] = i+1;
    }
     
    private void randomizeIndexes()
    {
        Random ran = new Random();
        
        // Shuffle Indexes
        //for(int i=N-1; i>=0; i--)
        //{
        //    int j = ran.nextInt(i+1);
        //    
        //    int temp = indexes[i];
        //    indexes[i] = indexes[j];
        //    indexes[j] = temp;
        // }
        
        for(int i=0; i<N; i++)
            indexes[i] = ran.nextInt(N);

    }


    private int countAttackingQueens()
    {

        int pairs = 0;

        for(int i=0; i<N; i++)
        {
            int currentQueen = i;
            int rowNum = indexes[i];
            
            for(int j=0; j<N; i++)
            {
                //Check Row
                if (board[i][j] > i)
                   pairs++;
            }





            
        }

        return pairs;

    }


}
