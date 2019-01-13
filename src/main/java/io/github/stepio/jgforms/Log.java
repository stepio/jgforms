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
