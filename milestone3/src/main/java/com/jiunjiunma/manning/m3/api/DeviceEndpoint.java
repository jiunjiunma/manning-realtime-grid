package com.jiunjiunma.manning.m3.api;

import manning.devices.raw.m2.RawRecord;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

@Path("/device")
@Produces(MediaType.APPLICATION_JSON)
public class DeviceEndpoint {
    private static Logger logger = LoggerFactory.getLogger(DeviceEndpoint.class);

    private final String topic;
    private final int maxSize;
    private final KafkaProducer kafkaProducer;
    private final S3 s3;

    public DeviceEndpoint(String topic, int maxSize, KafkaProducer kafkaProducer, S3 s3) {
        this.topic = topic;
        this.maxSize = maxSize;
        this.kafkaProducer = kafkaProducer;
        this.s3 = s3;
    }

    @POST
    @Path("{uuid}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public void send(@PathParam("uuid") String uuid,
                     @FormDataParam("file") InputStream inputStream,
                     @FormDataParam("file") FormDataContentDisposition fileMeta) throws IOException {

        byte[] data = inputStream.readAllBytes();
        long contentLength = data.length;
        RawRecord record = new RawRecord();
        record.setArrivalTimeMs(System.currentTimeMillis());
        record.setUuid(uuid);

        if (contentLength > maxSize) {
            // upload to s3
            logger.info("content exceeds maxSize " + maxSize + ", upload to s3");
            String objectKey = s3.put(uuid,
                                      fileMeta.getFileName(),
                                      new ByteArrayInputStream(data),
                                      contentLength);
            record.setBodyReference(objectKey);
        } else {
            logger.info("content not exceeds maxSize " + maxSize + ", send to kafka directly");
            record.setBody(ByteBuffer.wrap(data));
        }

        ProducerRecord<String, RawRecord> avroRecord = new ProducerRecord<>(
            topic, uuid, record
        );

        kafkaProducer.send(avroRecord);

    }

}
