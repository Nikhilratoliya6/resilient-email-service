public class Main {
    public static void main(String[] args) {
        EmailService emailService = new EmailService();

        // Testing rate limiting by sending more than 5 emails in quick succession
        for (int i = 0; i < 10; i++) {
            Email email = new Email(String.valueOf(i), "test@example.com", "Subject " + i, "Body " + i);
            System.out.println("Sending email " + (i + 1));
            emailService.sendEmail(email);
        }

        // Testing the circuit breaker with multiple failures
        for (int i = 0; i < 5; i++) {
            Email email = new Email("1", "test@example.com", "Subject 1", "Body 1");
            emailService.sendEmail(email);
        }
    }
}
