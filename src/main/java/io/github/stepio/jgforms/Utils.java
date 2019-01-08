package io.github.stepio.jgforms;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import static java.lang.String.format;

/**
 * Utility methods for using within this library.
 * Do not use apache commons or other utilities as such extra dependencies may be unwanted for Android.
 *
 * @author Igor Stepanov
 */
public class Utils {

    private Utils() {
    }

    public static String encode(String text) {
        try {
            if (isEmpty(text)) {
                return text;
            }
            return URLEncoder.encode(text, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            throw new IllegalStateException("Problem constructing URL to submit a form", ex);
        }
    }

    public static boolean hasLength(CharSequence text) {
        return text != null && text.length() > 0;
    }

    public static boolean isEmpty(Object str) {
        return (str == null || "".equals(str));
    }

    public static boolean isSuccess(int httpCode) {
        return 2 == httpCode / 100;
    }

    public static String message(String message, Object... params) {
        String result = message;
        if (hasLength(message)) {
            result = format(message, params);
        }
        return result;
    }

    public static void isNotEmpty(Map map, String comment, Object... params) {
        notNull(map, comment, params);
        state(!map.isEmpty(), comment, params);
    }

    public static void notNull(Object value, String comment, Object... params) {
        state(value != null, comment, params);
    }

    public static void state(boolean flag, String comment, Object... params) {
        if (!flag) {
            throw new IllegalArgumentException(message(comment, params));
        }
    }
}
