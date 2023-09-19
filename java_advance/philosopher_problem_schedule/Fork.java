package philosopher_problem_schedule;

public class Fork {
  private boolean isTaken = false;
  synchronized void take() throws InterruptedException{
    while (isTaken){
      wait();
    }
    isTaken = true;
  }

  synchronized void putDown(){
    isTaken = false;
    notifyAll();
  }
  
}
