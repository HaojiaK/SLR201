

public class SynchronizedMyThread{
    public static void main(String[] args){
        CommandsBuffer buffer = new CommandsBuffer();

        PushThread pushThread = new PushThread(buffer);
        PullThread pullThread = new PullThread(buffer);

        pushThread.start();
        pullThread.start();
    }

    static class CommandsBuffer{
        private String[] commands = new String[1024];
        private int nextStoreIdx = 0;
        private int nextTakeIdx = 0;

        public synchronized String popCommand(){
            try{
                while (true){
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
                while (true){
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
            } catch (Exception e) {e.printStackTrace();}
        }
    }

    static class PushThread extends Thread {
        private CommandsBuffer buffer;
        public PushThread(CommandsBuffer buffer){
            this.buffer = buffer;
        }

        @Override
        public void run(){
            for (int i=0; i<100; i++){
                buffer.pushCommand("Command "+i);
                try{
                    Thread.sleep(50);
                }catch(InterruptedException e){
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
            for (int i=0; i<100; i++){
                System.out.println(buffer.popCommand());
                try{
                    Thread.sleep(50);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }
}