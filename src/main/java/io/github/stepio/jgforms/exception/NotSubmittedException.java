package io.github.stepio.jgforms.exception;

/**
 * Indicates that for some reason form was not (or might not be) submitted.
 *
 * @author Igor Stepanov
 */
public class NotSubmittedException extends RuntimeException {

    public NotSubmittedException(String message) {
        super(message);
    }

    public NotSubmittedException(String message, Throwable cause) {
        super(message, cause);
    }
}
