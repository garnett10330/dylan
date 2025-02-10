package com.momo.dylantest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;

@SpringBootApplication
@EnableKafka       // 啟用 Kafka 消費者功能
@EnableKafkaStreams // 啟用 Kafka Streams 功能
public class DylantestApplication {

	public static void main(String[] args) {
		SpringApplication.run(DylantestApplication.class, args);
	}

}
