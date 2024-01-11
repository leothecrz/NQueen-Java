package crz.NQueenHill;

/**
 * Hello world!
 *
 */
public class Main 
{

    public static void main( String[] args )
    {
        
        if(args.length > 0)
        {
            switch(args[0])
            {
                case "test100" -> {runTest(); return;}
            }
        }


        Climber test = new Climber(8, 4);
        System.out.print("Init ");
        test.printBoard();
        System.out.println();

        if ( test.HillClimb(true))
        {
            test.printBoard();
            System.out.println("Success");
        }
        else
        {
            System.out.println("Failed");
        }

    }

    public static void runTest()
    {   
        
        System.out.println("Test");

        int Successes = 0;
        int Size = 1000;
        for (int i = 0; i < Size; i++) 
        {
            Climber c = new Climber(8, 5);
            if(c.HillClimb(false))
                Successes++;     
        }

        System.out.println("Success Count: " + Successes);
        System.out.println("Success Rate: " + ((double)Successes/(double)Size) );


    }

}
