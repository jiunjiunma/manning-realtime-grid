package com.jiunjiunma.manning.m2.stream.storage;

import com.jiunjiunma.manning.m2.dao.DeviceDAO;
import com.jiunjiunma.manning.m2.stream.common.StreamProcessor;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import io.dropwizard.Application;
import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.setup.Environment;
import manning.devices.canonical.m2.CanonicalKey;
import manning.devices.canonical.m2.CanonicalValue;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Predicate;
import org.jdbi.v3.core.Jdbi;

public class CanonicalStorageProcessor extends StreamProcessor<StorageConfiguration> {
    protected DeviceDAO dao;

    protected CanonicalStorageProcessor(Application<StorageConfiguration> application,
                                        String name,
                                        String description) {
        super(application, name, description);
    }

    @Override
    protected void init(Environment environment, StorageConfiguration configuration) {
        final JdbiFactory factory = new JdbiFactory();
        final Jdbi jdbi = factory.build(environment, configuration.getDataSourceFactory(), "postgresql");
        dao = jdbi.onDemand(DeviceDAO.class);
    }

    @Override
    protected Topology buildTopology(StorageConfiguration configuration) {
        final Serde<CanonicalKey> keySpecificAvroSerde =
            configureAvroSerde(configuration, new SpecificAvroSerde<>(), true);
        final Serde<CanonicalValue> valueSpecificAvroSerde =
            configureAvroSerde(configuration, new SpecificAvroSerde<>(), false);

        StreamsBuilder builder = new StreamsBuilder();
        builder.stream(configuration.getSource(),
                       Consumed.with(keySpecificAvroSerde, valueSpecificAvroSerde))
            // ensure that we only see events that have charging information
            .filter(new ContainsChargeFilter())
            // write the state to the database
            .foreach((key, value) -> {
                int charging = Integer.parseInt(value.getEvents().get("charging").toString());
                dao.setDeviceState(configuration.getDeviceTable(), key.getUuid().toString(), charging, "solar", 0);
            });
        return builder.build();
    }

}
