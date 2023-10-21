package SLR202_JAVA_ADVANCE.Java_Thread;

public class HelloThread extends Thread {
    public void run(){
        System.out.println("Hello from a thread!");
    }

    public static void main(String args[]){
        (new HelloThread()).start();
    }
}

