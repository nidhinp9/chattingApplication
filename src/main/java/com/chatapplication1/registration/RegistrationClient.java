package com.chatapplication1.registration;

import javax.swing.*;
import java.io.*;
import java.net.*;

public class RegistrationClient {

    private String host;
    private int port;

    public RegistrationClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void register() {
        try (Socket socket = new Socket(host, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // Prompt user for registration details
            String userName = JOptionPane.showInputDialog("Enter your desired username:");
            String password = JOptionPane.showInputDialog("Enter your desired password:");

            // Send registration details to the server
            out.println(userName);
            out.println(password);

            // Read response from the server
            String response = in.readLine();
            JOptionPane.showMessageDialog(null, response);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        RegistrationClient registrationClient = new RegistrationClient("localhost", 12346);
        registrationClient.register();
    }
}
