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
