public class MyThread extends Thread{
    private final String name;

    public MyThread(String name){
        this.name = name;
    }

    @Override
    public void run(){
        for (int i=1; i<100; i++){
            System.out.println(name + " " + i);

            try{
                Thread.sleep(50);
            } catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args){
        MyThread thread1 = new MyThread("Thread 1");
        MyThread thread2 = new MyThread("Thread 2");
        
        thread1.start();
        thread2.start();
    }
    
}