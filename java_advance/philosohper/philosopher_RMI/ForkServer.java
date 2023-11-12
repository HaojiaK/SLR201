package philosopher_RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

/*
 * Implement the RMI server class that provides the server logic for handling the philosophers' requests.
 */

public interface ForkServer extends Remote {
    void pickUpFork(int forkId) throws RemoteException, InterruptedException;
    void putDownFork(int forkId) throws RemoteException, InterruptedException;
}
