package philosopher_problem_schedule;
public class Philosopher extends Thread {
  private final Fork leftFork;
  private final Fork rightFork;
  private Scheduler scheduler;

  public Philosopher(String name, Fork leftFork, Fork rightFork, Scheduler scheduler){
    super(name);
    this.leftFork = leftFork;
    this.rightFork = rightFork;
    this.scheduler = scheduler;
  }

  public void run(){
    try{
      while(true){
        System.out.println(getName() + " is thinking.");
        sleep((int)(Math.random()*2000));
        System.out.println(getName()+" is hungry and wants to eat.");
        scheduler.requestForks();
        leftFork.take();
        System.out.println(getName()+" picked up left fork.");
        rightFork.take();
        System.out.println(getName()+ " picked up right fork and starts eating.");
        sleep((int)(Math.random()*2000));
        leftFork.putDown();
        System.out.println(getName() + "put down left fork.");
        rightFork.putDown();
        System.out.println(getName() + " put down right fork.");
        scheduler.releaseForks();
      }
    } catch (InterruptedException e){
      System.out.println(getName() + " is interrupted.");
    }
  }
}
