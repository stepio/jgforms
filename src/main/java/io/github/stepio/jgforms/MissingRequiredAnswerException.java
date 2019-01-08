package io.github.stepio.jgforms;

/**
 * Indicates that form was not submitted, most likely because answer for one of the mandatory questions is missing.
 *
 * @author Igor Stepanov
 */
public class MissingRequiredAnswerException extends NotSubmittedException {

    public MissingRequiredAnswerException(String message) {
        super(message);
    }
}
