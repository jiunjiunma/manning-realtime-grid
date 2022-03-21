package com.jiunjiunma.manning.m2.stream.storage;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiunjiunma.manning.m2.stream.common.StreamConfiguration;
import io.dropwizard.db.DataSourceFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class StorageConfiguration extends StreamConfiguration {
    @Valid
    @NotNull
    private DataSourceFactory database = new DataSourceFactory();

    @NotNull
    @JsonProperty
    private String deviceTable;

    @JsonProperty("database")
    public DataSourceFactory getDataSourceFactory() {
        return database;
    }

    @JsonProperty("database")
    public void setDataSourceFactory(DataSourceFactory dataSourceFactory) {
        this.database = dataSourceFactory;
    }

    public String getDeviceTable() {
        return deviceTable;
    }

    public void setDeviceTable(String deviceTable) {
        this.deviceTable = deviceTable;
    }

}
