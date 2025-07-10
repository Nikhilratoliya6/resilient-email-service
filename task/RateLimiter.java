import java.util.concurrent.atomic.AtomicInteger;

public class RateLimiter {
    private static final int MAX_EMAILS_PER_MINUTE = 5;
    private AtomicInteger emailsSent = new AtomicInteger(0);
    private long lastSendTime = System.currentTimeMillis();

    public boolean canSendEmail() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastSendTime > 60000) {
            emailsSent.set(0); // Reset the counter every minute
            lastSendTime = currentTime;
        }
        return emailsSent.get() < MAX_EMAILS_PER_MINUTE;
    }
}
