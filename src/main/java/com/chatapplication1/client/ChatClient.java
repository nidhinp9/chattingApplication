package com.chatapplication1.client;

import javax.swing.*;
import java.io.*;
import java.net.*;

public class ChatClient {
    private String host;
    private int port;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private ChatUI chatUI;

    public ChatClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() {
        try {
            socket = new Socket(host, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            System.out.println("Connected to Chat Server on port " + port);

            boolean authenticated = loginUser();
            if (authenticated) {
                System.out.println("Authentication successful; initializing chat UI...");
                openChat();
            } else {
                System.out.println("Failed to authenticate. Exiting client.");
                closeConnections();
            }

        } catch (IOException e) {
            System.out.println("Error in ChatClient: " + e.getMessage());
            e.printStackTrace();
            closeConnections();
        }
    }

    private boolean loginUser() {
        try {
            String response;
            while (true) {
                response = in.readLine();
                System.out.println("Received Response: " + response);

                if ("PROMPT_USERNAME".equals(response)) {
                    String userName = JOptionPane.showInputDialog("Enter your username:");
                    out.println(userName);
                    out.flush();
                } else if ("PROMPT_PASSWORD".equals(response)) {
                    String password = JOptionPane.showInputDialog("Enter your password:");
                    out.println(password);
                    out.flush();
                } else if (response != null && response.startsWith("AUTH_SUCCESS")) {
                    JOptionPane.showMessageDialog(null, response.replace("AUTH_SUCCESS: ", ""));
                    return true; // Successful login
                } else if (response != null && response.startsWith("AUTH_FAIL")) {
                    JOptionPane.showMessageDialog(null, response.replace("AUTH_FAIL: ", ""));
                } else {
                    System.out.println("Unexpected response or connection closed by server.");
                    return false;
                }
            }
        } catch (IOException e) {
            System.out.println("Login attempt failed due to an IO error: " + e.getMessage());
            return false;
        }
    }

    private void openChat() {
        System.out.println("Starting chat UI...");
        chatUI = new ChatUI(out);
        chatUI.setVisible(true);

        // Start ReadThread to maintain the connection with the server
        ReadThread readThread = new ReadThread(in, chatUI);
        readThread.start();
        System.out.println("ReadThread started successfully, maintaining connection with server.");
        
        try {
            readThread.join(); // Keep the main thread alive until ReadThread finishes
        } catch (InterruptedException e) {
            System.out.println("Chat client interrupted: " + e.getMessage());
        }
        
        closeConnections(); // Only close after ReadThread completes
    }

    private void closeConnections() {
        try {
            System.out.println("Closing client connections...");
            if (socket != null && !socket.isClosed()) socket.close();
            if (in != null) in.close();
            if (out != null) out.close();
        } catch (IOException e) {
            System.out.println("Error closing connections: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        ChatClient client = new ChatClient("localhost", 12345);
        client.start();
    }
}
