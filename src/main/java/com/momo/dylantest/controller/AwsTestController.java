package com.momo.dylantest.controller;

import com.momo.dylantest.response.Response;
import com.momo.dylantest.service.S3Service;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aws")
public class AwsTestController {

    @Resource
    private S3Service s3Service;


    private  static final String BUCKET_NAME_TEST = "my-bucket";
    @PostMapping("/upload")
    public Response uploadFile(@RequestParam String filePath) throws Exception {
        s3Service.uploadFile(BUCKET_NAME_TEST, filePath);
        return Response.success("File uploaded successfully!");
    }

}
