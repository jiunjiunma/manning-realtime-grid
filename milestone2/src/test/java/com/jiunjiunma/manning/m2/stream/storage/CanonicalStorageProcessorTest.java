package com.jiunjiunma.manning.m2.stream.storage;

import com.jiunjiunma.manning.m2.dao.DeviceDAO;
import com.jiunjiunma.manning.m2.dao.DeviceState;
import io.confluent.kafka.schemaregistry.client.MockSchemaRegistryClient;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import manning.devices.canonical.m2.CanonicalKey;
import manning.devices.canonical.m2.CanonicalValue;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.TestInputTopic;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.TopologyTestDriver;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.jdbi.v3.testing.JdbiRule;
import org.junit.*;

import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;

public class CanonicalStorageProcessorTest {
    private static final String SCHEMA_REGISTRY_SCOPE = CanonicalStorageProcessorTest.class.getName();
    private static final String MOCK_SCHEMA_REGISTRY_URL = "mock://" + SCHEMA_REGISTRY_SCOPE;
    private TopologyTestDriver testDriver;
    private Jdbi jdbi;

    @ClassRule
    static public JdbiRule jdbiRule = JdbiRule.embeddedPostgres();

    @Before
    public void start() {
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

    public static class TestCanonicalStorageProcessor extends CanonicalStorageProcessor {
        private Jdbi jdbi;

        protected TestCanonicalStorageProcessor(Application<StorageConfiguration> application, String name, String description, Jdbi jdbi) {
            super(application, name, description);
            this.jdbi = jdbi;
        }

        @Override
        protected void init(Environment environment, StorageConfiguration configuration) {
            dao = jdbi.onDemand(DeviceDAO.class);
        }
    }

    @Test
    public void testProcessor() throws Exception {
        CanonicalStorageProcessor processor = new TestCanonicalStorageProcessor(null,
                                                                                "storage",
                                                                                "test canonical storage processor",
                                                                                jdbi);

        StorageConfiguration config = new StorageConfiguration();
        config.setSource("test-topic");
        config.setDeviceTable("devices");
        Map<String, String> testKafkaSettings = Map.of(
            StreamsConfig.APPLICATION_ID_CONFIG, "test",
            StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092",
            "schema.registry.url", MOCK_SCHEMA_REGISTRY_URL
        );

        config.setKafkaSettings(testKafkaSettings);

        processor.init(null, config);
        Topology topology = processor.buildTopology(config);
        Assert.assertNotNull(topology);

        // setup test driver
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "test");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "dummy:1234");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class.getName());
        props.put("schema.registry.url", MOCK_SCHEMA_REGISTRY_URL);
        testDriver = new TopologyTestDriver(topology, props);

        // send to test topic
        Serde<CanonicalKey> keySpecificAvroSerde =
            processor.configureAvroSerde(config, new SpecificAvroSerde<>(), true);
        Serde<CanonicalValue> valueSpecificAvroSerde =
            processor.configureAvroSerde(config, new SpecificAvroSerde<>(), false);
        TestInputTopic<CanonicalKey, CanonicalValue> inputTopic = testDriver.createInputTopic(
            config.getSource(),
            keySpecificAvroSerde.serializer(),
            valueSpecificAvroSerde.serializer());
        CanonicalKey key = CanonicalKey.newBuilder().setUuid("12345").build();
        Map<String, String> eventMap = Map.of(
            "charging", "100"
        );
        CanonicalValue value = CanonicalValue.newBuilder()
            .setUuid("12345")
            .setEventTimeMs(System.currentTimeMillis())
            .setArrivalTimeMs(System.currentTimeMillis())
            .setRegionId(1L)
            .setEvents(eventMap).build();

        inputTopic.pipeInput(key, value);

        await().atMost(5, TimeUnit.SECONDS).until(() -> processor.dao.getDeviceState("devices", "12345") != null);

    }
}
