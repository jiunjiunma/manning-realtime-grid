package com.jiunjiunma.manning.m3.stream.storage;

import com.jiunjiunma.manning.m3.dao.MetaDAO;
import com.jiunjiunma.manning.m3.dao.StatusDAO;
import com.jiunjiunma.manning.m3.stream.common.StreamProcessor;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import io.dropwizard.Application;
import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.setup.Environment;
import manning.devices.canonical.m3.CanonicalMetaValue;
import manning.devices.canonical.m3.CanonicalStatusValue;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.jdbi.v3.core.Jdbi;

public class MetaStatusStorageProcessor extends StreamProcessor<MetaStorageConfiguration> {
    private MetaDAO metaDb;
    private StatusDAO statusDb;

    protected MetaStatusStorageProcessor(Application<MetaStorageConfiguration> application,
                                         String name,
                                         String description) {
        super(application, name, description);
    }

    @Override
    protected void init(Environment environment, MetaStorageConfiguration configuration) {
        final JdbiFactory factory = new JdbiFactory();
        final Jdbi jdbi = factory.build(environment, configuration.getDataSourceFactory(), "postgresql");
        metaDb = jdbi.onDemand(MetaDAO.class);
        statusDb = jdbi.onDemand(StatusDAO.class);
    }

    @Override
    protected Topology buildTopology(MetaStorageConfiguration conf) {
        final Serde<CanonicalStatusValue> statusSerde =
            configureAvroSerde(conf, new SpecificAvroSerde<>(), false);
        final Serde<CanonicalMetaValue> metaSerde =
            configureAvroSerde(conf, new SpecificAvroSerde<>(), false);

        // run the meta and status streams on the same instance for ease of deployment and management
        StreamsBuilder builder = new StreamsBuilder();
        builder.stream(conf.getStatusTopic(),
                       Consumed.with(Serdes.String(), statusSerde))
            // write the state to the database
            .foreach((uuid, status) -> {
                statusDb.write(conf.getStatusTable(), uuid,
                               status.getSourceTopic().toString(),
                               status.getSourcePartition(),
                               status.getSourceOffset(),
                               status.getSuccess(),
                               status.getParseStartEpochMs(),
                               status.getParseEndEpochMs(),
                               status.getParseDurationMs());
            });

        builder.stream(conf.getMetaTopic(),
                       Consumed.with(Serdes.String(), metaSerde))
            // write the state to the database
            .foreach((uuid, meta) -> {
                metaDb.write(conf.getMetaTable(), uuid,
                             meta.getSourceTopic().toString(),
                             meta.getSourcePartition(),
                             meta.getSourceOffset(),
                             meta.getArrivalTimeMs(),
                             meta.getMetaParseTimeEpochMs(),
                             meta.getNumEvents());
            });

        return builder.build();
    }

}
