package com.aws.s3.controllers;

import com.aws.s3.services.AwsS3Service;
import com.aws.s3.utils.FileUtils;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/aws/s3")
public class AwsS3Controller {

    //private Logger logger = LogManager.getLogger(AwsS3Controller.class);

    private String KEY;

    @Autowired()
    AwsS3Service service;

    @GetMapping("/buckets")
    public List<String> getBucketsList() {
        return service.getBucketsList()
                .stream()
                .map(b -> b.getName())
                .collect(Collectors.toList());
    }

    @GetMapping("/upload")
    public void upload() throws IOException {
        File file = FileUtils.createSampleFile();
        KEY = UUID.randomUUID().toString();
        service.upload(KEY, file);
    }

    @GetMapping("/delete")
    public void delete() throws IOException {
        service.delete(KEY);
    }
}
