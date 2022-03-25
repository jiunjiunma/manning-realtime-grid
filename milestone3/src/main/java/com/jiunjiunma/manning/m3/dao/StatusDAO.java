package com.jiunjiunma.manning.m3.dao;

import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.Define;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.Optional;

public interface StatusDAO {

    @SqlUpdate("INSERT INTO <table> (UUID, SOURCE_TOPIC, SOURCE_PARTITION, SOURCE_OFFSET,"
        + "SUCCESS, PARSE_TIME_START, PARSE_TIME_END, PARSE_DURATION_MS) VALUES "
        + "(:uuid, :sourceTopic, :sourcePartition, :sourceOffset,"
        + " :success, :parseStart, :parseEnd, :parseDuration)")
    void write(@Define("table") String statusTable, @Bind("uuid") String uuid,
               @Bind("sourceTopic") String sourceTopic, @Bind("sourcePartition") Integer sourcePartition,
               @Bind("sourceOffset") Long sourceOffset, @Bind("success") boolean success,
               @Bind("parseStart") Long parseStart, @Bind("parseEnd") Long parseEnd,
               @Bind("parseDuration") Long parseDuration);

    @SqlQuery("SELECT COUNT(*) FROM <table> WHERE UUID = :uuid AND SUCCESS = false")
    int badMessages(@Define("table") String statusTable, @Bind("uuid") String UUID);

    @SqlQuery("SELECT * FROM <table> WHERE UUID = :uuid ORDER BY SOURCE_OFFSET DESC LIMIT 1")
    @RegisterRowMapper(StatusRecord.StatusMapper.class)
    Optional<StatusRecord> selectLatest(@Define("table") String statusTable,
                                        @Bind("uuid") String deviceId);

    @SqlQuery("SELECT * FROM <table> WHERE SOURCE_OFFSET = :offset AND UUID = :uuid LIMIT 1")
    @RegisterRowMapper(StatusRecord.StatusMapper.class)
    Optional<StatusRecord> selectForOffset(@Define("table") String statusTable,
                                           @Bind("uuid") String deviceId, @Bind("offset") long offset);
}