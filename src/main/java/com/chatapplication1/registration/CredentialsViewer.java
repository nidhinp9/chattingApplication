package com.chatapplication1.registration;

import java.io.*;
import java.util.Map;

public class CredentialsViewer {

    public static void main(String[] args) {
        // Path to the credentials file
        String filePath = "credentials.ser";

        // Deserialize and print the credentials
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            Map<String, String> credentials = (Map<String, String>) ois.readObject();
            System.out.println("Stored Usernames and Passwords:");
            credentials.forEach((username, password) -> System.out.println("Username: " + username + ", Password: " + password));
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No stored credentials found or unable to read file.");
            e.printStackTrace();
        }
    }
}
