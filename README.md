# microservices-patterns

This repository contains the source code of the book "[Microservices Patterns (Chris Richardson)](https://www.manning.com/books/microservices-patterns)".

![](https://github.com/wuyichen24/microservices-patterns/blob/master/readme/pics/Richardson-MP-HI.png)

## Overview
This source code was re-organized by the [original source code](https://github.com/microservices-patterns/ftgo-application) of the book and I make sure each module is runnable.

## Application Context
This is an application called FTGO (Food to Go). Consumers use the FTGO website or mobile application to place food orders at local restaurants. FTGO coordinates a network of couriers who deliver the orders. It's also responsible for pay couriers and restuarants. Restuarants use the FTGO website to edit their menus and manage orders. The application use s various web services, including Stripe for payments, Twilio for messaging, and Amazon Simple Email Service (SES) for email.

## Structure
- Services
   - Order Service: Manages orders.
   - Restaurant Service: Maintains information about restaurants.
   - Consumer Service
   - [**Accounting Service**](docs/services/accounting_service.md): Handles billing and payments.
   - [**Kitchen Service**](docs/services/kitchen_service.md): Manages the preparation of orders.
   - Order History Service.
   - Delivery Service.

## Technology Stack
- Core
   - Spring
      - Spring Boot
      - Spring Data
      - Spring Cloud
- Message Queue
- Database

## Differences to The Original Source Code
- Remove all the proxy classes in the Order Service so that the code organization of 3 sages (the create order sage, the cancel order sage and the revise order sage) is consistent.

## Getting Started

