package io.github.stepio.jgforms;

import org.assertj.core.api.ThrowableAssert;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AnswerBuilderTest {

    @Test
    public void formKeyWithParametersToUrlString() throws MalformedURLException {
        URL url = AnswerBuilder.formKey("FORM_IDENTIFIER")
                .put(1464627081L, 1234567)
                .put(786688631L, "qwerty")
                .toUrl();
        assertThat(url.toString())
                .isEqualTo("https://docs.google.com/forms/d/e/FORM_IDENTIFIER/formResponse?entry.786688631=qwerty&entry.1464627081=1234567");
    }

    @Test
    public void formKeyWithParametersNullKey() {
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() {
                AnswerBuilder.formKey("FORM_IDENTIFIER")
                        .put(null, 1234567);
            }
        }).isInstanceOf(IllegalArgumentException.class).hasMessage("QuestionId cannot be null");
    }

    @Test
    public void formKeyWithParametersNullValue() {
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() {
                AnswerBuilder.formKey("FORM_IDENTIFIER")
                        .put("entry.123", null);
            }
        }).isInstanceOf(IllegalArgumentException.class).hasMessage("Answer for entry.123 cannot be null");
    }
}
