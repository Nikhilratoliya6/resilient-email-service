
# Resilient Email Sending Service

This project simulates an email sending service with key features like retry logic with exponential backoff, provider fallback, rate limiting, idempotency, and a circuit breaker for resilience. The email sending is simulated using two mock email providers, and the goal is to build a robust and fault-tolerant email service.

## Features

* **Retry Logic with Exponential Backoff**:

  * Emails are retried with increasing wait times (1s → 2s → 4s → 8s, etc.) when initial attempts fail.

* **Provider Fallback**:

  * If `MockProviderA` fails, the system automatically falls back to `MockProviderB` for retrying the email send.

* **Idempotency**:

  * Ensures that duplicate emails (based on email ID) are not sent. If an email with the same ID is attempted to be sent again, it is skipped.

* **Rate Limiting**:

  * No more than 5 emails can be sent per minute. Additional emails beyond this limit are delayed until the rate limit resets.

* **Circuit Breaker** (Bonus Feature):

  * If a provider fails consecutively for a specified number of times, it will be temporarily blocked (circuit breaker triggered) to avoid excessive retries from the same provider.

* **Status Tracking**:

  * Each email's status is tracked, including whether the email was successfully sent, failed, or retried.

## Prerequisites

* **Java 8** or later is required to run this project.

## How to Run the Project

### Step 1: Clone the repository or download the source code.

You can clone the repository using:

```bash
git clone <repository_url>
```

### Step 2: Compile the project

Navigate to the project directory and compile the Java files:

```bash
cd task
javac *.java


```

### Step 3: Run the program

To test the email sending logic, run the `Main` class:

```bash
java Main
```

### Step 4: Observe the output

The system will print logs to the console as it simulates sending emails, including details about retries, rate limiting, fallback, and circuit breaker triggers.

## Project Structure

* `Email.java`: Defines the structure of the email (ID, recipient, subject, body).
* `EmailProvider.java`: Interface for email providers (`send`, `resetFailureCount`, `isAvailable`).
* `MockProviderA.java`: Simulates the behavior of the first email provider (`MockProviderA`).
* `MockProviderB.java`: Simulates the behavior of the second email provider (`MockProviderB`).
* `EmailService.java`: Contains the logic for sending emails, retries, fallbacks, rate limiting, and more.
* `RateLimiter.java`: Ensures that no more than 5 emails are sent per minute.
* `IdempotencyStore.java`: Ensures that duplicate emails are not sent.
* `EmailStatus.java`: Enum for tracking the status of an email (pending, sent, failed, retrying).
* `Main.java`: Main class to test the email sending service.

## Key Classes & Logic

### 1. **EmailService**

The core class that handles sending emails, retry logic, and interaction with the email providers. It manages:

* **Exponential backoff** on retries.
* **Fallback to a secondary provider** if the first provider fails.
* **Rate limiting** to control email sending frequency.
* **Idempotency** to prevent duplicate email sends.

### 2. **Mock Providers (MockProviderA and MockProviderB)**

These are mock implementations of the `EmailProvider` interface. They simulate email sending with random success or failure and provide a fallback mechanism:

* **Circuit breaker**: After a certain number of consecutive failures, the provider is blocked temporarily.
* **Success/Failure Simulation**: Randomly determines whether an email send attempt succeeds or fails.

### 3. **Rate Limiting**

The rate limiter ensures that no more than 5 emails can be sent per minute. This prevents overwhelming the system with too many requests.

### 4. **Idempotency Store**

The idempotency store tracks the email IDs to ensure that no duplicate emails are sent. If an email with the same ID is encountered, it will be skipped.

### 5. **Circuit Breaker**

If a provider fails consecutively for 3 attempts, the circuit breaker will block the provider temporarily to avoid retry storms. This helps prevent overloading the provider and improves system reliability.

## Example Output

Here is an example of what the output looks like when running the program:

```
Sending email 1
Attempt to send email: Success
Email sent successfully.
Sending email 2
Attempt to send email: Failure
Switching to Provider B.
Attempt to send email: Success
Email sent successfully.
Sending email 3
Attempt to send email: Failure
Switching to Provider B.
Attempt to send email: Success
Email sent successfully.
Sending email 4
Attempt to send email: Failure
Switching to Provider B.
Attempt to send email: Failure
Rate limit exceeded. Waiting...
Sending email 5
Attempt to send email: Failure
Switching to Provider B.
Attempt to send email: Success
Email sent successfully.
Sending email 6
Attempt to send email: Failure
Switching to Provider B.
Attempt to send email: Success
Email sent successfully.
Sending email 7
Attempt to send email: Failure
Switching to Provider B.
Attempt to send email: Success
Email sent successfully.
Sending email 8
Attempt to send email: Failure
Switching to Provider B.
Attempt to send email: Success
Email sent successfully.
Sending email 9
Attempt to send email: Success
Email sent successfully.
Sending email 10
Attempt to send email: Success
Email sent successfully.
Circuit breaker triggered. Provider temporarily unavailable.
```

## Troubleshooting

* **Issue with Rate Limiting**: If emails are sent too quickly, you may encounter rate limiting. The system will wait for the rate limit to reset before sending additional emails.
* **Circuit Breaker**: If the circuit breaker is triggered, the provider will be unavailable for a temporary period. You may need to reset the failure count or wait for some time before retrying.

