# gRPC

## Overview
- Only the Order Service is implemented the gRPC APIs.

## Generate Stub
- The proto file is located in: `ftgo-order-service/src/main/proto/OrderService.proto`
   - Options
     | Option | Description |
     |----|----|
     | `java_multiple_files` | <li>If `true`, it would generate multiple stub files for different message types.<li>If `false`, it would generate only one stub file including all the message types. |
     | `java_package` | <li>The packake path of where you want to put your stub file (You need to manually move the stub file to this package path after generating it) |
     | `java_outer_classname` | <li>The name of the stub file. |
     
- Command for generating stub
  `./gradlew :ftgo-order-service:generateProto`
