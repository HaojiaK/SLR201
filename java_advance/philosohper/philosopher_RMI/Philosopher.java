package philosopher_RMI;
import java.util.Random;
import java.rmi.RemoteException;

public class Philosopher implements Runnable {
    private final int id;
    private final int leftForkId;
    private final int rightForkId;
    private final ForkServer server;
    private Random random = new Random();
    private int turns = 0;

    // Define the states for the philosopher
    private enum State { REFLECHIR, AFFAME, MANGER }
    private State state;

    public Philosopher(int id, int leftForkId, int rightForkId, ForkServer server) {
        this.id = id;
        this.leftForkId = leftForkId;
        this.rightForkId = rightForkId;
        this.server = server;
        this.state = State.REFLECHIR; // Initial state is reflection
    }

    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                switch (state) {
                    case REFLECHIR:
                        Thread.sleep(random.nextInt(256)); // Random thinking time
                        state = State.AFFAME; // Transition to hungry state
                        System.out.println(id + " is reflecting.");
                        break;

                    case AFFAME:
                        System.out.println(id + " is hungry and tries to pick up the left fork.");
                        
                        // Request the left fork from the server using RMI call
                        try {
                            server.pickUpFork(leftForkId);
                            System.out.println(id + " picked up the left fork and tries to pick up the right fork.");
                            
                            // Request the right fork from the server using RMI call
                            server.pickUpFork(rightForkId);
                            System.out.println(id + " picked up the right fork.");
                            
                            state = State.MANGER; // Transition to eating state
                            turns++;
                        } catch (RemoteException | InterruptedException e) {
                            System.out.println(id + " couldn't pick up both forks.");
                            try {
                                // Release both forks if unable to pick up both
                                server.putDownFork(leftForkId);
                                server.putDownFork(rightForkId);
                            } catch (RemoteException | InterruptedException e2) {
                                e2.printStackTrace();
                            }
                            Thread.sleep(100); // Add a delay to avoid infinite loop if unable to pick up the right fork
                            break;
                        }
                        break;

                    case MANGER:
                        System.out.println(id + " is eating.");
                        Thread.sleep(random.nextInt(256)); // Random eating time
                        
                        try {
                            // Release the left fork and right fork using RMI calls
                            server.putDownFork(leftForkId);
                            server.putDownFork(rightForkId);
                        } catch (RemoteException | InterruptedException e3) {
                            e3.printStackTrace();
                        }
                        System.out.println(id + " puts down the left fork and right fork.");
                        state = State.REFLECHIR; // Transition back to thinking state
                        break;
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // Add this getter to access the number of turns from the main class
    public int getTurns() {
        return turns;
    }

    public int getId() {
        return this.id;
    }
}
