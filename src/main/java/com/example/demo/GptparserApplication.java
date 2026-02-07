package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(scanBasePackages = {"com.example.demo", "com.example.capture"})
@EnableMongoRepositories(basePackages = "com.example.capture.repo")
public class GptparserApplication {

	public static void main(String[] args) {
		SpringApplication.run(GptparserApplication.class, args);
	}

}
