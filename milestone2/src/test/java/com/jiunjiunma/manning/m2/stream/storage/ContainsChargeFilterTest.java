package com.jiunjiunma.manning.m2.stream.storage;

import manning.devices.canonical.m2.CanonicalKey;
import manning.devices.canonical.m2.CanonicalValue;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class ContainsChargeFilterTest {
    @Test
    public void testFilter() {
        CanonicalKey key = CanonicalKey.newBuilder().setUuid("12345").build();
        Map<String, String> eventMap = Map.of(
            "charging", "100"
        );
        CanonicalValue value = CanonicalValue.newBuilder()
            .setUuid("12345")
            .setEventTimeMs(System.currentTimeMillis())
            .setArrivalTimeMs(System.currentTimeMillis())
            .setRegionId(1L)
            .setEvents(eventMap).build();

        ContainsChargeFilter filter = new ContainsChargeFilter();
        boolean result = filter.test(key, value);
        Assert.assertTrue(result);
    }
}
