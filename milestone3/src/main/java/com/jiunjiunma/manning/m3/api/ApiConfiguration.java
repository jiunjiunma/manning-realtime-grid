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
}
