package philosopher_RMI;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class ForkServerImpl extends UnicastRemoteObject implements ForkServer{
    private final Fork[] forks;

    public ForkServerImpl(int numForks) throws RemoteException{
        super();
        forks = new Fork[numForks];
        for(int i=0; i < numForks; i++){
            forks[i] = new Fork();
        }
    }

    // main method
    public static void main(String[] args) {
        try {
            // Start RMI registry on port 5000
            LocateRegistry.createRegistry(5000);

            // Create server
            ForkServerImpl server = new ForkServerImpl(5);

            // Bind server instance to URL
            Naming.rebind("//localhost:5000/ForkServer", server);
            
            System.out.println("ForkServer is ready.");
        } catch (Exception e) {
            System.out.println("ForkServer failed: " + e);
        }
    }

    @Override
    public void pickUpFork(int forkId) throws RemoteException, InterruptedException {
        synchronized (forks[forkId]){
            while (forks[forkId].isTaken()) {
                forks[forkId].wait();
            }
            forks[forkId].pickUp();;
        }
    }

    @Override
    public void putDownFork(int forkId) throws RemoteException, InterruptedException {
        synchronized (forks[forkId]){
            forks[forkId].putDown();
            forks[forkId].notifyAll();
        }
    }

}
