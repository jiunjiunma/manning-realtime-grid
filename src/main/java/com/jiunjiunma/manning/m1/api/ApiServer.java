package com.jiunjiunma.manning.m1.api;

import com.codahale.metrics.health.HealthCheck;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import io.dropwizard.Application;
import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.jdbi.v3.core.Jdbi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Properties;

public class ApiServer extends Application<StreamConfiguration> {
    private static Logger logger = LoggerFactory.getLogger(ApiServer.class);

    public static void main(String[] args) throws Exception {
        new ApiServer().run(args);
    }

    @Override
    public String getName() {
        return "manning-live-project";
    }

    @Override
    public void initialize(Bootstrap<StreamConfiguration> bootstrap) {
        super.initialize(bootstrap);
        bootstrap.addCommand(new StreamCommand(this, "stream", "stream event processor"));
    }

    @Override
    public void run(StreamConfiguration apiConfiguration, Environment environment) throws Exception {
        KafkaProducer kafkaProducer = createProducer(apiConfiguration.getKafkaSettings());
        environment.lifecycle().manage(new CloseableManaged(kafkaProducer));

        final JdbiFactory factory = new JdbiFactory();
        final Jdbi jdbi = factory.build(environment, apiConfiguration.getDataSourceFactory(), "postgresql");
        DeviceDAO dao = jdbi.onDemand(DeviceDAO.class);
        final DeviceEndpoint endpoint = new DeviceEndpoint(apiConfiguration.getTopic(), kafkaProducer, dao,
                                                           apiConfiguration.getDeviceTable());
        final HealthCheck healthCheck = new HealthCheckEndpoint();

        environment.jersey().register(endpoint);
        environment.healthChecks().register("health", healthCheck);

    }

    private KafkaProducer createProducer(Map<String, String> kafkaProperties) {
        Properties props = new Properties();
        props.put(ProducerConfig.ACKS_CONFIG, "1");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class.getName());
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.get("bootstrap.servers"));
        props.put("schema.registry.url", kafkaProperties.get("schema.registry.url"));
        return new KafkaProducer<>(props);
    }
}
