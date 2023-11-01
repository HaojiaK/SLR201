package philosopher_socket;
import java.io.*;
import java.net.*;


public class ForkServer {
    private final Fork[] forks;
    private ServerSocket serverSocket;

    public ForkServer(int numForks, int port) throws IOException{
        //Initialize and bind the serverSocket when ForkServer is created
        this.serverSocket = new ServerSocket(port);
        forks = new Fork[numForks];
        for (int i=0; i<numForks; i++){
            forks[i] = new Fork();
        }

        //Create a ServerSocket and bind it to the specified port
    }

    public void start() throws IOException {
        /*
         * The server listens for incoming connections from clients (philosophers) here.
         * When a connection is established, it starts a new thread to handle the client's requests.
         */
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
            /*
             * This is where the server handles incoming requests from the clients.
             * It reads the client's messages, processes them based on the command ("pickup" or "putdown"),
             * and sends a response back to client
             */
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

    public static void main(String[] args) {
        try{
            //Initialize a new ForkServer with the same number of forks as philosophers and listen on port 5000
            /*
             * We access NUMBER_OF_PHILOSOPHERS with PhilosopherClient.NUMBER_OF_PHILOSOPHERS.
             * This retrieves the value of NUMBER_OF_PHILOSOPHERS defined in the PhilosopherClient class.
             */
            ForkServer server = new ForkServer(PhilosopherClient.NUMBER_OF_PHILOSOPHERS, 5000);
            server.start();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}