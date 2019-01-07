package io.github.stepio.jgforms;

import java.util.concurrent.TimeUnit;

public class Configuration {

    private long connectTimeout;
    private long readTimeout;

    public Configuration() {
        // default timeout 5 seconds
        this.connectTimeout = TimeUnit.SECONDS.toMillis(5L);
        this.readTimeout = TimeUnit.SECONDS.toMillis(5L);
    }

    public long getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(long connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public long getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(long readTimeout) {
        this.readTimeout = readTimeout;
    }
}
