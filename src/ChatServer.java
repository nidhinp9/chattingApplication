import java.io.*;
import java.net.*;

public class ChatServer {
    private static ChatRoomManager chatRoomManager = ChatRoomManager.getInstance();

    public static void main(String[] args) throws IOException {
        System.out.println("Chat server started...");  // This confirms the server starts
        ServerSocket serverSocket = new ServerSocket(12345);

        while (true) {
            System.out.println("Waiting for a client connection...");  // Add this to see if the server is listening
            new ClientHandler(serverSocket.accept()).start();  // When a client connects, this will print the socket info
            System.out.println("Client connected!");  // Add this line to confirm the client connection
        }
    }

    private static class ClientHandler extends Thread {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;
        private ChatRoom currentRoom;
        private String userName;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                // Ask for username
                out.println("Enter your username:");
                userName = in.readLine();
                System.out.println("Username received: " + userName);  // Confirm username

                // Join or create a chat room
                out.println("Enter Room ID to join/create:");
                String roomId = in.readLine();
                currentRoom = chatRoomManager.createOrJoinRoom(roomId);
                currentRoom.addUser(out, userName);
                out.println("Joined room: " + roomId);
                System.out.println("User " + userName + " joined room: " + roomId);

                // Broadcast active users in the room
                out.println("Active users in room " + roomId + ":");
                out.println(currentRoom.getActiveUsers());

                // Handle messages
                String message;
                while ((message = in.readLine()) != null) {
                    if (MessageHandler.isValidMessage(message)) {
                        currentRoom.broadcastMessage(userName + ": " + message);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (currentRoom != null) {
                    currentRoom.broadcastMessage(userName + " has left the room.");
                    currentRoom.removeUser(out);
                }
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
