package crz.NQueenHill;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;


public class Climber
{
    
    private PriorityQueue<Node> currentNeighbors;

    private int N;
    private int stepsLimit;
    private int indexes[];
    private int board[][];
    private Node lastBestMove;

    public Climber(int N)
    {
        this(N, Integer.MAX_VALUE);
    }


    public Climber(int N, int stepLimit)
    {
        this.N = N;
        this.stepsLimit = stepLimit;
        this.lastBestMove = null;
        this.currentNeighbors = new PriorityQueue<>(Comparator.comparing(Node::getAttackingCount));
        
        clearBoard();
        randomizeBoard();
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
            int currentQueen = i+1;
            int rowNum = indexes[i];
            
            for(int j=0; j<N; j++)
            {
                //Check Row
                int attacking = board[rowNum][j];
                if(attacking > currentQueen)
                {
                    pairs++;
                    //System.out.println("Attacking: " + attacking + " from " + currentQueen);
                }

            }

            int startRow = rowNum;
            int startCol = i;
            // Main Diagonal 1,1
            while( ( (startCol >= 0 ) && (startCol < N) ) && ( (startRow >= 0) && (startRow < N) ) )
            {
                int attacking = board[startRow][startCol];
                if(attacking > currentQueen)
                {
                    pairs++;
                    //System.out.println("Attacking: " + attacking + " from " + currentQueen);
                }
                startRow += 1;
                startCol += 1;
            }


            startRow = rowNum;
            startCol = i;
            // Anti-Main Diagonal -1,1
            while( ( (startCol >= 0 ) && (startCol < N) ) && ( (startRow >= 0) && (startRow < N) ) )
            {
                int attacking = board[startRow][startCol];
                if(attacking > currentQueen)
                {
                    pairs++;
                    //System.out.println("Attacking: " + attacking + " from " + currentQueen);
                }
                startRow += -1;
                startCol += 1;
            }

            startRow = rowNum;
            startCol = i;
            // Left-Main Diagonal -1,-1
            while( ( (startCol >= 0 ) && (startCol < N) ) && ( (startRow >= 0) && (startRow < N) ) )
            {
                int attacking = board[startRow][startCol];
                if(attacking > currentQueen)
                {
                    pairs++;
                    //System.out.println("Attacking: " + attacking + " from " + currentQueen);
                }
                startRow += -1;
                startCol += -1;
            }

            startRow = rowNum;
            startCol = i;
            // Right-Anti Diagonal 1,-1
            while( ( (startCol >= 0 ) && (startCol < N) ) && ( (startRow >= 0) && (startRow < N) ) )
            {
                int attacking = board[startRow][startCol];
                if(attacking > currentQueen)
                {
                    pairs++;
                    //System.out.println("Attacking: " + attacking + " from " + currentQueen);
                }
                startRow += 1;
                startCol += -1;
            }

        }

        //System.out.println("Attacking Count: " + pairs + "\n");

        return pairs;
    }


    public boolean HillClimb(boolean printSteps)
    {

        int steps = 0;
        int currentAttackingCount = countAttackingQueens();
        currentNeighbors.clear();

        while(this.stepsLimit != steps++)
        {
            //Generate Neighbors
            for (int i = 0; i < N; i++) 
            {
                int queenIndex = indexes[i];
                int queenValue = i+1;
                board[queenIndex][i] = 0;

                for (int j = 0; j < N; j++) 
                {
                    if(j == queenIndex)
                        continue;

                    board[j][i] = queenValue;
                    indexes[i] = j;
                    currentNeighbors.add(new Node(countAttackingQueens(), queenValue, j));
                    board[j][i] = 0; 
                }

                indexes[i] = queenIndex;
                board[queenIndex][i] = queenValue;
            }

            //Find Best
            Node bestmove = currentNeighbors.poll();
            currentNeighbors.clear();

            int colIndex = bestmove.getQueenValue()-1;
            board[indexes[colIndex]][colIndex] = 0; //clear
            board[bestmove.getMoveToIndex()][colIndex] = bestmove.getQueenValue(); //make move
            indexes[colIndex] = bestmove.getMoveToIndex();

            //Test
            if(bestmove.getAttackingCount() == 0)
                return true;
            
            if(bestmove.getAttackingCount() >= currentAttackingCount)
            {
                if(printSteps)
                    System.out.println("Local Max");
                return false; // Local Max
            }
           
            currentAttackingCount = bestmove.getAttackingCount();
            lastBestMove = bestmove;
            if(printSteps)
            {    
                System.out.println("Step: " + steps);
                printBoard();
                System.out.println(bestmove + "\n");
            }
            
        }
        
        if(printSteps)
            System.out.println("Step Limit Reached"); 
        return false;
    }

    public Node getLastBestMove() 
    {
        return lastBestMove;
    }

}
