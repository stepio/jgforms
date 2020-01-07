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

import org.assertj.core.api.ThrowableAssert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.MissingFormatArgumentException;

import static io.github.stepio.jgforms.answer.Validation.encode;
import static io.github.stepio.jgforms.answer.Validation.hasLength;
import static io.github.stepio.jgforms.answer.Validation.isEmpty;
import static io.github.stepio.jgforms.answer.Validation.isTrue;
import static io.github.stepio.jgforms.answer.Validation.message;
import static io.github.stepio.jgforms.answer.Validation.notEmpty;
import static io.github.stepio.jgforms.answer.Validation.notNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ValidationTest {

    @Test
    public void encodeWithString() {
        assertThat(encode(null)).isNull();
        assertThat(encode("")).isEmpty();
        assertThat(encode(":-)")).isEqualTo("%3A-%29");
        assertThat(encode("Hello,\nworld!")).isEqualTo("Hello%2C%0Aworld%21");
        assertThat(encode("Hello,\r\nworld!")).isEqualTo("Hello%2C%0D%0Aworld%21");
    }

    @Test
    public void hasLengthWithString() {
        assertThat(hasLength(null)).isFalse();
        assertThat(hasLength("")).isFalse();
        assertThat(hasLength(" ")).isTrue();
        assertThat(hasLength("qwerty")).isTrue();
        assertThat(hasLength("123")).isTrue();
    }

    @Test
    public void isEmptyWithString() {
        assertThat(isEmpty(null)).isTrue();
        assertThat(isEmpty("")).isTrue();
        assertThat(isEmpty(" ")).isFalse();
        assertThat(isEmpty("qwerty")).isFalse();
        assertThat(isEmpty("123")).isFalse();
    }

    @Test
    public void messageNoParams() {
        assertThat(message(null)).isEqualTo(null);
        assertThat(message("")).isEqualTo("");
        assertThat(message("Qwerty123")).isEqualTo("Qwerty123");
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() {
                message("Text %s");
            }
        }).isInstanceOf(MissingFormatArgumentException.class).hasMessageStartingWith("Format specifier");
    }

    @Test
    public void messageWithParams() {
        assertThat(message("Qwerty123", "value")).isEqualTo("Qwerty123");
        assertThat(message("Text %s", "value")).isEqualTo("Text value");
        assertThat(message("Text %s %s", "value1", "value2")).isEqualTo("Text value1 value2");
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() {
                message("Text %s %s", "value");
            }
        }).isInstanceOf(MissingFormatArgumentException.class).hasMessageStartingWith("Format specifier");
    }

    @Test
    public void notEmptyMapWithParams() {
        notEmpty(Collections.singletonMap("key", "value"), "Valid value");
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() {
                notEmpty((Map) null, "Value is null");
            }
        }).isInstanceOf(IllegalArgumentException.class).hasMessage("Value is null");
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() {
                notEmpty(new HashMap<>(), "Value is empty");
            }
        }).isInstanceOf(IllegalArgumentException.class).hasMessage("Value is empty");
    }

    @Test
    public void notEmptyCollectionWithParams() {
        notEmpty(Collections.singletonList("value"), "Valid value");
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() {
                notEmpty((List) null, "Value is null");
            }
        }).isInstanceOf(IllegalArgumentException.class).hasMessage("Value is null");
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() {
                notEmpty(new ArrayList(), "Value is empty");
            }
        }).isInstanceOf(IllegalArgumentException.class).hasMessage("Value is empty");
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() {
                notEmpty(new LinkedList(), "Value is empty");
            }
        }).isInstanceOf(IllegalArgumentException.class).hasMessage("Value is empty");
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() {
                notEmpty(new HashSet(), "Value is empty");
            }
        }).isInstanceOf(IllegalArgumentException.class).hasMessage("Value is empty");
    }

    @Test
    public void notNullWithParams() {
        notNull(new Object(), "Valid value");
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() {
                notNull(null, "Value is null");
            }
        }).isInstanceOf(IllegalArgumentException.class).hasMessage("Value is null");
    }

    @Test
    public void isTrueWithParams() {
        isTrue(true, "Valid value");
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() {
                isTrue(false, "Value is false");
            }
        }).isInstanceOf(IllegalArgumentException.class).hasMessage("Value is false");
    }
}
