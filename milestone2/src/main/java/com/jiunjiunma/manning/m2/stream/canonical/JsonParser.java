package com.jiunjiunma.manning.m2.stream.canonical;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiunjiunma.manning.m2.api.S3;
import manning.devices.raw.m2.RawRecord;
import org.apache.kafka.streams.kstream.ValueMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import static com.jiunjiunma.manning.m2.stream.canonical.RawRecordUtil.message;

public class JsonParser implements ValueMapper<RawRecord, Iterable<Map<String, String>>> {
    private static final Logger logger = LoggerFactory.getLogger(JsonParser.class);

    private static final TypeReference<Map<String, String>> MAP_TYPE_REFERENCE =
        new TypeReference<>() {
        };
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private final S3 s3;

    public JsonParser(S3 s3) {
        this.s3 = s3;
    }

    @Override
    public Iterable<Map<String, String>> apply(RawRecord value) {
        return () -> {
            BufferedReader reader = new BufferedReader(new InputStreamReader(message(s3, value)));
            return reader.lines()
                .map(line -> {
                    try {
                        return (Map<String, String>) MAPPER.readValue(line, MAP_TYPE_REFERENCE);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .iterator();
        };
    }
}