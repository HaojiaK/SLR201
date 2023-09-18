package multiple_Threads;
public class MainClass{
  public static void main(String[] args){
    /*This is the main driver class of your program. It contains the main method which is the entry point of your program. 
     *Within this main method, you're creating a CommandsBuffer object and two threads (PushThread and PullThread) that operate on this buffer.
     *After creating these threads, you're starting them with start() method.
    */
    CommandsBuffer buffer = new CommandsBuffer();

    PushThread pushThread = new PushThread(buffer);
    PullThread pullThread = new PullThread(buffer);

    pushThread.start();
    pullThread.start();

  }

  static class CommandsBuffer{
    //这个函数的作用是构建popCommand和pushCommand两种方法的
    private String[] commands = new String[1024];
    private int nextStoreIdx = 0;
    private int nextTakeIdx = 0;

    public synchronized String popCommand(){
      try{
        while(true){
          if(nextTakeIdx == nextStoreIdx){
            wait();
          }else{
            String cmd =  commands[nextTakeIdx];
            nextTakeIdx = (nextTakeIdx + 1)%1024;
            notifyAll();
            return cmd;
          }
        }
      } catch (Exception e) {e.printStackTrace();}
      return null;
    }

    public synchronized void pushCommand(String cmd){
      try{
        while(true){
          int futureStoreIdx = (nextStoreIdx + 1)%1024;
          if(nextTakeIdx == futureStoreIdx){
            wait();
          } else {
            commands[futureStoreIdx] = cmd;
            nextStoreIdx = futureStoreIdx;
            notifyAll();
            return;
          }
        }
      }catch (Exception e) {e.printStackTrace();}
    }
  }

  static class PushThread extends Thread{
    private CommandsBuffer buffer;

    public PushThread(CommandsBuffer buffer){
      this.buffer = buffer;
    }

    @Override
    public void run(){
      for (int i=0; i<1; i++){
        buffer.pushCommand("Command" + i);
        try{
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }

  static class PullThread extends Thread{
    private CommandsBuffer buffer;

    public PullThread(CommandsBuffer buffer){
      this.buffer = buffer;
    }

    @Override
    public void run(){
      for(int i=0; i<100; i++){
        System.out.println(buffer.popCommand());
        try{
          Thread.sleep(1000);
        } catch (InterruptedException e){
          e.printStackTrace();
        }
      }
    }
  }
}