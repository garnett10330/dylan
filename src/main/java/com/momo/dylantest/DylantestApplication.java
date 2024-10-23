package com.momo.dylantest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;

@SpringBootApplication
public class DylantestApplication {

	public static void main(String[] args) {
		SpringApplication.run(DylantestApplication.class, args);
	}

}
