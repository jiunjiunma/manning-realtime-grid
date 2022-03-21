package com.jiunjiunma.manning.m2.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

public class S3Conf extends Configuration {
    @JsonProperty
    private String bucket;
    @JsonProperty
    private String endpoint;

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }
}
