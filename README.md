# microservices-patterns

This repository contains the source code of the book "[Microservices Patterns (Chris Richardson)](https://www.manning.com/books/microservices-patterns)".

![](https://github.com/wuyichen24/microservices-patterns/blob/master/readme/pics/Richardson-MP-HI.png)

## Overview

## Structure
- Services
   - Order Service: Manages orders.
   - Restaurant Service: Maintains information about restaurants.
   - Consumer Service
   - Accounting Service: Handles billing and payments
   - Kitchen Service: Manages the preparation of orders
   - Order History Service

## Application Context
This is an application called FTGO (Food to Go). Consumers use the FTGO website or mobile application to place food orders at local restaurants. FTGO coordinates a network of couriers who deliver the orders. It's also responsible for pay couriers and restuarants. Restuarants use the FTGO website to edit their menus and manage orders. The application use s various web services, including Stripe for payments, Twilio for messaging, and Amazon Simple Email Service (SES) for email.
