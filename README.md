## Chatting Application

A Java-based real-time chatting application that supports multiple clients. This application provides user registration and login functionality with encrypted messaging for secure communication between users.

### Features

- **User Registration and Login**: Users can register and log in with a unique username and password.
- **Secure Messaging**: Messages are encrypted for privacy.
- **Real-Time Chat**: Users can communicate in real time with other connected users.
- **Multi-Client Support**: Multiple users can connect to the server and chat simultaneously.

---

### Prerequisites

Before running the application, make sure you have the following installed:

- **Java JDK 8 or later**
- **Git** (optional, for cloning the repository)
- **A Java IDE** (e.g., IntelliJ IDEA, Eclipse, NetBeans) - recommended but not required if you want to compile and run the application directly from the terminal.

---

### Getting Started

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/username/chatting-application.git
   cd chatting-application
   ```

2. **Compile the Code** (if not using an IDE):
   - Navigate to the source directory and compile the files:
     ```bash
     javac -d bin src/com/chatapplication1/*.java src/com/chatapplication1/client/*.java src/com/chatapplication1/server/*.java
     ```

3. **Run the Server**:
   - Start the chat server before running any clients:
     ```bash
     java -cp bin com.chatapplication1.server.ChatServer
     ```
   - The server will start on port `12345` by default, and you should see "Chat Server started on port 12345" in the terminal.

4. **Run the Registration Client (Optional)**:
   - Use the registration client to register a new user:
     ```bash
     java -cp bin com.chatapplication1.registration.RegistrationClient
     ```
   - Follow the prompts to enter a new username and password, which will be saved for authentication.

5. **Run the Chat Client**:
   - Open a new terminal window for each client and run:
     ```bash
     java -cp bin com.chatapplication1.client.ChatClient
     ```
   - Enter your username and password to log in and begin chatting. You should see a chat interface where you can type and send messages to other users.

---

### Usage

- **Starting the Chat Server**: Run the `ChatServer` file. It will wait for clients to connect.
- **Registering New Users**: Use the `RegistrationClient` to register new users by entering a unique username and password.
- **Logging In**: Each user must log in using the `ChatClient` with a registered username and password.
- **Chatting**: After logging in, users can start sending messages. All messages are sent in real-time to connected users.

---

### Troubleshooting

- **Connection Issues**: Ensure that the server is running before clients attempt to connect. Clients must use the correct server IP and port.
- **Authentication Failures**: Make sure the username and password are registered. Double-check the credentials entered in the `RegistrationClient`.
- **Premature Disconnections**: Check that the server terminal does not display any errors. Restart the server and clients if needed.

---

### Contributing

Contributions are welcome! Please open an issue or submit a pull request to improve the application.

---

### License

This project is licensed under the MIT License.

---

This `README.md` file provides an overview, setup instructions, and a usage guide for users and developers to get started with the application. Feel free to modify any section as needed to match your specific application setup or usage details!
