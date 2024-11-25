package com.momo.dylantest.service;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
/**
 * S3 服務類。
 *
 * 此服務類負責與 AWS S3 交互，提供上傳文件到指定 S3 存儲桶的功能。
 */
@Service
public class S3Service {

    @Resource
    private S3Client s3Client;
    /**
     * 上傳文件到指定的 S3 存儲桶。
     *
     * @param bucketName S3 存儲桶的名稱。
     * @param filePath 要上傳的文件路徑。
     * @throws Exception 如果文件不存在或者上傳過程中出現錯誤。
     */
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
