package java_advance.Java_Thread;

public class MyThread  extends Thread{
    private String threadName;

    public MyThread(String threadName){
        this.threadName = threadName;
    }

    public void run(){
        for(int i=0; i<100; i++){
            System.out.println(threadName + ":" + i);
            try{Thread.sleep(10);
            } catch (Exception e) {
                System.out.println("Something went wrong.");
            }
        }
    }

    public static void main(String[] args){
        MyThread myThreadA = new MyThread("Thread A");
        MyThread myThreadB = new MyThread("Thread B");
        MyThread myThreadC = new MyThread("Thread C");

        myThreadA.start();
        myThreadB.start();
        myThreadC.start();


        try{myThreadA.join();} catch(Exception e){}
        try{myThreadB.join();} catch(Exception e){}
        try{myThreadC.join();} catch(Exception e){}

        System.out.println("END");
        System.exit(0);
    }
}
