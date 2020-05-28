# Directory Organization

## One service may have the following common directories
- **ftgo-xxxx-service**: The core code of the xxxx service.
- **ftgo-xxxx-service-api**: The shared library with other services. The classes in it can be called by other services.
- **ftgo-xxxx-service-contracts**: This directory is for contract testing only. It stores the contracts which are used to the integration tests between the xxxx servcie and other services.

## List of Directories
### Services
| Directory | Description |
|-----|-----|
| common-swagger | The code to generate API documentation (Swagger UI). |
| ftgo-accounting-service | The core code of the Accounting Service. |
| ftgo-accounting-service-api | The shared library of the Accounting Service. |
| ftgo-accounting-service-contracts | Stores the contracts of the integration between the Accounting Service and other services. |
| ftgo-api-gateway | The core code of the API Gateway. |
| ftgo-api-gateway-graphql | The GraphQL version of the API Gateway. |
| ftgo-common | The shared library for common code. |
| ftgo-common-jpa | Stores the orm.xml file for entity mapping. |
| ftgo-consumer-service | The core code of the Consumer Service. |
| ftgo-consumer-service-api | The shared library of the Consumer Service. |
| ftgo-consumer-service-contracts | Stores the contracts of the integration between the Consumer Service and other services. |
| ftgo-delivery-service | The core code of the Delivery Service. |
| ftgo-delivery-service-api | The shared library of the Delivery Service.  |
| ftgo-end-to-end-tests | The end-to-end test to test the whole application. |
| ftgo-kitchen-service |  The core code of the Kitchen Service. |
| ftgo-kitchen-service-api | The shared library of the Kitchen Service. |
| ftgo-kitchen-service-contracts | Stores the contracts of the integration between the Kitchen Service and other services. |
| ftgo-order-history-service | The core code of the Order History Service. |
| ftgo-order-service | The core code of the Order Service. |
| ftgo-order-service-api | The shared library of the Order Service. |
| ftgo-order-service-contracts | Stores the contracts of the integration between the Order Service and other services. |
| ftgo-restaurant-service | The core code of the Restaurant Service. |
| ftgo-restaurant-service-api | The shared library of the Restaurant Service. |
| ftgo-test-util | The helper classes for testing. |
