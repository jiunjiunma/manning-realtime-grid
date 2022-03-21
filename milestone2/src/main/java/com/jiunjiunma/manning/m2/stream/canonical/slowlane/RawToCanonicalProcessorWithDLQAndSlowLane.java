package com.jiunjiunma.manning.m2.stream.canonical.slowlane;

import com.jiunjiunma.manning.m2.api.S3;
import com.jiunjiunma.manning.m2.stream.canonical.dlq.CanonicalConfigurationWithDLQ;
import com.jiunjiunma.manning.m2.stream.common.StreamProcessor;
import io.dropwizard.Application;
import manning.devices.canonical.m2.CanonicalErrorValue;
import manning.devices.canonical.m2.CanonicalKey;
import manning.devices.canonical.m2.CanonicalValue;
import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.processor.RecordContext;
import org.apache.kafka.streams.processor.TopicNameExtractor;

import java.time.Duration;

import static com.jiunjiunma.manning.m2.stream.canonical.RawRecordUtil.rawRecordSerDe;

public class RawToCanonicalProcessorWithDLQAndSlowLane extends StreamProcessor<CanonicalConfWithSlowLane> {
    protected RawToCanonicalProcessorWithDLQAndSlowLane(Application<CanonicalConfWithSlowLane> application,
                                                        String name, String description) {
        super(application, name, description);
    }

    @Override
    protected Topology buildTopology(CanonicalConfWithSlowLane conf) {
        Duration maxParseTime = Duration.parse(conf.getMaxDuration());

        StreamsBuilder builder = new StreamsBuilder();
        builder.stream(conf.getSource(),
                       Consumed.with(Serdes.String(), rawRecordSerDe(conf)))
            .flatMapValues(new FastLaneParser(new S3(conf.getS3Conf()), maxParseTime))
            .map(new ParsedRecordReMapper())
            .to(new TopicExtractor(conf));

        return builder.build();
    }

    public static class TopicExtractor implements TopicNameExtractor<CanonicalKey, SpecificRecord> {
        private final CanonicalConfWithSlowLane config;

        public TopicExtractor(CanonicalConfWithSlowLane config) {
            this.config = config;
        }

        @Override
        public String extract(CanonicalKey canonicalKey, SpecificRecord specificRecord, RecordContext recordContext) {
            if (specificRecord instanceof CanonicalValue) {
                return config.getSink();
            } else if (specificRecord instanceof CanonicalErrorValue) {
                return config.getDLQ();
            } else {
                return config.getSlowLane();
            }
        }
    }
}
