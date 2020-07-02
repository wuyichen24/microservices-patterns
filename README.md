# microservices-patterns

[![Build Status](https://travis-ci.com/wuyichen24/microservices-patterns.svg?branch=master)](https://travis-ci.com/wuyichen24/microservices-patterns)
[![License](https://img.shields.io/badge/License-Apache%202.0-green.svg)](https://opensource.org/licenses/Apache-2.0) 

This repository contains the source code of the book "[Microservices Patterns (Chris Richardson)](https://www.manning.com/books/microservices-patterns)" and the personal study note of Eventuate Tram Framework (The whole solution of transactional messaging for Spring Boot microservices).

![](docs/Richardson-MP-HI.png)

## Overview
This source code was re-organized by the [original source code](https://github.com/microservices-patterns/ftgo-application) of the book and I make sure each module is runnable.

## Application Context
This is an application called FTGO (Food to Go). Consumers use the FTGO website or mobile application to place food orders at local restaurants. FTGO coordinates a network of couriers who deliver the orders. It's also responsible for pay couriers and restuarants. Restuarants use the FTGO website to edit their menus and manage orders. The application use s various web services, including Stripe for payments, Twilio for messaging, and Amazon Simple Email Service (SES) for email.

## Structure
![](docs/diagrams/architecture.png)
- **Business Logic Services**
   - [**Accounting Service**](docs/services/accounting_service.md): Handles billing and payments.
   - [**Consumer Service**](docs/services/consumer_service.md): Manages consumer accounts.
   - [**Delivery Service**](docs/services/delivery_service.md): Schedule, reschedule, and cancel deliveries.
   - [**Kitchen Service**](docs/services/kitchen_service.md): Manages the preparation of orders.
   - [**Order History Service**](docs/services/order_history_service.md): Collects order history.
   - [**Order Service**](docs/services/order_service.md): Manages orders.
   - [**Restaurant Service**](docs/services/restaurant_service.md): Maintains information about restaurants.
- **API Gateway**
   - [**API Gateway**](docs/services/api_gateway.md): Routes requests to downstream services and API Composition (Aggregation).
- **CDC Pipeline**
   - [**Eventuate CDC Service**](docs/services/eventuate_cdc_service.md): Helps other services publish messages from database to message queues.

## Technology Stack
- Core
   - Spring
      - Spring Boot
      - Spring Data
      - Spring Cloud
- Messaging
   - [Apache Kafka](https://kafka.apache.org/) with [Apache ZooKeeper](https://zookeeper.apache.org/)
   - [Eventuate Tram](https://eventuate.io/abouteventuatetram.html) (Transactional messaging framework)
- Database
   - [MySQL](https://www.mysql.com/)
- API Gateway
   - [Spring Cloud Gateway](https://spring.io/projects/spring-cloud-gateway) with [Spring Webflux](https://docs.spring.io/spring/docs/current/spring-framework-reference/web-reactive.html)

## Differences to The Original Source Code
- Removed all the proxy classes in the Order Service so that the code organization of 3 sages (the create order sage, the cancel order sage and the revise order sage) is consistent.
- Regenerated the stub for the gRPC server in the Order Service (The gRPC code is incompleted in the original source code and was causing issues when starting the Order Service).
- Finished the routing functionality for the Accounting Service, the Consumer Service, the Delivery Service and the Kitchen Service in the API gateway (Only the routing logic of the Order Service was finished in the original source code).
- Changed the port number of all the services and the API gateway.
- Replaced hardcoded channel names in multiple places. Centralized those channel names in one place.
- Fixed some bugs.
- Added more logging.
- Enhanced API documentation (Swagger UI).

## Study Notes
- [Study Notes](https://docs.google.com/document/d/1MkeEUyWfDU7HYYCo26GyaVbBHIS29iZ6FnOxuJ_4JHc/edit?usp=sharing)

## Documentation
- [Getting Started](docs/getting_started.md)
- [Architecture](docs/architecture.md)
- [Code Orgnization for Interservice Communication](docs/code_orgnization_for_interservice_communication.md)
- [Directory Organization](docs/directory_organization.md)
- [gRPC](docs/grpc.md)
- [Command Memo](docs/command_memo.md)
- [Eventuate CDC Service](docs/services/eventuate_cdc_service.md)
- [TODO List](docs/todo.md)
