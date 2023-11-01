package philosopher_socket;
import java.io.*;

public class PhilosopherClient {
    /*
     * This is a public static final field, which means it's a constant that can be accessed from anywhere in this application
     */
    public static final int NUMBER_OF_PHILOSOPHERS = 5;
    public static void main(String[] args) throws FileNotFoundException{
        /*
         * The client connects to the server by creating a new Philosopher object,
         * passing the server host and port as arguments.
         * The Philosopher class is responsible for managing the connection to the server.
         */
        
        final String SERVER_HORST = "localhost";
        final int SERVER_PORT = 5000; // The port number should match the one in the ForkServer

        //Check if there is a command line argument
        if(args.length>0 && args[0].equals("file")){
            PrintStream fileOut = new PrintStream("out.txt");
            System.setOut(fileOut);
            // To save the output into the out.txt, run the program with an argument : bin carpediem$ java philosohper.Main file
        }

        Philosopher[] philosophers = new Philosopher[NUMBER_OF_PHILOSOPHERS];
        Thread[] threads = new Thread[NUMBER_OF_PHILOSOPHERS];

        // Initialize the philosophers and starts each running in its own thread
        for(int i=0; i<NUMBER_OF_PHILOSOPHERS; i++){
            philosophers[i] = new Philosopher(i, i, (i+1)%NUMBER_OF_PHILOSOPHERS, SERVER_HORST, SERVER_PORT);
            threads[i] = new Thread(philosophers[i]);
            threads[i].start();
        }

        // Keep looping until the total number of turns of all philosophers reaches setting number

        int totalTurns = 0;
        while (totalTurns < 100) {
            try{
                Thread.sleep(10); // Pause a bit before the next check
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            //resetting totalTurns to 0, ensure that we're always starting the count from scartch.
            totalTurns = 0;
            for(Philosopher philosopher: philosophers){
                totalTurns += philosopher.getTurns();
            }
        }

        // After the total number reaches the maximum, interrupt all philosopher threads
        for (int i=0; i<NUMBER_OF_PHILOSOPHERS; i++) {
            System.out.println("Philosopher " + philosophers[i].getId() + " has eaten " + philosophers[i].getTurns() + "times in total.");
            //Interrupt the philosopher thread
            threads[i].interrupt();
        }
    }
}
