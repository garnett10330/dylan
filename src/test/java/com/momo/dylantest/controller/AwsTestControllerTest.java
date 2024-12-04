package com.momo.dylantest.controller;

import com.momo.dylantest.controller.test.AwsTestController;
import com.momo.dylantest.service.test.S3Service;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
class AwsTestControllerTest {

    private MockMvc mockMvc;

    @Mock
    private S3Service s3Service;

    @InjectMocks
    private AwsTestController awsTestController;

    public AwsTestControllerTest() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(awsTestController).build();
    }

    @Test
    void testUploadFile() throws Exception {
        String filePath = "test/file/path.txt";

        doNothing().when(s3Service).uploadFile(anyString(), eq(filePath));

        mockMvc.perform(post("/api/aws/upload/v1")
                        .param("filePath", filePath)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data").value("File uploaded successfully!"));

        verify(s3Service, times(1)).uploadFile(anyString(), eq(filePath));
    }
}
