/*Describe
 * • Define a Threadsubclass called MyThread 
 *  • Each instance receives a String name upon creation 
 *  • The run() method • Prints a message iteratively , for 100 times 
 *  • Each message: <the thread’s name> and <iteration index: 1..100> 
 *  • Sleeps for 50ms between iterations 
 *  • Prints an END message before exiting
  */
public class MyThread extends Thread {

    private final String name;

    public MyThread(String name) {
        this.name = name;
    }

    @Override //Why do we need to add this?
    public void run(){
        for (int i=1; i<=100; i++){
            System.out.println(name + " " + i);//Print message with thread's name and iteration index

            try{
                Thread.sleep(50); //Sleep for 50 milliseconds
            } catch(InterruptedException exc){
                exc.printStackTrace();
            }
        }

        System.out.println("END "+name); //Print END message before exiting
    }

    public static void main(String[] args){
        MyThread thread_1 = new MyThread("Thread 1"); //Create a new Thread

        thread_1.start();
    }
}
