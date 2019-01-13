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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static io.github.stepio.jgforms.answer.Validation.encode;
import static io.github.stepio.jgforms.answer.Validation.isNotEmpty;
import static io.github.stepio.jgforms.answer.Validation.notNull;
import static java.lang.String.format;
import static java.util.Collections.singletonList;

/**
 * Builds request URL for a Google Form.
 *
 * @author Igor Stepanov
 */
public class Builder {

    private static final String GOOGLE_FORM_TEMPLATE = "https://docs.google.com/forms/d/e/%s/formResponse";
    private static final String QUESTION_PARAM_FORMAT = "entry.%d";
    private static final String QUESTION_PARAM_YEAR = "_year";
    private static final String QUESTION_PARAM_MONTH = "_month";
    private static final String QUESTION_PARAM_DAY = "_day";
    private static final String QUESTION_PARAM_HOUR = "_hour";
    private static final String QUESTION_PARAM_MINUTE = "_minute";
    private static final String QUESTION_PARAM_SECOND = "_second";

    private static final String ANSWER_REQUIRED = "At least one answer is mandatory to submit a form";
    private static final String ANSWER_MANDATORY = "Value of answer is mandatory";
    private static final String ANSWERS_ALL_MANDATORY = "All values for answer are mandatory";
    private static final String META_DATA_MANDATORY = "MetaData for answer is mandatory";
    private static final String TIME_UNIT_MANDATORY = "TimeUnit for answer is mandatory";

    private String key;
    private Map<String, List<String>> answers;

    private Builder(String key) {
        this.key = key;
        this.answers = new HashMap<>();
    }

    /**
     * Submit answer for the question of type "Date" with options "Include year" and "Include time" enabled.
     * @param metaData the question in the form, holds it's numeric identifier
     * @param value the answer for the question about specific date
     * @return current builder for populating form's data
     */
    public Builder putDateTime(MetaData metaData, Calendar value) {
        notNull(metaData, META_DATA_MANDATORY);
        notNull(value, ANSWER_MANDATORY);
        putDate(metaData, value);
        putTime(metaData, value);
        return this;
    }

    /**
     * Submit answer for the question of type "Date" with option "Include time" disabled.
     * @param metaData the question in the form, holds it's numeric identifier
     * @param value the answer for the question about specific date
     * @return current builder for populating form's data
     */
    public Builder putDate(MetaData metaData, Calendar value) {
        notNull(metaData, META_DATA_MANDATORY);
        notNull(value, ANSWER_MANDATORY);
        put(toQuestionParam(metaData) + QUESTION_PARAM_YEAR, value.get(Calendar.YEAR));
        putDate(metaData, value.get(Calendar.MONTH) + 1, value.get(Calendar.DAY_OF_MONTH));
        return this;
    }

    /**
     * Submit answer for the question of type "Date" with option "Include year" disabled.
     * @param metaData the question in the form, holds it's numeric identifier
     * @param months number of months to answer the question about specific date
     * @param days number of days to answer the question about specific date
     * @return current builder for populating form's data
     */
    public Builder putDate(MetaData metaData, int months, int days) {
        notNull(metaData, META_DATA_MANDATORY);
        String questionKey = toQuestionParam(metaData);
        put(questionKey + QUESTION_PARAM_MONTH, months);
        put(questionKey + QUESTION_PARAM_DAY, days);
        return this;
    }

    /**
     * Submit answer for the question of type "Time" with option "Duration" enabled.
     * @param metaData the question in the form, holds it's numeric identifier
     * @param start date representing initial point of the interval
     * @param finish date representing final point of the interval
     * @return current builder for populating form's data
     */
    public Builder putDuration(MetaData metaData, Calendar start, Calendar finish) {
        notNull(metaData, META_DATA_MANDATORY);
        notNull(start, ANSWERS_ALL_MANDATORY);
        notNull(finish, ANSWERS_ALL_MANDATORY);
        return putDuration(metaData, finish.getTimeInMillis() - start.getTimeInMillis(), TimeUnit.MILLISECONDS);
    }

