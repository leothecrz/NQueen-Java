package crz.Annealing;

import java.util.Arrays;
import java.util.Random;


public class AnnealingSimulator
{
    
    private int N;
    private int indexes[];
    private int board[][];

    private int bestScore;
    private int bestboard[][];

    private int iterationLimit;

    private double Temperature;
    private double CoolingRate;

    public AnnealingSimulator(int N)
    {
        this(N, Integer.MAX_VALUE, 1000, 0.95);
    }

    public AnnealingSimulator(int N, int steplimit)
    {
        this(N, steplimit, 1000, 0.95);
    }

    public AnnealingSimulator(int N, int stepLimit, double Temp, double rate)
    {
        this.bestScore = 0;
        this.N = N;
        this.iterationLimit = stepLimit;
        this.Temperature = Temp;
        this.CoolingRate = rate;
        
        clearBoard();
        randomizeBoard();
    }
 

    public void clearBoard()
    {
        indexes = new int[N];
        board = new int[N][N];
        bestboard = new int[N][N];
        for(int i=0; i<N; i++)
        {
            indexes[i] = i;
            Arrays.fill(board[i],0);
            Arrays.fill(bestboard[i],0);
        }
    }


    public void printBoard()
    {
        System.out.println("Board: ");
        for(int i=0; i<N; i++)
        {
            for(int j=0; j<N; j++)
                    System.out.print(board[i][j] + ", " );
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
        for(int i=0; i<N; i++)
            indexes[i] = ran.nextInt(N);   
    }


    private static int countAttackingQueens(int[][] Board, int[] Indexes, int n)
    {
        int pairs = 0;

        for(int i=0; i<n; i++)
        {
            int currentQueen = i+1;
            int rowNum = Indexes[i];
            
            for(int j=0; j<n; j++)
            {
                int attacking = Board[rowNum][j];
                if(attacking > currentQueen)
                    pairs++;
            }

            int startRow = rowNum;
            int startCol = i;
            // Main Diagonal 1,1
            while( ( (startCol >= 0 ) && (startCol < n) ) && ( (startRow >= 0) && (startRow < n) ) )
            {
                int attacking = Board[startRow][startCol];
                if(attacking > currentQueen)
                    pairs++;
                    
                startRow += 1;
                startCol += 1;
            }


            startRow = rowNum;
            startCol = i;
            // Anti-Main Diagonal -1,1
            while( ( (startCol >= 0 ) && (startCol < n) ) && ( (startRow >= 0) && (startRow < n) ) )
            {
                int attacking = Board[startRow][startCol];
                if(attacking > currentQueen)
                    pairs++;
                    
                startRow += -1;
                startCol += 1;
            }

            startRow = rowNum;
            startCol = i;
            // Left-Main Diagonal -1,-1
            while( ( (startCol >= 0 ) && (startCol < n) ) && ( (startRow >= 0) && (startRow < n) ) )
            {
                int attacking = Board[startRow][startCol];
                if(attacking > currentQueen)
                    pairs++;
                    
                startRow += -1;
                startCol += -1;
            }

            startRow = rowNum;
            startCol = i;
            // Right-Anti Diagonal 1,-1
            while( ( (startCol >= 0 ) && (startCol < n) ) && ( (startRow >= 0) && (startRow < n) ) )
            {
                int attacking = Board[startRow][startCol];
                if(attacking > currentQueen)
                    pairs++;

                startRow += 1;
                startCol += -1;
            }

        }

        return pairs;
    }

    private void storeBoard()
    {
        bestScore = countAttackingQueens(board, indexes, N);
        bestboard = new int[N][N];
        for (int i = 0; i < N; i++) 
            Arrays.fill(bestboard[i],0);
        
        for (int i = 0; i < N; i++) 
          for (int j = 0; j < N; j++)  
            bestboard[i][j] = board[i][j]; 

    }

    private double acceptanceCheck(int newC, int oldC)
    {
        if(newC < oldC)
            return 1.0f;
        return Math.exp(oldC-newC/Temperature); 
    }

    private void fillBoardFromIndexes(int[] state)
    {
        board = new int[N][N];
        for(int i=0; i<N; i++)
            Arrays.fill(board[i],0);
    
        for (int i = 0; i < N; i++)
            board[state[i]][i] = i+1;
        
    }

    private int[] getRandomNeighbor(int[] state)
    {
        Random ran = new Random();
        int newState[] = Arrays.copyOf(state, N);
        newState[ran.nextInt(N)] = ran.nextInt(N);
        return newState;
    }

    public boolean Simulate(boolean printSteps)
    {
        Random ran = new Random();
        storeBoard();
        int currentCost = bestScore;
        int currentState[] = Arrays.copyOf(indexes, N);

        for (int i = 0; i < this.iterationLimit; i++) 
        {

            int[] newState = getRandomNeighbor(currentState);
            fillBoardFromIndexes(newState);
            int cost = countAttackingQueens(board, newState, N);

            if( acceptanceCheck(cost, currentCost) > ran.nextDouble())
            {
                currentState = newState;
                currentCost = cost;
            }
            else
            {
                fillBoardFromIndexes(currentState);
            }

            if(currentCost < bestScore)
            {
                indexes = currentState;
                storeBoard();
            }

            if(bestScore == 0)
                return true;
            
            Temperature *= CoolingRate;
        }
         
        return false;
    }


}
