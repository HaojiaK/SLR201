import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class LivelockExample {
    public final ReentrantLock lock1 = new ReentrantLock();
    public final ReentrantLock lock2 = new ReentrantLock();
    int NUM_ITERATIONS = 50;
    
    public void operation1(){
        while(NUM_ITERATIONS>0){
            NUM_ITERATIONS --;
            try {
                if(lock1.tryLock(50, TimeUnit.MILLISECONDS)){
                    Thread.sleep(50);
                    if (lock2.tryLock()){
                        System.out.println("Resource is " + Thread.currentThread().getName());
                        break;
                    } else {
                        lock1.unlock();
                        continue;
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void operation2(){
        while(NUM_ITERATIONS>0){
            NUM_ITERATIONS --;
            try{
                if(lock2.tryLock(50,TimeUnit.MILLISECONDS)){
                    Thread.sleep(50);
                    if(lock1.tryLock()){
                        System.out.println("Resource is "+ Thread.currentThread().getName());
                        break;
                    }else{
                        lock2.unlock();
                        continue;
                    }
                }
            } catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args){
        LivelockExample example = new LivelockExample();

        Thread T1 = new Thread(example::operation1);
        Thread T2 = new Thread(example::operation2);

        T1.start();
        T2.start();
    }
}

/*
A livelock is a situation where two are more threads are unable to make progress
because each is waiting for the other to release a lock.
*/

/*
This will cause a livelock if both threads continually fail to 
acquire both locks and keep releasing their first lock for the other thread.
*/ 
