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
[API Documentation (Swagger UI)](http://localhost:8083/swagger-ui.html)
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
- **delivery**
  | **Column** | **Type**| **Properties** |
  |----|----|----|
  | id | bigint(20) | PRI |
  | assigned_courier | bigint(20) | |
  | delivery_city | varchar(255) | |
  | delivery_state | varchar(255) | |
  | delivery_street1 | varchar(255) | |
  | delivery_street2 | varchar(255) | |
  | delivery_zip | varchar(255) | |
  | delivery_time | datetime | |
  | pick_up_time | datetime | |
  | pickup_city | varchar(255) | |
  | pickup_state | varchar(255) | |
  | pickup_street1 | varchar(255) | |
  | pickup_street2 | varchar(255) | |
  | pickup_zip | varchar(255) | |
  | restaurant_id | bigint(20) | |
  | state | varchar(255) | |

- **courier**
  | **Column** | **Type**| **Properties** |
  |----|----|----|
  | id | bigint(20) | PRI | 
  | available | bit(1) | |

- **courier_actions**
  | **Column** | **Type**| **Properties** |
  |----|----|----|
  | courier_id | bigint(20) | MUL |
  | delivery_id | bigint(20) | |
  | time | datetime | |
  | type | varchar(255) | |
  | city | varchar(255) | |
  | state | varchar(255) | |
  | street1 | varchar(255) | |
  | street2 | varchar(255) | |
  | zip | varchar(255) | |

- **delivery_service_restaurants**
  | **Column** | **Type**| **Properties** |
  |----|----|----|
  | id | bigint(20) | PRI |
  | city | varchar(255) | |
  | state | varchar(255) | |
  | street1 | varchar(255) | |
  | street2 | varchar(255) | |
  | zip | varchar(255) | |
  | restaurant_name | varchar(255) | |
