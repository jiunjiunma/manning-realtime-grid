package com.jiunjiunma.manning.m2.stream.canonical;

import manning.devices.raw.m2.RawRecord;
import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class JsonParserTest {
    @Test
    public void testParsing() {
        //raw value = {"uuid": "12345", "arrival_time_ms": 1647731899284, "body": "{\"foo\":\"bar\"}\n", "body_reference": null}
        String body = "{\"foo\":\"bar\"}\n";
        RawRecord value = RawRecord.newBuilder()
            .setUuid("12345")
            .setArrivalTimeMs(System.currentTimeMillis())
            .setBody(ByteBuffer.wrap(body.getBytes()))
            .setBodyReference(null)
            .build();

        JsonParser parser = new JsonParser(null);
        Iterable<Map<String, String>> iter = parser.apply(value);
        Assert.assertNotNull(iter);

        Map<String, String> bodyMap = iter.iterator().next();
        Assert.assertNotNull(bodyMap);
    }
}
