package io.github.stepio.jgforms;

import java.util.concurrent.TimeUnit;

/**
 * Holder for project-specific configurations, allows overriding default values.
 *
 * @author Igor Stepanov
 */
public class Configuration {

    private long connectTimeout;
    private long readTimeout;

    public Configuration() {
        // default timeout 5 seconds
        this.connectTimeout = TimeUnit.SECONDS.toMillis(5L);
        this.readTimeout = TimeUnit.SECONDS.toMillis(5L);
    }

    /**
     * Returns setting for connect timeout.
     * @return the connect timeout value in milliseconds
     * @see java.net.HttpURLConnection#getConnectTimeout()
     */
    public long getConnectTimeout() {
        return connectTimeout;
    }

    /**
     * Sets a specified timeout value, in milliseconds, to be used when opening a communications link to the resource referenced by this URLConnection.
     * @param connectTimeout the connect timeout value in milliseconds
     * @see java.net.HttpURLConnection#setConnectTimeout(int)
     */
    public void setConnectTimeout(long connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    /**
     * Returns setting for read timeout.
     * @return the read timeout value in milliseconds
     * @see java.net.HttpURLConnection#getReadTimeout()
     */
    public long getReadTimeout() {
        return readTimeout;
    }

    /**
     * Sets the read timeout to a specified timeout, in milliseconds.
     * @param readTimeout the timeout value to be used in milliseconds
     * @see java.net.HttpURLConnection#setReadTimeout(int)
     */
    public void setReadTimeout(long readTimeout) {
        this.readTimeout = readTimeout;
    }
}
