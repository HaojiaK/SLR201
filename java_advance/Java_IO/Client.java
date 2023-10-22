package java_advance.Java_IO;
import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args){
        try{
            //Create a Socket to connect to the server
            Socket socket = new Socket("localhost", 8000);

            //Get the OutputStream from the Socket and write your message to it
            PrintWriter outputToServer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader intputFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            //Get the InputStream from the Socket to read the server's response.
            String clientMessage = "World";
            outputToServer.println(clientMessage);

            //Read the server's response from the InputStream
            String serverMessage = intputFromServer.readLine();

            //Print the server's response
            System.out.println("Received from server: " + serverMessage);

            //Close the socket
            socket.close();
        }catch(IOException e){
            System.out.println("Error in clinet: " + e.getMessage());
        }
    }
}
