# Restaurant Service

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
| GET | `/restaurants/{restaurantId}` | | `GetRestaurantResponse` JSON | Get a restaurant by restaurant ID. |
| POST | `/restaurants` | `CreateRestaurantRequest` JSON | `CreateRestaurantResponse` JSON | Add a new restaurant. |

### gRPC APIs
**None**

## Commands
### Outbound Commands
**None**

### Inbound Commands
**None**

## Events
### Outbound Events
- Core event entity (Aggregate root entity): Restaurant

| Event | Target Service(s) | Description |
|----|----|----|
| RestaurantCreatedEvent | None | <li>The Restaurant Service notifies other services about a restaurant has been created. |

### Inbound Events
**None**

### Database
