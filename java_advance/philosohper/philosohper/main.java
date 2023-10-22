package philosohper;
import java.util.concurrent.*;
import java.io.*;

public class Main {
    public static void printOutput(String output, String[] args) throws FileNotFoundException{
        if (args.length>0 && args[0].equals("file")){
            PrintStream fileOut = new PrintStream((new FileOutputStream("out.txt", true)));
            fileOut.println(output);
            // if you want to save the output into the out.txt, run the program with an argument : bin carpediem$ java philosohper.Main file
        }else{
            System.out.println(output);
        }
    }
    public static void main(String[] args) throws FileNotFoundException{
        final int NUMBER_OF_PHILOSOPHERS = 5;
        ExecutorService executorService = null;
        Philosopher[] philosophers = null;
        Fork[] forks = new Fork[NUMBER_OF_PHILOSOPHERS];

        //Check if there is a command line argument
        if (args.length >0){
            //If the argument is "file", redirect output to a file
            if (args[0].equals("file")){
                PrintStream fileOut = new PrintStream("out.txt");
                System.setOut(fileOut);
            }
        }

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
                Thread.sleep(10); //pause a bit before the next check
                totalTurns = 0;
                for(Philosopher philosopher : philosophers){
                    totalTurns += philosopher.getTurns(); 
                }
            }

            for(Philosopher philosopher : philosophers){
                System.out.println("Philosopher " + philosopher.getId() + " has eaten " + philosopher.getTurns() + " times in total.");// Print that for each philosopher, how many turns they eat.             
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
