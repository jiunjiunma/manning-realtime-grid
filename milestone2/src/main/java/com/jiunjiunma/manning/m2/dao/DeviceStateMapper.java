package com.jiunjiunma.manning.m2.dao;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DeviceStateMapper implements RowMapper<DeviceState> {
    @Override
    public DeviceState map(ResultSet rs, StatementContext ctx) throws SQLException {
        DeviceState device = new DeviceState();
        device.setCharging(rs.getInt("charging"));
        device.setChargingSource(rs.getString("source"));
        device.setCurrentCapacity(rs.getInt("capacity"));
        return device;
    }
}