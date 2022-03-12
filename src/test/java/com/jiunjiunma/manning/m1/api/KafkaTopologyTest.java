package com.jiunjiunma.manning.m1.api;

import com.jiunjiunma.manning.m1.avro.ChargingEvent;
import io.confluent.kafka.schemaregistry.client.MockSchemaRegistryClient;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.kstream.Consumed;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.jdbi.v3.testing.JdbiRule;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;

public class KafkaTopologyTest {

    private static final String SCHEMA_REGISTRY_SCOPE = KafkaTopologyTest.class.getName();
    private static final String MOCK_SCHEMA_REGISTRY_URL = "mock://" + SCHEMA_REGISTRY_SCOPE;
    private TopologyTestDriver testDriver;
    private MockSchemaRegistryClient schemaRegistryClient;
    private Jdbi jdbi;

    @ClassRule
    static public JdbiRule jdbiRule = JdbiRule.embeddedPostgres();

    @Before
    public void start() {
        schemaRegistryClient = new MockSchemaRegistryClient();
        jdbi = jdbiRule.getJdbi();
        jdbi.installPlugin(new SqlObjectPlugin());
        // set up test table schema
        Handle handle = jdbi.open();
        handle.execute("create table devices (id varchar(32) primary key, charging int, source varchar(256), capacity int)");
    }

    @After
    public void tearDown() {
        Optional.ofNullable(testDriver).ifPresent(TopologyTestDriver::close);
        testDriver = null;
    }

    @Test
    public void testTopology() {
        DeviceDAO dao = jdbi.onDemand(DeviceDAO.class);

        List<ChargingEvent> received = new ArrayList<>();

        SpecificAvroSerde<ChargingEvent> valueSpecificAvroSerde = new SpecificAvroSerde<>(schemaRegistryClient);
        final Map<String, String> schema =
            Collections.singletonMap("schema.registry.url", MOCK_SCHEMA_REGISTRY_URL);
        valueSpecificAvroSerde.configure(schema, false);

        StreamsBuilder builder = new StreamsBuilder();
        builder.stream("input-topic",
                       Consumed.with(Serdes.String(), valueSpecificAvroSerde))
            .foreach((uuid, value) -> {
                 dao.setDeviceState("devices", uuid,
                                    value.getCharging(),
                                    value.getChargingSource().toString(),
                                    value.getCurrentCapacity());
            });
        Topology topology = builder.build();

        // setup test driver
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "test");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "dummy:1234");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.StringSerde.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class.getName());
        props.put("schema.registry.url", MOCK_SCHEMA_REGISTRY_URL);
        testDriver = new TopologyTestDriver(topology, props);

        // send to test topic
        TestInputTopic<String, ChargingEvent> inputTopic = testDriver.createInputTopic(
            "input-topic",
            new StringSerializer(),
            valueSpecificAvroSerde.serializer());
        ChargingEvent inputEvent = ChargingEvent.newBuilder()
            .setUuid("12345")
            .setCharging(100)
            .setChargingSource("solar")
            .setCharging(100)
            .build();
        inputTopic.pipeInput("12345", inputEvent);

        await().atMost(1, TimeUnit.SECONDS).until(() -> dao.getDeviceState("devices", "12345") != null);


    }

}
