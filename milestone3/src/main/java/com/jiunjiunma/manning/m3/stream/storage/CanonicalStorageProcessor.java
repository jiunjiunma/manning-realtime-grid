package com.jiunjiunma.manning.m3.stream.storage;

import com.jiunjiunma.manning.m3.stream.common.StreamProcessor;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import io.dropwizard.Application;
import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.setup.Environment;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.jdbi.v3.core.Jdbi;

public class CanonicalStorageProcessor extends StreamProcessor<StorageConfiguration> {

    protected CanonicalStorageProcessor(Application<StorageConfiguration> application,
                                        String name,
                                        String description) {
        super(application, name, description);
    }

    @Override
    protected void init(Environment environment, StorageConfiguration configuration) {
        final JdbiFactory factory = new JdbiFactory();
        final Jdbi jdbi = factory.build(environment, configuration.getDataSourceFactory(), "postgresql");
        //dao = jdbi.onDemand(DeviceDAO.class);
    }

    @Override
    protected Topology buildTopology(StorageConfiguration configuration) {
        // todo
        return null;
    }

}
