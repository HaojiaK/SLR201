package SLR202_JAVA_ADVANCE.Java_Thread;

public class HelloRunnable implements Runnable {
    public void run(){
        System.out.println("Hello fomr a thread!");
    }

    public static void main(String args[]){
        (new Thread(new HelloRunnable())).start();
    }
}
