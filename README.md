# GPT Query Capture API

A Spring Boot application that captures and stores ChatGPT queries with metadata in MongoDB. This API is designed to log user interactions with ChatGPT, including prompts, page URLs, device information, and timestamps.

## 🚀 Features

- **Prompt Capture**: Store ChatGPT queries with validation
- **Metadata Collection**: Track page URLs, user agents, device IDs, and extension versions
- **MongoDB Integration**: Persistent storage with local or cloud MongoDB
- **REST API**: Simple endpoints for capturing and retrieving data
- **Data Validation**: Input validation using Jakarta Bean Validation
- **CORS Support**: Configured for web extension integration

## 🛠️ Tech Stack

- **Java 17**
- **Spring Boot 4.0.2**
- **Spring Data MongoDB 5.0.2**
- **Jakarta Validation**
- **Gradle**
- **MongoDB** (Local or Atlas)

## 📋 Prerequisites

- Java 17 or higher
- MongoDB (local installation or Atlas account)
- Gradle (included via wrapper)

## 🔧 Installation & Setup

### 1. Clone the Repository
```bash
git clone <your-repo-url>
cd gptparser
```

### 2. MongoDB Setup

#### Option A: Local MongoDB
```bash
# Install MongoDB Community Edition
brew install mongodb-community

# Start MongoDB service
brew services start mongodb-community
```

#### Option B: MongoDB Atlas
1. Create a MongoDB Atlas account
2. Create a cluster
3. Add your IP to the network access list
4. Create a database user
5. Get your connection string

### 3. Configuration

Update `src/main/resources/application.properties`:

#### For Local MongoDB:
```properties
spring.application.name=gptparser
spring.data.mongodb.uri=mongodb://localhost:27017/gptparser
server.port=8080
```

#### For MongoDB Atlas:
```properties
spring.application.name=gptparser
spring.data.mongodb.uri=mongodb+srv://username:password@cluster.mongodb.net/gptparser?retryWrites=true&w=majority
server.port=8080
```

### 4. Run the Application
```bash
./gradlew bootRun
```

The application will start on `http://localhost:8080`

## 📖 API Documentation

### Capture Prompt
**POST** `/api/capture`

Stores a new ChatGPT query with metadata.

#### Request Body:
```json
{
  "prompt": "What is machine learning?",
  "pageUrl": "https://chat.openai.com",
  "capturedAt": "2026-02-07T20:11:22.404Z",
  "userAgent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7)...",
  "deviceId": "unique-device-identifier",
  "extensionVersion": "1.0.0",
  "sendMethod": "enter"
}
```

#### Validation Rules:
- `prompt`: Required, max 20,000 characters
- `pageUrl`: Optional, max 2,000 characters
- `capturedAt`: Optional ISO timestamp
- `userAgent`: Optional, max 400 characters
- `deviceId`: Optional, max 120 characters
- `extensionVersion`: Optional, max 40 characters
- `sendMethod`: Optional, max 10 characters ("enter" or "button")

#### Response:
```json
"67d75a1b2c3d4e5f6789abcd"
```
Returns the MongoDB document ID of the saved entry.

### Get All Captures
**GET** `/api/capture`

Retrieves all stored prompts.

#### Response:
```json
[
  {
    "id": "67d75a1b2c3d4e5f6789abcd",
    "prompt": "What is machine learning?",
    "pageUrl": "https://chat.openai.com",
    "capturedAt": "2026-02-07T20:11:22.404Z",
    "userAgent": "Mozilla/5.0...",
    "deviceId": "unique-device-identifier",
    "extensionVersion": "1.0.0",
    "sendMethod": "enter",
    "promptLength": 23,
    "promptHash": "abc123..."
  }
]
```

### Get Count
**GET** `/api/capture/count`

Returns the total number of stored captures.

#### Response:
```json
42
```

