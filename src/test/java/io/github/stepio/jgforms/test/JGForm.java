package io.github.stepio.jgforms.test;

import io.github.stepio.jgforms.question.MetaData;

public enum JGForm implements MetaData {

    USER_NAME(1464627081L),
    DIRECTORY(786688631L),
    CREATION_DATE(1117050788L),
    SAMPLE_DURATION(1536399354L);

    private long id;

    JGForm(long id) {
        this.id = id;
    }

    @Override
    public long getId() {
        return this.id;
    }
}
