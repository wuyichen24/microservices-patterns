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
### Order Workflow
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

| Method | URL | Request | Response | Description | 
|----|----|----|----|----|
| POST | `/orders` | `CreateOrderRequest` JSON | `CreateOrderResponse` JSON | Add a new order. |
| GET | `/orders/{orderId}` | | `GetOrderResponse` JSON | Get an order by order ID. |
| POST | `/orders/{orderId}/cancel` | | `GetOrderResponse` JSON | Cancel an order by order ID. |
| POST | `/orders/{orderId}/revise` | | `GetOrderResponse` JSON | Revise an order by order ID. |

## Commands
### Outbound Commands

| Target Service | Command | Saga | Description |
|----|----|----|----|
| Accounting Service | AuthorizeCommand | Create Order | Authorize the consumer's account of this order. |
| Accounting Service | ReverseAuthorizationCommand | Cancel Order | |
| Accounting Service | ReviseAuthorizationCommand | Revise Order | |
| Consumer Service | ValidateOrderByConsumerCommand | Create Order | |
| Kitchen Service | CreateTicketCommand | Create Order | |
| Kitchen Service | ConfirmCreateTicketCommand | Create Order | |
| Kitchen Service | CancelCreateTicketCommand | Create Order | |
| Kitchen Service | BeginCancelTicketCommand | Cancel Order | |
| Kitchen Service | UndoBeginCancelTicketCommand | Cancel Order | |
| Kitchen Service | ConfirmCancelTicketCommand | Cancel Order | |
| Kitchen Service | BeginReviseTicketCommand | Revise Order | |
| Kitchen Service | UndoBeginReviseTicketCommand | Revise Order | |
| Kitchen Service | ConfirmReviseTicketCommand | Revise Order | |
| Order Service | RejectOrderCommand | Create Order | |
| Order Service | ApproveOrderCommand | Create Order | |
| Order Service | BeginCancelCommand | Cancel Order | |
| Order Service | UndoBeginCancelCommand | Cancel Order | |
| Order Service | ConfirmCancelOrderCommand | Cancel Order | |
| Order Service | BeginReviseOrderCommand | Revise Order | |
| Order Service | UndoBeginReviseOrderCommand | Revise Order | |
| Order Service | ConfirmReviseOrderCommand | Revise Order | |

### Inbound Commands
- Inbound command channel name: `orderService`

## Events
### Outbound Events
- Core event entity (Aggregate root entity): Order 

### Inbound Events

### Database
