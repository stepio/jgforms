/*
 * Copyright (C) 2018 - 2019 Igor Stepanov. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.stepio.jgforms;

import io.github.stepio.jgforms.answer.Builder;
import io.github.stepio.jgforms.exception.InvalidFormException;
import io.github.stepio.jgforms.exception.MissingRequiredAnswerException;
import io.github.stepio.jgforms.exception.NotSubmittedException;
import io.github.stepio.jgforms.test.JGForm;
import org.assertj.core.api.ThrowableAssert;
import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;

import static io.github.stepio.jgforms.Submitter.isSuccess;
import static java.lang.System.getProperty;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SubmitterTest {

    private Submitter invalid;
    private Submitter submitter;

    @Before
    public void setUp() {
        Configuration configuration = new Configuration();
        configuration.setConnectTimeout(-1);
        configuration.setReadTimeout(-1);
        this.invalid = new Submitter(configuration);
        this.submitter = new Submitter(
                new Configuration()
        );
    }

    @Test
    public void submitFormExpectSuccess() throws MalformedURLException {
        Calendar date = Calendar.getInstance();
        Calendar from = Calendar.getInstance();
        from.set(date.get(Calendar.YEAR) - 1, date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH), 1, 2, 3);
        URL url = Builder.formKey("1FAIpQLScahJirT2sVrm0qDveeuiO1oZBJ5B7J0gdeI7UAZGohKEmi9g")
                .put(JGForm.USER_NAME, getProperty("user.name", "unknown"))
                .put(JGForm.DIRECTORY, getProperty("user.dir", "unknown"))
                .putDateTime(JGForm.CREATION_DATE, date)
                .putDuration(JGForm.SAMPLE_DURATION, from, date)
                .toUrl();
        this.submitter.submitForm(url);
    }

    @Test
    public void submitFormExpectNotSubmittedException() throws MalformedURLException {
        final URL url = Builder.formKey("dummy123456789qwerty")
                .put(JGForm.USER_NAME, "dummy")
                .toUrl();
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() {
                SubmitterTest.this.invalid.submitForm(url);
            }
        }).isInstanceOf(NotSubmittedException.class).hasMessage("Failed to submit form, unexpected exception");
    }

    @Test
    public void submitFormExpectInvalidFormException() throws MalformedURLException {
        final URL url = Builder.formKey("dummy123456789qwerty")
                .put(JGForm.USER_NAME, "dummy")
                .toUrl();
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() {
                SubmitterTest.this.submitter.submitForm(url);
            }
        }).isInstanceOf(InvalidFormException.class).hasMessage("Form's URL is invalid, probably invalid key");
    }

    @Test
    public void submitFormExpectMissingRequiredAnswerException() throws MalformedURLException {
        final URL url = Builder.formKey("1FAIpQLScahJirT2sVrm0qDveeuiO1oZBJ5B7J0gdeI7UAZGohKEmi9g")
                .put(JGForm.DIRECTORY, getProperty("user.dir", "unknown"))
                .toUrl();
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() {
                SubmitterTest.this.submitter.submitForm(url);
            }
        }).isInstanceOf(MissingRequiredAnswerException.class)
                .hasMessage("One or more required question is not answered or specified value is incorrect");
    }

    @Test
    public void isSuccessWithCommonStatuses() {
        assertThat(isSuccess(200)).isTrue();
        assertThat(isSuccess(201)).isTrue();
        assertThat(isSuccess(204)).isTrue();
        assertThat(isSuccess(0)).isFalse();
        assertThat(isSuccess(400)).isFalse();
        assertThat(isSuccess(500)).isFalse();
    }
}
