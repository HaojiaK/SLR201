package philosopher;
import java.util.Random;

public class Philosopher implements Runnable{
    private final Fork leftFork;
    private final Fork rightFork;
    private final int id;
    private Random random = new Random();
    private int turns = 0;
    //you could check about three status, leftFork, rightFork and id
    
    private enum State {AFFAME, MANGER, REFLECHIR};
    private State state;

    //Define the maximum number of attempts to pick up a fork
    private static final int MAX_ATTEMPTS = 3;

    public Philosopher(Fork leftFork, Fork rightFork, int id){
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        this.id = id;
        this.state = State.REFLECHIR;
        //the normal status is reflechir;
    }

    public void run(){
        try{
            while(!Thread.currentThread().isInterrupted()){
                switch(state){
                    case REFLECHIR:
                        Thread.sleep(random.nextInt(256)); // 1 a 256 milliseconds
                        state = State.AFFAME;
                        System.out.println(id + " is reflecting.");
                        break;
                    
                    case AFFAME:
                    //The philosopher should take the forks
                        System.out.println(id + " is hungry and tries to pick up the left fork.");
                        
                        //Initialize attempt counter
                        int attempts = 0;

                        //Keep trying until maximum attemps reached

                        while (attempts < MAX_ATTEMPTS){
                            try{
                                leftFork.pickUp();
                                System.out.println(id + " picked up the left fork and tries to pick up the right fork.");
                                rightFork.pickUp();
                                System.out.println(id + " picked up the right fork.");
                                
                                //If a philosopher takes the both forks, then change status into manger
                                state = State.MANGER;
                                turns++;
                                break; // Break from the loop if the philosopher was able to pick up both forks
                            } catch (InterruptedException e){
                                attempts++; // Increment attempts counter
                                System.out.println(id + " couldn't pick up both forks. Attempt " + attempts);
                                Thread.sleep(100); // Add a delay to avoid infinite loop if unable to pick up the right fork
                            }
                        }
                        // If the philosopher was unable to pick up both forks, transition back to thinking state
                        if (state != State.MANGER) state = State.REFLECHIR; 
                        break;

                    case MANGER:
                        System.out.println(id + " is eating.");
                        Thread.sleep(random.nextInt(256));
                        leftFork.putDown();//First put down the left fork
                        rightFork.putDown();
                        System.out.println(id + " puts down the left fork and right fork.");
                        state = State.REFLECHIR;
                        break;
                }
            }
        } catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }

    //Add this getter to access the turns from main class
    public int  getTurns(){
        return turns;
    }

    public int getId(){
        return this.id;
    }

}
