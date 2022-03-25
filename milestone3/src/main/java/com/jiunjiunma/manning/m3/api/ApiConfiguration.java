package com.jiunjiunma.manning.m3.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

public class ApiConfiguration extends Configuration {

    @Valid
    @NotNull
    private DataSourceFactory database = new DataSourceFactory();

    @JsonProperty
    private String metaTable;

    @JsonProperty
    private String statusTable;

    @JsonProperty
    private String topic;
    private int maxBodySize;
    private S3Conf s3Conf;
    private Map<String, String> kafkaSettings = new HashMap<>();

    @JsonProperty("database")
    public DataSourceFactory getDataSourceFactory() {
        return database;
    }

    @JsonProperty("database")
    public void setDataSourceFactory(DataSourceFactory dataSourceFactory) {
        this.database = dataSourceFactory;
    }

    public String getMetaTable() {
        return metaTable;
    }

    public void setMetaTable(String metaTable) {
        this.metaTable = metaTable;
    }

    public String getStatusTable() {
        return statusTable;
    }

    public void setStatusTable(String statusTable) {
        this.statusTable = statusTable;
    }

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
