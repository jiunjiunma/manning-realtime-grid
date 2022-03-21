package com.jiunjiunma.manning.m2.api;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class S3 {
    private static final Logger logger = LoggerFactory.getLogger(S3.class);
    private String bucket;
    private DateTimeFormatter format;
    private AmazonS3 s3;

    public S3(S3Conf s3Conf) {
        this(s3Conf.getBucket(), s3Conf.getEndpoint());
    }

    public S3(String bucket, String endpoint) {
        this.bucket = bucket;
        s3 = AmazonS3ClientBuilder.defaultClient();
        format = DateTimeFormatter.ISO_LOCAL_DATE.withZone(ZoneId.systemDefault() );
    }

    public String put(String deviceUUID, String fileName, InputStream stream, long size) {
        Instant now = Instant.now();
        // generate a day-based groups of the events, for easier debugging later
        String key = String.format("%s/%s/%s", format.format(now), deviceUUID, fileName);
        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentLength(size);
        PutObjectRequest request = new PutObjectRequest(bucket, key, stream, meta);
        s3.putObject(request);
        return key;
    }

    public InputStream read(String key) {
        S3Object o = s3.getObject(bucket, key);
        S3ObjectInputStream s3is = o.getObjectContent();
        return s3is;
    }
}
