import java.util.Random;

public class MockProviderB implements EmailProvider {
    private static final int MAX_FAILURES = 3;
    private static final int CIRCUIT_BREAKER_THRESHOLD = 3;
    private int failureCount = 0;
    private int consecutiveFailures = 0;
    private Random random = new Random();

    @Override
    public boolean send(Email email) {
        // Circuit breaker logic
        if (consecutiveFailures >= CIRCUIT_BREAKER_THRESHOLD) {
            System.out.println("Circuit breaker triggered. Provider temporarily unavailable.");
            return false; // Provider is unavailable due to consecutive failures
        }

        if (failureCount < MAX_FAILURES) {
            failureCount++;
            consecutiveFailures = failureCount; // Increase consecutive failures count
            return random.nextBoolean();
        }
        return false; // Simulate failure after 3 tries
    }

    @Override
    public void resetFailureCount() {
        failureCount = 0;
        consecutiveFailures = 0; // Reset consecutive failures count
    }

    @Override
    public boolean isAvailable() {
        return failureCount < MAX_FAILURES && consecutiveFailures < CIRCUIT_BREAKER_THRESHOLD;
    }
}
