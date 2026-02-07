package com.example.capture.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "prompt_logs")
public class PromptLog {

    @Id
    private String id;

    // Core
    private String prompt;

    @Indexed
    private Instant capturedAt;

    private String pageUrl;

    // Client metadata
    private String userAgent;

    @Indexed
    private String deviceId;

    private String extensionVersion;
    private String sendMethod; // "enter" | "button"

    // Derived fields (helpful for analytics/search)
    private Integer promptLength;
    private String promptHash; // sha-256 hex (optional)

    public PromptLog() {}

    // Getters/Setters (generate in IDE)
    public String getId() { return id; }
    public String getPrompt() { return prompt; }
    public Instant getCapturedAt() { return capturedAt; }
    public String getPageUrl() { return pageUrl; }
    public String getUserAgent() { return userAgent; }
    public String getDeviceId() { return deviceId; }
    public String getExtensionVersion() { return extensionVersion; }
    public String getSendMethod() { return sendMethod; }
    public Integer getPromptLength() { return promptLength; }
    public String getPromptHash() { return promptHash; }

    public void setId(String id) { this.id = id; }
    public void setPrompt(String prompt) { this.prompt = prompt; }
    public void setCapturedAt(Instant capturedAt) { this.capturedAt = capturedAt; }
    public void setPageUrl(String pageUrl) { this.pageUrl = pageUrl; }
    public void setUserAgent(String userAgent) { this.userAgent = userAgent; }
    public void setDeviceId(String deviceId) { this.deviceId = deviceId; }
    public void setExtensionVersion(String extensionVersion) { this.extensionVersion = extensionVersion; }
    public void setSendMethod(String sendMethod) { this.sendMethod = sendMethod; }
    public void setPromptLength(Integer promptLength) { this.promptLength = promptLength; }
    public void setPromptHash(String promptHash) { this.promptHash = promptHash; }
}

// package com.example.capture.model;

// import org.springframework.data.annotation.Id;
// import org.springframework.data.mongodb.core.mapping.Document;

// import java.time.Instant;

// @Document(collection = "prompt_logs")
// public class PromptLog {
//     @Id
//     private String id;

//     private String prompt;
//     private String pageUrl;
//     private Instant capturedAt;

//     // basic metadata (optional)
//     private String userAgent;
//     private String source; // e.g. "chatgpt.com"

//     public PromptLog() {}

//     public PromptLog(String prompt, String pageUrl, Instant capturedAt, String userAgent, String source) {
//         this.prompt = prompt;
//         this.pageUrl = pageUrl;
//         this.capturedAt = capturedAt;
//         this.userAgent = userAgent;
//         this.source = source;
//     }

//     public String getId() { return id; }
//     public String getPrompt() { return prompt; }
//     public String getPageUrl() { return pageUrl; }
//     public Instant getCapturedAt() { return capturedAt; }
//     public String getUserAgent() { return userAgent; }
//     public String getSource() { return source; }

//     public void setId(String id) { this.id = id; }
//     public void setPrompt(String prompt) { this.prompt = prompt; }
//     public void setPageUrl(String pageUrl) { this.pageUrl = pageUrl; }
//     public void setCapturedAt(Instant capturedAt) { this.capturedAt = capturedAt; }
//     public void setUserAgent(String userAgent) { this.userAgent = userAgent; }
//     public void setSource(String source) { this.source = source; }
// }
