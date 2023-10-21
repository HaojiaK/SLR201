import java.util.concurrent.locks.ReentrantLock;

//I need to create an instance of the DeadlockExample class and call these methods on that instance.
class Thread1 extends Thread {
    private DeadlockExample example;

    public Thread1(DeadlockExample example){
        this.example = example;
    }

    public void run(){
        example.operation1();
    }
}

class Thread2 extends Thread{
    private DeadlockExample example;

    public Thread2(DeadlockExample example){
        this.example = example;
    }

    public void run(){
        example.operation2();
    }
}

public class DeadlockExample{
    private ReentrantLock lock1 = new ReentrantLock();
    private ReentrantLock lock2 = new ReentrantLock();

    public void operation1(){
        lock1.lock();
        try{
            Thread.sleep(50); // Sleep for 50 milliseconds
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        lock2.lock();
        try{
            System.out.println("Resource is " + Thread.currentThread().getName());
        } finally {
            lock2.unlock();
            lock1.unlock();
        }
    }

    public void operation2(){
        lock2.lock();
        try{
            Thread.sleep(50); // Sleep for 50 milliseconds
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        lock1.lock();
        try{
            System.out.println("Resource is " + Thread.currentThread().getName());
        } finally {
            lock1.unlock();
            lock2.unlock();
        }
    }

    public static void main(String[] args){
        DeadlockExample example = new DeadlockExample();
        Thread1 T1 = new Thread1(example);
        Thread2 T2 = new Thread2(example);

        T1.start();
        T2.start();
    }
}

/*
 * In your operation1() method, you’re acquiring lock1 and then lock2. 
 * In the operation2() method, you’re acquiring lock2 and then lock1.
 * If Thread1 acquires lock1 and at the same time Thread2 acquires lock2, 
 * they will be waiting for each other to release their locks, hence causing a deadlock.

Also, your Thread1 and Thread2 classes are calling the methods operation1() and operation2(),
but these methods are not defined in these classes or their parent class (Thread). 
They are defined in the DeadlockExample class. 
You need to create an instance of the DeadlockExample class and call these methods on that instance.
 */


