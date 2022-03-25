package com.jiunjiunma.manning.m3.stream.canonical;

import com.jiunjiunma.manning.m3.api.S3;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import manning.devices.raw.m2.RawRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class RawRecordUtil {
    private static final Logger logger = LoggerFactory.getLogger(RawRecordUtil.class);

    public static InputStream message(S3 s3, RawRecord value) {
        String objectKey = value.getBodyReference();
        if (objectKey != null) {
            // fetch from s3
            return s3.read(value.getBodyReference());
        } else {
            return new ByteArrayInputStream(value.getBody().array());
        }
    }

    public static SpecificAvroSerde<RawRecord> rawRecordSerDe(CanonicalConfiguration config) {
        SpecificAvroSerde<RawRecord> serde = new SpecificAvroSerde<>();
        serde.configure(config.getKafkaSettings(), false);
        return serde;
    }


}
