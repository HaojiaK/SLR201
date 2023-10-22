package philosohper;
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

            //keep looping until the total number of turns of all philosophers reaches 500
            int totalTurns = 0;
            while(totalTurns < 100){
                totalTurns = 0;
                for(Philosopher philosopher : philosophers){
                    totalTurns += philosopher.getTurns(); 
                }
                Thread.sleep(100); //pause a bit before the next check
            }

            for(Philosopher philosopher : philosophers){
                System.out.println("Philosopher " + philosopher.getId() + " turns: " + philosopher.getTurns());// Print that for each philosopher, how many turns they eat.             
            }

            //After the total number of turns reaches 200, shutdown the executor service
            executorService.shutdownNow();

            //Wait for all philosophers to finish their current turn before exiting the program
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);

            System.out.println("End of the project");

        } catch (InterruptedException e){
            System.out.println("There was an error in the ExecutorService.");
        }
    }
}
