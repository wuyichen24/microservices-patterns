# Delivery Service

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
| GET | `/deliveries/{deliveryId}` | | `DeliveryStatus` JSON | Get delivery information by delivery ID. |
| POST | `/couriers/{courierId}/availability` | `CourierAvailability` JSON | "OK" | Update a courier availability by courier ID. |

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
| Kitchen Service | TicketAcceptedEvent | <li>The Kitchen Service notifies other services about an order (ticket) has been accepted.<li>The Delivery Service will assign an available courier to this order. |
| Kitchen Service | TicketCancelledEvent | <li>The Kitchen Service notifies other services about an order (ticket) has been cancelled.<li>The Delivery Service will remove this order from the delivery plan of the assigned courier. |
| Order Service | OrderCreatedEvent | <li>The Order Service notifies other services about a new order has been created.<li>The Delivery Service will create a delivery info record for this order. |
| Restaurant Service | RestaurantCreatedEvent | The Restaurant Service notifies other services about a new restaurant record has been created.The Delivery Service will create a same new restaurant record in its database correspondingly. |

### Database
