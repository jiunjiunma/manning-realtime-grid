package com.jiunjiunma.manning.m3.stream.status;

import com.jiunjiunma.manning.m3.stream.canonical.CanonicalRecordMapper;
import manning.devices.canonical.m2.CanonicalErrorValue;
import manning.devices.canonical.m2.CanonicalKey;
import manning.devices.canonical.m2.CanonicalValue;
import manning.devices.canonical.m3.CanonicalStatusValue;
import manning.devices.raw.m2.RawRecord;
import org.apache.avro.specific.SpecificRecord;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KeyValueMapper;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;

public class ParsedRecordReMapper
    implements KeyValueMapper<String, MultiParsedValue, KeyValue<String, SpecificRecord>> {
    private final CanonicalRecordMapper canonicalMapper;

    public ParsedRecordReMapper() {
        this.canonicalMapper = new CanonicalRecordMapper();
    }

    @Override
    public KeyValue<String, SpecificRecord> apply(String uuid, MultiParsedValue value) {
        if (value.status) {
            CanonicalStatusValue statusValue = CanonicalStatusValue.newBuilder()
                .setUuid(uuid)
                .setSourceTopic(value.topic)
                .setSourcePartition(value.partition)
                .setParseStartEpochMs(value.parseStartTimeMs)
                .setParseEndEpochMs(value.parseEndTimeMs)
                .setParseDurationMs(value.parseEndTimeMs-value.parseStartTimeMs)
                .setSuccess(value.success)
                .build();
            return KeyValue.pair(uuid, statusValue);
        }
        if (value.failed != null) {
            return mapError(new ImmutablePair<>(value.failed, value.source));
        } else if (value.parsed != null) {
            KeyValue<CanonicalKey, CanonicalValue> kv = canonicalMapper.apply(uuid, value.parsed);
            return new KeyValue<>(uuid, kv.value);
        } else {
            // it must have been slow, so just end the basic record
            return new KeyValue<>(uuid, value.source);
        }
    }

    private KeyValue<String, SpecificRecord> mapError(ImmutablePair<Exception, RawRecord> pair) {
        RawRecord rawRecord = pair.getRight();
        Exception exception = pair.getLeft();
        CanonicalErrorValue errorValue = CanonicalErrorValue.newBuilder()
            .setRawRecordBytes(ByteBuffer.wrap(rawRecord.toString().getBytes()))
            .setStackTrace(buildTrace(exception))
            .build();
        return KeyValue.pair(rawRecord.getUuid(), errorValue);
    }

    private List<String> buildTrace(Exception exception) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        exception.printStackTrace(pw);
        return Arrays.asList(sw.toString().split("\n"));
    }
}
