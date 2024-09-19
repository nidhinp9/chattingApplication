public class MessageHandler {
    public static boolean isValidMessage(String message) {
        return message != null && !message.trim().isEmpty();
    }
}
