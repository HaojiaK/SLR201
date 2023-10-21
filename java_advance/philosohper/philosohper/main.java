package philosohper;

import java.io.IOException;
import java.util.concurrent.*;
import java.io.*;

public class main {
    public static void main(String[] args) throws FileNotFoundException{
        final int NUMBER_OF_PHILOSOPHERS = 5;
        ExecutorService executorService = null;
        Philosopher[] philosophers = null;
        Fork[] forks = new Fork[NUMBER_OF_PHILOSOPHERS];

        //Redirect the output stream to a file
        PrintStream fileOut = new PrintStream("out.txt");
        System.setOut(fileOut);

        try{
            philosophers = new Philosopher[NUMBER_OF_PHILOSOPHERS];
            //Initialize the fork
            for(int i=0; i<NUMBER_OF_PHILOSOPHERS; i++){
                forks[i] = new Fork();
            }

            //Initialize the philosophers and start each running in its iwn thread.
            executorService = Executors.newFixedThreadPool(NUMBER_OF_PHILOSOPHERS);

            for(int i=0; i<NUMBER_OF_PHILOSOPHERS; i++){
                philosophers[i] = new Philosopher(forks[i], forks[(i+1)%NUMBER_OF_PHILOSOPHERS], i);
                executorService.execute(philosophers[i]);
            }

            //Runu the simulation for 10 seconds
            executorService.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e){
            System.out.println("There was an error in the ExecutorService.");
        } finally {
            if(executorService != null){
                executorService.shutdown();

                //When the 60 seconds are over, stop all philosophers and finish the program.
                for (Philosopher philosopher : philosophers){
                    philosopher.interrupt();
                }
            }
        }
    }
}
