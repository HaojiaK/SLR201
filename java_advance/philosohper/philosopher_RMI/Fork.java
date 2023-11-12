package philosopher_RMI;

public class Fork{
    private boolean isTaken = false;
    
    public synchronized boolean isTaken(){
        return isTaken;
    }

    public synchronized void pickUp() throws InterruptedException{
        while(isTaken){
            wait();
        }
        isTaken = true;
        // If the fork has been picked up, then wait untill it is available, then pick it up and flag the fork as isTake
    }

    public synchronized void putDown() throws InterruptedException{
        isTaken = false;
        notifyAll();
    }
}