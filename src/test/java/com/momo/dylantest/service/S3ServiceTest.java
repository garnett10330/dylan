package com.momo.dylantest.service;

import com.momo.dylantest.service.test.S3Service;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class S3ServiceTest {

    @InjectMocks
    private S3Service s3Service;

    @Mock
    private S3Client s3Client;

    @Test
    void testUploadFile_FileNotFound() {
        // Arrange
        String bucketName = "test-bucket";
        String filePath = "nonexistent-file.txt";

        // Act & Assert
        Exception exception = assertThrows(FileNotFoundException.class, () ->
                s3Service.uploadFile(bucketName, filePath));

        assertEquals("File not found: " + filePath, exception.getMessage());
        verifyNoInteractions(s3Client); // 確保 S3Client 沒有被調用
    }

    @Test
    void testUploadFile_Success() throws Exception {
        // Arrange
        String bucketName = "test-bucket";
        String filePath = "existing-file.txt";

        // 模擬文件存在
        mockStatic(Files.class);
        when(Files.exists(Paths.get(filePath))).thenReturn(true);

        // Act
        s3Service.uploadFile(bucketName, filePath);

        // Assert
        verify(s3Client, times(1)).putObject(
                any(PutObjectRequest.class),
                eq(Paths.get(filePath))
        );
    }
}
