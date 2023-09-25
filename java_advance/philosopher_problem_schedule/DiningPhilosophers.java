package philosopher_problem_schedule;

/*1.定义资源（叉子）：每个叉子被视为一个共享资源，每个哲学家需要用两个叉子来吃饭。
  *在Java中，创建一个folk类来代表叉子，叉子有被拿起和放下两种状态。当叉子被拿起时，只能等待放下，不然不可调用
  */

/*2.定义哲学家：每个哲学家可以被视为一个线程，他可以处于就餐和思考两种状态。
  * 在Java中，你可以创建一个Philosopher类，继承Thread类或实现Runnable接口。
  */

/*3.协调资源的使用：你需要确保一个哲学家在吃饭时，他需要的two folks都是可用的。
  * 如果其中一只folk正在被另一位哲学家使用，那么这位哲学家需要等待，直到两只folk都可以使用。
  * 使用Java的synchronized关键字来同步对筷子的访问 
*/

/*4.避免死锁：死锁是指两个或更多的线程都在等待对方释放资源，导致都在无期限地等待下去。
  * 为了避免死锁，你可以实施某种策略，
  * 比如让哲学家在他们都饿的时候，只有编号最小的哲学家可以拿起他左边的筷子，其他哲学家只能拿起右边的筷子 */

/*5.避免饥饿：饥饿是指一个或多个线程因为无法获得所需的资源，而无法继续执行。
为了避免饥饿，你可以实施公平性策略，比如使用一个公平的锁，或者使用一个调度算法，确保每个哲学家都有吃饭的机会 */

/*6.模拟就餐过程：在主程序中，你需要创建多个哲学家（线程）并启动他们。
每个哲学家都应该在思考和吃饭之间交替，每位哲学家在吃一次饭后都需要暂停，不能立刻吃下一顿。 */


public class DiningPhilosophers{
  public static void main(String[] args){
    Fork[] forks = new Fork[5];
    for (int i=0; i<forks.length; i++){
      forks[i] = new Fork();
    }

    Scheduler scheduler = new Scheduler(); // Create the scheduler

    Philosopher[] philosophers = new Philosopher[5];
    for(int i=0; i<philosophers.length; i++){
      Fork leftFork = forks[i];
      Fork rightFork = forks[(i+1) % forks.length];
      if(i == philosophers.length-1){
        //The last philosopher picks up the right fork first
        philosophers[i] = new Philosopher("Philosopher" + (i+1), rightFork, leftFork, scheduler);
      }else{
        philosophers[i] = new Philosopher("Philosopher" + (i+1), leftFork, rightFork, scheduler);
      }
    }
  }
}
