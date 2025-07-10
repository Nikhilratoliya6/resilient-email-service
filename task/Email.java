public class Email {
    private String emailId;
    private String recipient;
    private String subject;
    private String body;

    // Constructor, getters, and setters
    public Email(String emailId, String recipient, String subject, String body) {
        this.emailId = emailId;
        this.recipient = recipient;
        this.subject = subject;
        this.body = body;
    }

    public String getEmailId() {
        return emailId;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }
}
