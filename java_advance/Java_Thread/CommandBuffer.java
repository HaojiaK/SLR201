package SLR202_JAVA_ADVANCE.Java_Thread;


class PushThread extends Thread {
    private CommandBuffer buffer;

    public PushThread(CommandBuffer buffer){
        this.buffer = buffer;
    }
    @Override 
    // Indicates that a method declaration is intended to override a method declaration in a superclass.
    public void run() {
        for (int i=0; i<100; i++){
            buffer.pushCommand("Command" + i);
            try{
                Thread.sleep(10);
            }catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class PullThread extends Thread {
    private CommandBuffer buffer;

    public PullThread(CommandBuffer buffer){
        this.buffer = buffer;
    }

    @Override
    public void run(){
        for(int i=0; i<100; i++){
            String command = buffer.popCommand();
            System.out.println("Executed command: " + command);
            try{
                Thread.sleep(10);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}

public class CommandBuffer {
    //An array to store the commands to be executed
    private String[] commands = new String[1024];

    //Index where to store the next arriving command
    //Condition: (nextStoreIdx + 1) % 1024 != lastTakeIdx
    private int nextStoreIdx = 0;

    //Index where to take the next command to execute
    //Condition: nextTakeIdx != nextStoreIdx
    private int nextTakeIdx = 0;

    public String popCommand(){
        try{
            while (true) {
                if (nextTakeIdx == nextStoreIdx) {
                }else{
                    String cmd = commands[nextTakeIdx];
                    nextTakeIdx = (nextTakeIdx + 1) % 1024;
                    System.out.println("Popoed command: "+cmd); //Added this line
                    return cmd;
                }
            }
        } catch (Exception e) {e.printStackTrace();}
        return null;
    }

    public void pushCommand(String cmd){
        try{
            while (true) {
                int futureStoreIdx = (nextStoreIdx + 1) % 1024;
                if (nextTakeIdx == futureStoreIdx) {
                } else {
                    commands[futureStoreIdx] = cmd;
                    nextStoreIdx = futureStoreIdx;
                    System.out.println("Pushed command: "+ cmd); //Added this line 
                    return;
                }
            }
        } catch (Exception e) {e.printStackTrace();}
    }

    public static void main(String[] args){
        CommandBuffer buffer = new CommandBuffer();
        PushThread pushThread = new PushThread(buffer);
        PullThread pullThread = new PullThread(buffer);

        pushThread.start();
        pullThread.start();

        try{
            pushThread.join();
            pullThread.join();
        }catch(InterruptedException e){
            e.printStackTrace();
        }

    }
}

