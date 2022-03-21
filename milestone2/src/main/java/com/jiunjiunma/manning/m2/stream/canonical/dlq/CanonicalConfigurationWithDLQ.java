package com.jiunjiunma.manning.m2.stream.canonical.dlq;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiunjiunma.manning.m2.stream.canonical.CanonicalConfiguration;

public class CanonicalConfigurationWithDLQ extends CanonicalConfiguration {
    @JsonProperty("dlq")
    private String dlq;

    public String getDLQ() {
        return dlq;
    }

    public void setDLQ(String dlq) {
        this.dlq = dlq;
    }
}
