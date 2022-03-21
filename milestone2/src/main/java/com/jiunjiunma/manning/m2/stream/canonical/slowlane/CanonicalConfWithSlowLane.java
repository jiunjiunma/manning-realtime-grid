package com.jiunjiunma.manning.m2.stream.canonical.slowlane;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiunjiunma.manning.m2.stream.canonical.dlq.CanonicalConfigurationWithDLQ;

public class CanonicalConfWithSlowLane extends CanonicalConfigurationWithDLQ {
    @JsonProperty
    private String maxDuration;
    @JsonProperty
    private String slowLane;

    public String getMaxDuration() {
        return maxDuration;
    }

    public void setMaxDuration(String maxDuration) {
        this.maxDuration = maxDuration;
    }

    public String getSlowLane() {
        return slowLane;
    }

    public void setSlowLane(String slowLane) {
        this.slowLane = slowLane;
    }
}
