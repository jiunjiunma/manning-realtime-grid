package com.jiunjiunma.manning.m2.stream.canonical.dlq;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiunjiunma.manning.m2.api.S3;
import com.jiunjiunma.manning.m2.stream.canonical.JsonParser;
import manning.devices.raw.m2.RawRecord;
import org.apache.kafka.streams.kstream.ValueMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.util.Either;
import scala.util.Left;
import scala.util.Right;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import static com.jiunjiunma.manning.m2.stream.canonical.RawRecordUtil.message;

public class DLQJsonParser implements ValueMapper<RawRecord, Iterable<Either<Exception, Map<String, String>>>> {
    private static final Logger logger = LoggerFactory.getLogger(DLQJsonParser.class);

    private static final TypeReference<Map<String, String>> MAP_TYPE_REFERENCE =
        new TypeReference<>() {
        };

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private final S3 s3;

    public DLQJsonParser(S3 s3) {
        this.s3 = s3;
    }

    @Override
    public Iterable<Either<Exception, Map<String, String>>> apply(RawRecord value) {
        return () -> {
            BufferedReader reader = new BufferedReader(new InputStreamReader(message(s3, value)));
            return reader.lines()
                .map(line -> {
                    Either<Exception, Map<String, String>> result;
                    try {
                        result = Right.apply((Map<String, String>) MAPPER.readValue(line, MAP_TYPE_REFERENCE));
                    } catch (Exception e) {
                        result = Left.apply(e);
                    }
                    return result;
                })
                .iterator();
        };
    }
}