package com.jiunjiunma.manning.m1.api;

import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.Define;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface DeviceDAO {
    @SqlQuery("select charging, source, capacity from <table> where id = :id")
    @RegisterRowMapper(DeviceStateMapper.class)
    DeviceState getDeviceState(@Define("table") String table, @Bind("id") String uuid);

    @SqlUpdate("insert into <table> (id, charging, source, capacity) values (:id, :charging, :source, :capacity) " +
               "on conflict (id) do update set charging=:charging, source=:source, capacity=:capacity")
    void setDeviceState(@Define("table") String table,
                        @Bind("id")String uuid,
                        @Bind("charging") int charging,
                        @Bind("source") String chargingSource,
                        @Bind("capacity") int currentCapacity);
}
