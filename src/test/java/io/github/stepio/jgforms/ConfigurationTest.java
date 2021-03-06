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

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ConfigurationTest {

    private Configuration configuration;

    @Before
    public void setUp() {
        this.configuration = new Configuration();
    }

    @Test
    public void getConnectTimeoutEqualsDefault() {
        assertThat(this.configuration.getConnectTimeout()).isEqualTo(5000L);
    }

    @Test
    public void getConnectTimeoutEqualsSet() {
        this.configuration.setConnectTimeout(123L);
        assertThat(this.configuration.getConnectTimeout()).isEqualTo(123L);
    }

    @Test
    public void getReadTimeoutEqualsDefault() {
        assertThat(this.configuration.getReadTimeout()).isEqualTo(5000L);
    }

    @Test
    public void getReadTimeoutEqualsSet() {
        this.configuration.setReadTimeout(456L);
        assertThat(this.configuration.getReadTimeout()).isEqualTo(456L);
    }
}
