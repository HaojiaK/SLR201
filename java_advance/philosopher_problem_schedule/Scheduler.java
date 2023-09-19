package philosopher_problem_schedule;

public class Scheduler {
  private boolean forkAvailable;

  public Scheduler(){
    this.forkAvailable = true;
  }

  public synchronized void requestForks() throws InterruptedException{
    while(!forkAvailable){
      wait();
    }
    forkAvailable = false;
  }

  public synchronized void releaseForks(){
    forkAvailable = true;
    notifyAll();
  }
}
