package com.jiunjiunma.manning.m3.stream.canonical;

import manning.devices.canonical.m2.CanonicalKey;
import manning.devices.canonical.m2.CanonicalValue;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KeyValueMapper;

import java.util.Map;

public class CanonicalRecordMapper implements KeyValueMapper<String, Map<String, String>, KeyValue<CanonicalKey, CanonicalValue>> {
    @Override
    public KeyValue<CanonicalKey, CanonicalValue> apply(String s, Map<String, String> events) {
        CanonicalKey key = CanonicalKey.newBuilder().setUuid(s).build();

        CanonicalValue value = CanonicalValue.newBuilder()
            .setUuid(s)
            .setEventTimeMs(System.currentTimeMillis())
            .setArrivalTimeMs(System.currentTimeMillis())
            .setRegionId(1L)
            .setEvents(events).build();

        return KeyValue.pair(key, value);
    }
}
