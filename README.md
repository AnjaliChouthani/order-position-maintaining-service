**Order Position Maintaining Service - Task Home Assessment**



**Overview**

A Java 17 + Spring Boot project consisting of two independent services that process order updates and maintain the current net position of trading symbols.

**Services**

1. Order Update Service

Reads order updates from a CSV file incrementally.
Validates each order and skips invalid rows.
Handles duplicate event_id.
Sends valid events to the Position Maintaining Service using HTTP.
Processes events in CSV order with a maximum rate of 50 events/second.

2. Position Maintaining Service

Receives order events through a REST API.
Maintains the net position for each symbol in memory.
BUY increases the position and SELL decreases it.
Ignores duplicate events.
Provides the current positions through GET /position.


**Architecture**
```text
CSV File
   |
   v
Order Update Service
   |
   | HTTP
   v
Position Maintaining Service
   |
   | GET /position
   v
Current Positions
```

**Tech Stack**

- Java 17
- Spring Boot
- Spring Web / REST
- Maven
- Jakarta Validation
- HTTP
- In-memory data structures


**Example**

```csv
event_id,symbol,transaction_type,quantity
evt-0001,RELIANCE,BUY,90
evt-0002,TCS,SELL,75
evt-0003,RELIANCE,SELL,30
```

Response:

{
  "RELIANCE": 60,
  "TCS": -75
}

**Configuration**

Service ports, input CSV path, and Position Maintaining Service URL are configurable through application.properties.

**How to Run**

Start Position Maintaining Service:

```cmd
cd PositionMaintainingService
mvnw.cmd spring-boot:run
```

Start Order Update Service in another terminal:

```cmd
cd OrderUpdateService
mvnw.cmd spring-boot:run
```

Tests

Run tests using:
```cmd
mvnw.cmd test
```

Tests cover position calculations, duplicate events, invalid inputs, multiple symbols, zero/negative positions, and the /position API.

**Limitations**
Position and processed event IDs are stored in memory.
State is lost after service restart.
No database or external message broker is used.
Durable/exactly-once delivery is not implemented.


**AI-Assisted Development**

AI-assisted tools were used for understanding requirements, debugging, exploring implementation approaches, and documentation. All submitted code and design decisions were reviewed and understood.
