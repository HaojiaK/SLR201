package philosopher_problem;
public class Philosopher extends Thread {
  private final Fork leftFork;
  private final Fork rightFork;

  Philosopher(String name, Fork leftFork, Fork rightFork){
    super(name);
    this.leftFork = leftFork;
    this.rightFork = rightFork;
  }

  public void run(){
    try{
      while(true){
        System.out.println(getName() + " is thinking.");
        sleep((int)(Math.random()*2000));
        System.out.println(getName()+" is hungry and wants to eat.");
        //Ensure at least one philosopher picks up the right fork first
        //为什么要从第一个philosopher开始呢？
        if (getName().equals("Philosopher-1")){
          rightFork.take();
          System.out.println(getName()+" picked up right fork.");
          leftFork.take();
          System.out.println(getName() + " picked up left fork.");
        }else{
          leftFork.take();
          System.out.println(getName() + " picked up right fork and starts eating.");
        }
        sleep((int)(Math.random()*2000));
        leftFork.putDown();
        System.out.println(getName() + "put down left fork.");
        rightFork.putDown();
        System.out.println(getName() + " put down right fork.");
      }
    } catch (InterruptedException e){
      System.out.println(getName() + " is interrupted.");
    }
  }
}
