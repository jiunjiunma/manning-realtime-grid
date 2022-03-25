package com.jiunjiunma.manning.m3.stream.status;

import manning.devices.raw.m2.RawRecord;
import org.apache.kafka.streams.processor.ProcessorContext;

import java.time.Instant;
import java.util.Map;

public class MultiParsedValue {
    RawRecord source;
    Exception failed;
    Map<String, String> parsed;
    boolean slow = false;
    boolean status = false;
    String topic;
    int partition;
    long offset;
    long parseStartTimeMs;
    long parseEndTimeMs;
    boolean success;

    public MultiParsedValue(RawRecord source) {
        this.source = source;
    }

    public MultiParsedValue fail(Exception cause){
        this.failed = cause;
        return this;
    }

    public MultiParsedValue parsed(Map<String, String> events){
        this.parsed = events;
        return this;
    }

    public MultiParsedValue slow(){
        this.slow = true;
        return this;
    }

    public MultiParsedValue status(boolean success, Instant start, Instant end, ProcessorContext context) {
        this.status = true;
        this.parseStartTimeMs = start.toEpochMilli();
        this.parseEndTimeMs = end.toEpochMilli();
        this.success = success;
        this.topic = context.topic();
        this.partition = context.partition();
        this.offset = context.offset();
        return this;
    }
}
