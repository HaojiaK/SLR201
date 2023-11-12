package philosopher_RMI;
import java.rmi.RemoteException;
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

    @Override
    public synchronized void pickUpFork(int forkId) throws RemoteException, InterruptedException {
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
