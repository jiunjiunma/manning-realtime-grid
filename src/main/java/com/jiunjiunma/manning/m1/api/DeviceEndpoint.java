package com.jiunjiunma.manning.m1.api;

import com.jiunjiunma.manning.m1.avro.ChargingEvent;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/device")
@Produces(MediaType.APPLICATION_JSON)
public class DeviceEndpoint {
    private static Logger logger = LoggerFactory.getLogger(DeviceEndpoint.class);
    private final String topic;
    private final KafkaProducer kafkaProducer;
    private final DeviceDAO dao;
    private final String tableName;

    public DeviceEndpoint(String topic, KafkaProducer kafkaProducer, DeviceDAO dao, String tableName) {
        this.topic = topic;
        this.kafkaProducer = kafkaProducer;
        this.dao = dao;
        this.tableName = tableName;
    }

    @SuppressWarnings("unchecked")
    @POST
    @Path("{uuid}")
    @Consumes("application/json")
    public void setDeviceState(@PathParam("uuid") String uuid, DeviceState deviceState)  {
        ProducerRecord<String, ChargingEvent> record = new ProducerRecord<>(
            topic, uuid,
            new ChargingEvent(uuid,
                              deviceState.getCharging(),
                              deviceState.getChargingSource(),
                              deviceState.getCurrentCapacity())
        );
        // don't check result
        kafkaProducer.send(record);
    }

    @GET
    @Path("{uuid}")
    public DeviceState getDeviceState(@PathParam("uuid") String uuid) {
        return dao.getDeviceState(tableName, uuid);
    }
}
