package io.github.stepio.jgforms.answer;

import io.github.stepio.jgforms.question.MetaData;
import io.github.stepio.jgforms.test.JGForm;
import org.assertj.core.api.ThrowableAssert;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class BuilderTest {

    @Test
    public void formKeyWithParametersToUrlString() throws MalformedURLException {
        URL url = Builder.formKey("FORM_IDENTIFIER")
                .put(JGForm.USER_NAME, 1234567)
                .put(JGForm.DIRECTORY, "qwerty")
                .toUrl();
        assertThat(url.toString())
                .isEqualTo("https://docs.google.com/forms/d/e/FORM_IDENTIFIER/formResponse?entry.786688631=qwerty&entry.1464627081=1234567");
    }

    @Test
    public void formKeyWithParametersNullKey() {
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() {
                Builder.formKey("FORM_IDENTIFIER")
                        .put((MetaData) null, 1234567);
            }
        }).isInstanceOf(IllegalArgumentException.class).hasMessage("MetaData for answer is mandatory");
    }

    @Test
    public void formKeyWithParametersNullValue() {
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() {
                Builder.formKey("FORM_IDENTIFIER")
                        .put(JGForm.USER_NAME, (String) null);
            }
        }).isInstanceOf(IllegalArgumentException.class).hasMessage("Value of answer is mandatory");
    }
}
