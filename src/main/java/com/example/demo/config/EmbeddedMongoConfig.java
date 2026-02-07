package com.example.demo.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(value = "spring.mongodb.embedded.enabled", havingValue = "true", matchIfMissing = false)
public class EmbeddedMongoConfig {
}
