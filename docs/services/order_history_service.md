# Order History Service

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
[API Documentation (Swagger UI)](http://localhost:8085/swagger-ui.html)
| Method | URL | Request | Response | Description | 
|----|----|----|----|----|
| GET | `/orders?consumerId={consumerId}` | | `GetOrdersResponse` JSON | Get all the historical orders from the consumer. |
| GET | `/orders/{orderId}` | | `GetOrderResponse` JSON | Get a historical order by order ID. | 

### gRPC APIs
**None**

## Commands
### Outbound Commands
**None**

### Inbound Commands
**None**

## Events
### Outbound Events
**None**

### Inbound Events
| Source Service | Event | Description |
|----|----|----|
| Order Service | OrderCreatedEvent | <li>The Order Service notifies other services about a new order record has been created.<li>The Order History Service will create a corresponding historical order entry in its database. |

### Database
