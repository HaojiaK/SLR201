package java_advance.Java_IO;
import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args){
        try{
            //create a ServerSocket and make it listen for connection
            ServerSocket serverSocket = new ServerSocket(8000);
            System.out.println("Server is running and waiting for a client");
            
            //when a client connects, accept the connection and get the Socket
            Socket socket = serverSocket.accept();

            //get the InputStream from the socket to read data sent by the client
            BufferedReader inputFormClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter outputToClient = new PrintWriter(socket.getOutputStream(), true);

            //Read the client's message from the InputStream
            String clientMessage = inputFormClient.readLine();
            System.out.println("Received from clinet: " + clientMessage);

            //Create a response message by concatenating "Hello", the client's message, and "!"
            String serverMessage = "Hello " + clientMessage + "!";
            //Get the OutputStream from the Socket and write the response message to it
            outputToClient.println(serverMessage);
            
            //Close the Socket and ServerSocket
            socket.close();
            serverSocket.close();
        } catch(IOException e){
            System.out.println("Error in server: "+ e.getMessage());
        }
    }
}
