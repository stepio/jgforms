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

import io.github.stepio.jgforms.exception.InvalidFormException;
import io.github.stepio.jgforms.exception.MissingRequiredAnswerException;
import io.github.stepio.jgforms.exception.NotSubmittedException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Submits Google Form data using Java native {@code HttpURLConnection}.
 *
 * @author Igor Stepanov
 */
public class Submitter {

    private static final Log LOG = Log.getLogger(Submitter.class);

    protected Configuration configuration;

    public Submitter(Configuration configuration) {
        this.configuration = configuration;
    }

    /**
     * Submit specified data to the Google Form.
     * @param formAnswersUrl the URL of the Google's Form with pre-filled data
     * @throws InvalidFormException if form's URL is invalid, most probably because of the wrong key
     * @throws MissingRequiredAnswerException if at least one of the required parameters is missing or incorrect value is specified
     * @throws NotSubmittedException if unexpected problem occurred (unexpected response code or {@link RuntimeException})
     */
    public void submitForm(URL formAnswersUrl) {
        HttpURLConnection connection = null;
        int statusCode;
        try {
            connection = (HttpURLConnection) formAnswersUrl.openConnection();
            connection.setConnectTimeout((int) this.configuration.getConnectTimeout());
            connection.setReadTimeout((int) this.configuration.getReadTimeout());
            statusCode = connection.getResponseCode();
        } catch (IOException | RuntimeException ex) {
            throw new NotSubmittedException("Failed to submit form, unexpected exception", ex);
        } finally {
            close(connection);
        }
        if (!isSuccess(statusCode)) {
            switch (statusCode) {
                case 400:
                    throw new MissingRequiredAnswerException("One or more required question is not answered or specified value is incorrect");
                case 404:
                    throw new InvalidFormException("Form's URL is invalid, probably invalid key");
                default:
                    throw new NotSubmittedException("Failed to submit form, unexpected status code");
            }
        }
    }

    protected void close(HttpURLConnection connection) {
        if (connection != null) {
            try {
                connection.disconnect();
            } catch (RuntimeException ex) {
                LOG.error("Failed to disconnect", ex);
            }
        }
    }

    protected static boolean isSuccess(int httpCode) {
        return 2 == httpCode / 100;
    }
}
