package com.jiunjiunma.manning.m3.dao;

import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.Define;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;
import java.util.Optional;

public interface MetaDAO {
    @SqlUpdate("INSERT INTO <table> (UUID, SOURCE_TOPIC, SOURCE_PARTITION, SOURCE_OFFSET,"
        + "ARRIVAL_TIME_MS, META_PARSE_TIME_MS, NUM_EVENTS) VALUES "
        + "(:uuid, :sourceTopic, :sourcePartition, :sourceOffset, :arrivalTimeMs,"
        + " :metaParseTime, :numEvents)")
    void write(@Define("table") String table, @Bind("uuid") String uuid,
               @Bind("sourceTopic") String sourceTopic, @Bind("sourcePartition") Integer sourcePartition,
               @Bind("sourceOffset") Long sourceOffset, @Bind("arrivalTimeMs") Long arrivalTimeMs,
               @Bind("metaParseTime") Long metaParseTime, @Bind("numEvents") long numEvents);

    @SqlQuery("SELECT UUID FROM <table> WHERE ARRIVAL_TIME_MS > :since")
    List<String> since(@Define("table") String table, @Bind("since") long since);

    @SqlQuery("SELECT COUNT(DISTINCT UUID) FROM <table>")
    int numberOfDevices(@Define("table") String table);

    @SqlQuery("SELECT * FROM <table> WHERE SOURCE_OFFSET = :offset AND UUID = :uuid LIMIT 1")
    @RegisterRowMapper(MetaRecord.MetaMapper.class)
    Optional<MetaRecord> selectForOffset(@Define("table") String table,
                                         @Bind("uuid") String uuid, @Bind("offset") long offset);
}
