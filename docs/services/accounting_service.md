# Accounting Service

- [**Business Logic**](#business-logic)
- [**APIs**](#apis)
- [**Commands**](#commands)
   - [Outbound Commands](#outbound-commands)
   - [Inbound Commands](#inbound-commands)
- [**Events**](#events)
   - [Outbound Events](#outbound-events)
   - [Inbound Events](#inbound-events)
- [**Database**](#database)

## Business Logic

## APIs
| Method | URL | Request | Response | Description | 
|----|----|----|----|----|
| GET | `/accounts//{accountId}` | | `GetAccountResponse` JSON | Get an account by account ID. |

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
### Inbound Events

### Database
