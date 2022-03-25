package com.jiunjiunma.manning.m3.dao;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.jdbi.v3.testing.JdbiRule;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import java.util.Optional;

public class DAOTest {
    @ClassRule
    static public JdbiRule jdbiRule = JdbiRule.embeddedPostgres();

    private static final String createStatusTableSQL = """
        create table device_status (
            uuid varchar(32),
            source_topic varchar(256),
            source_partition integer,
            source_offset bigint,
            success boolean,
            parse_time_start bigint,
            parse_time_end bigint,
            parse_duration_ms bigint
        )
        """;

    private static final String createMetaTableSQL = """
        create table device_meta(
            uuid varchar(32),
            source_topic varchar(256),
            source_partition integer,
            source_offset bigint,
            arrival_time_ms bigint,
            meta_parse_time_ms bigint,
            num_events integer
        )
        """;

    private Jdbi jdbi;

    @Before
    public void setup() {
        jdbi = jdbiRule.getJdbi();
        jdbi.installPlugin(new SqlObjectPlugin());
    }

    @Test
    public void testDeviceStatusDAO() {

        Handle handle = jdbi.open();
        handle.execute(createStatusTableSQL);

        String tableName = "device_status";
        String uuid = "12345";
        StatusDAO dao = jdbi.onDemand(StatusDAO.class);
        dao.write(tableName, uuid, "topic", 0, 0L, true, System.currentTimeMillis(), System.currentTimeMillis(), 1L);
        dao.write(tableName, uuid, "topic", 0, 0L, true, System.currentTimeMillis(), System.currentTimeMillis(), 1L);
        dao.write(tableName, uuid, "topic", 0, 0L, false, System.currentTimeMillis(), System.currentTimeMillis(), 1L);
        int numOfBadMessages = dao.badMessages(tableName, uuid);
        Assert.assertEquals(numOfBadMessages, 1);

        Optional<StatusRecord> status = dao.selectLatest(tableName, uuid);
        Assert.assertFalse(status.isEmpty());
        StatusRecord record = status.get();
        Assert.assertNotNull(record);

        Optional<StatusRecord> nonExistance = dao.selectLatest(tableName, "nosuchuuid");
        Assert.assertTrue(nonExistance.isEmpty());
    }

    @Test
    public void testMetaDAO() {

        Handle handle = jdbi.open();
        handle.execute(createMetaTableSQL);

        String tableName = "device_meta";
        String uuid1 = "12345";
        String uuid2 = "54321";
        MetaDAO dao = jdbi.onDemand(MetaDAO.class);
        dao.write(tableName, uuid1, "topic", 0, 0L, System.currentTimeMillis(), 0L, 30);
        dao.write(tableName, uuid1, "topic", 0, 0L, System.currentTimeMillis(), 0L, 30);
        dao.write(tableName, uuid2, "topic", 0, 0L, System.currentTimeMillis(), 0L, 30);
        int numOfDevices = dao.numberOfDevices(tableName);
        Assert.assertEquals(numOfDevices, 2);

    }
}
