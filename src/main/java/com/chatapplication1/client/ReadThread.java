package com.chatapplication1.client;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadThread extends Thread {
    private BufferedReader in;
    private ChatUI chatUI;

    public ReadThread(BufferedReader in, ChatUI chatUI) {
        this.in = in;
        this.chatUI = chatUI;
    }

    public void run() {
        try {
            String message;
            System.out.println("ReadThread is running and waiting for messages from the server...");
            while ((message = in.readLine()) != null) {
                System.out.println("Received message: " + message); // Log each message for verification
                chatUI.appendMessage(message);
            }
        } catch (IOException e) {
            System.out.println("ReadThread encountered an error: " + e.getMessage());
        } finally {
            System.out.println("ReadThread finished. Disconnected from the chat server.");
            chatUI.appendMessage("Disconnected from the chat server.");
        }
    }
}
