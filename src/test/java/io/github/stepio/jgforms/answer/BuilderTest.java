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

package io.github.stepio.jgforms.answer;

import io.github.stepio.jgforms.question.MetaData;
import io.github.stepio.jgforms.test.JGForm;
import org.assertj.core.api.ThrowableAssert;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class BuilderTest {

    @Test
    public void formKeyWithParametersToUrlString() throws MalformedURLException {
        URL url = Builder.formKey("FORM_IDENTIFIER")
                .put(JGForm.USER_NAME, 1234567)
                .put(JGForm.DIRECTORY, "qwerty")
                .put(JGForm.CHOICES, Collections.singletonList("Second"))
                .toUrl();
        assertThat(url.toString())
                .isEqualTo("https://docs.google.com/forms/d/e/FORM_IDENTIFIER/formResponse?" +
                        "entry.786688631=qwerty&entry.176659521=Second&entry.1464627081=1234567");
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
