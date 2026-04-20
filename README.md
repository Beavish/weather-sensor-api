# Weather Sensor API

A Spring Boot REST API for ingesting and querying weather sensor data.
Built as a backend engineering exercise focusing on clean architecture, validation, and testing.

---

## 🚀 Features

* Create weather readings (temperature, humidity, wind speed)
* Query aggregated statistics (e.g. AVG, MIN, MAX)
* Validation on request payloads (including custom time range validation)
* Global exception handling with structured error responses
* H2 database for local development
* Unit + integration (E2E) tests

---

## 🛠️ Tech Stack

* Java 17
* Spring Boot
* Spring Web MVC
* Spring Data JPA
* H2 Database
* Maven
* JUnit 5 / MockMvc

---

## 📦 Project Structure

```
controller   → REST endpoints  
service      → business logic  
repository   → database access  
entity       → JPA entities  
dto          → request/response models  
validation   → custom validators  
exception    → global error handling  
```

---

## ▶️ Running the Application

```bash
mvn clean install
mvn spring-boot:run
```

App runs on:

```
http://localhost:8080
```

---

## 🧪 Running Tests

```bash
mvn test
```

Tests use an **in-memory H2 database** (`application-test.yml`) to avoid conflicts with local data.

---

## 📡 API Endpoints

### 1. Create Weather Reading

**POST** `/api/v1/weather-readings`

Example request:

```json
{
  "sensorId": "sensor-1",
  "temperature": 20.0,
  "humidity": 60.0,
  "windSpeed": 10.0,
  "timestamp": "2026-04-19T18:00:00Z"
}
```

Response:

```
201 Created
```

---

### 2. Query Weather Data

**POST** `/api/v1/weather-readings/query`

Example request:

```json
{
  "sensorIds": ["sensor-1"],
  "metrics": ["TEMPERATURE"],
  "stat": "AVG",
  "from": "2026-04-18T18:00:00Z",
  "to": "2026-04-19T18:00:00Z"
}
```

⚠️ Note:

* Time range must be **at least 1 day** (validated via custom annotation)

---

## ❗ Error Handling

All errors return a consistent structure:

```json
{
  "timestamp": "2026-04-19T17:29:54Z",
  "status": 400,
  "error": "Validation Failed",
  "message": "Request validation failed",
  "details": {
    "metrics": "At least one metric must be provided"
  }
}
```

---

## 🧪 Testing Approach

* **Unit tests**

    * Service layer logic
    * Validation rules

* **Integration (E2E) tests**

    * Full request → DB → response flow using MockMvc

---



## 👨‍💻 Author

Kieran Williams
