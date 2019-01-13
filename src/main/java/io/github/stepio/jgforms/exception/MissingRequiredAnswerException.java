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
 * Indicates that form was not submitted, most likely because answer for one of the mandatory questions is missing.
 * Another reason could be that data is submitted partially or with incorrect format.
 * Good example of partial data would be usage of method {@link io.github.stepio.jgforms.answer.Builder#putDate(io.github.stepio.jgforms.question.MetaData, java.util.Calendar)}
 * instead of {@link io.github.stepio.jgforms.answer.Builder#putDateTime(io.github.stepio.jgforms.question.MetaData, java.util.Calendar)} when option "Include time" is enabled.
 *
 * @author Igor Stepanov
 */
public class MissingRequiredAnswerException extends NotSubmittedException {

    public MissingRequiredAnswerException(String message) {
        super(message);
    }
}
