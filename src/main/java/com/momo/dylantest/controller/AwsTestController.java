package com.momo.dylantest.controller;

import com.momo.dylantest.response.Response;
import com.momo.dylantest.service.S3Service;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
/**
 * AWS 測試控制器。
 *
 * 該控制器負責與 AWS S3 的交互，提供文件上傳功能。
 */
@RestController
@RequestMapping("/api/aws")
public class AwsTestController {

    @Resource
    private S3Service s3Service;
    private  static final String BUCKET_NAME_TEST = "my-bucket";

    /**
     * 上傳文件到 S3 存儲桶。
     *
     * @param filePath 要上傳的文件路徑。
     * @return 成功上傳文件的回應信息。{@link Response<String>}
     * @throws Exception 如果上傳過程中發生錯誤。
     */
    @PostMapping("/upload/v1")
    public Response uploadFile(@RequestParam String filePath) throws Exception {
        s3Service.uploadFile(BUCKET_NAME_TEST, filePath);
        return Response.success("File uploaded successfully!");
    }

}
