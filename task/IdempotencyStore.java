import java.util.HashSet;
import java.util.Set;

public class IdempotencyStore {
    private Set<String> sentEmailIds = new HashSet<>();

    public boolean contains(String emailId) {
        return sentEmailIds.contains(emailId);
    }

    public void add(String emailId) {
        sentEmailIds.add(emailId);
    }
}
