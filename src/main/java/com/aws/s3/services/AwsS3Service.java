package com.aws.s3.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.model.*;

import java.io.File;
import java.util.List;

@Service
public class AwsS3Service {

    private Logger logger = LogManager.getLogger(AwsS3Service.class);

    @Value("${userBucket}")
    private String userBucket;

    private String regionName;

    private AmazonS3 s3;

    @Autowired
    public AwsS3Service(@Value("${regionName:us-east-1}") String regionName) {
        s3 = AmazonS3ClientBuilder
                .standard()
                .withRegion(regionName)
                .build();
    }

    public List<Bucket> getBucketsList() {
        logger.debug("Get buckets list");
        return s3.listBuckets();
    }

    public void upload(String key, File file) {
        logger.debug("Uploading a new object to S3 from a file");
        s3.putObject(new PutObjectRequest(userBucket, key, file));
    }

    public void delete(String key) {
        logger.debug("Deleting an object\n");
        s3.deleteObject(userBucket, key);
    }
}
