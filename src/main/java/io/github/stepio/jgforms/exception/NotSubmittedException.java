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

package io.github.stepio.jgforms.exception;

/**
 * Indicates that for some reason form was not (or might not be) submitted.
 *
 * @author Igor Stepanov
 */
public class NotSubmittedException extends RuntimeException {

    public NotSubmittedException(String message) {
        super(message);
    }

    public NotSubmittedException(String message, Throwable cause) {
        super(message, cause);
    }
}
