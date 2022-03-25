package com.jiunjiunma.manning.m3.stream.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.AbstractIterator;
import com.jiunjiunma.manning.m3.api.S3;
import manning.devices.raw.m2.RawRecord;
import org.apache.kafka.streams.kstream.ValueTransformer;
import org.apache.kafka.streams.processor.ProcessorContext;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;

import static com.jiunjiunma.manning.m3.stream.canonical.RawRecordUtil.message;

public class FastLaneParserAndStatus implements ValueTransformer<RawRecord,
    Iterable<MultiParsedValue>> {

    private static final TypeReference<Map<String, Object>> MAP_TYPE_REFERENCE =
        new TypeReference<Map<String, Object>>() {
        };
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private final S3 s3;
    private final Duration max;
    private ProcessorContext context;

    public FastLaneParserAndStatus(S3 s3, Duration maxTime) {
        this.s3 = s3;
        this.max = maxTime;
    }

    @Override
    public void init(ProcessorContext context) {
        this.context = context;
    }

    @Override
    public Iterable<MultiParsedValue> transform(RawRecord value) {
        return () -> {
            final Instant start = Instant.now();
            BufferedReader reader = new BufferedReader(new InputStreamReader(message(s3, value)));
            return new AbstractIterator<MultiParsedValue>() {
                private boolean success = true;
                private boolean done = false;
                private boolean status = false;

                @Override
                protected MultiParsedValue computeNext() {
                    if (done) {
                        if (!status) {
                            return status();
                        }
                        return endOfData();
                    }
                    Instant incrementalTime = Instant.now();
                    Duration remainingParsingTime = Duration.between(start, incrementalTime).minus(max);
                    if (remainingParsingTime.isZero() || remainingParsingTime.isNegative()) {
                        return new MultiParsedValue(value).slow();
                    }
                    try {
                        String line = reader.readLine();
                        // no more records, return the metadata about this parse
                        if (line == null) {
                            done = true;
                            return status();
                        }
                        return new MultiParsedValue(value).parsed(MAPPER.readValue(line, MAP_TYPE_REFERENCE));
                    } catch (Exception e) {
                        done = true;
                        this.success = false;
                        return new MultiParsedValue(value).fail(e);
                    }
                }

                private MultiParsedValue status() {
                    status = true;
                    Instant end = Instant.now();
                    return new MultiParsedValue(value).status(success, start, end, context);
                }
            };
        };
    }

    @Override
    public void close() {

    }
}