# Directory Organization

## One service may have the following common directories
- **ftgo-xxxx-service**: The core code of the xxxx service.
- **ftgo-xxxx-service-api**: The shared library with other services. The classes in it can be called by other services.
- **ftgo-xxxx-service-contracts**: This directory is for contract testing only. It stores the contracts which are used to the integration tests between the xxxx servcie and other services.
