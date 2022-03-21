package com.jiunjiunma.manning.m2.stream.canonical.dlq;

import com.jiunjiunma.manning.m2.stream.canonical.CanonicalRecordMapper;
import manning.devices.canonical.m2.CanonicalErrorValue;
import manning.devices.canonical.m2.CanonicalKey;
import manning.devices.canonical.m2.CanonicalValue;
import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KeyValueMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.util.Either;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ExceptionOrRecordMapper implements
    KeyValueMapper<String, Either<Exception, Map<String, String>>, KeyValue<CanonicalKey, SpecificRecord>> {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionOrRecordMapper.class);

    private final CanonicalRecordMapper canonicalRecordMapper = new CanonicalRecordMapper();

    @Override
    public KeyValue<CanonicalKey, SpecificRecord> apply(String s,
                                                          Either<Exception, Map<String, String>> exceptionOrRecord) {
        if (exceptionOrRecord.isRight()) {
            Map<String, String> map = exceptionOrRecord.right().get();
            KeyValue<CanonicalKey, CanonicalValue> pair = canonicalRecordMapper.apply(s, map);
            return KeyValue.pair(pair.key, pair.value);
        } else {
            CanonicalKey key = CanonicalKey.newBuilder().setUuid(s).build();
            CanonicalErrorValue value = mapError(exceptionOrRecord.left().get());
            return KeyValue.pair(key, value);
        }
    }

    public CanonicalErrorValue mapError(Exception exception) {
        CanonicalErrorValue errorValue = CanonicalErrorValue.newBuilder()
            .setRawRecordBytes(ByteBuffer.wrap("dummy-data".getBytes(StandardCharsets.UTF_8)))
            .setStackTrace(buildTrace(exception))
            .build();
        return errorValue;
    }

    private List<String> buildTrace(Exception exception) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        exception.printStackTrace(pw);
        return Arrays.asList(sw.toString().split("\n"));
    }
}
