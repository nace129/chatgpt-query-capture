package com.example.capture.controller;

import com.example.capture.dto.CaptureRequest;
import com.example.capture.model.PromptLog;
import com.example.capture.service.CaptureService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CaptureController {

    private final CaptureService service;

    public CaptureController(CaptureService service) {
        this.service = service;
    }

    @PostMapping("/capture")
    public ResponseEntity<?> capture(@Valid @RequestBody CaptureRequest req) {
        PromptLog saved = service.save(req);
        return ResponseEntity.ok(saved.getId());
    }

    @GetMapping("/capture")
    public ResponseEntity<List<PromptLog>> getAllCaptures() {
        List<PromptLog> allLogs = service.findAll();
        return ResponseEntity.ok(allLogs);
    }

    @GetMapping("/capture/count")
    public ResponseEntity<Long> getCount() {
        long count = service.count();
        return ResponseEntity.ok(count);
    }
}

// package com.example.capture;

// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import com.example.capture.model.PromptLog;
// import com.example.capture.repo.PromptLogRepository;

// import java.time.Instant;

// @RestController
// @RequestMapping("/api")
// public class CaptureController {

//     private final PromptLogRepository repo;

//     public CaptureController(PromptLogRepository repo) {
//         this.repo = repo;
//     }

//     public static class CaptureRequest {
//         public String prompt;
//         public String pageUrl;
//         public String capturedAt; // ISO string from browser (optional)
//         public String userAgent;
//         public String source;
//         public String apiKey; // simple protection (see below)
//     }

//     @PostMapping("/capture")
//     public ResponseEntity<?> capture(@RequestBody CaptureRequest req) {
//         // Minimal validation
//         if (req == null || req.prompt == null || req.prompt.trim().isEmpty()) {
//             return ResponseEntity.badRequest().body("Missing prompt");
//         }

//         // Very simple protection: require apiKey (replace with real auth later)
//         if (req.apiKey == null || !req.apiKey.equals(System.getenv().getOrDefault("CAPTURE_API_KEY", "dev-key"))) {
//             return ResponseEntity.status(401).body("Unauthorized");
//         }

//         Instant ts;
//         try {
//             ts = (req.capturedAt != null) ? Instant.parse(req.capturedAt) : Instant.now();
//         } catch (Exception e) {
//             ts = Instant.now();
//         }

//         PromptLog saved = repo.save(new PromptLog(
//                 req.prompt.trim(),
//                 req.pageUrl,
//                 ts,
//                 req.userAgent,
//                 req.source != null ? req.source : "chatgpt.com"
//         ));

//         return ResponseEntity.ok(saved.getId());
//     }
// }
