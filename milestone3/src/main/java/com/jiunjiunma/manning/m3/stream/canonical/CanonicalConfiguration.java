package com.jiunjiunma.manning.m3.stream.canonical;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiunjiunma.manning.m3.api.S3Conf;
import com.jiunjiunma.manning.m3.stream.common.StreamConfiguration;

public class CanonicalConfiguration extends StreamConfiguration {

    @JsonProperty
    private String sink;

    @JsonProperty("s3")
    private S3Conf s3Conf;

    public String getSink() {
        return sink;
    }

    public void setSink(String sink) {
        this.sink = sink;
    }

    public S3Conf getS3Conf() {
        return s3Conf;
    }

    public void setS3Conf(S3Conf s3Conf) {
        this.s3Conf = s3Conf;
    }
}
