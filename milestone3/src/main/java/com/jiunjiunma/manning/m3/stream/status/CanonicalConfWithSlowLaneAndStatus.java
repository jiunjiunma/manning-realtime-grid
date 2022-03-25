package com.jiunjiunma.manning.m3.stream.status;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiunjiunma.manning.m3.stream.canonical.CanonicalConfiguration;

public class CanonicalConfWithSlowLaneAndStatus extends CanonicalConfiguration {
    @JsonProperty
    private String maxDuration;
    @JsonProperty
    private String slowLane;
    @JsonProperty("dlq")
    private String dlq;

    private String statusTopic;

    public String getDLQ() {
        return dlq;
    }

    public void setDLQ(String dlq) {
        this.dlq = dlq;
    }

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

    public String getStatusTopic() {
        return statusTopic;
    }

    public void setStatusTopic(String statusTopic) {
        this.statusTopic = statusTopic;
    }
}
