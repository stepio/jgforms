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
