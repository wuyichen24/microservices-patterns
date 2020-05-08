# Architecture

## Services
| Official Name | Directory Name | Application Name | Host | Port |
|---|---|---|---|---|
| Accounting Service | ftgo-accounting-service | ftgo-accounting-service | localhost | 8081 |
| Consumer Service | ftgo-consumer-service | ftgo-consumer-service | localhost | 8082 |
| Delivery Service | ftgo-delivery-service | ftgo-delivery-service | localhost | 8083 |
| Kitchen Service | ftgo-kitchen-service | ftgo-kitchen-service | localhost | 8084 |
| Order History Service | ftgo-order-history-service | ftgo-order-history-service | localhost | 8085 |
| Order Service | ftgo-order-service | ftgo-order-service | localhost | 8086 |
| Restaurant Service | ftgo-restaurant-service | ftgo-restaurant-service | localhost | 8087 |

## API Gateway
| Official Name | Directory Name | Application Name | Host | Port |
|---|---|---|---|---|
| API Gateway | ftgo-api-gateway | ftgo-api-gateway | localhost | 8080 |

## Message Queue
| Application | Host | Port | Version |
|---|---|---|---|
| Apache ZooKeeper | localhost | 2181 | 3.4.14 |
| Apache Kafka | localhost | 9092 | 2.2.0 |

### Topic
| Topic Name | Message Type | Producer | Consumer | Description |
|----|----|----|----|----|
| accountingService | Command | <li>Order Service | Accounting Service | |
| consumerService | Command | <li>Order Service | Consumer Service | |
| kitchenService | Command | <li>Order Service | Kitchen Service | |
| orderService | Command | <li>Order Service | Order Service | |
| com.ftgo.accountingservice.model.Account | Event | Accounting Service | | |
| com.ftgo.consumerservice.model.Consumer | Event | Consumer Service | <li>Accounting Service | |
| com.ftgo.orderservice.model.Order | Event | Order Service | <li>Delivery Service<li>Order History Service | |
| com.ftgo.restaurantservice.model.Restaurant | Event | Restaurant Service | <li>Order Service<li>Kitchen Service<li>Delivery Service | |
| com.ftgo.kitchenservice.model.Ticket | Event | Kitchen Service | <li>Delivery Service | | 

