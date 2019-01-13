package io.github.stepio.jgforms.exception;

/**
 * Indicates that form was not submitted, most likely because answer for one of the mandatory questions is missing.
 * Another reason could be that data is submitted partially or with incorrect format.
 * Good example of partial data would be usage of method {@code io.github.stepio.jgforms.answer.Builder#putDate(io.github.stepio.jgforms.question.MetaData, java.util.Calendar)}
 * instead of {@code io.github.stepio.jgforms.answer.Builder#putDateTime(io.github.stepio.jgforms.question.MetaData, java.util.Calendar)} when option "Include time" is enabled.
 *
 * @author Igor Stepanov
 */
public class MissingRequiredAnswerException extends NotSubmittedException {

    public MissingRequiredAnswerException(String message) {
        super(message);
    }
}
