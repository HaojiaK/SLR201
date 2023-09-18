package multiple_Threads;

public class CommandsBuffer {
  private String[] commands = new String[1024];
  private int nextStoreIdx = 0;
  private int nextTakeIdx = 0;

  public synchronized String popCommand(){
    try{
      while(true){
        if(nextTakeIdx == nextStoreIdx){
          wait();
        }else{
          String cmd = commands[nextTakeIdx];
          nextTakeIdx = (nextTakeIdx + 1)%1024;
          notifyAll();
          return cmd;
        }
      }
    } catch (Exception e){e.printStackTrace();}
    return null;
  }

  public synchronized void pushCommand(String cmd){
    try{
      while(true){
        int futureStoreIdx = (nextStoreIdx + 1)%1024;
        if(nextTakeIdx == futureStoreIdx){
          wait();
        }else{
          commands[futureStoreIdx] = cmd;
          nextStoreIdx = futureStoreIdx;
          notifyAll();
          return;
        }
      }
    } catch( Exception e){e.printStackTrace();}
  }
}


