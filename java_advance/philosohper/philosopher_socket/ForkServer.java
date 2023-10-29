package philosopher_socket;
import java.io.*;
import java.net.*;


public class ForkServer {
    private final Fork[] forks;
    private ServerSocket serverSocket;

    public ForkServer(int numForks, int port) throws IOException{
        forks = new Fork[numForks];
        for (int i=0; i<numForks; i++){
            forks[i] = new Fork();
        }
        serverSocket = new ServerSocket();
    }

    public void start() throws IOException {
        while(true){
            Socket clienSocket = serverSocket.accept();
            new Thread(new ClientHandler(clienSocket)).start();
        }
    }

    private class ClientHandler implements Runnable{
        private final Socket clienSocket;
        public ClientHandler(Socket socket) {
            this.clienSocket = socket;
        }

        @Override
        public void run() {
            try (
                PrintWriter out = new PrintWriter(clienSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clienSocket.getInputStream()));
            ){
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    //Parse the request
                    String[] parts = inputLine.split(" ");
                    String command = parts[0];
                    int forkIndex = Integer.parseInt(parts[1]);

                    if(command.equals("pickup")){
                        try{
                            forks[forkIndex].pickUp();
                            out.println("ok");
                        } catch (InterruptedException e){
                            e.printStackTrace();
                            out.println("error");
                        }
                    } else if (command.equals("putdown")){
                        try{
                            forks[forkIndex].putDown();
                            out.println("ok");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            out.println("error");
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try{
                    clienSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}