# gRPC

## Overview
- Only the Order Service is implemented the gRPC APIs.

## Generate Stub
- The proto file is located in: `ftgo-order-service/src/main/proto/OrderService.proto`
   - Options
     | Option | Description |
     |----|----|
     | `java_multiple_files` | |
     | `java_package` | |
     | `java_outer_classname` | |
     
- Command for generating stub
  `./gradlew :ftgo-order-service:generateProto`
