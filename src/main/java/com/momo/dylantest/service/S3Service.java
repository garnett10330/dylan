package com.momo.dylantest.service;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class S3Service {

    @Resource
    private S3Client s3Client;

    public void uploadFile(String bucketName, String filePath) throws Exception {
        // 檢查檔案是否存在
        if (!Files.exists(Paths.get(filePath))) {
            throw new FileNotFoundException("File not found: " + filePath);
        }
        s3Client.putObject(PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(Paths.get(filePath).getFileName().toString())
                        .build(),
                Paths.get(filePath));
    }
}
