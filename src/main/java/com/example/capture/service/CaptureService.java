package com.example.capture.service;

import com.example.capture.dto.CaptureRequest;
import com.example.capture.model.PromptLog;
import com.example.capture.repo.PromptLogRepository;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.HexFormat;
import java.util.List;

@Service
public class CaptureService {

    private final PromptLogRepository repo;

    public CaptureService(PromptLogRepository repo) {
        this.repo = repo;
    }

    public PromptLog save(CaptureRequest req) {
        PromptLog log = new PromptLog();

        String prompt = req.prompt.trim();
        log.setPrompt(prompt);

        log.setPageUrl(req.pageUrl);
        log.setUserAgent(req.userAgent);
        log.setDeviceId(req.deviceId);
        log.setExtensionVersion(req.extensionVersion);
        log.setSendMethod(normalizeSendMethod(req.sendMethod));

        log.setCapturedAt(parseInstantOrNow(req.capturedAt));

        log.setPromptLength(prompt.length());
        log.setPromptHash(sha256Hex(prompt));

        return repo.save(log);
    }

    public List<PromptLog> findAll() {
        return repo.findAll();
    }

    public long count() {
        return repo.count();
    }

    private String normalizeSendMethod(String s) {
        if (s == null) return null;
        s = s.trim().toLowerCase();
        return (s.equals("enter") || s.equals("button")) ? s : "unknown";
    }

    private Instant parseInstantOrNow(String iso) {
        try {
            if (iso == null || iso.isBlank()) return Instant.now();
            return Instant.parse(iso);
        } catch (Exception e) {
            return Instant.now();
        }
    }

    private String sha256Hex(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(input.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (Exception e) {
            // If hashing fails, don't block saving the prompt
            return null;
        }
    }
}
