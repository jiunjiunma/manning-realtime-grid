package com.jiunjiunma.manning.m1.api;

import com.jiunjiunma.manning.m1.avro.ChargingEvent;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import io.dropwizard.Application;
import io.dropwizard.cli.ConfiguredCommand;
import io.dropwizard.cli.EnvironmentCommand;
import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.lifecycle.Managed;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import net.sourceforge.argparse4j.inf.Namespace;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.jdbi.v3.core.Jdbi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class StreamCommand extends EnvironmentCommand<StreamConfiguration> {
    private static final Logger logger = LoggerFactory.getLogger(StreamCommand.class);

    protected StreamCommand(Application<StreamConfiguration> application, String name, String description) {
        super(application, name, description);
    }

    @Override
    protected void run(Environment environment, Namespace namespace, StreamConfiguration streamConfiguration) throws Exception {
        final JdbiFactory factory = new JdbiFactory();
        final Jdbi jdbi = factory.build(environment, streamConfiguration.getDataSourceFactory(), "postgresql");
        DeviceDAO dao = jdbi.onDemand(DeviceDAO.class);

        final Properties streamsConfiguration = new Properties();
        streamsConfiguration.putAll(streamConfiguration.getKafkaSettings());
        streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, streamConfiguration.getApplicationId());
        streamsConfiguration.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        SpecificAvroSerde<ChargingEvent> serde = new SpecificAvroSerde<>();
        serde.configure(streamConfiguration.getKafkaSettings(), false);

        final StreamsBuilder builder = new StreamsBuilder();
        builder.stream(streamConfiguration.getTopic(),
                       Consumed.with(Serdes.String(), serde))
            .foreach((uuid, value) -> {
                dao.setDeviceState("devices", uuid,
                                   value.getCharging(),
                                   value.getChargingSource() == null ? "solar": value.getChargingSource().toString(),
                                   value.getCurrentCapacity());
            });

        final KafkaStreams streams = new KafkaStreams(builder.build(), streamsConfiguration);

        streams.cleanUp();

        // Now run the processing topology via `start()` to begin processing its input data.
        streams.start();

        // Add shutdown hook to respond to SIGTERM and gracefully close the Streams application.
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));

    }
}
