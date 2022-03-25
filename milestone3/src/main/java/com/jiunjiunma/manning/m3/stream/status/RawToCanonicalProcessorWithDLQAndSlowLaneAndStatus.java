package com.jiunjiunma.manning.m3.stream.status;

import com.jiunjiunma.manning.m3.api.S3;
import com.jiunjiunma.manning.m3.stream.common.StreamProcessor;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import io.dropwizard.Application;
import manning.devices.canonical.m2.CanonicalErrorValue;
import manning.devices.canonical.m2.CanonicalKey;
import manning.devices.canonical.m2.CanonicalValue;
import manning.devices.canonical.m3.CanonicalMetaValue;
import manning.devices.canonical.m3.CanonicalStatusValue;
import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.processor.RecordContext;
import org.apache.kafka.streams.processor.TopicNameExtractor;

import java.time.Duration;

import static com.jiunjiunma.manning.m3.stream.canonical.RawRecordUtil.rawRecordSerDe;


public class RawToCanonicalProcessorWithDLQAndSlowLaneAndStatus extends StreamProcessor<CanonicalConfWithSlowLaneAndStatus> {
    protected RawToCanonicalProcessorWithDLQAndSlowLaneAndStatus(Application<CanonicalConfWithSlowLaneAndStatus> application,
                                                                 String name, String description) {
        super(application, name, description);
    }

    @Override
    protected Topology buildTopology(CanonicalConfWithSlowLaneAndStatus conf) {
        Duration maxParseTime = Duration.parse(conf.getMaxDuration());
        final Serde<SpecificRecord> valueSerde =
            configureAvroSerde(conf, new SpecificAvroSerde<>(), false);

        StreamsBuilder builder = new StreamsBuilder();
        builder.stream(conf.getSource(),
                       Consumed.with(Serdes.String(), rawRecordSerDe(conf)))
            .flatTransformValues(() -> new FastLaneParserAndStatus(new S3(conf.getS3Conf()), maxParseTime))
            .map(new ParsedRecordReMapper())
            .to(new TopicExtractor(conf), Produced.with(Serdes.String(), valueSerde));

        return builder.build();
    }

    public static class TopicExtractor implements TopicNameExtractor<String, SpecificRecord> {
        private final CanonicalConfWithSlowLaneAndStatus config;

        public TopicExtractor(CanonicalConfWithSlowLaneAndStatus config) {
            this.config = config;
        }

        @Override
        public String extract(String uuid, SpecificRecord specificRecord, RecordContext recordContext) {
            if (specificRecord instanceof CanonicalStatusValue) {
                return config.getStatusTopic();
            } else if (specificRecord instanceof CanonicalValue) {
                return config.getSink();
            } else if (specificRecord instanceof CanonicalErrorValue) {
                return config.getDLQ();
            } else {
                return config.getSlowLane();
            }
        }
    }
}
