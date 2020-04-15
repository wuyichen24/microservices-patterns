# Code Orgnization for Interservice Communication

- [Command](#command)
- [Event](#event)

## Command
![](diagrams/interservice_communication.png)

| Class | Description | Example |
|----|----|----|
| **Controller** | Defines RESTful APIs | <li>[OrderServiceController](../ftgo-order-service/src/main/java/com/ftgo/orderservice/controller/OrderServiceController.java) |
| **Service** | Defines the core operations for the service. | <li>[OrderService](../ftgo-order-service/src/main/java/com/ftgo/orderservice/service/OrderService.java) |
| **Saga** | Defines the Saga process for a specific operation. <ul><li>Each Saga process consists of multiples steps (step = state).<li>Each step consists of a command and the info of which service to execute that command.</ul> | <li>[CreateOrderSaga](../ftgo-order-service/src/main/java/com/ftgo/orderservice/saga/createorder/CreateOrderSaga.java) |
| **Proxy** | Defines the command endpoints for connecting a service-specific channel and sending the commands to a specific service. | <li>[KitchenServiceProxy](../ftgo-order-service/src/main/java/com/ftgo/orderservice/saga/proxy/KitchenServiceProxy.java) |
| **Commands Handler** | Defines how to handle each command. <ul><li>Defines mapping relationship between the commands and the command handlers.<li>Each command handler defines the operations need to be executed after receiving a specific command.</ul> | <li>[OrderServiceCommandHandlers](../ftgo-order-service/src/main/java/com/ftgo/orderservice/command/OrderServiceCommandHandlers.java) |

## Event
