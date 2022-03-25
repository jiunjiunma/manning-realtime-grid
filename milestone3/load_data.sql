drop table device_status;
drop table device_meta;
create table device_status (
            uuid varchar(32),
            source_topic varchar(256),
            source_partition integer,
            source_offset bigint,
            success boolean,
            parse_time_start bigint,
            parse_time_end bigint,
            parse_duration_ms bigint
);
create table device_meta(
            uuid varchar(32),
            source_topic varchar(256),
            source_partition integer,
            source_offset bigint,
            arrival_time_ms bigint,
            meta_parse_time_ms bigint,
            num_events integer
);
insert into device_status (uuid, source_topic, source_partition, source_offset, success, parse_time_start, parse_time_end, parse_duration_ms) values ('12345', 'topic', 0, 0, True, 0, 0, 0);
insert into device_meta (uuid, source_topic, source_partition, source_offset, arrival_time_ms, meta_parse_time_ms, num_events) values ('12345', 'topic', 0, 0, 0, 0, 1);