    /**
     * Submit answer for the question of type "Time" with option "Duration" enabled.
     * @param metaData the question in the form, holds it's numeric identifier
     * @param duration length of the time interval to submit
     * @param timeUnit units, representing current timed interval
     * @return current builder for populating form's data
     */
    public Builder putDuration(MetaData metaData, long duration, TimeUnit timeUnit) {
        notNull(metaData, META_DATA_MANDATORY);
        notNull(timeUnit, TIME_UNIT_MANDATORY);
        long value = timeUnit.toSeconds(duration);
        int seconds = (int) value % 60;
        value /= 60;
        int minutes = (int) value % 60;
        value /= 60;
        int hours = (int) value % 60;
        return putDuration(metaData, hours, minutes, seconds);
    }

    /**
     * Submit answer for the question of type "Time" with option "Duration" enabled.
     * @param metaData the question in the form, holds it's numeric identifier
     * @param hours number of hours to answer the question about specific time duration
     * @param minutes number of minutes to answer the question about specific time duration
     * @param seconds number of seconds to answer the question about specific time duration
     * @return current builder for populating form's data
     */
    public Builder putDuration(MetaData metaData, int hours, int minutes, int seconds) {
        notNull(metaData, META_DATA_MANDATORY);
        String questionKey = toQuestionParam(metaData);
        put(questionKey + QUESTION_PARAM_HOUR, hours);
        put(questionKey + QUESTION_PARAM_MINUTE, minutes);
        put(questionKey + QUESTION_PARAM_SECOND, seconds);
        return this;
    }

    /**
     * Submit answer for the question of type "Time" with option "Time" enabled.
     * @param metaData the question in the form, holds it's numeric identifier
     * @param value the answer for the question about specific time
     * @return current builder for populating form's data
     */
    public Builder putTime(MetaData metaData, Calendar value) {
        notNull(metaData, META_DATA_MANDATORY);
        notNull(value, ANSWER_MANDATORY);
        String questionKey = toQuestionParam(metaData);
        put(questionKey + QUESTION_PARAM_HOUR, value.get(Calendar.HOUR_OF_DAY));
        put(questionKey + QUESTION_PARAM_MINUTE, value.get(Calendar.MINUTE));
        return this;
    }

    /**
     * Submit answer for the question of types "Short answer" or "Paragraph".
     * @param metaData the question in the form, holds it's numeric identifier
     * @param value the answer for the question, to be converted into {@link String}
     * @return current builder for populating form's data
     */
    public Builder put(MetaData metaData, Number value) {
        notNull(metaData, META_DATA_MANDATORY);
        notNull(value, ANSWER_MANDATORY);
        return put(toQuestionParam(metaData), value);
    }

    /**
     * Submit answer for the question of types "Short answer" or "Paragraph".
     * @param metaData the question in the form, holds it's numeric identifier
     * @param value the answer for the question
     * @return current builder for populating form's data
     */
    public Builder put(MetaData metaData, String value) {
        notNull(metaData, META_DATA_MANDATORY);
        notNull(value, ANSWER_MANDATORY);
        return put(toQuestionParam(metaData), value);
    }

    protected Builder put(String questionKey, Number value) {
        return put(questionKey, String.valueOf(value));
    }

    protected Builder put(String questionKey, String value) {
        this.answers.put(questionKey, singletonList(value));
        return this;
    }

    /**
     * Build {@link String} representation for the {@code URL} to the Google Form using previously populated data.
     * @return constructed textual {@code URL} form
     */
    public String toUrlString() {
        isNotEmpty(this.answers, ANSWER_REQUIRED);
        StringBuilder template = new StringBuilder();
        template.append(format(GOOGLE_FORM_TEMPLATE, Builder.this.key))
                .append('?');
        boolean first = true;
        for (Map.Entry<String, List<String>> entry : this.answers.entrySet()) {
            for (String value : entry.getValue()) {
                if (first) {
                    first = false;
                } else {
                    template.append('&');
                }
                template.append(entry.getKey())
                        .append('=')
                        .append(encode(value));
            }
        }
        return template.toString();
    }

    /**
     * Build {@link URL} to the Google Form using previously populated data.
     * @return constructed {@code URL}
     * @throws MalformedURLException
     *          If incorrect data specified as form's key or specific parameters
     */
    public URL toUrl() throws MalformedURLException {
        return new URL(toUrlString());
    }

    private static String toQuestionParam(MetaData questionKey) {
        return format(QUESTION_PARAM_FORMAT, questionKey.getId());
    }

    /**
     * Prepare {@code Builder} instance for the given identifier of a Google Form to populate data and prepare it for submission.
     * @param key identifier of the specific Google Form to fill
     * @return current builder for populating form's data
     */
    public static Builder formKey(String key) {
        return new Builder(key);
    }
}
