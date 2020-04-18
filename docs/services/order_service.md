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
### Sagas (Chain Operations)
- **Create order**
  | Step No. | Service | Command | Compensation Command (for rollback) | 
  |----|----|----|----|
  | 1 | Order Service | | RejectOrderCommand |
  | 2 | Consumer Service | ValidateOrderByConsumerCommand | |
  | 3 | Kitchen Service | CreateTicketCommand | CancelCreateTicketCommand |
  | 4 | Accounting Service | AuthorizeCommand | |
  | 5 | Kitchen Service | ConfirmCreateTicketCommand | |
  | 6 | Order Service | ApproveOrderCommand | |
  
- **Cancel order**
  | Step No. | Service | Command | Compensation Command (for rollback) | 
  |----|----|----|----|
  | 1 | Order Service | BeginCancelCommand | UndoBeginCancelCommand |
  | 2 | Kitchen Service | BeginCancelTicketCommand | UndoBeginCancelTicketCommand |
  | 3 | Accounting Service | ReverseAuthorizationCommand | |
  | 4 | Kitchen Service | ConfirmCancelTicketCommand | |
  | 5 | Order Service | ConfirmCancelOrderCommand | |

- **Revise order**
  | Step No. | Service | Command | Compensation Command (for rollback) | 
  |----|----|----|----|
  | 1 | Order Service | BeginReviseOrderCommand | UndoBeginReviseOrderCommand |
  | 2 | Kitchen Service | BeginReviseTicketCommand | UndoBeginReviseTicketCommand |
  | 3 | Accounting Service | ReviseAuthorizationCommand | |
  | 4 | Kitchen Service | ConfirmReviseTicketCommand | |
  | 5 | Order Service | ConfirmReviseOrderCommand | |

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
| Accounting Service | AuthorizeCommand | Create order | |
| Accounting Service | ReverseAuthorizationCommand | Cancel order | |
| Accounting Service | ReviseAuthorizationCommand | Revise order | |
| Consumer Service | ValidateOrderByConsumerCommand | Create order | |
| Kitchen Service | CreateTicketCommand | Create order | |
| Kitchen Service | ConfirmCreateTicketCommand | Create order | |
| Kitchen Service | CancelCreateTicketCommand | Create order | |
| Kitchen Service | BeginCancelTicketCommand | Cancel order | |
| Kitchen Service | UndoBeginCancelTicketCommand | Cancel order | |
| Kitchen Service | ConfirmCancelTicketCommand | Cancel order | |
| Kitchen Service | BeginReviseTicketCommand | Revise order | |
| Kitchen Service | UndoBeginReviseTicketCommand | Revise order | |
| Kitchen Service | ConfirmReviseTicketCommand | Revise order | |
| Order Service | RejectOrderCommand | Create order | |
| Order Service | ApproveOrderCommand | Create order | |
| Order Service | BeginCancelCommand | Cancel order | |
| Order Service | UndoBeginCancelCommand | Cancel order | |
| Order Service | ConfirmCancelOrderCommand | Cancel order | |
| Order Service | BeginReviseOrderCommand | Revise order | |
| Order Service | UndoBeginReviseOrderCommand | Revise order | |
| Order Service | ConfirmReviseOrderCommand | Revise order | |

### Inbound Commands
- Inbound command channel name: `orderService`

| Command | Description |
|-----|----|
| RejectOrderCommand | |
| ApproveOrderCommand | |
| BeginCancelCommand | |
| UndoBeginCancelCommand | |
| ConfirmCancelOrderCommand | |
| BeginReviseOrderCommand | |
| UndoBeginReviseOrderCommand | |
| ConfirmReviseOrderCommand | |

## Events
### Outbound Events
- Core event entity (Aggregate root entity): Order 

### Inbound Events

### Database
