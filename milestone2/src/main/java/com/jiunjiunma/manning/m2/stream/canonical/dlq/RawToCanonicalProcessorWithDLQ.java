package com.jiunjiunma.manning.m2.stream.canonical.dlq;

import com.jiunjiunma.manning.m2.api.S3;
import com.jiunjiunma.manning.m2.stream.common.StreamProcessor;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import io.dropwizard.Application;
import manning.devices.canonical.m2.CanonicalKey;
import manning.devices.canonical.m2.CanonicalValue;
import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.processor.RecordContext;
import org.apache.kafka.streams.processor.TopicNameExtractor;

import static com.jiunjiunma.manning.m2.stream.canonical.RawRecordUtil.rawRecordSerDe;

public class RawToCanonicalProcessorWithDLQ extends StreamProcessor<CanonicalConfigurationWithDLQ> {
    protected RawToCanonicalProcessorWithDLQ(Application<CanonicalConfigurationWithDLQ> application, String name, String description) {
        super(application, name, description);
    }

    @Override
    protected Topology buildTopology(CanonicalConfigurationWithDLQ config) {
        StreamsBuilder builder = new StreamsBuilder();
        builder.stream(config.getSource(),
                       // key: UUID of the record, extracted here just for tracing
                       // value: RawRecord, with either the body or a reference to an external store
                       Consumed.with(Serdes.String(), rawRecordSerDe(config)))
            .flatMapValues(new DLQJsonParser(new S3(config.getS3Conf())))
            .map(new ExceptionOrRecordMapper())
            .to(new TopicExtractor(config));

        return builder.build();
    }

    public static class TopicExtractor implements TopicNameExtractor<CanonicalKey, SpecificRecord> {
        private final CanonicalConfigurationWithDLQ config;

        public TopicExtractor(CanonicalConfigurationWithDLQ config) {
            this.config = config;
        }

        @Override
        public String extract(CanonicalKey canonicalKey, SpecificRecord specificRecord, RecordContext recordContext) {
            if (specificRecord instanceof CanonicalValue) {
                return config.getSink();
            } else {
                return config.getDLQ();
            }
        }
    }
}
