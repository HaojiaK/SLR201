import java.util.concurrent.*;
/*
Yes, you would need to change the Main class as well. The major difference would be that you wouldn't create the Fork objects in the Main class anymore.
You would only create the Philosopher objects and give them the IDs of the forks they should use.
The Philosopher objects would then communicate with the ForkServer to use the forks. 
Here's an example of how you might change the Main class*/

public class Main {
    public static void main(String[] args){
        final int NUMBER_OF_PHILOSOPHERS = 5;
        ExecutorService executorService = null;
        Philosopher[] philosophers = null;

        try{
            philosophers = new Philosopher[NUMBER_OF_PHILOSOPHERS];

            // Initialize the philosophers and start each running in its own thread.
            executorService = Executors.newFixedThreadPool(NUMBER_OF_PHILOSOPHERS);

            for(int i=0; i<NUMBER_OF_PHILOSOPHERS; i++){
                philosophers[i] = new Philosopher(i, i, (i+1)%NUMBER_OF_PHILOSOPHERS, "localhost", 8000);
                executorService.execute(philosophers[i]);
            }

            // Keep looping until the total number of turns of all philosophers reaches 500
            int totalTurns = 0;
            while(totalTurns < 500){
                Thread.sleep(10); // Pause a bit before the next check
                totalTurns = 0;
                for(Philosopher philosopher : philosophers){
                    totalTurns += philosopher.getTurns();
                }
            }

            for(Philosopher philosopher : philosophers){
                System.out.println("Philosopher " + philosopher.getId() + " has eaten " + philosopher.getTurns() + " times in total.");
            }

            // After the total number of turns reaches 500, shutdown the executor service
            executorService.shutdownNow();

            // Wait for all philosophers to finish their current turn before exiting the program
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);

            System.out.println("End of the project");

        } catch (InterruptedException e){
            System.out.println("There was an error in the ExecutorService.");
        }
    }
}
