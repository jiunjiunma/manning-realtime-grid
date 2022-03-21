package com.jiunjiunma.manning.m2.stream.canonical;

import com.jiunjiunma.manning.m2.api.S3;
import com.jiunjiunma.manning.m2.stream.common.StreamProcessor;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import io.confluent.kafka.streams.serdes.avro.GenericAvroSerde;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import io.dropwizard.Application;
import manning.devices.canonical.m2.CanonicalKey;
import manning.devices.canonical.m2.CanonicalValue;
import manning.devices.raw.m2.RawRecord;
import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Produced;

import static com.jiunjiunma.manning.m2.stream.canonical.RawRecordUtil.rawRecordSerDe;

public class RawCanonicalProcessor extends StreamProcessor<CanonicalConfiguration> {
    protected RawCanonicalProcessor(Application<CanonicalConfiguration> application, String name, String description) {
        super(application, name, description);
    }

    @Override
    protected Topology buildTopology(CanonicalConfiguration configuration) {
        Serde<CanonicalKey> keySerde = configureAvroSerde(configuration,
                                                          new SpecificAvroSerde<>(),
                                                          true);
        Serde<CanonicalValue> valueSerde = configureAvroSerde(configuration,
                                                              new SpecificAvroSerde<>(),
                                                              false);

        StreamsBuilder builder = new StreamsBuilder();
        builder.stream(configuration.getSource(),
                       // key: UUID of the record, extracted here just for tracing
                       // value: RawRecord, with either the body or a reference to an external store
                       Consumed.with(Serdes.String(), rawRecordSerDe(configuration)))
            .flatMapValues(new JsonParser(new S3(configuration.getS3Conf())))
            .map(new CanonicalRecordMapper())
            .to(configuration.getSink(), Produced.with(keySerde, valueSerde));

        return builder.build();
    }
}
