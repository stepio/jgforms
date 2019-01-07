package io.github.stepio.jgforms;

public class NotSubmittedException extends RuntimeException {

    public NotSubmittedException(String message) {
        super(message);
    }

    public NotSubmittedException(String message, Throwable cause) {
        super(message, cause);
    }
}
