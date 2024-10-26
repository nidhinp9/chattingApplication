package com.chatapplication1.server;

import com.chatapplication1.client.CryptoUtils;
import java.io.*;
import java.net.*;

public class ClientHandler extends Thread {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String userName;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            boolean authenticated = false;
            while (!authenticated) {
                System.out.println("Attempting to authenticate user...");

                // Prompt for username
                out.println("PROMPT_USERNAME");
                String username = in.readLine();
                System.out.println("Received Username: " + username);

                // Prompt for password
                out.println("PROMPT_PASSWORD");
                String password = in.readLine();
                System.out.println("Received Password: " + password);

                // Authenticate user after both username and password are received
                if (ChatServer.authenticateUser(username, password)) {
                    this.userName = username;
                    out.println("AUTH_SUCCESS: Welcome to the chat, " + userName + "!");
                    System.out.println("User authenticated successfully: " + username);
                    authenticated = true;
                    ChatServer.addClient(this);
                } else {
                    out.println("AUTH_FAIL: Authentication failed. Try again.");
                    System.out.println("Authentication failed for user: " + username);
                }
            }

            // Chat session after successful login
            String incomingMessage;
            while ((incomingMessage = in.readLine()) != null) {
                String message = CryptoUtils.decrypt(incomingMessage);
                ChatServer.broadcastMessage(userName + ": " + message, this);
            }
        } catch (IOException e) {
            System.out.println("Client disconnected or IO error: " + e.getMessage());
        } finally {
            System.out.println("Removing client: " + userName);
            ChatServer.removeClient(this);
            closeConnections();
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    public String getUserName() {
        return userName;
    }

    private void closeConnections() {
        try {
            System.out.println("Closing server-side connections for user: " + userName);
            if (socket != null) socket.close();
            if (in != null) in.close();
            if (out != null) out.close();
        } catch (IOException e) {
            System.out.println("Error closing server-side connections: " + e.getMessage());
        }
    }
}
