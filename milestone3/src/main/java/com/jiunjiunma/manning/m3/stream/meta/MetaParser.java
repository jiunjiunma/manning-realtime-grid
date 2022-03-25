package com.jiunjiunma.manning.m3.stream.meta;

import com.jiunjiunma.manning.m3.api.S3;
import manning.devices.canonical.m3.CanonicalMetaValue;
import manning.devices.raw.m2.RawRecord;
import org.apache.kafka.streams.kstream.ValueTransformer;
import org.apache.kafka.streams.processor.ProcessorContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Instant;

import static com.jiunjiunma.manning.m3.stream.canonical.RawRecordUtil.message;

public class MetaParser implements ValueTransformer<RawRecord, CanonicalMetaValue> {
    private S3 s3;
    private ProcessorContext context;

    public MetaParser(S3 s3) {
        this.s3 = s3;
    }

    @Override
    public void init(ProcessorContext processorContext) {
        this.context = processorContext;
    }

    @Override
    public void close() {
    }

    @Override
    public CanonicalMetaValue transform(RawRecord rawRecord) {

        CanonicalMetaValue value = CanonicalMetaValue.newBuilder()
            .setUuid(rawRecord.getUuid())
            .setArrivalTimeMs(rawRecord.getArrivalTimeMs())
            .setMetaParseTimeEpochMs(Instant.now().toEpochMilli())
            .setNumEvents(countEvents(rawRecord))
            .setSourceTopic(context.topic())
            .setSourcePartition(context.partition())
            .setSourceOffset(context.offset())
            .build();
        return value;
    }

    private long countEvents(RawRecord rawRecord) {
        long numEvents = 0;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(message(s3, rawRecord)));
            while (reader.readLine() != null) {
                ++numEvents;
            }
        } catch (IOException e) {
            // error reading rawRecord, nothing we can do, return whatever we have
        }
        return numEvents;
    }
}
