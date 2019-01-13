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
