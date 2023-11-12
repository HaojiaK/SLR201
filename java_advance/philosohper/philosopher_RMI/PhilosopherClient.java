package philosopher_RMI;

import java.rmi.Naming;
import java.rmi.RemoteException;

public class PhilosopherClient {
    /*
     * a. Set up the RMI connection to the server.
     * b. Replace socket-based I/O with RMI method calls to pick up and put down forks
     */
    public static void main(String[] args) throws RemoteException, InterruptedException{
        try{
            //Connect to the RMI server
            String serverUrl = "rmi://localhost/ForkServer"; //Change to the actual RMI server URL
            ForkServer server = (ForkServer) Naming.lookup(serverUrl);

            //Initialize the philosophers and threads
            int NUMBER_OF_PHILOSOPHERS= 5;
            Philosopher[] philosophers = new Philosopher[NUMBER_OF_PHILOSOPHERS];
            Thread[] threads = new Thread[NUMBER_OF_PHILOSOPHERS];

            //Create and start the philosopher threads
            for (int i=0; i < NUMBER_OF_PHILOSOPHERS; i++){
                int leftForkId = i;
                int rightForkId = (i+1)%NUMBER_OF_PHILOSOPHERS;
                philosophers[i] = new Philosopher(i, leftForkId, rightForkId, server);
                threads[i] = new Thread(philosophers[i]);
                threads[i].start();
            }

            //Wait for the philosopher threads to finish
            for (int i=0; i<NUMBER_OF_PHILOSOPHERS; i++){
                threads[i].join();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
