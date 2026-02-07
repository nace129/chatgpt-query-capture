package com.example.capture.repo;

import com.example.capture.model.PromptLog;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.Instant;
import java.util.List;

public interface PromptLogRepository extends MongoRepository<PromptLog, String> {
    List<PromptLog> findTop20ByDeviceIdOrderByCapturedAtDesc(String deviceId);
    List<PromptLog> findByCapturedAtBetween(Instant start, Instant end);
}

// package com.example.capture.repo;

// import org.springframework.data.mongodb.repository.MongoRepository;

// import com.example.capture.model.PromptLog;

// public interface PromptLogRepository extends MongoRepository<PromptLog, String> {}
