package com.momo.dylantest.controller.message;


import com.momo.dylantest.response.Response;
import com.momo.dylantest.service.message.KafkaMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * KafkaTestController 提供簡單的 HTTP 介面來測試 Kafka 功能：
 *
 * 1. 正常訊息發送至 primary 與 secondary topic
 * 2. 發送包含 "error" 字串的訊息，觸發 Consumer 例外拋出，驗證重試與死信處理
 * 3. 發送訊息至 Kafka Streams 的輸入 topic，並觀察流處理結果（轉成大寫後發送到 stream output topic）
 *
 * 使用範例：
 *  - GET http://localhost:8650/api/kafka/sendToSingle?message=hello
 *  - GET http://localhost:8650/api/kafka/sendToMultiConsumer?message=world
 *  - GET http://localhost:8650/api/kafka/sendToMultiGroup?message=HelloKafka
 *  - GET http://localhost:8650/api/kafka/sendError?message=error   (此請求將觸發例外)
 *  - GET http://localhost:8650/api/kafka/sendStream?message=streamtest
 */
@RestController
@RequestMapping("/api/kafka")
@RequiredArgsConstructor
public class KafkaTestController {

    private final KafkaMessageService kafkaMessageService;

    /**
     * 測試發送正常訊息到 Single topic
     * @param message 要發送的訊息
     * @return 發送結果字串
     */
    @GetMapping("/sendToSingle")
    public Response<String> sendToSingle(@RequestParam("message") String message) {
        return Response.success(kafkaMessageService.sendToSingle(message));
    }

    /**
     * 測試發送正常訊息到 MultiConsumer topic
     * @param message 要發送的訊息
     * @return 發送結果字串
     */
    @GetMapping("/sendToMultiConsumer")
    public Response<String> sendToMultiConsumer(@RequestParam("message") String message) {
        return Response.success(kafkaMessageService.sendToMultiConsumer(message));
    }

    /**
     * 測試發送正常訊息到 MultiGroup topic
     * @param message 要發送的訊息
     * @return 發送結果字串
     */
    @GetMapping("/sendToMultiGroup")
    public Response<String> sendToMultiGroup(@RequestParam("message") String message) {
        return Response.success(kafkaMessageService.sendToMultiGroup(message));
    }

    /**
     * 測試發送包含 "error" 字串的訊息到 Single topic，
     * 讓 Consumer 拋出例外以觸發 retry 邏輯，重試失敗後訊息會轉送至死信佇列
     * @param message 要發送的訊息，預設值為 "error"
     * @return 發送結果字串
     */
    @GetMapping("/sendError")
    public Response<String> sendError(@RequestParam(value = "message", defaultValue = "error") String message) {
        return Response.success(kafkaMessageService.sendToSingle(message));
    }

    /**
     * 測試發送訊息到 Kafka Streams 的輸入 topic，
     * 此訊息將會由 Kafka Streams 處理後轉成大寫並送出到 stream output topic
     * @param message 要發送的訊息
     * @return 發送結果字串
     */
    @GetMapping("/sendStream")
    public Response<String> sendStream(@RequestParam("message") String message) {
        return Response.success(kafkaMessageService.sendToStream(message));
    }
}
