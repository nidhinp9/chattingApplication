# Chat Application

This is a simple multi-client chat application built in Java using socket programming. It supports public chat among users.

## Features

- **Multi-client support**: Multiple users can connect to the chat server simultaneously.
- **Public chat**: Messages sent by a user are broadcast to all connected users.
- **User-friendly prompts**: Users are prompted to enter their username when they join the chat.

## Prerequisites

- Java Development Kit (JDK) installed (preferably OpenJDK 11+).
- Basic terminal/command-line knowledge.

## Setup

### 1. Clone the repository or download the code files

```bash
git clone https://github.com/your-username/chat-app.git
cd chat-app
```

### 2. Compile the Java files

Use the following command to compile the server and client files:

```bash
javac src/*.java
```

### 3. Running the Server

To start the chat server, execute the following command in the terminal:

```bash
java -cp src ChatServer
```

You should see the message:

```
Chat server started...
```

The server is now waiting for clients to connect.

### 4. Running the Client

To start a chat client, open a new terminal window and run the following command:

```bash
java -cp src ChatClient
```

You will be prompted to enter your name. After joining, you can chat with other users.

You can start as many clients as needed in separate terminals to simulate multiple users.

## Public Chat

To send a public message, simply type your message and hit enter. All connected users will see the message.

Example:

```
Hello, everyone!
```

## How to Exit

To leave the chat, simply close the client terminal window. The server will detect the disconnection and notify the remaining users.

## Example

### Starting the Server

```bash
java -cp src ChatServer
```

Output:
```
Chat server started...
```

### Starting a Client

```bash
java -cp src ChatClient
```

Client output:
```
Enter your name:
Alice
Alice has joined the chat!
```

### Sending a Public Message

```
Hello everyone!
```

Output for other clients:
```
Alice: Hello everyone!
```

### Client Disconnection

When a user closes their terminal, the server will broadcast their disconnection to the other clients:

```
Alice has left the chat.
```

## Error Handling

If a user sends a message and there’s an issue, they will receive an appropriate error message based on the context.

## Future Improvements

- Add support for message history.
- Enhance with file transfer functionality.
- Implement authentication for secure communication.
