package com.jiunjiunma.manning.m2.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

public class ApiConfiguration extends Configuration {
    @JsonProperty
    private String topic;
    private int maxBodySize;
    private S3Conf s3Conf;
    private Map<String, String> kafkaSettings = new HashMap<>();

    @JsonProperty
    public int getMaxBodySize() {
        return maxBodySize;
    }

    @JsonProperty
    public void setMaxBodySize(int maxBodySize) {
        this.maxBodySize = maxBodySize;
    }

    @JsonProperty("s3")
    public S3Conf getS3Conf() {
        return s3Conf;
    }

    @JsonProperty("s3")
    public void setS3Conf(S3Conf s3Conf) {
        this.s3Conf = s3Conf;
    }

    @JsonProperty("kafka")
    public Map<String, String> getKafkaSettings() {
        return kafkaSettings;
    }

    public void setKafkaSettings(Map<String, String> kafkaSettings) {
        this.kafkaSettings = kafkaSettings;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
