package com.momo.dylantest.util;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.*;
public class Test {

    public static void main(String[] args) {
        // 創建一個 HttpClient
        HttpClient client = HttpClient.newHttpClient();

        // 定義多個 API URL
        List<String> urls = List.of(
                "https://jsonplaceholder.typicode.com/posts/1",
                "https://jsonplaceholder.typicode.com/posts/2",
                "https://jsonplaceholder.typicode.com/posts/3"
        );

        // 創建 Executor Service，使用 Virtual Threads
        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

        try {
            // 提交多個 API 請求作為並行任務
            List<CompletableFuture<String>> futures = urls.stream()
                    .map(url -> CompletableFuture.supplyAsync(() -> fetchApi(client, url), executor))
                    .toList();

            // 等待所有請求完成
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

            // 處理結果
            futures.forEach(future -> {
                try {
                    System.out.println("Response: " + future.get());
                } catch (Exception e) {
                    System.err.println("Error: " + e.getMessage());
                }
            });
        } finally {
            executor.shutdown();
        }
    }

    // 發送 HTTP GET 請求
    private static String fetchApi(HttpClient client, String url) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch API: " + url, e);
        }
    }
}
