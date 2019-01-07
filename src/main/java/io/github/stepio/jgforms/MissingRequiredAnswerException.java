package io.github.stepio.jgforms;

public class MissingRequiredAnswerException extends NotSubmittedException {

    public MissingRequiredAnswerException(String message) {
        super(message);
    }
}
