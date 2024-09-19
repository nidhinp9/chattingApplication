import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class ChatRoom {
    private String roomId;
    private Map<PrintWriter, String> users;

    public ChatRoom(String roomId) {
        this.roomId = roomId;
        users = new HashMap<>();
    }

    public void addUser(PrintWriter writer, String username) {
        users.put(writer, username);
    }

    public void removeUser(PrintWriter writer) {
        users.remove(writer);
    }

    public void broadcastMessage(String message) {
        for (PrintWriter writer : users.keySet()) {
            writer.println(message);
        }
    }

    public String getActiveUsers() {
        StringBuilder userList = new StringBuilder();
        for (String user : users.values()) {
            userList.append(user).append("\n");
        }
        return userList.toString();
    }

    public String getRoomId() {
        return roomId;
    }
}
