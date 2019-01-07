package io.github.stepio.jgforms;

import org.junit.Before;
import org.junit.Test;

public class LogTest {

    private Log log;

    @Before
    public void setUp() {
        this.log = Log.getLogger(LogTest.class);
    }

    @Test
    public void errorCausesNoException() {
        this.log.error("dummy", new RuntimeException("message"));
    }
}
