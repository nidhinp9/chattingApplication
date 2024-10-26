package com.chatapplication1.registration;

import java.io.*;
import java.net.*;

public class RegistrationHandler extends Thread {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public RegistrationHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            out.println("Enter desired username:");
            String username = in.readLine();
            out.println("Enter desired password:");
            String password = in.readLine();

            if (RegistrationServer.registerUser(username, password)) {
                out.println("Registration successful! You can now log in on the Chat Server.");
            } else {
                out.println("Username already taken. Please try again with a different username.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
    }

    private void closeConnections() {
        try {
            if (socket != null) socket.close();
            if (in != null) in.close();
            if (out != null) out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
