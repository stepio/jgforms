package io.github.stepio.jgforms;

/**
 * Indicates that form was not submitted, most probably because invalid form key was specified.
 *
 * @author Igor Stepanov
 */
public class InvalidFormException extends NotSubmittedException {

    public InvalidFormException(String message) {
        super(message);
    }
}
