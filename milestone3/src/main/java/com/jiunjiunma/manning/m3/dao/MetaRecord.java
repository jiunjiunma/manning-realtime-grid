package com.jiunjiunma.manning.m3.dao;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class MetaRecord {
    @JsonProperty
    private String deviceId;
    @JsonProperty
    private long arrivalTime;
    @JsonProperty
    private long metaParseTime;
    @JsonProperty
    private int numEvents;

    public long getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(long arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public long getMetaParseTime() {
        return metaParseTime;
    }

    public void setMetaParseTime(long metaParseTime) {
        this.metaParseTime = metaParseTime;
    }

    public int getNumEvents() {
        return numEvents;
    }

    public void setNumEvents(int numEvents) {
        this.numEvents = numEvents;
    }

    public static class MetaMapper implements RowMapper<MetaRecord> {
        @Override
        public MetaRecord map(ResultSet rs, StatementContext ctx) throws SQLException {
            MetaRecord metaRecord = new MetaRecord();
            metaRecord.setDeviceId(rs.getString("uuid"));
            metaRecord.setArrivalTime(rs.getLong("arrival_time_ms"));
            metaRecord.setMetaParseTime(rs.getLong("meta_parse_time_ms"));
            metaRecord.setNumEvents(rs.getInt("num_events"));

            return metaRecord;
        }
    }
}
