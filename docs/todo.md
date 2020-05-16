# TODO

## Order Service
| Directory | Package | Class | Function | Description |
|----|----|----|----|----|
| `main` | `com.ftgo.orderservice.service` | `OrderService` | `noteReversingAuthorization()` | |
| `main` | `com.ftgo.orderservice.service` | `OrderService` | `reviseMenu()` | |
| `main` | `com.ftgo.orderservice.model` | `Restaurant` | `reviseMenu`() | |
| `main` | `com.ftgo.orderservice.model` | `Restaurant` | `verifyRestaurantDetails`() | |
| `main` | `com.ftgo.orderservice.event.model` | `OrderRevisionRejectedEvent` | `OrderRevisionRejectedEvent()` | |
| `test` | `com.ftgo.orderservice.saga` | `CreateOrderSagaTest` | `shouldCreateOrder()` | Add `TicketDetails` parameter. |
| `test` | `com.ftgo.orderservice.saga` | `CreateOrderSagaTest` | `shouldRejectDueToFailedAuthorizxation()` | Add `TicketDetails` parameter. | 
| `integration-test` | `com.ftgo.orderservice.saga` | `sendAndReceiveCommand()` | Verify that replyClass is allowed. | 

## API Gateway
| Directory | Package | Class | Function | Description |
|----|----|----|----|----|
| `main` | `com.ftgo.apigateway.model` | `BillInfo` | | |
| `main` | `com.ftgo.apigateway.model` | `DeliveryInfo` | | |
| `main` | `com.ftgo.apigateway.model` | `TicketInfo` | | |
| `main` | `com.ftgo.apigateway.service.accounting` | `AccountingServiceProxy` | `findBillByOrderId()` | |
| `main` | `com.ftgo.apigateway.service.delivery` | `DeliveryServiceProxy` | `findDeliveryByOrderId()` | |
| `main` | `com.ftgo.apigateway.service.kitchen` | `KitchenServiceProxy` | `findTicketById()` | |
| `main` | `com.ftgo.apigateway.service.order` | `OrderDetails` | `OrderDetails()` | |

## Consumer Service
| Directory | Package | Class | Function | Description |
|----|----|----|----|----|
| `main` | `com.ftgo.consumerservice.model` | `Consumer` | `validateOrderByConsumer()` | |
