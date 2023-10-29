package philosopher_socket;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

public class Philosopher implements Runnable{

    private final int id;
    //you could check about three status, leftFork, rightFork and id
    private final int leftForkId;
    private final int rightForkId;
    private final String serverHost;
    private final int serverPort;
    private Random random = new Random();
    private int turns = 0;
    
    private enum State {AFFAME, MANGER, REFLECHIR};
    private State state;

    public Philosopher(int id, int leftForkId, int rightForkId, String serverHost, int serverPort){
        this.id = id;
        this.leftForkId = leftForkId;
        this.rightForkId = rightForkId;
        this.serverHost = serverHost;
        this.serverPort = serverPort;
        this.state = State.REFLECHIR;
        //the normal status is reflechir;
    }

    public void run(){
        Socket socket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        try{
            socket = new Socket(serverHost, serverPort);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while(!Thread.currentThread().isInterrupted()){
                switch(state){
                    case REFLECHIR:
                        Thread.sleep(random.nextInt(256)); // 1 a 256 milliseconds
                        state = State.AFFAME;
                        System.out.println(id + " is reflecting.");
                        break;
                    
                    case AFFAME:
                    //The philosopher should take the forks
                        System.out.println(id + " is hungry and tries to pick up the left fork.");
                        try{pickUpFork(out, in, leftForkId);
                        } catch (IOException e){
                            e.printStackTrace();
                        }

                        try{
                            System.out.println(id + " picked up the left fork and tries to pick up the right fork.");
                            try{pickUpFork(out, in, rightForkId);
                            } catch (IOException e1){
                                e1.printStackTrace();
                            }
                            System.out.println(id + " picked up the right fork.");
                            //If a philosopher takes the both forks, then change status into manger
                            state = State.MANGER;
                            turns++;
                        } catch (InterruptedException e){
                            System.out.println(id + " couldn't pick up the right fork and put down the left fork");   
                            try{
                                putDownFork(out, in, rightForkId);
                            } catch (IOException e2){
                                e2.printStackTrace();
                            }                            
                            Thread.sleep(100); //Add a delay to avoid an infinite loop if a philosopher failing to take the right fork.
                            break;
                        }
                        break;
                    case MANGER:
                        System.out.println(id + " is eating.");
                        Thread.sleep(random.nextInt(256));
                        try{
                            putDownFork(out, in, leftForkId);
                        } catch (IOException e3){
                            e3.printStackTrace();
                        }  

                        try{
                            putDownFork(out, in, rightForkId);
                        } catch (IOException e4){
                            e4.printStackTrace();
                        }  
                        System.out.println(id + " puts down the left fork and right fork.");
                        state = State.REFLECHIR;
                        break;
                }
            }
        } catch (UnknownHostException e){
            Thread.currentThread().interrupt();
        } catch (IOException e){
            e.printStackTrace();
        } catch (InterruptedException e){
            Thread.currentThread().interrupt();
        } finally {
            try{
                if(in != null){
                    in.close();
                }
                if(out != null){
                    out.close();
                }
                if(socket != null){
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void pickUpFork (PrintWriter out, BufferedReader in, int forkId) throws IOException, InterruptedException{
        out.println("pickup " + forkId);
        while (!in.readLine().equals("ok")){
            Thread.sleep(100);
        }
    }

    private void putDownFork(PrintWriter out, BufferedReader in, int forkId) throws IOException, InterruptedException {
        out.println("putdown " + forkId);
        while (!in.readLine().equals("ok")){
            Thread.sleep(100);
        }
    }

    //Add this getter to access the turns from main class
    public int  getTurns(){
        return turns;
    }

    public int getId(){
        return this.id;
    }

}
