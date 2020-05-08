# microservices-patterns

This repository contains the source code of the book "[Microservices Patterns (Chris Richardson)](https://www.manning.com/books/microservices-patterns)".

![](https://github.com/wuyichen24/microservices-patterns/blob/master/readme/pics/Richardson-MP-HI.png)

## Overview
This source code was re-organized by the [original source code](https://github.com/microservices-patterns/ftgo-application) of the book and I make sure each module is runnable.

## Application Context
This is an application called FTGO (Food to Go). Consumers use the FTGO website or mobile application to place food orders at local restaurants. FTGO coordinates a network of couriers who deliver the orders. It's also responsible for pay couriers and restuarants. Restuarants use the FTGO website to edit their menus and manage orders. The application use s various web services, including Stripe for payments, Twilio for messaging, and Amazon Simple Email Service (SES) for email.

## Structure
- Services
   - [**Order Service**](docs/services/order_service.md): Manages orders.
   - [**Restaurant Service**](docs/services/restaurant_service.md): Maintains information about restaurants.
   - [**Consumer Service**](docs/services/consumer_service.md)
   - [**Accounting Service**](docs/services/accounting_service.md): Handles billing and payments.
   - [**Kitchen Service**](docs/services/kitchen_service.md): Manages the preparation of orders.
   - [**Order History Service**](docs/services/order_history_service.md).
   - Delivery Service: Schedule, reschedule, and cancel deliveries.
   - [**API Gateway**](docs/services/api_gateway.md)

## Technology Stack
- Core
   - Spring
      - Spring Boot
      - Spring Data
      - Spring Cloud
- Messaging
   - [Eventuate Tram](https://eventuate.io/abouteventuatetram.html)
   - [Apache Kafka](https://kafka.apache.org/) with [Apache ZooKeeper](https://zookeeper.apache.org/)
- Database
   - [MySQL](https://www.mysql.com/)
- API Gateway
   - [Spring Cloud Gateway](https://spring.io/projects/spring-cloud-gateway) with [Spring Webflux](https://docs.spring.io/spring/docs/current/spring-framework-reference/web-reactive.html)

## Differences to The Original Source Code
- Remove all the proxy classes in the Order Service so that the code organization of 3 sages (the create order sage, the cancel order sage and the revise order sage) is consistent.
- Regenerate the stub for the gRPC server in the Order Service (The gRPC code is incompleted in the original source code and was causing issues when starting the Order Service).

## Getting Started

