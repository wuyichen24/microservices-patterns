# Order Service

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
### Ticket Workflow
![](../diagrams/order_workflow.png)

| State | Description |
|----|----|
| **APPROVAL_PENDING** | A new order has been created locally. Wait for other services finish their corresponding operations for creating a order, like the consumer’s credit card will be authorized. |
| **APPROVED** | The order has been approved. The consumer’s credit card has been authorized. |
| **REJECTED** | The order has been rejected. One of the services rejected the order or authorization failed. |
| **CANCEL_PENDING** | The order start to be cancelled. Wait for other services finish their corresponding operations for cancelling the order. |
| **CANCELLED** | The order has been cancelled. |
| **REVISION_PENDING** | The order start to be revised. Wait for other services finish their corresponding operations for revising the order. |

## APIs

## Commands
### Outbound Commands
### Inbound Commands
- Inbound command channel name: `orderService`

## Events
### Outbound Events
- Core event entity (Aggregate root entity): Order 

### Inbound Events

### Database
