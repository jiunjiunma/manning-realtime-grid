package com.jiunjiunma.manning.m3.stream.storage;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiunjiunma.manning.m3.stream.common.StreamConfiguration;
import io.dropwizard.db.DataSourceFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class MetaStorageConfiguration extends StreamConfiguration {
    @JsonProperty
    private String metaTopic;
    @JsonProperty
    private String metaTable;
    @JsonProperty
    private String statusTopic;
    @JsonProperty
    private String statusTable;
    @Valid
    @NotNull
    private DataSourceFactory database = new DataSourceFactory();

    @JsonProperty("database")
    public DataSourceFactory getDataSourceFactory() {
        return database;
    }

    @JsonProperty("database")
    public void setDataSourceFactory(DataSourceFactory dataSourceFactory) {
        this.database = dataSourceFactory;
    }

    public String getMetaTopic() {
        return metaTopic;
    }

    public void setMetaTopic(String metaTopic) {
        this.metaTopic = metaTopic;
    }

    public String getMetaTable() {
        return metaTable;
    }

    public void setMetaTable(String metaTable) {
        this.metaTable = metaTable;
    }

    public String getStatusTopic() {
        return statusTopic;
    }

    public void setStatusTopic(String statusTopic) {
        this.statusTopic = statusTopic;
    }

    public String getStatusTable() {
        return statusTable;
    }

    public void setStatusTable(String statusTable) {
        this.statusTable = statusTable;
    }
}
