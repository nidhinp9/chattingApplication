package com.chatapplication1.registration;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

public class RegistrationServer {
    private static final int PORT = 12346;
    private static Map<String, String> credentials = new HashMap<>();

    static {
        loadCredentialsFromFile();
        System.out.println("Loaded Credentials on Registration Server:");
        credentials.forEach((username, password) -> System.out.println("Username: " + username + ", Password: " + password));
    }

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Registration Server started on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client attempting to register: " + clientSocket);
                new RegistrationHandler(clientSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized boolean registerUser(String username, String password) {
        if (!credentials.containsKey(username)) {
            credentials.put(username, password);
            saveCredentialsToFile();
            return true;
        }
        return false;
    }

    private static void saveCredentialsToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("credentials.ser"))) {
            oos.writeObject(credentials);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadCredentialsFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("credentials.ser"))) {
            credentials = (Map<String, String>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No existing credentials found. Starting fresh.");
        }
    }
}
