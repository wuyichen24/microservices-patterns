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

## Events
### Outbound Events
### Inbound Events

### Database
