package com.jiunjiunma.manning.m3.stream.meta;

import com.jiunjiunma.manning.m3.api.S3;
import com.jiunjiunma.manning.m3.stream.canonical.CanonicalConfiguration;
import com.jiunjiunma.manning.m3.stream.common.StreamProcessor;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import io.dropwizard.Application;
import manning.devices.canonical.m3.CanonicalMetaValue;
import manning.devices.canonical.m3.CanonicalStatusValue;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Produced;

import static com.jiunjiunma.manning.m3.stream.canonical.RawRecordUtil.rawRecordSerDe;

public class MetadataToCanonicalStreamProcessor extends StreamProcessor<CanonicalConfiguration> {
    protected MetadataToCanonicalStreamProcessor(Application<CanonicalConfiguration> application, String name, String description) {
        super(application, name, description);
    }

    @Override
    protected Topology buildTopology(CanonicalConfiguration configuration) {
        final Serde<CanonicalMetaValue> metaSerde =
            configureAvroSerde(configuration, new SpecificAvroSerde<>(), false);
        StreamsBuilder builder = new StreamsBuilder();
        builder.stream(configuration.getSource(),
                       // key: UUID of the record, extracted here just for tracing
                       // value: RawRecord, with either the body or a reference to an external store
                       Consumed.with(Serdes.String(), rawRecordSerDe(configuration)))
            .transformValues(() -> new MetaParser(new S3(configuration.getS3Conf())))
            .to(configuration.getSink(), Produced.with(Serdes.String(), metaSerde));
        return builder.build();
    }
}
