package com.jiunjiunma.manning.m2.stream.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

public class StreamConfiguration extends Configuration {
    @NotNull
    protected Map<String, String> kafkaSettings = new HashMap<>();
    @NotNull

    @JsonProperty
    protected String source;

    @JsonProperty("kafka")
    public Map<String, String> getKafkaSettings() {
        return kafkaSettings;
    }

    @JsonProperty("kafka")
    public void setKafkaSettings(Map<String, String> kafkaSettings) {
        this.kafkaSettings = kafkaSettings;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
