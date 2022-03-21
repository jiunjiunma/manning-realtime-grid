package com.jiunjiunma.manning.m2.stream.canonical.slowlane;

import com.jiunjiunma.manning.m2.stream.canonical.CanonicalRecordMapper;
import manning.devices.canonical.m2.CanonicalErrorValue;
import manning.devices.canonical.m2.CanonicalKey;
import manning.devices.canonical.m2.CanonicalValue;
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
    implements KeyValueMapper<String, ParsedValue, KeyValue<CanonicalKey, SpecificRecord>> {
    private final CanonicalRecordMapper canonicalMapper;

    public ParsedRecordReMapper() {
        this.canonicalMapper = new CanonicalRecordMapper();
    }

    @Override
    public KeyValue<CanonicalKey, SpecificRecord> apply(String uuid, ParsedValue value) {
        if (value.failed != null) {
            return mapError(new ImmutablePair<>(value.failed, value.source));
        } else if (value.parsed != null) {
            KeyValue<CanonicalKey, CanonicalValue> kv = canonicalMapper.apply(uuid, value.parsed);
            return new KeyValue<>(kv.key, kv.value);
        } else {
            // it must have been slow, so just end the basic record
            CanonicalKey key = CanonicalKey.newBuilder()
                .setUuid(uuid)
                .build();
            return new KeyValue<>(key, value.source);
        }
    }

    private KeyValue<CanonicalKey, SpecificRecord> mapError(ImmutablePair<Exception, RawRecord> pair) {
        RawRecord rawRecord = pair.getRight();
        Exception exception = pair.getLeft();
        CanonicalErrorValue errorValue = CanonicalErrorValue.newBuilder()
            .setRawRecordBytes(ByteBuffer.wrap(rawRecord.toString().getBytes()))
            .setStackTrace(buildTrace(exception))
            .build();
        CanonicalKey key = CanonicalKey.newBuilder()
            .setUuid(rawRecord.getUuid())
            .build();
        return KeyValue.pair(key, errorValue);
    }

    private List<String> buildTrace(Exception exception) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        exception.printStackTrace(pw);
        return Arrays.asList(sw.toString().split("\n"));
    }
}
