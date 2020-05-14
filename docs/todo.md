# TODO

## Order Service
| Directory | Package | Class | Function | Description |
|----|----|----|----|----|
| `main` | `com.ftgo.orderservice.service` | `OrderService` | `noteReversingAuthorization()` | |
| `main` | `com.ftgo.orderservice.service` | `OrderService` | `reviseMenu()` | |
| `main` | `com.ftgo.orderservice.model` | `Restaurant` | `reviseMenu`() | |
| `main` | `com.ftgo.orderservice.model` | `Restaurant` | `verifyRestaurantDetails`() | |
| `main` | `com.ftgo.orderservice.event.model` | `OrderRevisionRejectedEvent` | `OrderRevisionRejectedEvent()` | |
| `test` | `com.ftgo.orderservice.saga` | `CreateOrderSagaTest` | `shouldCreateOrder()` | Add `TicketDetails` parameter |
| `test` | `com.ftgo.orderservice.saga` | `CreateOrderSagaTest` | `shouldRejectDueToFailedAuthorizxation()` | Add `TicketDetails` parameter |
