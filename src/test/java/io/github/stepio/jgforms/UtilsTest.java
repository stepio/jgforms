package io.github.stepio.jgforms;

import org.assertj.core.api.ThrowableAssert;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.MissingFormatArgumentException;

import static io.github.stepio.jgforms.Utils.encode;
import static io.github.stepio.jgforms.Utils.hasLength;
import static io.github.stepio.jgforms.Utils.isEmpty;
import static io.github.stepio.jgforms.Utils.isNotEmpty;
import static io.github.stepio.jgforms.Utils.isSuccess;
import static io.github.stepio.jgforms.Utils.message;
import static io.github.stepio.jgforms.Utils.notNull;
import static io.github.stepio.jgforms.Utils.state;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class UtilsTest {

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
    public void isSuccessWithCommonStatuses() {
        assertThat(isSuccess(200)).isTrue();
        assertThat(isSuccess(201)).isTrue();
        assertThat(isSuccess(204)).isTrue();
        assertThat(isSuccess(0)).isFalse();
        assertThat(isSuccess(400)).isFalse();
        assertThat(isSuccess(500)).isFalse();
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
        }).isInstanceOf(MissingFormatArgumentException.class).hasMessage("Format specifier '%s'");
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
        }).isInstanceOf(MissingFormatArgumentException.class).hasMessage("Format specifier '%s'");
    }

    @Test
    public void isNotEmptyWithParams() {
        isNotEmpty(Collections.singletonMap("key", "value"), "Valid value");
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() {
                isNotEmpty(null, "Value is null");
            }
        }).isInstanceOf(IllegalArgumentException.class).hasMessage("Value is null");
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() {
                isNotEmpty(new HashMap<>(), "Value is empty");
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
    public void stateWithParams() {
        state(true, "Valid value");
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() {
                state(false, "Value is false");
            }
        }).isInstanceOf(IllegalArgumentException.class).hasMessage("Value is false");
    }
}
