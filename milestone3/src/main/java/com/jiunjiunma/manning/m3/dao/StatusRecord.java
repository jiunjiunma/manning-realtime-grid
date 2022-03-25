package com.jiunjiunma.manning.m3.dao;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class StatusRecord {
    @JsonProperty
    private String deviceId;
    @JsonProperty
    private long parseDuration;
    @JsonProperty
    private long parseStart;
    @JsonProperty
    private long parseEnd;
    @JsonProperty
    private boolean success;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public long getParseDuration() {
        return parseDuration;
    }

    public void setParseDuration(long parseDuration) {
        this.parseDuration = parseDuration;
    }

    public long getParseStart() {
        return parseStart;
    }

    public void setParseStart(long parseStart) {
        this.parseStart = parseStart;
    }

    public long getParseEnd() {
        return parseEnd;
    }

    public void setParseEnd(long parseEnd) {
        this.parseEnd = parseEnd;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class StatusMapper implements RowMapper<StatusRecord> {
        @Override
        public StatusRecord map(ResultSet rs, StatementContext ctx) throws SQLException {
            StatusRecord record = new StatusRecord();
            record.setDeviceId(rs.getString("uuid"));
            record.setParseDuration(rs.getLong("parse_duration_ms"));
            record.setParseStart(rs.getLong("parse_time_start"));
            record.setParseEnd(rs.getLong("parse_time_end"));
            record.setSuccess(rs.getBoolean("success"));

            return record;
        }
    }
}
