import java.io.*;
import java.net.Socket;
import java.util.Random;

public class Philosopher implements Runnable{
    private final int id;
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
    }

    public void run(){
        try (Socket socket = new Socket(serverHost, serverPort);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            while(!Thread.currentThread().isInterrupted()){
                switch(state){
                    case REFLECHIR:
                        Thread.sleep(random.nextInt(256)); // 1 to 256 milliseconds
                        System.out.println(id + " is reflecting.");
                        state = State.AFFAME;
                        break;
                    case AFFAME:
                        System.out.println(id + " is hungry and tries to pick up the left fork.");
                        pickUpFork(out, in, leftForkId);
                        System.out.println(id + " picked up the left fork and tries to pick up the right fork.");
                        pickUpFork(out, in, rightForkId);
                        System.out.println(id + " picked up the right fork.");
                        state = State.MANGER;
                        turns++;
                        break;
                    case MANGER:
                        System.out.println(id + " is eating.");
                        Thread.sleep(random.nextInt(256));
                        System.out.println(id + " puts down the right fork.");
                        putDownFork(out, in, rightForkId);
                        System.out.println(id + " puts down the left fork.");
                        putDownFork(out, in, leftForkId);
                        state = State.REFLECHIR;
                        break;
                }
            }
        } catch (Exception e){
            Thread.currentThread().interrupt();
        }
    }

    private void pickUpFork(PrintWriter out, BufferedReader in, int forkId) throws IOException, InterruptedException {
        out.println("pickup " + forkId);
        while (!in.readLine().equals("ok")) {
            Thread.sleep(1000);
        }
    }

    private void putDownFork(PrintWriter out, BufferedReader in, int forkId) throws IOException, InterruptedException {
        out.println("putdown " + forkId);
        while (!in.readLine().equals("ok")) {
            Thread.sleep(1000);
        }
    }

    public int getTurns(){
        return turns;
    }

    public int getId(){
        return this.id;
    }
}
