package com.jiunjiunma.manning.m3.stream.common;

import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import io.dropwizard.Application;
import io.dropwizard.cli.EnvironmentCommand;
import io.dropwizard.setup.Environment;
import net.sourceforge.argparse4j.inf.Namespace;
import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.Topology;

import java.util.Properties;

public abstract class StreamProcessor<T extends StreamConfiguration> extends EnvironmentCommand<T> {
    protected StreamProcessor(Application<T> application, String name, String description) {
        super(application, name, description);
    }

    abstract protected Topology buildTopology(T configuration);

    protected void init(Environment environment, T configuration) {
        // no-op
    }

    @Override
    protected void run(Environment environment, Namespace namespace, T streamConfiguration) throws Exception {

        init(environment, streamConfiguration);

        final Properties kafkaProperties = new Properties();
        kafkaProperties.putAll(streamConfiguration.getKafkaSettings());

        Topology topology = buildTopology(streamConfiguration);
        final KafkaStreams streams = new KafkaStreams(topology, kafkaProperties);

        // only use this for dev
        streams.cleanUp();

        // Now run the processing topology via `start()` to begin processing its input data.
        streams.start();

        // Add shutdown hook to respond to SIGTERM and gracefully close the Streams application.
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));

    }

    public <T2 extends SpecificRecord> Serde<T2> configureAvroSerde(
        StreamConfiguration config, SpecificAvroSerde<T2> serde, boolean flag) {
        serde.configure(config.getKafkaSettings(), flag);
        return serde;
    }
}