## 🏗️ Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/example/
│   │       ├── demo/
│   │       │   └── GptparserApplication.java     # Main application class
│   │       └── capture/
│   │           ├── controller/
│   │           │   └── CaptureController.java    # REST endpoints
│   │           ├── dto/
│   │           │   └── CaptureRequest.java       # Request validation
│   │           ├── model/
│   │           │   └── PromptLog.java           # MongoDB entity
│   │           ├── repo/
│   │           │   └── PromptLogRepository.java # Data access
│   │           ├── service/
│   │           │   └── CaptureService.java      # Business logic
│   │           └── config/
│   │               └── WebConfig.java           # CORS configuration
│   └── resources/
│       └── application.properties               # Configuration
└── test/
    └── java/
        └── com/example/demo/
            └── GptparserApplicationTests.java   # Test class
```

## 🔍 Data Model

### PromptLog Entity
```java
@Document(collection = "prompt_logs")
public class PromptLog {
    @Id
    private String id;
    
    private String prompt;           // The ChatGPT query
    private String pageUrl;          // Source page URL
    @Indexed
    private Instant capturedAt;      // When captured
    private String userAgent;        // Browser info
    private String deviceId;         // Device identifier
    private String extensionVersion; // Extension version
    private String sendMethod;       // How sent ("enter"/"button")
    private Integer promptLength;    // Character count
    private String promptHash;       // SHA-256 hash
}
```

## 🧪 Testing

### Run Tests
```bash
./gradlew test
```

### Manual API Testing

#### Add a capture:
```bash
curl -X POST http://localhost:8080/api/capture \
  -H "Content-Type: application/json" \
  -d '{
    "prompt": "Explain quantum computing",
    "pageUrl": "https://chat.openai.com",
    "deviceId": "test-device-123",
    "sendMethod": "enter"
  }'
```

#### Get all captures:
```bash
curl http://localhost:8080/api/capture
```

#### Get count:
```bash
curl http://localhost:8080/api/capture/count
```

## 🔧 Database Management

### View Data with MongoDB Compass
1. Install: `brew install mongodb-compass`
2. Open and connect to: `mongodb://localhost:27017`
3. Navigate to `gptparser` database → `prompt_logs` collection

### View Data with MongoDB Shell
```bash
# Connect to database
mongosh mongodb://localhost:27017/gptparser

# View all documents
db.prompt_logs.find().pretty()

# Count documents
db.prompt_logs.countDocuments()

# Find recent entries
db.prompt_logs.find().sort({capturedAt: -1}).limit(5)
```

## 🚨 Troubleshooting

### Common Issues

#### 1. MongoDB Connection Failed
**Error**: `MongoSocketOpenException: Exception opening socket`

**Solutions**:
- Ensure MongoDB is running: `brew services start mongodb-community`
- Check connection string in `application.properties`
- For Atlas: verify IP whitelist and credentials

#### 2. Bean Validation Errors
**Error**: `javax.validation cannot be resolved`

**Solution**: Ensure you're using Jakarta validation imports:
```java
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
```

#### 3. Repository Not Found
**Error**: `No qualifying bean of type 'PromptLogRepository'`

**Solution**: Ensure main class has proper annotations:
```java
@SpringBootApplication(scanBasePackages = {"com.example.demo", "com.example.capture"})
@EnableMongoRepositories(basePackages = "com.example.capture.repo")
```

## 📝 Development Notes

### Key Configuration Changes Made:
1. **Package Scanning**: Added `scanBasePackages` to include capture package
2. **MongoDB Repositories**: Enabled with `@EnableMongoRepositories`
3. **Validation**: Migrated from `javax.validation` to `jakarta.validation`
4. **CORS**: Configured for cross-origin requests
5. **Dependencies**: Added validation starter for Spring Boot 4.x

### Migration from javax to jakarta:
Spring Boot 3.0+ requires Jakarta EE instead of Java EE:
- `javax.validation` → `jakarta.validation`
- Added `spring-boot-starter-validation` dependency explicitly

## 🎯 Future Enhancements

- [ ] Add pagination for GET endpoints
- [ ] Implement search and filtering
- [ ] Add authentication/authorization
- [ ] Create database indexes for better performance
- [ ] Add API rate limiting
- [ ] Implement data export functionality
- [ ] Add metrics and monitoring

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📞 Support

If you encounter any issues or have questions, please open an issue on GitHub.
