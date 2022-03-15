package com.jiunjiunma.manning.m1.api;

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

    @NotNull
    private Map<String, String> kafkaSettings = new HashMap<>();

    @NotNull
    private String topic;

    @NotNull
    private String deviceTable;

    @JsonProperty("database")
    public DataSourceFactory getDataSourceFactory() {
        return database;
    }

    @JsonProperty("database")
    public void setDataSourceFactory(DataSourceFactory dataSourceFactory) {
        this.database = dataSourceFactory;
    }

    @JsonProperty("kafka")
    public Map<String, String> getKafkaSettings() {
        return kafkaSettings;
    }

    public void setKafkaSettings(Map<String, String> kafkaSettings) {
        this.kafkaSettings = kafkaSettings;
    }

    @JsonProperty("topic")
    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @JsonProperty("deviceTable")
    public String getDeviceTable() {
        return deviceTable;
    }

    public void setDeviceTable(String deviceTable) {
        this.deviceTable = deviceTable;
    }
}
