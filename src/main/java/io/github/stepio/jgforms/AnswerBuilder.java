package io.github.stepio.jgforms;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static io.github.stepio.jgforms.Utils.encode;
import static io.github.stepio.jgforms.Utils.isNotEmpty;
import static io.github.stepio.jgforms.Utils.notNull;
import static java.lang.String.format;

public class AnswerBuilder {

    static final String GOOGLE_FORM_TEMPLATE = "https://docs.google.com/forms/d/e/%s/formResponse";
    static final String QUESTION_PARAM_FORMAT = "entry.%d";

    private String key;
    private Map<String, String> answers;

    public AnswerBuilder(String key) {
        this.key = key;
        this.answers = new HashMap<>();
    }

    public AnswerBuilder put(Long questionId, Object value) {
        notNull(questionId, "QuestionId cannot be null");
        notNull(value, "Answer for %d cannot be null", questionId);
        return put(questionId, String.valueOf(value));
    }

    public AnswerBuilder put(Long questionId, String value) {
        notNull(questionId, "QuestionId cannot be null");
        notNull(value, "Answer for %d cannot be null", questionId);
        return put(toQuestionParam(questionId), String.valueOf(value));
    }

    public AnswerBuilder put(String questionKey, String value) {
        notNull(questionKey, "QuestionKey cannot be null");
        notNull(value, "Answer for %s cannot be null", questionKey);
        this.answers.put(questionKey, value);
        return this;
    }

    public String toUrlString() {
        isNotEmpty(this.answers, "At least one answer is mandatory to submit a form");
        StringBuilder template = new StringBuilder();
        template.append(format(GOOGLE_FORM_TEMPLATE, AnswerBuilder.this.key))
                .append('?');
        boolean first = true;
        for (Map.Entry<String, String> entry : this.answers.entrySet()) {
            if (first) {
                first = false;
            } else {
                template.append('&');
            }
            template.append(entry.getKey())
                    .append('=')
                    .append(encode(entry.getValue()));
        }
        return template.toString();
    }

    public URL toUrl() throws MalformedURLException {
        return new URL(toUrlString());
    }

    static String toQuestionParam(Long questionId) {
        notNull(questionId, "QuestionId cannot be null");
        return format(QUESTION_PARAM_FORMAT, questionId);
    }

    public static AnswerBuilder formKey(String key) {
        return new AnswerBuilder(key);
    }
}
