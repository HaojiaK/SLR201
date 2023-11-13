package philosopher_RMI;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class PhilosopherClient {
    public static final int NUMBER_OF_PHILOSOPHERS = 5;

    public static void main(String[] args) throws RemoteException, InterruptedException, FileNotFoundException {
        try {
            // Redirect output to file if "file" argument is provided
            if (args.length > 0 && args[0].equals("file")) {
                PrintStream fileOut = new PrintStream("out.txt");
                System.setOut(fileOut);
            }
            
            // Connect to the RMI server
            String serverUrl = "rmi://localhost:5000/ForkServer";
            ForkServer server = (ForkServer) Naming.lookup(serverUrl);
            
            // Initialize the philosophers and threads
            Philosopher[] philosophers = new Philosopher[NUMBER_OF_PHILOSOPHERS];
            Thread[] threads = new Thread[NUMBER_OF_PHILOSOPHERS];
            
            // Create and start the philosopher threads
            for (int i = 0; i < NUMBER_OF_PHILOSOPHERS; i++) {
                int leftForkId = i;
                int rightForkId = (i + 1) % NUMBER_OF_PHILOSOPHERS;
                philosophers[i] = new Philosopher(i, leftForkId, rightForkId, server);
                threads[i] = new Thread(philosophers[i]);
                threads[i].start();
            }
            
            // Keep looping until the total number of turns reaches 100
            int totalTurns = 0;
            while (totalTurns < 100) {
                Thread.sleep(10); // Pause a bit before the next check
                totalTurns = 0;
                for (Philosopher philosopher : philosophers) {
                    totalTurns += philosopher.getTurns();
                }
            }
            
            // After the total number reaches the maximum, interrupt all philosopher threads
            for (int i = 0; i < NUMBER_OF_PHILOSOPHERS; i++) {
                System.out.println("Philosopher " + philosophers[i].getId() + " has eaten " + philosophers[i].getTurns() + " times in total.");
                threads[i].interrupt();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
