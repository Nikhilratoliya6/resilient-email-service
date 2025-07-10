public interface EmailProvider {
    boolean send(Email email);
    void resetFailureCount();
    boolean isAvailable();
}
