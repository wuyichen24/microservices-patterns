# Consumer Service

- [**Business Logic**](#business-logic)
- [**APIs**](#apis)
   - [RESTful APIs](#restful-apis)
   - [gRPC APIs](#grpc-apis)
- [**Commands**](#commands)
   - [Outbound Commands](#outbound-commands)
   - [Inbound Commands](#inbound-commands)
- [**Events**](#events)
   - [Outbound Events](#outbound-events)
   - [Inbound Events](#inbound-events)
- [**Database**](#database)

## Business Logic

## APIs
### RESTful APIs
[API Documentation (Swagger UI)](http://localhost:8082/swagger-ui.html)
| Method | URL | Request | Response | Description | 
|----|----|----|----|----|
| POST | `/consumers` | `CreateConsumerRequest` JSON | `CreateConsumerResponse` JSON | Add a new consumer. |
| GET | `/consumers/{consumerId}` | | `GetConsumerResponse` JSON | Get a consumer by consumer ID. |

### gRPC APIs
**None**

## Commands
### Outbound Commands
### Inbound Commands
- Inbound command channel name: consumerService

| Command | Description |
|----|----|
| ValidateOrderByConsumerCommand | |

## Events
### Outbound Events
| Event | Target Service(s) | Description |
|----|----|----|
| ConsumerCreatedEvent | | |

### Inbound Events
**None**

### Database
- **consumer**
  | **Column** | **Type**| **Properties** |
  |----|----|----|
  | id | bigint(20) | PRI | 
  | first_name | varchar(255) | | 
  | last_name | varchar(255) | | 
