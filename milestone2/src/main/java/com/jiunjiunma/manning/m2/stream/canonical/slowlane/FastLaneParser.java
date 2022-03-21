package com.jiunjiunma.manning.m2.stream.canonical.slowlane;

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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.jiunjiunma.manning.m2.stream.canonical.RawRecordUtil.message;

public class FastLaneParser implements ValueMapper<RawRecord, Iterable<ParsedValue>> {
    private static final Logger logger = LoggerFactory.getLogger(JsonParser.class);

    private static final TypeReference<Map<String, String>> MAP_TYPE_REFERENCE =
        new TypeReference<>() {
        };
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final S3 s3;
    private final Duration maxParseTime;

    public FastLaneParser(S3 s3, Duration maxParseTime) {
        this.s3 = s3;
        this.maxParseTime = maxParseTime;
    }

    @Override
    public Iterable<ParsedValue> apply(RawRecord rawRecord) {
        return () -> {
            return computeNext(message(s3, rawRecord), rawRecord);

            /*
            BufferedReader reader = new BufferedReader(new InputStreamReader(message(s3, rawRecord)));

            return reader.lines()
                .map(line -> {
                    ParsedValue result = new ParsedValue(rawRecord);
                    try {
                        Instant start = Instant.now();
                        Map<String, String> parsed = MAPPER.readValue(line, MAP_TYPE_REFERENCE);
                        Instant end = Instant.now();
                        Duration parseTime = Duration.between(start, end);
                        if (parseTime.compareTo(maxParseTime) > 0) {
                            // exceed max parse time
                            result.slow();
                        } else {
                            result.parsed(parsed);
                        }
                    } catch (Exception e) {
                        result.fail(e);
                    }
                    return result;
                })
                .iterator();

             */
        };
    }

    private Iterator<ParsedValue> computeNext(InputStream inputStream, RawRecord rawRecord) {
        List<ParsedValue> parsedResults = new ArrayList<>();
        ParsedValue slowOrFailed = new ParsedValue(rawRecord);
        Instant start = Instant.now();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                ParsedValue result = new ParsedValue(rawRecord);
                Map<String, String> parsed = MAPPER.readValue(line, MAP_TYPE_REFERENCE);
                Instant end = Instant.now();
                Duration parseTime = Duration.between(start, end);
                if (parseTime.compareTo(maxParseTime) > 0) {
                    // exceed max parse time
                    return List.of(slowOrFailed.slow()).iterator();
                } else {
                    parsedResults.add(result.parsed(parsed));
                }
            }
        } catch (Exception e) {
            return List.of(slowOrFailed.fail(e)).iterator();
        }

        return parsedResults.iterator();
    }
}
