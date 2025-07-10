import java.util.concurrent.TimeUnit;

public class EmailService {
    private EmailProvider providerA = new MockProviderA();
    private EmailProvider providerB = new MockProviderB();
    private RateLimiter rateLimiter = new RateLimiter();
    private IdempotencyStore idempotencyStore = new IdempotencyStore();
    private int maxRetries = 5;

    public void sendEmail(Email email) {
        if (idempotencyStore.contains(email.getEmailId())) {
            System.out.println("Duplicate email ID. Skipping send.");
            return;
        }

        idempotencyStore.add(email.getEmailId());

        boolean success = false;
        EmailProvider currentProvider = providerA;
        int retryCount = 0;

        while (retryCount < maxRetries && !success) {
            if (rateLimiter.canSendEmail()) {
                success = attemptSend(currentProvider, email);
                if (!success && currentProvider == providerA) {
                    System.out.println("Switching to Provider B.");
                    currentProvider = providerB;
                }
                retryCount++;
                if (!success) {
                    long waitTime = (long) Math.pow(2, retryCount); // Exponential backoff
                    System.out.println("Retrying... attempt " + (retryCount + 1) + " with wait time of " + waitTime + " seconds.");
                    try {
                        TimeUnit.SECONDS.sleep(waitTime);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            } else {
                System.out.println("Rate limit exceeded. Waiting...");
                try {
                    TimeUnit.SECONDS.sleep(60); // Wait for rate limit to reset
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        if (!success) {
            System.out.println("Failed to send email after retries.");
        } else {
            System.out.println("Email sent successfully.");
        }
    }

    private boolean attemptSend(EmailProvider provider, Email email) {
        boolean success = provider.send(email);
        System.out.println("Attempt to send email: " + (success ? "Success" : "Failure"));
        return success;
    }
}
