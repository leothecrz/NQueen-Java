package crz.Annealing;

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


        AnnealingSimulator test = new AnnealingSimulator(8, 10000);
        System.out.print("Init ");
        test.printBoard();
        System.out.println();

        if(test.Simulate(true))
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
            AnnealingSimulator c = new AnnealingSimulator(8, 1000);
            if(c.Simulate(false))
                Successes++;     
        }

        System.out.println("Success Count: " + Successes);
        System.out.println("Success Rate: " + ((double)Successes/(double)Size) );

    }

}
