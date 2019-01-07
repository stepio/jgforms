package io.github.stepio.jgforms;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static io.github.stepio.jgforms.Utils.isSuccess;

public class Submitter {

    private static final Log LOG = Log.getLogger(Submitter.class);

    protected Configuration configuration;

    public Submitter(Configuration configuration) {
        this.configuration = configuration;
    }

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
                    throw new MissingRequiredAnswerException("One or more required question is not answered");
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
}
