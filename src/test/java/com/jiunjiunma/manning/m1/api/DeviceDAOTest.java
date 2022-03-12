package com.jiunjiunma.manning.m1.api;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.jdbi.v3.testing.JdbiRule;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

public class DeviceDAOTest {
    @ClassRule
    static public JdbiRule jdbiRule = JdbiRule.embeddedPostgres();

    @Test
    public void testDeviceDAO() {
        Jdbi jdbi = jdbiRule.getJdbi();
        jdbi.installPlugin(new SqlObjectPlugin());

        Handle handle = jdbi.open();
        handle.execute("create table devices (id varchar(32) primary key, charging int, source varchar(256), capacity int)");

        handle.execute("INSERT INTO devices (id, charging, source, capacity) VALUES('123', 1, 'solar', 100)");

        DeviceDAO dao = jdbi.onDemand(DeviceDAO.class);
        DeviceState deviceState = dao.getDeviceState("devices", "123");
        Assert.assertNotNull(deviceState);
        dao.setDeviceState("devices", "123", 30, "utility", 80);
        DeviceState newDeviceState = dao.getDeviceState("devices", "123");
        Assert.assertNotNull(newDeviceState);

    }
}
