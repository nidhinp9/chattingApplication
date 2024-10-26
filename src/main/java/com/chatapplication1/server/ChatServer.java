package com.chatapplication1.server;

import java.io.*;
import java.net.*;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ChatServer {
    private static final int PORT = 12345;
    private static Map<String, String> credentials; // Assume this is loaded properly from credentials.ser
    private static Set<ClientHandler> clientHandlers = new HashSet<>(); // To manage connected clients

    // Load credentials at server startup (assume the loadCredentialsFromFile() method exists)
    static {
        loadCredentialsFromFile(); 
        System.out.println("Loaded Credentials on Chat Server:");
        credentials.forEach((username, password) -> System.out.println("Username: " + username + ", Password: " + password));
    }

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Chat Server started on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client attempting to connect: " + clientSocket);
                ClientHandler handler = new ClientHandler(clientSocket);
                handler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to add a client handler to the set
    public static void addClient(ClientHandler handler) {
        synchronized (clientHandlers) {
            clientHandlers.add(handler);
            System.out.println("Client added: " + handler.getUserName());
        }
    }

    // Method to remove a client handler from the set
    public static void removeClient(ClientHandler handler) {
        synchronized (clientHandlers) {
            clientHandlers.remove(handler);
            System.out.println("Client removed: " + handler.getUserName());
        }
    }

    // Broadcast message to all connected clients
    public static void broadcastMessage(String message, ClientHandler sender) {
        synchronized (clientHandlers) {
            for (ClientHandler client : clientHandlers) {
                if (client != sender) {
                    client.sendMessage(message);
                }
            }
        }
    }

    // Authenticate users based on stored credentials
    public static synchronized boolean authenticateUser(String username, String password) {
        return credentials.containsKey(username) && credentials.get(username).equals(password);
    }

    // Assume method to load credentials from file exists
    private static void loadCredentialsFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("credentials.ser"))) {
            credentials = (Map<String, String>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No saved credentials found.");
        }
    }
}
