package io.github.stepio.jgforms;

import org.assertj.core.api.ThrowableAssert;
import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static java.lang.System.getProperty;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SubmitterTest {

    private Submitter submitter;

    @Before
    public void setUp() {
        Configuration configuration = new Configuration();
        this.submitter = new Submitter(configuration);
    }

    @Test
    public void submitFormExpectSuccess() throws MalformedURLException {
        URL url = AnswerBuilder.formKey("1FAIpQLScahJirT2sVrm0qDveeuiO1oZBJ5B7J0gdeI7UAZGohKEmi9g")
                .put(1464627081L, getProperty("user.name", "unknown"))
                .put(786688631L, getProperty("user.dir", "unknown"))
                .toUrl();
        this.submitter.submitForm(url);
    }

    @Test
    public void submitFormExpectNoForm() throws MalformedURLException {
        final URL url = AnswerBuilder.formKey("dummy123456789qwerty")
                .put(123L, "dummy")
                .toUrl();
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() {
                SubmitterTest.this.submitter.submitForm(url);
            }
        }).isInstanceOf(InvalidFormException.class).hasMessage("Form's URL is invalid, probably invalid key");
    }

    @Test
    public void submitFormExpectMissingAnswer() throws MalformedURLException {
        final URL url = AnswerBuilder.formKey("1FAIpQLScahJirT2sVrm0qDveeuiO1oZBJ5B7J0gdeI7UAZGohKEmi9g")
                .put(786688631L, getProperty("user.dir", "unknown"))
                .toUrl();
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() {
                SubmitterTest.this.submitter.submitForm(url);
            }
        }).isInstanceOf(MissingRequiredAnswerException.class).hasMessage("One or more required question is not answered");
    }
}
