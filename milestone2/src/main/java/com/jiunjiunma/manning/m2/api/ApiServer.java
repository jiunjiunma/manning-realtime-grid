package com.jiunjiunma.manning.m2.api;

import com.codahale.metrics.health.HealthCheck;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import io.dropwizard.Application;
import io.dropwizard.forms.MultiPartBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Properties;

public class ApiServer extends Application<ApiConfiguration> {
    private static Logger logger = LoggerFactory.getLogger(ApiServer.class);

    public static void main(String[] args) throws Exception {
        new ApiServer().run(args);
    }

    @Override
    public void initialize(Bootstrap<ApiConfiguration> bootstrap) {
        bootstrap.addBundle(new MultiPartBundle());
    }

    @Override
    public void run(ApiConfiguration apiConfiguration, Environment environment) throws Exception {
        KafkaProducer kafkaProducer = createProducer(apiConfiguration.getKafkaSettings());
        environment.lifecycle().manage(new CloseableManaged(kafkaProducer));

        S3 s3 = new S3(apiConfiguration.getS3Conf());
        final DeviceEndpoint endpoint = new DeviceEndpoint(apiConfiguration.getTopic(),
                                                           apiConfiguration.getMaxBodySize(),
                                                           kafkaProducer,
                                                           s3);

        environment.jersey().register(endpoint);
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
