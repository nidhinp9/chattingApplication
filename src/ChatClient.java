import java.io.*;
import java.net.*;

public class ChatClient {
    private static BufferedReader in;  // For reading server messages
    private static PrintWriter out;    // For sending messages to server
    private static BufferedReader stdIn;  // For reading input from user

    public static void main(String[] args) {
        try {
            // Connect to the server
            Socket socket = new Socket("localhost", 12345);
            System.out.println("Connected to chat server");

            // Input and output streams for server communication
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            // Input stream to take input from the user
            stdIn = new BufferedReader(new InputStreamReader(System.in));

            // Thread to listen to messages from the server
            new Thread(new IncomingMessagesHandler()).start();

            // Read user input and send to server
            String userInput;
            while ((userInput = stdIn.readLine()) != null) {
                out.println(userInput);  // Send message to the server
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Thread to handle incoming messages from server
    private static class IncomingMessagesHandler implements Runnable {
        public void run() {
            try {
                String serverMessage;
                while ((serverMessage = in.readLine()) != null) {
                    System.out.println(serverMessage);  // Display message from server
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
