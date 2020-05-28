# Accounting Service

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
[API Documentation (Swagger UI)](http://localhost:8081/swagger-ui.html)
| Method | URL | Request | Response | Description | 
|----|----|----|----|----|
| GET | `/accounts/{accountId}` | | `GetAccountResponse` JSON | Get an account by account ID. |

### gRPC APIs
**None**

## Commands
### Outbound Commands
**None**

### Inbound Commands
- Inbound command channel name: `accountingService`

| Command | Description |
|-----|----|
| AuthorizeCommand | |
| ReverseAuthorizationCommand | |
| ReviseAuthorizationCommand | |

## Events
### Outbound Events

| Event | Target Service(s) | Description |
|----|----|----|
| AccountAuthorizationFailedEvent | None | <li>The Accounting Service notifies other services about the authorization of an account failed. |
| AccountAuthorizedEvent | None | <li>The Accounting Service notifies other services about the authorization of an account successed |
| AccountCreatedEvent | None | <li>The Accounting Service notifies other services about an account has been created. |

### Inbound Events

| Source Service | Event | Description |
|----|----|----|
| Consumer Service | ConsumerCreatedEvent | <li>The Consumer Service notifies other services about a new consumer record has been created.<li>The Accounting Service will create a new account record for the new consumer record. |
   
### Database
