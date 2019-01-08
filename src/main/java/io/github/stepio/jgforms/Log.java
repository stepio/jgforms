package io.github.stepio.jgforms;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Wrapper for Java-native logger.
 * Can be easily removed with ProGuard if necessary.
 *
 * @author Igor Stepanov
 */
public class Log {

    private Logger logger;

    private Log(String name) {
        logger = Logger.getLogger(name);
    }

    public void error(String comment, Throwable ex) {
        logger.log(Level.SEVERE, comment, ex);
    }

    public static Log getLogger(String name) {
        return new Log(name);
    }

    public static Log getLogger(Class<?> clazz) {
        return getLogger(clazz.getName());
    }
}
