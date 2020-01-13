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

package io.github.stepio.jgforms.test;

import io.github.stepio.jgforms.question.MetaData;

public enum JGForm implements MetaData {

    USER_NAME(1464627081L),
    DIRECTORY(786688631L),
    CREATION_DATE(1117050788L),
    SAMPLE_DURATION(1536399354L),
    CHOICES(176659521L);

    private long id;

    JGForm(long id) {
        this.id = id;
    }

    @Override
    public long getId() {
        return this.id;
    }
}
